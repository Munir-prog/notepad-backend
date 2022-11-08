--liquibase formatted sql

--changeset mzikrullaev:1
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY ,
    email VARCHAR(128) NOT NULL UNIQUE ,
    password VARCHAR(256) ,
    lastname VARCHAR(128) NOT NULL ,
    firstname VARCHAR(128) NOT NULL ,
    birthday TIMESTAMP ,
    phone VARCHAR(32) UNIQUE
);

--changeset mzikrullaev:2
CREATE TABLE IF NOT EXISTS role
(
    id SERIAL PRIMARY KEY ,
    role VARCHAR(16) UNIQUE NOT NULL
);

--changeset mzikrullaev:3
CREATE TABLE IF NOT EXISTS users_role
(
    user_id BIGINT NOT NULL REFERENCES users (id),
    role_id INT NOT NULL REFERENCES role (id),
    UNIQUE (user_id, role_id)
);

