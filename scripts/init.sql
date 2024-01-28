-- Create roles table
CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    role VARCHAR(255) NOT NULL
);
-- Create persons table
CREATE TABLE persons
(
    id            UUID PRIMARY KEY,
    username      VARCHAR(255),
    password      VARCHAR(255),
    email         VARCHAR(255),
    active        BOOLEAN,
    mobile_number INT,
    role_id       INT REFERENCES roles (id)
);

-- Create admins table
CREATE TABLE admins
(
    id         UUID PRIMARY KEY REFERENCES persons (id),
    superadmin BOOLEAN
);
-- Create users table
CREATE TABLE users
(
    id  UUID PRIMARY KEY REFERENCES persons (id),
    vip BOOLEAN
);
-- Create courier_statuses table
CREATE TABLE courier_statuses
(
    id     SERIAL PRIMARY KEY,
    status VARCHAR(255) NOT NULL
);

-- Create couriers table
CREATE TABLE couriers
(
    id                UUID PRIMARY KEY REFERENCES persons (id),
    courier_status_id INT,
    FOREIGN KEY (courier_status_id) REFERENCES courier_statuses (id)
);

-- Create parcels table
CREATE TABLE parcels
(
    id        UUID PRIMARY KEY,
    user_id   UUID,
    item_name VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id)

);
-- Create order_statuses table
CREATE TABLE order_statuses
(
    id     SERIAL PRIMARY KEY,
    status VARCHAR(255) NOT NULL
);

-- Create orders table
CREATE TABLE orders
(
    id                      UUID PRIMARY KEY,
    parcel_id               UUID ,
    courier_id              UUID,
    created_time            TIMESTAMP,
    start_time              TIMESTAMP,
    finish_time             TIMESTAMP,
    FOREIGN KEY (parcel_id) REFERENCES parcels (id),
    FOREIGN KEY (courier_id) REFERENCES couriers (id)
);

-- Create order_location table
CREATE TABLE order_location
(
    id               UUID PRIMARY KEY,
    order_id         UUID,
    start_location   POINT,
    finish_location  POINT,
    coordinates      POINT[],
    current_location POINT,
    FOREIGN KEY (order_id) REFERENCES orders (id)

);

-- Create order_status_history table
CREATE TABLE order_status_history
(
    id          UUID PRIMARY KEY,
    order_id    UUID,
    agent_id    UUID,
    status_id   INT,
    change_time TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (agent_id) REFERENCES persons (id),
    FOREIGN KEY (status_id) REFERENCES order_statuses (id)

);

