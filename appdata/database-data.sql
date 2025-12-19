CREATE SCHEMA IF NOT EXISTS "public";

CREATE  TABLE "public".account ( 
	id                   integer  NOT NULL GENERATED  BY DEFAULT AS IDENTITY ,
	firstname            varchar(255)    ,
	lastname             varchar(255)    ,
	"password"           varchar(255)    ,
	username             varchar(255)    ,
	CONSTRAINT account_pkey PRIMARY KEY ( id )
 );

CREATE  TABLE "public".category ( 
	id                   integer  NOT NULL GENERATED  BY DEFAULT AS IDENTITY ,
	name                 varchar(255)    ,
	CONSTRAINT category_pkey PRIMARY KEY ( id )
 );

CREATE  TABLE "public".province ( 
	id                   integer  NOT NULL GENERATED  BY DEFAULT AS IDENTITY ,
	name                 varchar(255)    ,
	CONSTRAINT province_pkey PRIMARY KEY ( id )
 );

CREATE  TABLE "public".purchase ( 
	id                   integer  NOT NULL GENERATED  BY DEFAULT AS IDENTITY ,
	purchase_date        timestamp    ,
	purchaser_id         integer    ,
	CONSTRAINT purchase_pkey PRIMARY KEY ( id ),
	CONSTRAINT fkm7jx4xmfqbxg2d2g8yqb7cc27 FOREIGN KEY ( purchaser_id ) REFERENCES "public".account( id )   
 );

CREATE  TABLE "public"."role" ( 
	id                   integer  NOT NULL GENERATED  BY DEFAULT AS IDENTITY ,
	name                 varchar(255)    ,
	CONSTRAINT role_pkey PRIMARY KEY ( id )
 );

CREATE  TABLE "public".account_roles ( 
	account_id           integer  NOT NULL  ,
	roles_id             integer  NOT NULL  ,
	CONSTRAINT fk70s9enq5d1oywl7v8vis5ke5w FOREIGN KEY ( roles_id ) REFERENCES "public"."role"( id )   ,
	CONSTRAINT fktp61eta5i06bug3w1qr6286uf FOREIGN KEY ( account_id ) REFERENCES "public".account( id )   
 );

CREATE  TABLE "public".product ( 
	id                   integer  NOT NULL GENERATED  BY DEFAULT AS IDENTITY ,
	image                varchar(255)    ,
	in_stock             double precision    ,
	name                 varchar(255)    ,
	price                double precision    ,
	unit                 varchar(255)    ,
	category_id          integer    ,
	province_id          integer    ,
	CONSTRAINT product_pkey PRIMARY KEY ( id ),
	CONSTRAINT fk1mtsbur82frn64de7balymq9s FOREIGN KEY ( category_id ) REFERENCES "public".category( id )   ,
	CONSTRAINT fkis3bcatg70lhkpokh17xqg2jl FOREIGN KEY ( province_id ) REFERENCES "public".province( id )   
 );

CREATE  TABLE "public".purchase_detail ( 
	id                   integer  NOT NULL GENERATED  BY DEFAULT AS IDENTITY ,
	amount               real    ,
	price                real    ,
	product_id           integer    ,
	purchase_id          integer    ,
	CONSTRAINT purchase_detail_pkey PRIMARY KEY ( id ),
	CONSTRAINT fk79a6tsn4e9qfillme2u9kr3i2 FOREIGN KEY ( product_id ) REFERENCES "public".product( id )   ,
	CONSTRAINT fk65hoe4yy1817l2vm74msb8eq5 FOREIGN KEY ( purchase_id ) REFERENCES "public".purchase( id )   
 );

CREATE  TABLE "public".purchase_details ( 
	purchase_id          integer  NOT NULL  ,
	details_id           integer  NOT NULL  ,
	CONSTRAINT fkokyu61bio7a71f99cf08l7rx5 FOREIGN KEY ( details_id ) REFERENCES "public".purchase_detail( id )   ,
	CONSTRAINT fkk16rlf7byn3rg2iq2eym1sbv2 FOREIGN KEY ( purchase_id ) REFERENCES "public".purchase( id )   
 );

CREATE  TABLE "public".restock ( 
	id                   integer  NOT NULL GENERATED  BY DEFAULT AS IDENTITY ,
	amount               real    ,
	administrator_id     integer    ,
	product_id           integer    ,
	restock_date         timestamp    ,
	CONSTRAINT restock_pkey PRIMARY KEY ( id ),
	CONSTRAINT fkjb8mh4ona6lgy468mqlr4li02 FOREIGN KEY ( administrator_id ) REFERENCES "public".account( id )   ,
	CONSTRAINT fks289fuhq0fepnf7nvddyh2gbw FOREIGN KEY ( product_id ) REFERENCES "public".product( id )   
 );

CREATE OR REPLACE VIEW most_purchased AS SELECT "public".most_purchased,
    pr.name,
    pu.amount
   FROM product pr,
    ( SELECT purchase_detail.product_id,
            round((sum(purchase_detail.amount))::numeric, 2) AS amount
           FROM purchase_detail
          GROUP BY purchase_detail.product_id) pu
  WHERE ("public".most_purchased = pu.product_id)
  ORDER BY pu.amount DESC
 LIMIT 7;

