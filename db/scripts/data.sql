-- Insert data into roles table
INSERT INTO roles (role)
VALUES ('admin'),
       ('user'),
       ('courier');

-- Insert data into persons table
INSERT INTO persons (username, password, email, active, mobile_number, role_id)
VALUES ('admin1', 'password', 'admin1@example.com', true, '1234567890', 1),
       ('user1', 'password', 'user1@example.com', true, '9876543210', 2),
       ('courier1', 'password', 'courier1@example.com', true, '5555555555', 3),
       ('courier2', 'password', 'courier2@example.com', true, '52352', 3);

-- Insert data into admins table
INSERT INTO admins (id, superadmin)
VALUES (1, true);

-- Insert data into users table
INSERT INTO users (id, vip)
VALUES (2, false);

-- Insert data into courier_statuses table
INSERT INTO courier_statuses (status)
VALUES ('Available'),
       ('In Transit'),
       ('Coffe Break');

-- Insert data into couriers table
INSERT INTO couriers (id, courier_status_id)
VALUES (3, 2),
       (4, 2);

-- Insert data into parcels table
INSERT INTO parcels (user_id, item_name)
VALUES (2, 'Electronics'),
       (2, 'Clothing'),
       (2, 'Food');

-- Insert data into order_statuses table
INSERT INTO order_statuses (status)
VALUES ('Processing'),
       ('In Transit'),
       ('Delivered');

-- Insert data into order_location table
INSERT INTO order_location (start_location, finish_location, current_location)
VALUES (POINT(40.7128, -74.0060), POINT(41.8781, -87.6298), POINT(40.7128, -74.0060)),
       (POINT(34.0522, -118.2437), null, null),
       (POINT(1, -12), POINT(34.0522, -118.2437), POINT(32, -114));

-- Insert data into order_status_history table
INSERT INTO order_status_history (agent_id, status_id, change_time)
VALUES (1, 1, '2024-01-29 12:30:00'),
       (3, 2, '2024-01-29 14:00:00'),
       (1, 3, '2024-01-29 15:00:00');

-- Insert data into orders table
INSERT INTO orders (parcel_id, courier_id, order_status_history_id, order_location_id, created_time, start_time,
                    finish_time)
VALUES (1, 3, 1, 1, '2024-01-29 12:00:00', null, null),
       (2, 4, 2, 2, '2024-01-29 13:30:00', null, null),
       (3, 4, 3, 3, '2024-01-29 14:30:00', '2024-01-29 14:31:00', '2024-01-29 14:35:00');

