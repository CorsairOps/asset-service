-- Fleets
INSERT INTO fleets (id, name, description)
VALUES
    (1, 'Atlantic Fleet', 'Primary naval fleet operating in the Atlantic Ocean.'),
    (2, 'Pacific Fleet', 'Primary naval fleet operating in the Pacific Ocean.'),
    (3, 'Drone Squadron Alpha', 'Specialized drone operations unit.'),
    (4, 'Rapid Response Ground Unit', 'Ground vehicle unit for quick deployment.');

-- Assets
INSERT INTO assets (id, name, type, status, fleet_id)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'USS Alpha', 'SHIP', 'ACTIVE', 1),
    ('22222222-2222-2222-2222-222222222222', 'USS Bravo', 'SHIP', 'MAINTENANCE', 1),
    ('33333333-3333-3333-3333-333333333333', 'USS Charlie', 'SUBMARINE', 'ACTIVE', 2),
    ('44444444-4444-4444-4444-444444444444', 'Drone 01', 'DRONE', 'ACTIVE', 3),
    ('55555555-5555-5555-5555-555555555555', 'Drone 02', 'DRONE', 'INACTIVE', 3),
    ('66666666-6666-6666-6666-666666666666', 'F-18 Hornet 01', 'AIRCRAFT', 'ACTIVE', 2),
    ('77777777-7777-7777-7777-777777777777', 'F-18 Hornet 02', 'AIRCRAFT', 'MAINTENANCE', 2),
    ('88888888-8888-8888-8888-888888888888', 'Humvee 01', 'GROUND_VEHICLE', 'ACTIVE', 4),
    ('99999999-9999-9999-9999-999999999999', 'Humvee 02', 'GROUND_VEHICLE', 'INACTIVE', 4);