INSERT INTO "public".account( id, firstname, lastname, "password", username ) VALUES ( 2, 'John', 'Doe', 'mdpJohn', 'johndoe');
INSERT INTO "public".account( id, firstname, lastname, "password", username ) VALUES ( 3, 'Ada', 'Wong', 'mdpAda', 'adawong');
INSERT INTO "public".category( id, name ) VALUES ( 1, 'Fruit');
INSERT INTO "public".category( id, name ) VALUES ( 2, 'Vegetable');
INSERT INTO "public".province( id, name ) VALUES ( 1, 'Antananarivo');
INSERT INTO "public".province( id, name ) VALUES ( 2, 'Antsiranana');
INSERT INTO "public".province( id, name ) VALUES ( 3, 'Fianarantsoa');
INSERT INTO "public".province( id, name ) VALUES ( 4, 'Mahajanga');
INSERT INTO "public".province( id, name ) VALUES ( 5, 'Toamasina');
INSERT INTO "public".province( id, name ) VALUES ( 6, 'Toliara');
INSERT INTO "public".purchase( id, purchase_date, purchaser_id ) VALUES ( 8, '2025-10-31 12:00:00 AM', 2);
INSERT INTO "public".purchase( id, purchase_date, purchaser_id ) VALUES ( 1, '2025-11-20 07:01:42 PM', 2);
INSERT INTO "public".purchase( id, purchase_date, purchaser_id ) VALUES ( 2, '2025-11-21 09:42:03 AM', 2);
INSERT INTO "public"."role"( id, name ) VALUES ( 1, 'Client');
INSERT INTO "public"."role"( id, name ) VALUES ( 2, 'Admin');
INSERT INTO "public".account_roles( account_id, roles_id ) VALUES ( 2, 1);
INSERT INTO "public".account_roles( account_id, roles_id ) VALUES ( 2, 2);
INSERT INTO "public".account_roles( account_id, roles_id ) VALUES ( 3, 1);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 2, '2.jpg', 5000.0, 'Lettuce', 1000.0, 'kg', 2, 2);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 4, '4.jpeg', 15000.0, 'Mandarin', 1000.0, 'kg', 1, 4);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 6, '6.jpg', 15000.0, 'Lentils', 800.0, 'kg', 2, 6);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 7, '7.jpg', 21000.0, 'Celery', 700.0, 'kg', 2, 1);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 8, '8.jpg', 22000.0, 'Leek', 650.0, 'kg', 2, 2);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 9, '9.jpg', 16000.0, 'Onion', 500.0, 'kg', 2, 3);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 10, '10.jpg', 17500.0, 'Apple', 1000.0, 'kg', 1, 4);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 11, '11.png', 15000.0, 'Lemon', 750.0, 'kg', 1, 5);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 12, '12.png', 12000.0, 'Grape', 800.0, 'kg', 1, 6);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 13, '13.jpg', 15000.0, 'Pear', 1000.0, 'kg', 1, 1);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 14, '14.jpeg', 15000.0, 'Apricot', 1000.0, 'kg', 1, 2);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 15, '15.jpg', 20000.0, 'Cherry', 700.0, 'kg', 1, 2);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 16, '16.jpg', 16000.0, 'Peach', 1000.0, 'kg', 1, 3);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 17, '17.jpg', 14000.0, 'Broccoli', 800.0, 'kg', 2, 4);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 18, '18.jpg', 17000.0, 'Zucchini', 1200.0, 'kg', 2, 5);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 19, '19.jpg', 20000.0, 'Carrot', 950.0, 'kg', 2, 6);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 3, '3.jpg', 12000.90000000596, 'Strawberry', 500.0, 'kg', 1, 3);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 5, '5.jpg', 20001.0, 'Beans', 750.0, 'kg', 2, 5);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 20, '20.jpg', 21000.0, 'Radish', 800.0, 'kg', 2, 1);
INSERT INTO "public".product( id, image, in_stock, name, price, unit, category_id, province_id ) VALUES ( 1, '1.jpeg', 10200.0, 'Banana', 2000.0, 'kg', 1, 1);
INSERT INTO "public".purchase_detail( id, amount, price, product_id, purchase_id ) VALUES ( 15, 1.0, 2000.0, 1, 8);
INSERT INTO "public".purchase_detail( id, amount, price, product_id, purchase_id ) VALUES ( 16, 0.5, 250.0, 3, 8);
INSERT INTO "public".purchase_detail( id, amount, price, product_id, purchase_id ) VALUES ( 1, 2.0, 4000.0, 1, 1);
INSERT INTO "public".purchase_detail( id, amount, price, product_id, purchase_id ) VALUES ( 2, 1.0, 500.0, 3, 1);
INSERT INTO "public".purchase_detail( id, amount, price, product_id, purchase_id ) VALUES ( 3, 1.0, 800.0, 20, 1);
INSERT INTO "public".purchase_detail( id, amount, price, product_id, purchase_id ) VALUES ( 4, 1.0, 2000.0, 1, 2);
INSERT INTO "public".purchase_detail( id, amount, price, product_id, purchase_id ) VALUES ( 5, 0.4, 200.0, 3, 2);
INSERT INTO "public".purchase_detail( id, amount, price, product_id, purchase_id ) VALUES ( 6, 0.3, 225.00002, 5, 2);
INSERT INTO "public".purchase_details( purchase_id, details_id ) VALUES ( 8, 15);
INSERT INTO "public".purchase_details( purchase_id, details_id ) VALUES ( 8, 16);
INSERT INTO "public".purchase_details( purchase_id, details_id ) VALUES ( 1, 1);
INSERT INTO "public".purchase_details( purchase_id, details_id ) VALUES ( 1, 2);
INSERT INTO "public".purchase_details( purchase_id, details_id ) VALUES ( 1, 3);
INSERT INTO "public".purchase_details( purchase_id, details_id ) VALUES ( 2, 4);
INSERT INTO "public".purchase_details( purchase_id, details_id ) VALUES ( 2, 5);
INSERT INTO "public".purchase_details( purchase_id, details_id ) VALUES ( 2, 6);
INSERT INTO "public".restock( id, amount, administrator_id, product_id, restock_date ) VALUES ( 10, 200.0, 2, 1, '2025-12-15 10:13:01 AM');