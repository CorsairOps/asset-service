package com.corsairops.assetservice;

import com.corsairops.assetservice.dto.AssetLocationRequest;
import com.corsairops.assetservice.dto.AssetRequest;
import com.corsairops.assetservice.dto.AssetResponse;
import com.corsairops.assetservice.model.AssetStatus;
import com.corsairops.assetservice.model.AssetType;
import com.corsairops.assetservice.repository.AssetRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {KafkaAutoConfiguration.class})
public class AssetServiceIntegrationTests {
    private static final List<AssetRequest> bodies = List.of(
            new AssetRequest("USS New Boat 5", AssetType.SHIP, AssetStatus.ACTIVE, -180.0, -90.0),
            new AssetRequest("USS Another Boat", AssetType.SHIP, AssetStatus.ACTIVE, 45.0, 45.0),
            new AssetRequest("Cargo Master 3000", AssetType.SUBMARINE, AssetStatus.MAINTENANCE, 100.0, 20.0)
    );

    @LocalServerPort
    private int port;

    @Autowired
    private AssetRepository assetRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterEach
    void cleanUp() {
        assetRepository.deleteAll();
    }

    @Test
    void givenValidRequest_createAsset_shouldReturnCreatedAsset() {
        createAsset(getSampleRequestBody(0));
    }

    @Test
    void givenDuplicatedRequest_createAsset_shouldReturnConflict() {
        createAsset(getSampleRequestBody(0));

        given()
                .contentType("application/json")
                .body(getSampleRequestBody(0))
                .when()
                .post("/api/assets")
                .then()
                .statusCode(409);
    }

    @Test
    void givenUnuniqueName_updateAsset_shouldReturnConflict() {
        // Create first asset
        createAsset(getSampleRequestBody(0));

        // Create second asset
        String id2 = createAsset(getSampleRequestBody(1)).id().toString();

        // Attempt to update second asset with the name of the first asset
        var initialRequest = getSampleRequestBody(1);
        AssetRequest updateRequest = new AssetRequest(getSampleRequestBody(0).name(), initialRequest.type(), initialRequest.status(), initialRequest.longitude(), initialRequest.latitude());

        given()
                .contentType("application/json")
                .body(updateRequest)
                .when()
                .put("/api/assets/" + id2)
                .then()
                .statusCode(409);
    }

    @Test
    void givenUniqueName_updateAsset_shouldReturnUpdatedAsset() {
        // Create first asset
        String id = createAsset(getSampleRequestBody(0)).id().toString();

        var initialRequest = getSampleRequestBody(0);
        AssetRequest updateRequest = new AssetRequest("New Unique Name", initialRequest.type(), initialRequest.status(), initialRequest.longitude(), initialRequest.latitude());

        updateAsset(updateRequest, id);
    }

    @Test
    void givenInvalidId_deleteAsset_shouldReturnNotFound() {
        given()
                .when()
                .delete("/api/assets/26316e73-fa98-472e-b6d2-a58eac6678da")
                .then()
                .statusCode(404);
    }

    @Test
    void givenValidId_deleteAsset_shouldReturnNoContent() {
        String id = createAsset(getSampleRequestBody(0)).id().toString();
        given()
                .when()
                .delete("/api/assets/" + id)
                .then()
                .statusCode(204);
    }

