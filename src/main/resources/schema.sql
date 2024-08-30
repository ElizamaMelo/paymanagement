CREATE SEQUENCE card_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE payment_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS payment (
    id BIGINT PRIMARY KEY,
    amount DOUBLE PRECISION NOT NULL,
    payment_date DATE NOT NULL,
    description VARCHAR(255),
    user_id BIGINT NOT NULL,
    card_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS card (
    id BIGINT PRIMARY KEY,
    card_number VARCHAR(20) NOT NULL,
    cvv VARCHAR(4) NOT NULL,
    owner_name VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL,
    expiration_date DATE NOT NULL
);