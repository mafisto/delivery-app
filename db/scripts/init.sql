-- Create roles table
CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    role VARCHAR(255) NOT NULL
);
-- Create persons table
CREATE TABLE persons
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    active        BOOLEAN,
    mobile_number VARCHAR(255) NOT NULL,
    role_id       INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Create admins table
CREATE TABLE admins
(
    id         INT PRIMARY KEY REFERENCES persons (id),
    superadmin BOOLEAN
);
-- Create users table
CREATE TABLE users
(
    id  INT PRIMARY KEY REFERENCES persons (id),
    vip BOOLEAN
);
-- Create courier_statuses table
CREATE TABLE courier_statuses
(
    id     SERIAL PRIMARY KEY,
    status VARCHAR(255) NOT NULL
);


-- Create parcels table
CREATE TABLE parcels
(
    id        SERIAL PRIMARY KEY,
    user_id   INT,
    item_name VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id)

);


-- Create couriers table
CREATE TABLE couriers
(
    id                SERIAL PRIMARY KEY REFERENCES persons (id),
    courier_status_id INT,
    FOREIGN KEY (courier_status_id) REFERENCES courier_statuses (id)
);

-- Create order_statuses table
CREATE TABLE order_statuses
(
    id     SERIAL PRIMARY KEY,
    status VARCHAR(255) NOT NULL
);
-- Create order_status_history table
CREATE TABLE order_status_history
(
    id          SERIAL PRIMARY KEY,
    order_id    INT,
    agent_id    INT,
    status_id   INT,
    change_time TIMESTAMP NOT NULL,
    FOREIGN KEY (agent_id) REFERENCES persons (id),
    FOREIGN KEY (status_id) REFERENCES order_statuses (id)

);

-- Create order_location table
CREATE TABLE order_location
(
    id               SERIAL PRIMARY KEY,
    start_location   POINT,
    finish_location  POINT,
    coordinates      POINT[],
    current_location POINT
);

-- Create orders table
CREATE TABLE orders
(
    id                      SERIAL PRIMARY KEY,
    parcel_id               INT,
    courier_id              INT,
    order_status_history_id INT,
    order_location_id       INT,
    created_time            TIMESTAMP NOT NULL,
    start_time              TIMESTAMP,
    finish_time             TIMESTAMP,
    FOREIGN KEY (parcel_id) REFERENCES parcels (id),
    FOREIGN KEY (courier_id) REFERENCES couriers (id),
    FOREIGN KEY (order_status_history_id) REFERENCES order_status_history (id),
    FOREIGN KEY (order_location_id) REFERENCES order_location (id)

);
