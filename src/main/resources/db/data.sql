---- Inserting data
------ Add an admin user
------ Password is: Admin1234
INSERT INTO USER (user_uuid, first_name, last_name, username, password, enabled)
VALUES (RANDOM_UUID(), 'Mohamed', 'Taman', 'mohamed.taman@gmail.com',
        '$2a$10$G2MD5wiMcpXh2oIwqJJKX.HxEXyBbR2dHxjgFii/aNwtg49G2C7cK',true);

------ Add user authorities
INSERT INTO AUTHORITY (user_id, authority) VALUES (1,'ADMIN');
INSERT INTO AUTHORITY (user_id, authority) VALUES (1,'CLIENT');

------ Add a country
INSERT INTO COUNTRY (NAME)
VALUES ('Egypt');

------ Add a city
INSERT INTO CITY (COUNTRY_ID, NAME, DESCRIPTION)
VALUES (1, 'Cairo', 'A great city to visit 1');

------ Add a city comments
INSERT INTO CITY_COMMENT (CITY_ID, DESCRIPTION, USER_ID)
VALUES (1, 'Nice city to visit 1', 1);
INSERT INTO CITY_COMMENT (CITY_ID, DESCRIPTION, USER_ID)
VALUES (1, 'Great city to visit 2', 1);
INSERT INTO CITY_COMMENT (CITY_ID, DESCRIPTION, USER_ID)
VALUES (1, 'Great city to visit 3', 1);
INSERT INTO CITY_COMMENT (CITY_ID, DESCRIPTION, USER_ID)
VALUES (1, 'Great city to visit 4', 1);
INSERT INTO CITY_COMMENT (CITY_ID, DESCRIPTION, USER_ID)
VALUES (1, 'Great city to visit 5', 1);
INSERT INTO CITY_COMMENT (CITY_ID, DESCRIPTION, USER_ID)
VALUES (1, 'Great city to visit 6', 1);
INSERT INTO CITY_COMMENT (CITY_ID, DESCRIPTION, USER_ID)
VALUES (1, 'Great city to visit 7', 1);
INSERT INTO CITY_COMMENT (CITY_ID, DESCRIPTION, USER_ID)
VALUES (1, 'Great city to visit 8', 1);
INSERT INTO CITY_COMMENT (CITY_ID, DESCRIPTION, USER_ID)
VALUES (1, 'Great city to visit 9', 1);
INSERT INTO CITY_COMMENT (CITY_ID, DESCRIPTION, USER_ID)
VALUES (1, 'Great city to visit 10', 1);
INSERT INTO CITY_COMMENT (CITY_ID, DESCRIPTION, USER_ID)
VALUES (1, 'Great city to visit 11', 1);
INSERT INTO CITY_COMMENT (CITY_ID, DESCRIPTION, USER_ID)
VALUES (1, 'Great city to visit 12', 1);
INSERT INTO CITY_COMMENT (CITY_ID, DESCRIPTION, USER_ID)
VALUES (1, 'Great city to visit 13', 1);
INSERT INTO CITY_COMMENT (CITY_ID, DESCRIPTION, USER_ID)
VALUES (1, 'Great city to visit 14', 1);

------ Add airports
INSERT INTO AIRPORT( AIRPORT_ID, NAME, CITY_ID, COUNTRY_ID, CITY, COUNTRY
                   , IATA, ICAO, LATITUDE, LONGITUDE, ALTITUDE, TIMEZONE, DST, TZ, TYPE
                   , DATA_SOURCE)
VALUES (2965, 'Goroka Airport', 1, 1, 'Goroka', 'Papua New Guinea', 'GKA', 'AYGA',
        -6.081689834590001, 145.391998291, 5282, 10, 'U', 'Pacific/Port_Moresby', 'airport',
        'OurAirports');

INSERT INTO AIRPORT( AIRPORT_ID, NAME, CITY_ID, COUNTRY_ID, CITY, COUNTRY, IATA
                   , ICAO, LATITUDE, LONGITUDE, ALTITUDE, TIMEZONE, DST, TZ, TYPE, DATA_SOURCE)
VALUES (2990, 'Goroka Airport', 1, 1, 'Goroka', 'Papua New Guinea', 'GKA', 'AYGA',
        -6.081689834590001, 145.391998291, 5282, 10, 'U', 'Pacific/Port_Moresby', 'airport',
        'OurAirports');

INSERT INTO AIRPORT( AIRPORT_ID, NAME, CITY_ID, COUNTRY_ID, CITY, COUNTRY, IATA
                   , ICAO, LATITUDE, LONGITUDE, ALTITUDE, TIMEZONE, DST, TZ, TYPE, DATA_SOURCE)
VALUES (4029, 'Goroka Airport', 1, 1, 'Goroka', 'Papua New Guinea', 'GKA', 'AYGA',
        -6.081689834590001, 145.391998291, 5282, 10, 'U', 'Pacific/Port_Moresby', 'airport',
        'OurAirports');

------ Add routes
INSERT INTO ROUTE( AIRLINE_CODE, AIRLINE_ID, SOURCE_AIRPORT, SOURCE_AIRPORT_ID
                 , DESTINATION_AIRPORT, DESTINATION_AIRPORT_ID, CODE_SHARE, STOPS, EQUIPMENT, PRICE)
VALUES ('2B', 410, 'AER', 2965, 'KZN', 2990, 0, 0, 'CR2', 95.87);

INSERT INTO ROUTE( AIRLINE_CODE, AIRLINE_ID, SOURCE_AIRPORT, SOURCE_AIRPORT_ID
                 , DESTINATION_AIRPORT, CODE_SHARE, STOPS, EQUIPMENT, PRICE)
VALUES ('2B', 410, 'DME', 4029, 'TGK', 0, 0, 'CR2', 98.25);