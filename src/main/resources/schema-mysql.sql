USE `library-db`;

create table if not exists patron_phonenumbers (
    patron_id INTEGER,
    type VARCHAR(50),
    number VARCHAR(50)
    );

create table if not exists patrons (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    patron_id VARCHAR(36),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email_address VARCHAR(50),
    contact_method_preference VARCHAR(50),
    street_address VARCHAR (50),
    city VARCHAR (50),
    province VARCHAR (50),
    country VARCHAR (50),
    postal_code VARCHAR (9)
    );

create table if not exists books (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    isbn BIGINT UNIQUE,
    catalog_id VARCHAR(36),
    title VARCHAR(255),
    collection VARCHAR(255),
    edition VARCHAR(255),
    publisher VARCHAR(255),
    synopsis VARCHAR(500),
    language VARCHAR(50),
    status VARCHAR(50),
    first_name VARCHAR(50),
    last_name VARCHAR(50)
    );

create table if not exists catalogs (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    catalog_id VARCHAR(36),
    type VARCHAR(50),
    size INTEGER
    );