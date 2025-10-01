-- Fleets
INSERT INTO fleets (id, name, description)
VALUES
    (1, 'Atlantic Fleet', 'Primary naval fleet operating in the Atlantic Ocean.'),
    (2, 'Pacific Fleet', 'Primary naval fleet operating in the Pacific Ocean.'),
    (3, 'Drone Squadron Alpha', 'Specialized drone operations unit.'),
    (4, 'Rapid Response Ground Unit', 'Ground vehicle unit for quick deployment.');

-- Assets
INSERT INTO assets (id, name, type, status)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'USS Alpha', 'SHIP', 'ACTIVE'),
    ('22222222-2222-2222-2222-222222222222', 'USS Bravo', 'SHIP', 'MAINTENANCE'),
    ('33333333-3333-3333-3333-333333333333', 'USS Charlie', 'SUBMARINE', 'ACTIVE'),
    ('44444444-4444-4444-4444-444444444444', 'Drone 01', 'DRONE', 'ACTIVE'),
    ('55555555-5555-5555-5555-555555555555', 'Drone 02', 'DRONE', 'INACTIVE'),
    ('66666666-6666-6666-6666-666666666666', 'F-18 Hornet 01', 'AIRCRAFT', 'ACTIVE'),
    ('77777777-7777-7777-7777-777777777777', 'F-18 Hornet 02', 'AIRCRAFT', 'MAINTENANCE'),
    ('88888888-8888-8888-8888-888888888888', 'Humvee 01', 'GROUND_VEHICLE', 'ACTIVE'),
    ('99999999-9999-9999-9999-999999999999', 'Humvee 02', 'GROUND_VEHICLE', 'INACTIVE');

-- Fleet Assets (assigning assets to fleets)
INSERT INTO fleet_assets (asset_id, fleet_id)
VALUES
    ('11111111-1111-1111-1111-111111111111', 1),  -- USS Alpha → Atlantic Fleet
    ('22222222-2222-2222-2222-222222222222', 1),  -- USS Bravo → Atlantic Fleet
    ('33333333-3333-3333-3333-333333333333', 2),  -- USS Charlie → Pacific Fleet
    ('44444444-4444-4444-4444-444444444444', 3),  -- Drone 01 → Drone Squadron Alpha
    ('55555555-5555-5555-5555-555555555555', 3),  -- Drone 02 → Drone Squadron Alpha
    ('66666666-6666-6666-6666-666666666666', 2),  -- F-18 Hornet 01 → Pacific Fleet
    ('77777777-7777-7777-7777-777777777777', 2),  -- F-18 Hornet 02 → Pacific Fleet
    ('88888888-8888-8888-8888-888888888888', 4),  -- Humvee 01 → Rapid Response Ground Unit
    ('99999999-9999-9999-9999-999999999999', 4);  -- Humvee 02 → Rapid Response Ground Unit