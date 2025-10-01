-- Insert assets
INSERT INTO assets (id, name, type, status, longitude, latitude)
VALUES
    ('a1111111-1111-1111-1111-111111111111', 'USS Enterprise', 'SHIP', 'ACTIVE', -74.0059, 40.7128),   -- NYC
    ('a2222222-2222-2222-2222-222222222222', 'USS Ohio', 'SUBMARINE', 'MAINTENANCE', -122.4194, 37.7749), -- SF
    ('a3333333-3333-3333-3333-333333333333', 'F-35 Lightning II', 'AIRCRAFT', 'ACTIVE', -77.0369, 38.9072), -- DC
    ('a4444444-4444-4444-4444-444444444444', 'MQ-9 Reaper', 'DRONE', 'ACTIVE', -80.1918, 25.7617), -- Miami
    ('a5555555-5555-5555-5555-555555555555', 'Bradley IFV', 'GROUND_VEHICLE', 'INACTIVE', -95.3698, 29.7604); -- Houston

-- Insert asset location history (with multiple movements)
INSERT INTO asset_locations (asset_id, longitude, latitude, timestamp)
VALUES
    -- USS Enterprise (moves up the coast)
    ('a1111111-1111-1111-1111-111111111111', -75.1652, 39.9526, NOW() - INTERVAL '2 days'), -- Philadelphia
    ('a1111111-1111-1111-1111-111111111111', -74.0059, 40.7128, NOW()),                     -- NYC (current)

    -- USS Ohio (static near SF, in maintenance)
    ('a2222222-2222-2222-2222-222222222222', -122.4194, 37.7749, NOW()), -- San Francisco (current)

    -- F-35 (flew around DC)
    ('a3333333-3333-3333-3333-333333333333', -76.6122, 39.2904, NOW() - INTERVAL '1 day'), -- Baltimore
    ('a3333333-3333-3333-3333-333333333333', -77.0369, 38.9072, NOW()),                   -- DC (current)

    -- MQ-9 Reaper (patrol route near Miami)
    ('a4444444-4444-4444-4444-444444444444', -80.2264, 25.7889, NOW() - INTERVAL '3 hours'),
    ('a4444444-4444-4444-4444-444444444444', -80.1918, 25.7617, NOW()),                   -- Miami (current)

    -- Bradley IFV (inactive, last seen in Houston)
    ('a5555555-5555-5555-5555-555555555555', -95.3698, 29.7604, NOW()); -- Houston (current)