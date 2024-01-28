
-- Insert sample data into roles table
INSERT INTO roles (id, role)
VALUES (1, 'admin');
INSERT INTO roles (id, role)
VALUES (2, 'user');
INSERT INTO roles (id, role)
VALUES (3, 'courier');

-- Insert sample data into persons table
INSERT INTO persons (id, username, password, email, active, mobile_number, role_id)
VALUES ('5418ee15-8396-400b-b5e3-a1658b2fe5a3', 'admin1', 'password', 'admin1_email@example.com', true, 11111, 1);
INSERT INTO persons (id, username, password, email, active, mobile_number, role_id)
VALUES ('d8fbe36c-6976-49a3-a002-b511749501aa', 'admin2', 'password', 'admin2_email@example.com', true, 21313, 1);
INSERT INTO persons (id, username, password, email, active, mobile_number, role_id)
VALUES ('b82d0d5b-4a36-4f35-90cd-0c18b9759d84', 'user1', 'password', 'user1_email@example.com', true, 4144, 2);
INSERT INTO persons (id, username, password, email, active, mobile_number, role_id)
VALUES ('e19ade84-6284-4764-864b-97da84f62519', 'user2', 'password', 'user2_email@example.com', true, 121, 2);
INSERT INTO persons (id, username, password, email, active, mobile_number, role_id)
VALUES ('19f98338-e654-47e3-9715-4d50c97c366b', 'courier1', 'password', 'courier1_email@example.com', true, 33333, 3);
INSERT INTO persons (id, username, password, email, active, mobile_number, role_id)
VALUES ('919c4093-d214-4e6d-82f3-d3722b2eff03', 'courier2', 'password', 'courier2_email@example.com', true, 44444, 3);

-- Insert sample data into admins table
INSERT INTO admins (id, superadmin)
VALUES ('5418ee15-8396-400b-b5e3-a1658b2fe5a3', true);
INSERT INTO admins (id, superadmin)
VALUES ('d8fbe36c-6976-49a3-a002-b511749501aa', false);


-- Insert sample data into users table
INSERT INTO users (id, vip)
VALUES ('b82d0d5b-4a36-4f35-90cd-0c18b9759d84', true);
INSERT INTO users (id, vip)
VALUES ('e19ade84-6284-4764-864b-97da84f62519', false);



-- Insert sample data into courier_statuses table
INSERT INTO courier_statuses (id, status)
VALUES (1, 'active');
INSERT INTO courier_statuses (id, status)
VALUES (2, 'inactive');


-- Insert sample data into couriers table
INSERT INTO couriers (id, courier_status_id)
VALUES ('19f98338-e654-47e3-9715-4d50c97c366b', 1);
INSERT INTO couriers (id, courier_status_id)
VALUES ('919c4093-d214-4e6d-82f3-d3722b2eff03', 2);


-- Insert sample data into parcels table
INSERT INTO parcels (id, user_id, item_name)
VALUES ('4894c278-2da5-4396-8991-edb05c899bc8', 'b82d0d5b-4a36-4f35-90cd-0c18b9759d84', 'milk');
INSERT INTO parcels (id, user_id, item_name)
VALUES ('50ec16d7-90a1-4b35-807f-268666e67e67', 'e19ade84-6284-4764-864b-97da84f62519', 'wood');


-- Insert sample data into order_statuses table
INSERT INTO order_statuses (id, status)
VALUES (1, 'assigned');
INSERT INTO order_statuses (id, status)
VALUES (2, 'in_progress');
INSERT INTO order_statuses (id, status)
VALUES (3, 'completed');
INSERT INTO order_statuses (id, status)
VALUES (4, 'cancelled');


-- Insert sample data into orders table
INSERT INTO orders (id, parcel_id, courier_id, created_time, start_time,
                    finish_time)
VALUES ('28028cd7-cff8-4d4a-811e-54a8f002db47', '4894c278-2da5-4396-8991-edb05c899bc8',
        '19f98338-e654-47e3-9715-4d50c97c366b',
        '2024-01-28 12:00:00', '2024-01-28 12:00:00', '2024-01-28 14:00:00');
INSERT INTO orders (id, parcel_id, courier_id, created_time, start_time,finish_time)
VALUES ('14a86268-84ab-4c6f-9e58-fa588c840eba', '4894c278-2da5-4396-8991-edb05c899bc8',
        '19f98338-e654-47e3-9715-4d50c97c366b',
        '2024-01-28 12:00:00',  '2024-01-28 12:00:00', '2024-01-28 14:00:00');



-- Insert sample data into order_location table
INSERT INTO order_location (id, order_id, start_location, finish_location, coordinates, current_location)
VALUES ('5516eeeb-20f0-4583-ac88-3c42848e2d41', '28028cd7-cff8-4d4a-811e-54a8f002db47', '(18,28)', '(28,34)', '{"(20,30)","(22,32)"}','(15,17)');
INSERT INTO order_location (id, order_id, start_location, finish_location, coordinates, current_location)
VALUES ('f329c4ab-fcdd-4263-ad93-4272f75b2f0a', '14a86268-84ab-4c6f-9e58-fa588c840eba', '(18,28)', '(28,34)', '{"(20,30)","(22,32)"}','(15,17)');


-- Insert sample data into order_status_history table
INSERT INTO order_status_history (id, order_id, agent_id, status_id, change_time)
VALUES ('aff63ae6-dd2a-4e3b-abf9-7df2a56f07ac', '28028cd7-cff8-4d4a-811e-54a8f002db47', '5418ee15-8396-400b-b5e3-a1658b2fe5a3', 1, '2024-01-28 12:00:00');
INSERT INTO order_status_history (id, order_id, agent_id, status_id, change_time)
VALUES ('1cdd286c-a5d4-4b08-8d7e-b5ebb03404f0', '14a86268-84ab-4c6f-9e58-fa588c840eba', 'e19ade84-6284-4764-864b-97da84f62519', 4, '2024-01-28 12:00:00');

