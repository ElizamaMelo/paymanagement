CREATE TABLE payment (
    id BIGINT PRIMARY KEY,
    amount DOUBLE PRECISION NOT NULL,
    payment_date DATE NOT NULL,
    description VARCHAR(255),
    user_id BIGINT NOT NULL
);

CREATE TABLE card (
    id BIGINT PRIMARY KEY,
    card_number VARCHAR(20) NOT NULL,
    cvv VARCHAR(4) NOT NULL,
    owner_name VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL,
    expiration_date DATE NOT NULL
);

-- Insert data into the `payment` table
INSERT INTO payment (id, amount, payment_date, description, user_id) VALUES
(1, 100.50, '2024-08-01', 'Payment for Order #12345', 101),
(2, 200.75, '2024-08-15', 'Payment for Order #12346', 102),
(3, 50.00, '2024-08-20', 'Payment for Subscription Renewal', 103),
(4, 75.25, '2024-08-22', 'Payment for Order #12347', 104),
(5, 300.00, '2024-08-25', 'Payment for Electronics Purchase', 105);

-- Insert data into the `card` table
INSERT INTO card (id, card_number, cvv, owner_name, user_id, expiration_date) VALUES
(1, '1234567812345678', '123', 'John Doe', 101, '2025-12-31'),
(2, '8765432187654321', '456', 'Jane Smith', 102, '2026-01-31'),
(3, '1111222233334444', '789', 'Alice Brown', 103, '2025-06-30'),
(4, '5555666677778888', '321', 'Bob White', 104, '2026-03-31'),
(5, '9999000011112222', '654', 'Charlie Black', 105, '2025-09-30');