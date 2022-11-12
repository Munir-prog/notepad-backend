--liquibase formatted sql

--changeset mzikrullaev:1
CREATE TABLE IF NOT EXISTS note
(
    id BIGSERIAL PRIMARY KEY ,
    tittle VARCHAR(255) NOT NULL ,
    text TEXT ,
    created_at TIMESTAMP NOT NULL ,
    updated_at TIMESTAMP ,
    deleted INT NOT NULL ,
    user_id BIGINT REFERENCES users (id)
);


