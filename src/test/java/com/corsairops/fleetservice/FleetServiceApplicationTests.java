package com.corsairops.fleetservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class FleetServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}