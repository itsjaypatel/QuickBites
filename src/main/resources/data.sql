INSERT INTO user_entity (first_name, last_name, email, password)
VALUES
    ('Born', 'Vita', 'bornvita261@gmail.com', '$2a$10$h9dBXtautHgTKq3C5es6aOSBY7nfwcI.lOINXrR/OWGtJqv0C/b..'),
    ('Jay 1781', 'Patel', 'pateljay1781@gmail.com', '$2a$10$h9dBXtautHgTKq3C5es6aOSBY7nfwcI.lOINXrR/OWGtJqv0C/b..'),
    ('liam', 'smith', 'liamsmith@gmail.com', '$2a$10$h9dBXtautHgTKq3C5es6aOSBY7nfwcI.lOINXrR/OWGtJqv0C/b..'),
    ('olivia', 'johnson', 'oliviajohnson@gmail.com', '$2a$10$h9dBXtautHgTKq3C5es6aOSBY7nfwcI.lOINXrR/OWGtJqv0C/b..'),
    ('michael', 'brown', 'michaelbrown@gmail.com', '$2a$10$h9dBXtautHgTKq3C5es6aOSBY7nfwcI.lOINXrR/OWGtJqv0C/b..'),
    ('Jay 2k20', 'Patel', 'officialjay2k20@gmail.com', '$2a$10$h9dBXtautHgTKq3C5es6aOSBY7nfwcI.lOINXrR/OWGtJqv0C/b..'),
    ('ava', 'williams', 'avawilliams@gmail.com', '$2a$10$h9dBXtautHgTKq3C5es6aOSBY7nfwcI.lOINXrR/OWGtJqv0C/b..'),
    ('ethan', 'jones', 'ethanjones@gmail.com', '$2a$10$h9dBXtautHgTKq3C5es6aOSBY7nfwcI.lOINXrR/OWGtJqv0C/b..'),
    ('isabella', 'davis', 'isabelladavis@gmail.com', '$2a$10$h9dBXtautHgTKq3C5es6aOSBY7nfwcI.lOINXrR/OWGtJqv0C/b..'),
    ('lucas', 'wilson', 'lucaswilson@gmail.com', '$2a$10$h9dBXtautHgTKq3C5es6aOSBY7nfwcI.lOINXrR/OWGtJqv0C/b..'),
    ('admin', '', 'admin@gmail.com', '$2a$10$h9dBXtautHgTKq3C5es6aOSBY7nfwcI.lOINXrR/OWGtJqv0C/b..');

INSERT INTO user_entity_roles (user_entity_id, roles)
VALUES
    (1, 'CUSTOMER'),
    (2, 'CUSTOMER'),
    (3, 'CUSTOMER'),
    (4, 'CUSTOMER'),
    (5, 'CUSTOMER'),
    (6, 'CUSTOMER'),
    (7, 'CUSTOMER'),
    (8, 'CUSTOMER'),
    (9, 'CUSTOMER'),
    (10,'CUSTOMER'),
    (1, 'DELIVERY_PARTNER'),
    (2, 'DELIVERY_PARTNER'),
    (3, 'DELIVERY_PARTNER'),
    (4, 'DELIVERY_PARTNER'),
    (5, 'DELIVERY_PARTNER'),
    (9, 'DELIVERY_PARTNER'),
    (10,'DELIVERY_PARTNER'),
    (6, 'RESTAURANT_PARTNER'),
    (7, 'RESTAURANT_PARTNER'),
    (8, 'RESTAURANT_PARTNER'),
    (11,'ADMIN');
INSERT INTO wallet (user_id,balance)
VALUES
    (1, 1000.00),
    (2, 1000.00),
    (3, 1000.00),
    (4, 1000.00),
    (5, 1000.00),
    (6, 1000.00),
    (7, 1000.00),
    (8, 1000.00),
    (9, 1000.00),
    (10,1000.00),
    (11,200000.00);

INSERT INTO customer (user_id, rating, location)
VALUES
    (1, 3.2, 'POINT (72.88221216837208 21.151538885141235)'),
    (2, 4.5, 'POINT (72.88321216837208 21.152538885141235)'),
    (3, 4.0, 'POINT (72.88421216837208 21.153538885141235)'),
    (4, 3.8, 'POINT (72.88521216837208 21.154538885141235)'),
    (5, 4.2, 'POINT (72.88621216837208 21.155538885141235)'),
    (6, 3.9, 'POINT (72.88721216837208 21.156538885141235)'),
    (7, 4.7, 'POINT (72.88821216837208 21.157538885141235)'),
    (8, 4.1, 'POINT (72.88921216837208 21.158538885141235)'),
    (9, 4.3, 'POINT (72.89021216837208 21.159538885141235)'),
    (10, 3.6, 'POINT (72.89121216837208 21.160538885141235)');

INSERT INTO cart (customer_id)
VALUES
    (1),
    (2),
    (3),
    (4),
    (5),
    (6),
    (7),
    (8),
    (9),
    (10);


INSERT INTO delivery_partner (user_id, rating, driving_licence, location)
VALUES
    (1, 4.0, 'DL1234567890', 'POINT (72.89221216837208 21.161538885141235)'),
    (2, 3.7, 'DL0987654321', 'POINT (72.89321216837208 21.162538885141235)'),
    (3, 4.5, 'DL1122334455', 'POINT (72.89421216837208 21.163538885141235)'),
    (4, 4.2, 'DL2233445566', 'POINT (72.89521216837208 21.164538885141235)'),
    (5, 4.8, 'DL3344556677', 'POINT (72.89621216837208 21.165538885141235)'),
    (9, 3.8, 'DL7788990011', 'POINT (72.90021216837208 21.169538885141235)'),
    (10, 4.3, 'DL8899001122', 'POINT (72.90121216837208 21.170538885141235)');

INSERT INTO restaurant (name, rating, address)
VALUES
    ('Golden Spoon', 4.0, 'POINT (72.89421216837208 21.163538885141235)'),
    ('Green Valley Cafe', 3.8, 'POINT (72.89521216837208 21.164538885141235)'),
    ('Sunset Bistro', 4.3, 'POINT (72.89621216837208 21.165538885141235)');

INSERT INTO restaurant_partner(user_id,registration_no,restaurant_id)
VALUES
    (6,'RES1234567890',1),
    (7,'RES0987654321',2),
    (8,'RES1122334455',3);

INSERT INTO food_item (title, restaurant_id, price, rating)
VALUES
    ('Veg Burger', 1, 150.00, 4.1),
    ('Cheese Pizza', 2, 300.00, 4.5),
    ('Pasta Alfredo', 3, 250.00, 4.0),
    ('Chicken Wrap', 3, 200.00, 3.9),
    ('Paneer Tikka', 2, 180.00, 4.3),
    ('French Fries', 1, 80.00, 4.2),
    ('Margherita Pizza', 2, 280.00, 4.6),
    ('Grilled Sandwich', 3, 120.00, 4.1),
    ('BBQ Chicken Wings', 3, 350.00, 4.7),
    ('Veggie Salad', 2, 140.00, 4.0);


