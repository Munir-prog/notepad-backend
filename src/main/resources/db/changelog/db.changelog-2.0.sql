--liquibase formatted sql

--changeset mzikrullaev:1
ALTER TABLE role
    RENAME COLUMN role TO name;

