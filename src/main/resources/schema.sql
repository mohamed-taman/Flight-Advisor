-- Dropping tables
DROP TABLE IF EXISTS route;
DROP TABLE IF EXISTS airport;
DROP TABLE IF EXISTS city_comment;
DROP TABLE IF EXISTS city;
DROP TABLE IF EXISTS country;
DROP TABLE IF EXISTS user;

-- Start - User table definition

CREATE TABLE IF NOT EXISTS user
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_uuid  UUID UNIQUE             NOT NULL,
    first_name VARCHAR(100)            NOT NULL,
    last_name  VARCHAR(100)            NOT NULL,
    type       ENUM ('ADMIN','CLIENT') NOT NULL DEFAULT 'Client',
    username   VARCHAR(150) UNIQUE     NOT NULL,
    passkey    VARCHAR(255)            NOT NULL
);
-- End - User table definition

-- Start - Country, City, airports, and routes tables definition
CREATE TABLE IF NOT EXISTS country
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS city
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(100) NOT NULL,
    country_id  INT          NOT NULL,
    CONSTRAINT city_country_fk
        FOREIGN KEY (country_id)
            REFERENCES country (id)
);

CREATE TABLE IF NOT EXISTS city_comment
(
    id          INT AUTO_INCREMENT NOT NULL,
    city_id     INT                NOT NULL,
    user_id     INT                NOT NULL,
    description VARCHAR(1000)      NOT NULL,
    created_at  DATETIME           NOT NULL DEFAULT now(),
    updated_on  DATETIME,

    CONSTRAINT city_comment_pk
        PRIMARY KEY (id, city_id),

    CONSTRAINT comment_city_fk
        FOREIGN KEY (city_id)
            REFERENCES city (id),

    CONSTRAINT comment_user_fk
        FOREIGN KEY (user_id)
            REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS airport
(
    airport_id  INT(5) PRIMARY KEY                 NOT NULL,
    name        VARCHAR(255)                       NOT NULL,
    city_id     INT                                NOT NULL,
    country_id  INT                                NOT NULL,
    city        VARCHAR(100)                       NOT NULL,
    country     VARCHAR(100)                       NOT NULL,
    iata        VARCHAR(3),
    icao        VARCHAR(4),
    latitude    DECIMAL(12, 6)                     NOT NULL,
    longitude   DECIMAL(12, 6)                     NOT NULL,
    altitude    INT(6),
    timezone    INT(2),
    dst         ENUM ('E','A','S','O','Z','N','U') NOT NULL,
    tz          VARCHAR(50),
    type        VARCHAR(50)                        NOT NULL,
    data_source VARCHAR(255)                       NOT NULL,

    CONSTRAINT airport_city_fk
        FOREIGN KEY (city_id)
            REFERENCES city (id),

    CONSTRAINT airport_country_fk
        FOREIGN KEY (country_id)
            REFERENCES country (id)
);

COMMENT ON COLUMN airport.airport_id IS 'Identifier for this airport.';
COMMENT ON COLUMN airport.name IS 'Name of airport.';
-- COMMENT ON COLUMN airport.city_id IS 'Main city served by airport.';
-- COMMENT ON COLUMN airport.country_id IS 'Country or territory where airport is located.';
COMMENT ON COLUMN airport.iata IS '3-letter IATA code. Null if not assigned/unknown.';
COMMENT ON COLUMN airport.icao IS '4-letter ICAO code. Null if not assigned.';
COMMENT ON COLUMN airport.latitude IS 'Decimal degrees, usually to six significant digits.';
COMMENT ON COLUMN airport.longitude IS 'Decimal degrees, usually to six significant digits.';
COMMENT ON COLUMN airport.altitude IS 'In feet.';
COMMENT ON COLUMN airport.timezone IS 'Hours offset from UTC.';
COMMENT ON COLUMN airport.dst IS 'Daylight savings time. One of E (Europe), A (US/Canada), S (South America), O(Australia), Z (New Zealand), N (None) or U (Unknown).';
COMMENT ON COLUMN airport.tz IS 'Timezone in "tz" (Olson) format, eg. "America/Los_Angeles".';
COMMENT ON COLUMN airport.type IS 'Type of the airport.';
COMMENT ON COLUMN airport.data_source IS 'Source of this data.';


CREATE TABLE IF NOT EXISTS route
(
    id                     INT AUTO_INCREMENT PRIMARY KEY,
    airline_code           VARCHAR(3),
    airline_id             INT(5),
    source_airport         VARCHAR(4),
    source_airport_id      INT(4),
    destination_airport    VARCHAR(4),
    destination_airport_id INT(4),
    code_share             BOOLEAN,
    stops                  INT(3),
    equipment              VARCHAR(100),
    price                  DECIMAL(6, 3),

    CONSTRAINT route_source_airport_fk
        FOREIGN KEY (source_airport_id)
            REFERENCES airport (airport_id),

    CONSTRAINT route_destination_airport_fk
        FOREIGN KEY (destination_airport_id)
            REFERENCES airport (airport_id)
);

COMMENT ON COLUMN route.airline_code IS '2-letter (IATA) or 3-letter (ICAO) code of the airline.';
COMMENT ON COLUMN route.airline_id IS 'Identifier for airline.';
COMMENT ON COLUMN route.source_airport IS '3-letter (IATA) or 4-letter (ICAO) code of the source airport.';
COMMENT ON COLUMN route.source_airport_id IS 'Identifier for source airport.';
COMMENT ON COLUMN route.destination_airport IS '3-letter (IATA) or 4-letter (ICAO) code of the destination airport.';
COMMENT ON COLUMN route.destination_airport_id IS 'Unique OpenFlights identifier for destination airport.';
COMMENT ON COLUMN route.code_share IS '"Y" if this flight is a code-share, empty otherwise.';
COMMENT ON COLUMN route.stops IS 'Number of stops on this flight ("0" for direct).';
COMMENT ON COLUMN route.equipment IS '3-letter codes for plane type(s) generally used on this flight, separated by spaces.';
COMMENT ON COLUMN route.price IS 'Flight cost';

-- End - Country, City, airports, and routes tables definition