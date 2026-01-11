INSERT INTO role( id, name ) VALUES ( 1, 'Client');
INSERT INTO role( id, name ) VALUES ( 2, 'Admin');

INSERT INTO account( id, firstname, lastname, password, username ) VALUES ( 2, 'John', 'Doe', 'mdpJohn', 'johndoe');
INSERT INTO account( id, firstname, lastname, password, username ) VALUES ( 3, 'Ada', 'Wong', 'mdpAda', 'adawong');

INSERT INTO account_roles( account_id, roles_id ) VALUES ( 2, 1);
INSERT INTO account_roles( account_id, roles_id ) VALUES ( 2, 2);
INSERT INTO account_roles( account_id, roles_id ) VALUES ( 3, 1);

INSERT INTO category( id, name ) VALUES ( 1, 'Fruit');
INSERT INTO category( id, name ) VALUES ( 2, 'Vegetable');

INSERT INTO province( id, name ) VALUES ( 1, 'Antananarivo');
INSERT INTO province( id, name ) VALUES ( 2, 'Antsiranana');
INSERT INTO province( id, name ) VALUES ( 3, 'Fianarantsoa');
INSERT INTO province( id, name ) VALUES ( 4, 'Mahajanga');
INSERT INTO province( id, name ) VALUES ( 5, 'Toamasina');
INSERT INTO province( id, name ) VALUES ( 6, 'Toliara');

INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 2, '2.jpg', 5000.0, 'Lettuce', 1000.0, 'kg', 2, 2);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 4, '4.jpeg', 15000.0, 'Mandarin', 1000.0, 'kg', 1, 4);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 6, '6.jpg', 15000.0, 'Lentils', 800.0, 'kg', 2, 6);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 7, '7.jpg', 21000.0, 'Celery', 700.0, 'kg', 2, 1);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 8, '8.jpg', 22000.0, 'Leek', 650.0, 'kg', 2, 2);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 9, '9.jpg', 16000.0, 'Onion', 500.0, 'kg', 2, 3);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 10, '10.jpg', 17500.0, 'Apple', 1000.0, 'kg', 1, 4);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 11, '11.png', 15000.0, 'Lemon', 750.0, 'kg', 1, 5);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 12, '12.png', 12000.0, 'Grape', 800.0, 'kg', 1, 6);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 13, '13.jpg', 15000.0, 'Pear', 1000.0, 'kg', 1, 1);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 14, '14.jpeg', 15000.0, 'Apricot', 1000.0, 'kg', 1, 2);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 15, '15.jpg', 20000.0, 'Cherry', 700.0, 'kg', 1, 2);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 16, '16.jpg', 16000.0, 'Peach', 1000.0, 'kg', 1, 3);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 17, '17.jpg', 14000.0, 'Broccoli', 800.0, 'kg', 2, 4);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 18, '18.jpg', 17000.0, 'Zucchini', 1200.0, 'kg', 2, 5);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 19, '19.jpg', 20000.0, 'Carrot', 950.0, 'kg', 2, 6);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 3, '3.jpg', 12000.90000000596, 'Strawberry', 500.0, 'kg', 1, 3);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 5, '5.jpg', 20001.0, 'Beans', 750.0, 'kg', 2, 5);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 20, '20.jpg', 21000.0, 'Radish', 800.0, 'kg', 2, 1);
INSERT INTO product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 1, '1.jpeg', 10200.0, 'Banana', 2000.0, 'kg', 1, 1);