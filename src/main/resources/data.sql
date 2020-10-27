DROP TABLE IF EXISTS customer;

CREATE TABLE customer
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name  VARCHAR(250) NOT NULL,
    balance    DECIMAL(10, 2) DEFAULT 0.0
);

INSERT INTO customer (first_name, last_name)
VALUES ('Mohamed', 'Taman'),
       ('Issa', 'Ahmed'),
       ('Matin', 'Abbasi'),
       ('Milica', 'Jovicic'),
       ('Angèl', 'Wijnhard'),
       ('Fatih', 'Akbas'),
       ('Ibrahim', 'Moustafa');