    @Test
    void givenTwoLocations_getAssetLocations_shouldReturnLocationsInDescendingOrder() {
        // Create first asset
        AssetResponse asset = createAsset(getSampleRequestBody(0));
        String id = String.valueOf(asset.id());

        // Update asset location
        var initialRequest = getSampleRequestBody(0);
        AssetRequest updateRequest = new AssetRequest(initialRequest.name(), initialRequest.type(), initialRequest.status(), 10.0, 10.0);

        updateAsset(updateRequest, id);

        // Get asset locations
        given()
                .when()
                .get("/api/assets/" + id + "/locations")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2))
                .body("[0].longitude", equalTo(10.0F))
                .body("[0].latitude", equalTo(10.0F))
                .body("[1].longitude", equalTo(-180.0F))
                .body("[1].latitude", equalTo(-90.0F));
    }

    @Test
    public void whenGetAllAssets_thenAssetsAreReturned() {
        addAllSampleAssets();
        given()
                .when()
                .get("/api/assets")
                .then()
                .statusCode(200)
                .body("size()", equalTo(bodies.size()));
    }

    @Test
    public void givenInvalidId_whenGetAssetById_thenNotFound() {
        given()
                .when()
                .get("/api/assets/26316e73-fa98-472e-b6d2-a58eac6678da")
                .then()
                .statusCode(404);
    }

    @Test
    public void givenValidId_whenGetAssetById_thenAssetIsReturned() {
        List<AssetResponse> assets = addAllSampleAssets();
        String id = String.valueOf(assets.getFirst().id());
        given()
                .when().get("/api/assets/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("name", equalTo(bodies.getFirst().name()))
                .body("type", equalTo(bodies.getFirst().type().toString()))
                .body("status", equalTo(bodies.getFirst().status().toString()))
                .body("longitude", equalTo(bodies.getFirst().longitude().floatValue()))
                .body("latitude", equalTo(bodies.getFirst().latitude().floatValue()));
    }

    @Test
    void whenGetAssetCount_thenReturnCorrectCount() {
        addAllSampleAssets();
        given()
                .when()
                .get("/api/assets/count")
                .then()
                .statusCode(200)
                .body(equalTo(String.valueOf(bodies.size())));
    }

    @Test
    void whenChangeAssetLocation_thenChangeLocation() {
        AssetResponse asset = createAsset(getSampleRequestBody(0));
        String id = String.valueOf(asset.id());

        AssetLocationRequest locationRequest = new AssetLocationRequest(UUID.fromString(id), 50.0, 50.0);

        given()
                .contentType("application/json")
                .body(locationRequest)
                .when()
                .put("/api/assets/{assetId}/locations", id)
                .then()
                .statusCode(204);

        // Verify changed
        given()
                .when()
                .get("/api/assets/{assetId}", id)
                .then()
                .statusCode(200)
                .body("longitude", equalTo(50.0F))
                .body("latitude", equalTo(50.0F));
    }

    @Test
    void givenInvalidRequest_whenChangeAssetLocation_thenBadRequest() {
        AssetResponse asset = createAsset(getSampleRequestBody(0));
        String id = String.valueOf(asset.id());

        AssetLocationRequest locationRequest = new AssetLocationRequest(UUID.fromString(id), -91.0, 181.0);

        given()
                .contentType("application/json")
                .body(locationRequest)
                .when()
                .put("/api/assets/{assetId}/locations", id)
                .then()
                .statusCode(400);
    }

    private List<AssetResponse> addAllSampleAssets() {
        return bodies.stream().map(this::createAsset).toList();
    }

    private AssetResponse updateAsset(AssetRequest request, String id) {
        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .put("/api/assets/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("name", equalTo(request.name()))
                .body("type", equalTo(request.type().toString()))
                .body("status", equalTo(request.status().toString()))
                .body("longitude", equalTo(request.longitude().floatValue()))
                .body("latitude", equalTo(request.latitude().floatValue()))
                .extract().as(AssetResponse.class);
    }

    private AssetResponse createAsset(AssetRequest request) {
        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/assets")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo(request.name()))
                .body("type", equalTo(request.type().toString()))
                .body("status", equalTo(request.status().toString()))
                .body("longitude", equalTo(request.longitude().floatValue()))
                .body("latitude", equalTo(request.latitude().floatValue()))
                .extract().as(AssetResponse.class);
    }

    private AssetRequest getSampleRequestBody(int index) {
        return bodies.get(index);
    }
}