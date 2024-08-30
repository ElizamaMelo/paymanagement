-- Insert data into the `card` table using nextval for the ID
INSERT INTO card (id, card_number, cvv, owner_name, user_id, expiration_date) VALUES
(nextval('card_sequence'), '1234567812345678', '123', 'John Doe', 101, '2025-12-31'),
(nextval('card_sequence'), '8765432187654321', '456', 'Jane Smith', 102, '2026-01-31'),
(nextval('card_sequence'), '1111222233334444', '789', 'Alice Brown', 103, '2025-06-30'),
(nextval('card_sequence'), '5555666677778888', '321', 'Bob White', 104, '2026-03-31'),
(nextval('card_sequence'), '9999000011112222', '654', 'Charlie Black', 105, '2025-09-30');

-- Insert data into the `payment` table using nextval for the ID and subquery to get the card_id
INSERT INTO payment (id, amount, payment_date, description, user_id, card_id) VALUES
(nextval('payment_sequence'), 100.50, '2024-08-01', 'Payment for Order #12345', 101, (SELECT id FROM card WHERE card_number = '1234567812345678')),
(nextval('payment_sequence'), 200.75, '2024-08-15', 'Payment for Order #12346', 102, (SELECT id FROM card WHERE card_number = '8765432187654321')),
(nextval('payment_sequence'), 50.00, '2024-08-20', 'Payment for Subscription Renewal', 103, (SELECT id FROM card WHERE card_number = '1111222233334444')),
(nextval('payment_sequence'), 75.25, '2024-08-22', 'Payment for Order #12347', 104, (SELECT id FROM card WHERE card_number = '5555666677778888')),
(nextval('payment_sequence'), 300.00, '2024-08-25', 'Payment for Electronics Purchase', 105, (SELECT id FROM card WHERE card_number = '9999000011112222'));