CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    roles VARCHAR(255) NOT NULL,
    expiry_date DATE,
    password_attempt INTEGER NOT NULL DEFAULT 0,
    enabled BOOLEAN NOT NULL,
    expired BOOLEAN NOT NULL,
    account_locked BOOLEAN NOT NULL,
    credential_locked BOOLEAN NOT NULL
);