---- Inserting data
------ Add an admin user
------ Password is: Admin1234
INSERT INTO USERS (user_uuid, first_name, last_name, username, password, enabled)
VALUES (RANDOM_UUID(), 'Mohamed', 'Taman', 'admin@traveladvisor.com',
        '$2a$10$G2MD5wiMcpXh2oIwqJJKX.HxEXyBbR2dHxjgFii/aNwtg49G2C7cK',true);

------ Add user authorities
INSERT INTO AUTHORITY (user_id, authority) VALUES (1,'ADMIN');
INSERT INTO AUTHORITY (user_id, authority) VALUES (1,'CLIENT');