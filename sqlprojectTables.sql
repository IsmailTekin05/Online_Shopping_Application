-- Veritabanı oluşturma
CREATE DATABASE IF NOT EXISTS project_database;
USE project_database;

-- SQL PROJECT: ONLINE SHOPPING SYSTEM

-- AUTO_INCREMENT yazilmazsa her yeni kayıt eklerken ID'yi elle vermen gerekir. Bu hatalara sebep olur.

-- 1. CUSTOMER TABLE
CREATE TABLE customer (
    customerID INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(50),
    phone_number VARCHAR(11) UNIQUE,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    height INT,
    age INT,
    referrerID INT,
    FOREIGN KEY (referrerID) REFERENCES customer(customerID)
);

-- 2. PAYMENT_INFO TABLE
CREATE TABLE payment_info (
    cardNumber VARCHAR(20) PRIMARY KEY,
    expirationDate DATE,
    cvc VARCHAR(5),
    card_owner VARCHAR(50),
    cardName VARCHAR(50),
    customerID INT,
    FOREIGN KEY (customerID) REFERENCES customer(customerID)
);

-- 3. ADDRESS TABLE
CREATE TABLE address (
    address_name VARCHAR(50),
    customerID INT,
    city VARCHAR(50),
    district VARCHAR(50),
    street VARCHAR(50),
    building_number VARCHAR(10),
    PRIMARY KEY (address_name, customerID),
    FOREIGN KEY (customerID) REFERENCES customer(customerID)
);


-- 4. SHIPPING_COMPANY TABLE
CREATE TABLE shipping_company (
    shipmentID INT AUTO_INCREMENT PRIMARY KEY,
    companyName VARCHAR(255) UNIQUE
);

-- 5. SELLER TABLE
CREATE TABLE seller (
    sellerID INT AUTO_INCREMENT PRIMARY KEY,
    sellerName VARCHAR(100) UNIQUE,
    experience INT,
    rating DECIMAL(3,2),
    shipmentID INT,
    FOREIGN KEY (shipmentID) REFERENCES shipping_company(shipmentID)
);

-- 6. GAME TABLE
CREATE TABLE game (
    gameID INT AUTO_INCREMENT PRIMARY KEY,
    gameName VARCHAR(100),
    gameType VARCHAR(50),
    resetHours INT
);

-- 7. COUPON TABLE
CREATE TABLE coupon (
    couponID INT AUTO_INCREMENT PRIMARY KEY,
    couponCode VARCHAR(50) UNIQUE,
    discountAmount DECIMAL(5,2),
    expirationDate DATE,
    sellerID INT,
    gameID INT DEFAULT NULL,
    FOREIGN KEY (sellerID) REFERENCES seller(sellerID),
    FOREIGN KEY (gameID) REFERENCES game(gameID)
);


-- 8. PRODUCT TABLE
CREATE TABLE product (
    productID INT AUTO_INCREMENT PRIMARY KEY,
    productName VARCHAR(100),
    price DECIMAL(5,2),
    color VARCHAR(30),
    size VARCHAR(10),
    material VARCHAR(50),
    stock INT,
    sellerID INT,
    FOREIGN KEY (sellerID) REFERENCES seller(sellerID)
);

-- 9. CART TABLE
CREATE TABLE cart (
    cartID INT AUTO_INCREMENT PRIMARY KEY,
    customerID INT,
    FOREIGN KEY (customerID) REFERENCES customer(customerID)
);

-- 10. CART_ITEM TABLE
CREATE TABLE cart_item (
    cartID INT,
    productID INT,
    quantity INT,
    PRIMARY KEY (cartID, productID),
    FOREIGN KEY (cartID) REFERENCES cart(cartID),
    FOREIGN KEY (productID) REFERENCES product(productID)
);

-- 11. FAVORITES TABLE
CREATE TABLE favorites (
    productID INT,
    customerID INT,
    PRIMARY KEY (productID, customerID),
    FOREIGN KEY (productID) REFERENCES product(productID),
    FOREIGN KEY (customerID) REFERENCES customer(customerID)
);

-- 12. REVIEW TABLE
CREATE TABLE review (
    productID INT,
    customerID INT,
    stars INT,
    comment TEXT,
    rating_date DATE,
    PRIMARY KEY (productID, customerID),
    FOREIGN KEY (productID) REFERENCES product(productID),
    FOREIGN KEY (customerID) REFERENCES customer(customerID)
);

-- 13. ORDER TABLE
CREATE TABLE orders (
    orderID INT AUTO_INCREMENT PRIMARY KEY,
    orderDate DATE,
    customerID INT,
    address_name VARCHAR(50),
    FOREIGN KEY (customerID) REFERENCES customer(customerID),
    FOREIGN KEY (address_name, customerID) REFERENCES address(address_name, customerID)
);

ALTER TABLE order_product
ADD COLUMN quantity INT DEFAULT 1;
-- 14. ORDER_PRODUCT TABLE
CREATE TABLE order_product (
    orderID INT,
    productID INT,
    
    PRIMARY KEY (orderID, productID),
    FOREIGN KEY (orderID) REFERENCES orders(orderID),
    FOREIGN KEY (productID) REFERENCES product(productID)
);

-- 15. PLAYS TABLE
CREATE TABLE plays (
    customerID INT,
    gameID INT,
    PRIMARY KEY (customerID, gameID),
    FOREIGN KEY (customerID) REFERENCES customer(customerID),
    FOREIGN KEY (gameID) REFERENCES game(gameID)
);

-- 16. CUSTOMER_PRODUCT TABLE
CREATE TABLE customer_product (
    customerID INT,
    productID INT,
    quantity INT DEFAULT 1,
    PRIMARY KEY (customerID, productID),
    FOREIGN KEY (customerID) REFERENCES customer(customerID),
    FOREIGN KEY (productID) REFERENCES product(productID)
);

-- 17. CARTITEM_PRODUCT TABLE
CREATE TABLE cartitem_product (
    cartID INT,
    productID INT,
    PRIMARY KEY (cartID, productID),
    FOREIGN KEY (cartID) REFERENCES cart(cartID),
    FOREIGN KEY (productID) REFERENCES product(productID)
);

-- 18. INVITES TABLE
CREATE TABLE invites (
    customerID INT,
    referrerID INT,
    PRIMARY KEY (customerID, referrerID),
    FOREIGN KEY (customerID) REFERENCES customer(customerID),
    FOREIGN KEY (referrerID) REFERENCES customer(customerID)
);

ALTER TABLE plays ADD COLUMN playTime DATETIME DEFAULT CURRENT_TIMESTAMP;


-- ÖRNEK VERİ (İsteğe bağlı)
INSERT INTO customer VALUES (1, 'Ali Veli', '05321234567', 'ali@example.com', '12345', 175, 25, NULL);
INSERT INTO shipping_company VALUES (1, 'Shipping Company 1');
INSERT INTO shipping_company VALUES (2, 'Shipping Company 2');
INSERT INTO shipping_company VALUES (3, 'Shipping Company 3');
INSERT INTO seller VALUES (1, 'Seller1', 4, 4.7, 3);
INSERT INTO seller VALUES (2, 'Seller2', 3, 4.2, 1);
INSERT INTO seller VALUES (3, 'Seller3', 2, 3.9, 3);
INSERT INTO seller VALUES (4, 'Seller4', 5, 4.8, 2);
INSERT INTO seller VALUES (5, 'Seller5', 3, 3.2, 2);
INSERT INTO seller VALUES (6, 'Seller6', 4, 3.0, 3);
INSERT INTO seller VALUES (7, 'Seller7', 2, 4.9, 1);
INSERT INTO seller VALUES (8, 'Seller8', 5, 3.8, 2);
INSERT INTO seller VALUES (9, 'Seller9', 3, 4.1, 1);
INSERT INTO seller VALUES (10, 'Seller10', 3, 4.8, 1);
INSERT INTO product VALUES (1, 'Tshirt', 160.00, 'Black', 'S', 'Cotton', 100, 1);
INSERT INTO product VALUES (2, 'Tshirt', 160.00, 'Black', 'M', 'Cotton', 100, 1);
INSERT INTO product VALUES (3, 'Tshirt', 160.00, 'Black', 'L', 'Cotton', 50, 1);
INSERT INTO product VALUES (4, 'Tshirt', 140.00, 'White', 'S', 'Cotton', 100, 1);
INSERT INTO product VALUES (5, 'Tshirt', 140.00, 'White', 'M', 'Cotton', 100, 1);
INSERT INTO product VALUES (6, 'Tshirt', 140.00, 'White', 'L', 'Cotton', 50, 1);
INSERT INTO product VALUES (7, 'Tshirt', 150.00, 'Blue', 'S', 'Cotton', 60, 1);
INSERT INTO product VALUES (8, 'Tshirt', 150.00, 'Blue', 'M', 'Cotton', 60, 1);
INSERT INTO product VALUES (9, 'Tshirt', 150.00, 'Blue', 'L', 'Cotton', 40, 1);
INSERT INTO product VALUES (10, 'Tshirt', 150.00, 'Green', 'S', 'Cotton', 60, 1);
INSERT INTO product VALUES (11, 'Tshirt', 150.00, 'Green', 'M', 'Cotton', 60, 1);
INSERT INTO product VALUES (12, 'Tshirt', 150.00, 'Green', 'L', 'Cotton', 40, 1);
INSERT INTO product VALUES (13, 'Tshirt', 150.00, 'Grey', 'S', 'Cotton', 70, 1);
INSERT INTO product VALUES (14, 'Tshirt', 150.00, 'Grey', 'M', 'Cotton', 70, 1);
INSERT INTO product VALUES (15, 'Tshirt', 150.00, 'Grey', 'L', 'Cotton', 50, 1);
INSERT INTO product VALUES (16, 'Tshirt', 155.00, 'Pink', 'XS', 'Cotton', 70, 1);
INSERT INTO product VALUES (17, 'Tshirt', 155.00, 'Pink', 'S', 'Cotton', 70, 1);
INSERT INTO product VALUES (18, 'Tshirt', 155.00, 'Pink', 'M', 'Cotton', 70, 1);
INSERT INTO product VALUES (19, 'Tshirt', 155.00, 'Pink', 'L', 'Cotton', 50, 1);
INSERT INTO product VALUES (20, 'Tshirt', 155.00, 'Pink', 'XL', 'Cotton', 30, 1);
INSERT INTO product VALUES (21, 'Jeans', 450.00, 'Blue', 'XS', 'Denim', 120, 2);
INSERT INTO product VALUES (22, 'Jeans', 450.00, 'Blue', 'S', 'Denim', 120, 2);
INSERT INTO product VALUES (23, 'Jeans', 450.00, 'Blue', 'M', 'Denim', 120, 2);
INSERT INTO product VALUES (24, 'Jeans', 450.00, 'Blue', 'L', 'Denim', 100, 2);
INSERT INTO product VALUES (25, 'Jeans', 450.00, 'Blue', 'XL', 'Denim', 100, 2);
INSERT INTO product VALUES (26, 'Jeans', 450.00, 'Grey', 'XS', 'Denim', 120, 2);
INSERT INTO product VALUES (27, 'Jeans', 450.00, 'Grey', 'S', 'Denim', 120, 2);
INSERT INTO product VALUES (28, 'Jeans', 450.00, 'Grey', 'M', 'Denim', 120, 2);
INSERT INTO product VALUES (29, 'Jeans', 450.00, 'Grey', 'L', 'Denim', 100, 2);
INSERT INTO product VALUES (30, 'Jeans', 450.00, 'Grey', 'XL', 'Denim', 100, 2);
INSERT INTO product VALUES (31, 'Shorts', 350.00, 'Blue', 'XS', 'Denim', 120, 2);
INSERT INTO product VALUES (32, 'Shorts', 350.00, 'Blue', 'S', 'Denim', 120, 2);
INSERT INTO product VALUES (33, 'Shorts', 350.00, 'Blue', 'M', 'Denim', 120, 2);
INSERT INTO product VALUES (34, 'Shorts', 350.00, 'Blue', 'L', 'Denim', 100, 2);
INSERT INTO product VALUES (35, 'Shorts', 350.00, 'Blue', 'XL', 'Denim', 100, 2);
INSERT INTO product VALUES (36, 'Shorts', 350.00, 'Grey', 'XS', 'Denim', 120, 2);
INSERT INTO product VALUES (37, 'Shorts', 350.00, 'Grey', 'S', 'Denim', 120, 2);
INSERT INTO product VALUES (38, 'Shorts', 350.00, 'Grey', 'M', 'Denim', 120, 2);
INSERT INTO product VALUES (39, 'Shorts', 350.00, 'Grey', 'L', 'Denim', 100, 2);
INSERT INTO product VALUES (40, 'Shorts', 350.00, 'Grey', 'XL', 'Denim', 100, 2);
INSERT INTO product VALUES (41, 'Dress', 200.00, 'Black', 'XS', 'Silk', 80, 3);
INSERT INTO product VALUES (42, 'Dress', 200.00, 'Black', 'S', 'Silk', 80, 3);
INSERT INTO product VALUES (43, 'Dress', 200.00, 'Black', 'M', 'Silk', 80, 3);
INSERT INTO product VALUES (44, 'Dress', 200.00, 'Black', 'L', 'Silk', 80, 3);
INSERT INTO product VALUES (45, 'Dress', 200.00, 'Black', 'XL', 'Silk', 80, 3);
INSERT INTO product VALUES (46, 'Dress', 200.00, 'White', 'XS', 'Silk', 80, 3);
INSERT INTO product VALUES (47, 'Dress', 200.00, 'White', 'S', 'Silk', 80, 3);
INSERT INTO product VALUES (48, 'Dress', 200.00, 'White', 'M', 'Silk', 80, 3);
INSERT INTO product VALUES (49, 'Dress', 200.00, 'White', 'L', 'Silk', 80, 3);
INSERT INTO product VALUES (50, 'Dress', 200.00, 'White', 'XL', 'Silk', 80, 3);
INSERT INTO product VALUES (51, 'Dress', 200.00, 'Pink', 'XS', 'Silk', 80, 3);
INSERT INTO product VALUES (52, 'Dress', 200.00, 'Pink', 'S', 'Silk', 80, 3);
INSERT INTO product VALUES (53, 'Dress', 200.00, 'Pink', 'M', 'Silk', 80, 3);
INSERT INTO product VALUES (54, 'Dress', 200.00, 'Pink', 'L', 'Silk', 80, 3);
INSERT INTO product VALUES (55, 'Dress', 200.00, 'Pink', 'XL', 'Silk', 80, 3);
INSERT INTO product VALUES (56, 'Dress', 200.00, 'Green', 'XS', 'Silk', 80, 3);
INSERT INTO product VALUES (57, 'Dress', 200.00, 'Green', 'S', 'Silk', 80, 3);
INSERT INTO product VALUES (58, 'Dress', 200.00, 'Green', 'M', 'Silk', 80, 3);
INSERT INTO product VALUES (59, 'Dress', 200.00, 'Green', 'L', 'Silk', 80, 3);
INSERT INTO product VALUES (60, 'Dress', 200.00, 'Green', 'XL', 'Silk', 80, 3);
INSERT INTO product VALUES (61, 'Jacket', 450.00, 'Black', 'XS', 'Leather', 100, 4);
INSERT INTO product VALUES (62, 'Jacket', 450.00, 'Black', 'S', 'Leather', 100, 4);
INSERT INTO product VALUES (63, 'Jacket', 450.00, 'Black', 'M', 'Leather', 100, 4);
INSERT INTO product VALUES (64, 'Jacket', 450.00, 'Black', 'L', 'Leather', 100, 4);
INSERT INTO product VALUES (65, 'Jacket', 450.00, 'Black', 'XL', 'Leather', 100, 4);
INSERT INTO product VALUES (66, 'Jacket', 450.00, 'Brown', 'XS', 'Leather', 100, 4);
INSERT INTO product VALUES (67, 'Jacket', 450.00, 'Brown', 'S', 'Leather', 100, 4);
INSERT INTO product VALUES (68, 'Jacket', 450.00, 'Brown', 'M', 'Leather', 100, 4);
INSERT INTO product VALUES (69, 'Jacket', 450.00, 'Brown', 'L', 'Leather', 100, 4);
INSERT INTO product VALUES (70, 'Jacket', 450.00, 'Brown', 'XL', 'Leather', 100, 4);
INSERT INTO product VALUES (71, 'Jacket', 350.00, 'Navy', 'XS', 'Denim', 100, 4);
INSERT INTO product VALUES (72, 'Jacket', 350.00, 'Navy', 'S', 'Denim', 100, 4);
INSERT INTO product VALUES (73, 'Jacket', 350.00, 'Navy', 'M', 'Denim', 100, 4);
INSERT INTO product VALUES (74, 'Jacket', 350.00, 'Navy', 'L', 'Denim', 100, 4);
INSERT INTO product VALUES (75, 'Jacket', 350.00, 'Navy', 'XL', 'Denim', 100, 4);
INSERT INTO product VALUES (76, 'Jacket', 250.00, 'White', 'XS', 'Polyester', 100, 4);
INSERT INTO product VALUES (77, 'Jacket', 250.00, 'White', 'S', 'Polyester', 100, 4);
INSERT INTO product VALUES (78, 'Jacket', 250.00, 'White', 'M', 'Polyester', 100, 4);
INSERT INTO product VALUES (79, 'Jacket', 250.00, 'White', 'L', 'Polyester', 100, 4);
INSERT INTO product VALUES (80, 'Jacket', 250.00, 'White', 'XL', 'Polyester', 100, 4);
INSERT INTO product VALUES (81, 'Skirt', 250.00, 'Black', 'XS', 'Polyester', 50, 5);
INSERT INTO product VALUES (82, 'Skirt', 250.00, 'Black', 'S', 'Polyester', 50, 5);
INSERT INTO product VALUES (83, 'Skirt', 250.00, 'Black', 'M', 'Polyester', 50, 5);
INSERT INTO product VALUES (84, 'Skirt', 250.00, 'Black', 'L', 'Polyester', 50, 5);
INSERT INTO product VALUES (85, 'Skirt', 250.00, 'Black', 'XL', 'Polyester', 50, 5);
INSERT INTO product VALUES (86, 'Skirt', 350.00, 'Grey', 'XS', 'Denim', 50, 5);
INSERT INTO product VALUES (87, 'Skirt', 350.00, 'Grey', 'S', 'Denim', 50, 5);
INSERT INTO product VALUES (88, 'Skirt', 350.00, 'Grey', 'M', 'Denim', 50, 5);
INSERT INTO product VALUES (89, 'Skirt', 350.00, 'Grey', 'L', 'Denim', 50, 5);
INSERT INTO product VALUES (90, 'Skirt', 350.00, 'Grey', 'XL', 'Denim', 50, 5);
INSERT INTO product VALUES (91, 'Hat', 50.00, 'Black', 'Standard', 'Polyester', 20, 5);
INSERT INTO product VALUES (92, 'Hat', 50.00, 'White', 'Standard', 'Polyester', 20, 5);
INSERT INTO product VALUES (93, 'Hat', 50.00, 'Grey', 'Standard', 'Polyester', 20, 5);
INSERT INTO product VALUES (94, 'Hat', 50.00, 'Blue', 'Standard', 'Polyester', 20, 5);
INSERT INTO product VALUES (95, 'Hat', 50.00, 'Green', 'Standard', 'Polyester', 20, 5);
INSERT INTO product VALUES (96, 'Hat', 50.00, 'Pink', 'Standard', 'Polyester', 20, 5);
INSERT INTO product VALUES (97, 'Hat', 50.00, 'Brown', 'Standard', 'Polyester', 20, 5);
INSERT INTO product VALUES (98, 'Hat', 50.00, 'Red', 'Standard', 'Polyester', 20, 5);
INSERT INTO product VALUES (99, 'Hat', 50.00, 'Yellow', 'Standard', 'Polyester', 20, 5);
INSERT INTO product VALUES (100, 'Hat', 50.00, 'Purple', 'Standard', 'Polyester', 20, 5);
INSERT INTO product VALUES (101, 'Sweater', 400.00, 'Black', 'XS', 'Wool', 100, 6);
INSERT INTO product VALUES (102, 'Sweater', 400.00, 'Black', 'S', 'Wool', 100, 6);
INSERT INTO product VALUES (103, 'Sweater', 400.00, 'Black', 'M', 'Wool', 100, 6);
INSERT INTO product VALUES (104, 'Sweater', 400.00, 'Black', 'L', 'Wool', 100, 6);
INSERT INTO product VALUES (105, 'Sweater', 400.00, 'Black', 'XL', 'Wool', 100, 6);
INSERT INTO product VALUES (106, 'Sweater', 400.00, 'White', 'XS', 'Wool', 100, 6);
INSERT INTO product VALUES (107, 'Sweater', 400.00, 'White', 'S', 'Wool', 100, 6);
INSERT INTO product VALUES (108, 'Sweater', 400.00, 'White', 'M', 'Wool', 100, 6);
INSERT INTO product VALUES (109, 'Sweater', 400.00, 'White', 'L', 'Wool', 100, 6);
INSERT INTO product VALUES (110, 'Sweater', 400.00, 'White', 'XL', 'Wool', 100, 6);
INSERT INTO product VALUES (111, 'Sweater', 400.00, 'Red', 'XS', 'Wool', 100, 6);
INSERT INTO product VALUES (112, 'Sweater', 400.00, 'Red', 'S', 'Wool', 100, 6);
INSERT INTO product VALUES (113, 'Sweater', 400.00, 'Red', 'M', 'Wool', 100, 6);
INSERT INTO product VALUES (114, 'Sweater', 400.00, 'Red', 'L', 'Wool', 100, 6);
INSERT INTO product VALUES (115, 'Sweater', 400.00, 'Red', 'XL', 'Wool', 100, 6);
INSERT INTO product VALUES (116, 'Sweater', 400.00, 'Pink', 'XS', 'Wool', 100, 6);
INSERT INTO product VALUES (117, 'Sweater', 400.00, 'Pink', 'S', 'Wool', 100, 6);
INSERT INTO product VALUES (118, 'Sweater', 400.00, 'Pink', 'M', 'Wool', 100, 6);
INSERT INTO product VALUES (119, 'Sweater', 400.00, 'Pink', 'L', 'Wool', 100, 6);
INSERT INTO product VALUES (120, 'Sweater', 400.00, 'Pink', 'XL', 'Wool', 100, 6);

INSERT INTO product VALUES (121, 'Perfume - Ocean Breeze', 870.00, 'Blue', 'Standard', 'Glass', 50, 7);
INSERT INTO product VALUES (122, 'Perfume - Sunset Rose', 890.00, 'Red', 'Standard', 'Glass', 45, 7);
INSERT INTO product VALUES (123, 'Lipstick', 120.00, 'Pink', 'Standard', 'Wax', 40, 7);
INSERT INTO product VALUES (124, 'Lipstick', 120.00, 'Red', 'Standard', 'Wax', 50, 7);
INSERT INTO product VALUES (125, 'Foundation', 190.00, 'Beige', 'Standard', 'Liquid', 90, 7);
INSERT INTO product VALUES (126, 'Foundation', 190.00, 'Ivory', 'Standard', 'Liquid', 90, 7);
INSERT INTO product VALUES (127, 'Mascara', 150.00, 'Black', 'Standard', 'Gel', 100, 7);
INSERT INTO product VALUES (128, 'Mascara', 150.00, 'Brown', 'Standard', 'Gel', 100, 7);
INSERT INTO product VALUES (129, 'Eyeshadow', 80.00, 'Black', 'Standard', 'Powder', 100, 7);
INSERT INTO product VALUES (130, 'Eyeshadow', 80.00, 'Brown', 'Standard', 'Powder', 80, 7);
INSERT INTO product VALUES (131, 'Eyeshadow', 80.00, 'Grey', 'Standard', 'Powder', 70, 7);
INSERT INTO product VALUES (132, 'Eyeshadow', 80.00, 'Blue', 'Standard', 'Powder', 60, 7);
INSERT INTO product VALUES (133, 'Eyeshadow', 80.00, 'Green', 'Standard', 'Powder', 65, 7);
INSERT INTO product VALUES (134, 'Eyeshadow', 80.00, 'Pink', 'Standard', 'Powder', 90, 7);
INSERT INTO product VALUES (135, 'Eyeshadow', 80.00, 'White', 'Standard', 'Powder', 90, 7);
INSERT INTO product VALUES (136, 'Eyeliner', 95.00, 'Black', 'Standard', 'Liquid', 90, 7);
INSERT INTO product VALUES (137, 'Eyeliner', 95.00, 'Brown', 'Standard', 'Liquid', 90, 7);
INSERT INTO product VALUES (138, 'Eyeliner', 95.00, 'White', 'Standard', 'Liquid', 90, 7);
INSERT INTO product VALUES (139, 'Eyeliner', 95.00, 'Blue', 'Standard', 'Liquid', 90, 7);
INSERT INTO product VALUES (140, 'Eyeliner', 95.00, 'Pink', 'Standard', 'Liquid', 90, 7);
INSERT INTO product VALUES (141, 'Blush', 180.00, 'Pink', 'Standard', 'Powder', 100, 8);
INSERT INTO product VALUES (142, 'Blush', 180.00, 'Red', 'Standard', 'Powder', 100, 8);
INSERT INTO product VALUES (143, 'Highlighter', 150.00, 'Champagne', 'Standard', 'Powder', 100, 8);
INSERT INTO product VALUES (144, 'Highlighter', 150.00, 'Rose Gold', 'Standard', 'Powder', 90, 8);
INSERT INTO product VALUES (145, 'Concealer', 170.00, 'Light', 'Standard', 'Liquid', 90, 8);
INSERT INTO product VALUES (146, 'Concealer', 170.00, 'Medium', 'Standard', 'Liquid', 100, 8);
INSERT INTO product VALUES (147, 'Concealer', 170.00, 'Dark', 'Standard', 'Liquid', 100, 8);
INSERT INTO product VALUES (148, 'Nail Polish', 60.00, 'White', 'Standard', 'Liquid', 80, 8);
INSERT INTO product VALUES (149, 'Nail Polish', 60.00, 'Black', 'Standard', 'Liquid', 80, 8);
INSERT INTO product VALUES (150, 'Nail Polish', 60.00, 'Pink', 'Standard', 'Liquid', 100, 8);
INSERT INTO product VALUES (151, 'Nail Polish', 60.00, 'Blue', 'Standard', 'Liquid', 80, 8);
INSERT INTO product VALUES (152, 'Nail Polish', 60.00, 'Red', 'Standard', 'Liquid', 60, 8);
INSERT INTO product VALUES (153, 'Nail Polish', 60.00, 'Orange', 'Standard', 'Liquid', 70, 8);
INSERT INTO product VALUES (154, 'Nail Polish', 60.00, 'Purple', 'Standard', 'Liquid', 50, 8);
INSERT INTO product VALUES (155, 'Nail Polish', 60.00, 'Burgundy', 'Standard', 'Liquid', 50, 8);
INSERT INTO product VALUES (156, 'Nail Polish', 60.00, 'Navy Blue', 'Standard', 'Liquid', 50, 8);
INSERT INTO product VALUES (157, 'Nail Polish', 60.00, 'Green', 'Standard', 'Liquid', 70, 8);
INSERT INTO product VALUES (158, 'Nail Polish', 60.00, 'Yellow', 'Standard', 'Liquid', 70, 8);
INSERT INTO product VALUES (159, 'Nail Polish', 60.00, 'Beige', 'Standard', 'Liquid', 70, 8);
INSERT INTO product VALUES (160, 'Nail Polish', 60.00, 'Brown', 'Standard', 'Liquid', 80, 8);
INSERT INTO product VALUES (161, 'Body Lotion', 280.00, 'White', 'Standard', 'Cream', 80, 9);
INSERT INTO product VALUES (162, 'Body Lotion', 280.00, 'Brown', 'Standard', 'Cream', 60, 9);
INSERT INTO product VALUES (163, 'Face Wash', 160.00, 'Clear', 'Standard', 'Liquid', 60, 9);
INSERT INTO product VALUES (164, 'Face Cream', 290.00, 'White', 'Standard', 'Cream', 60, 9);
INSERT INTO product VALUES (165, 'Eye Cream', 260.00, 'White', 'Standard', 'Cream', 70, 9);
INSERT INTO product VALUES (166, 'Sunscreen', 280.00, 'White', 'Standard', 'Cream', 70, 9);
INSERT INTO product VALUES (167, 'Lip Gloss', 110.00, 'Pink', 'Standard', 'Gel', 90, 9);
INSERT INTO product VALUES (168, 'Lip Gloss', 110.00, 'Red', 'Standard', 'Gel', 90, 9);
INSERT INTO product VALUES (169, 'Lip Gloss', 110.00, 'Nude', 'Standard', 'Gel', 100, 9);
INSERT INTO product VALUES (170, 'Lip Gloss', 110.00, 'Clear', 'Standard', 'Gel', 100, 9);
INSERT INTO product VALUES (171, 'Lip Liner', 80.00, 'Pink', 'Standard', 'Standard', 100, 9);
INSERT INTO product VALUES (172, 'Lip Liner', 80.00, 'Red', 'Standard', 'Standard', 100, 9);
INSERT INTO product VALUES (173, 'Lip Liner', 80.00, 'Brown', 'Standard', 'Standard', 100, 9);
INSERT INTO product VALUES (174, 'Lip Liner', 80.00, 'Nude', 'Standard', 'Standard', 150, 9);
INSERT INTO product VALUES (175, 'Shampoo', 140.00, 'White', 'Standard', 'Liquid', 50, 9);
INSERT INTO product VALUES (176, 'Conditioner', 145.00, 'White', 'Standard', 'Cream', 90, 9);
INSERT INTO product VALUES (177, 'Body Wash', 130.00, 'Clear', 'Standard', 'Liquid', 90, 9);
INSERT INTO product VALUES (178, 'Hand Cream', 90.00, 'White', 'Standard', 'Cream', 90, 9);
INSERT INTO product VALUES (179, 'Body Lotion', 150.00, 'White', 'Standard', 'Cream', 80, 9);
INSERT INTO product VALUES (180, 'Body Mist', 150.00, 'Pink', 'Standard', 'Liquid', 80, 9);
INSERT INTO product VALUES (181, 'Toothpaste', 60.00, 'White', 'Standard', 'Paste', 100, 10);
INSERT INTO product VALUES (182, 'Mouthwash', 80.00, 'Blue', 'Standard', 'Liquid', 100, 10);
INSERT INTO product VALUES (183, 'Deodorant', 80.00, 'Clear', 'Standard', 'Solid', 90, 10);
INSERT INTO product VALUES (184, 'Lip Balm', 55.00, 'Clear', 'Standard', 'Wax', 90, 10);
INSERT INTO product VALUES (185, 'Perfume - Spicy Wood', 920.00, 'Brown', 'Standard', 'Glass', 70, 10);
INSERT INTO product VALUES (186, 'Perfume - Fresh Citrus', 880.00, 'Yellow', 'Standard', 'Glass', 70, 10);
INSERT INTO product VALUES (187, 'Toothbrush - Soft', 7.50, 'Blue', 'Standard', 'Plastic', 80, 10);
INSERT INTO product VALUES (188, 'Toothbrush - Medium', 7.50, 'Blue', 'Standard', 'Plastic', 80, 10);
INSERT INTO product VALUES (189, 'Toothbrush - Firm', 7.50, 'Blue', 'Standard', 'Plastic', 80, 10);
INSERT INTO product VALUES (190, 'Toothbrush - Soft', 7.50, 'Pink', 'Standard', 'Plastic', 80, 10);
INSERT INTO product VALUES (191, 'Toothbrush - Medium', 7.50, 'Pink', 'Standard', 'Plastic', 80, 10);
INSERT INTO product VALUES (192, 'Toothbrush - Firm', 7.50, 'Pink', 'Standard', 'Plastic', 80, 10);
INSERT INTO product VALUES (193, 'Toothbrush - Soft', 7.50, 'Green', 'Standard', 'Plastic', 30, 10);
INSERT INTO product VALUES (194, 'Toothbrush - Medium', 7.50, 'Green', 'Standard', 'Plastic', 30, 10);
INSERT INTO product VALUES (195, 'Toothbrush - Firm', 7.50, 'Green', 'Standard', 'Plastic', 30, 10); //////
INSERT INTO product VALUES (196, 'Toothbrush - Soft', 7.50, 'Purple', 'Standard', 'Plastic', 40, 10);
INSERT INTO product VALUES (197, 'Toothbrush - Medium', 7.50, 'Purple', 'Standard', 'Plastic', 40, 10);
INSERT INTO product VALUES (198, 'Toothbrush - Firm', 7.50, 'Purple', 'Standard', 'Plastic', 40, 10);
INSERT INTO product VALUES (199, 'Travel Toothbrush', 8.50, 'Blue', 'Standard', 'Plastic', 60, 10);
INSERT INTO product VALUES (200, 'Travel Toothbrush', 8.50, 'Pink', 'Standard', 'Plastic', 60, 10);


-- Yeni Ürün Ekleme ya da Mevcut Ürüne Stok Arttırma
DELIMITER //

CREATE PROCEDURE AddOrUpdateProductStock(
    IN p_sellerID INT,
    IN p_productName VARCHAR(100),
    IN p_quantity INT,
    IN p_price DECIMAL(10,2),
    IN p_color VARCHAR(50),
    IN p_size VARCHAR(50),
    IN p_material VARCHAR(50)
)
BEGIN
    DECLARE v_productID INT DEFAULT NULL;
    DECLARE v_exists INT;

    -- Ürün var mı kontrol et
    SELECT COUNT(*) INTO v_exists
    FROM product
    WHERE sellerID = p_sellerID AND productName = p_productName;

    IF v_exists > 0 THEN
        -- ID'yi al
        SELECT productID INTO v_productID
        FROM product
        WHERE sellerID = p_sellerID AND productName = p_productName
        LIMIT 1;

        -- Stok güncelle
        UPDATE product
        SET stock = stock + p_quantity
        WHERE productID = v_productID;
    ELSE
        -- Yeni ürün ekle
        INSERT INTO product(productName, price, color, size, material, stock, sellerID)
        VALUES (p_productName, p_price, p_color, p_size, p_material, p_quantity, p_sellerID);
    END IF;
END//

DELIMITER ;

-- Kargo Firmasını Gösteren Function
DELIMITER //

CREATE FUNCTION GetShippingCompanyName(p_sellerID INT)
RETURNS VARCHAR(100)
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE companyName VARCHAR(100);

    SELECT sc.companyName INTO companyName
    FROM shipping_company sc
    JOIN seller s ON s.shipmentID = sc.shipmentID
    WHERE s.sellerID = p_sellerID;

    RETURN companyName;
END//

DELIMITER ;

-- Gelirleri Hesaplayan Function
DELIMITER //
CREATE FUNCTION GetSellerIncome2(p_sellerID INT)
RETURNS DECIMAL(10,2)
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE total_income DECIMAL(10,2);

    SELECT SUM(p.price * op.quantity)
    INTO total_income
    FROM order_product op
    JOIN product p ON op.productID = p.productID
    WHERE p.sellerID = p_sellerID;

    RETURN IFNULL(total_income, 0);
END;//
DELIMITER ;

-- Sadece aktif kuponu varsa engelle
DELIMITER //

CREATE TRIGGER check_active_coupon
BEFORE INSERT ON coupon
FOR EACH ROW
BEGIN
    DECLARE active_coupon_count INT;

    SELECT COUNT(*) INTO active_coupon_count
    FROM coupon
    WHERE sellerID = NEW.sellerID
      AND expirationDate > NOW(); -- sadece süresi dolmamış olanlar

    IF active_coupon_count >= 1 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Seller already has an active coupon.';
    END IF;
END//

DELIMITER ;

-- Stok Azaltma Trigger’ı

DELIMITER //

CREATE TRIGGER reduce_stock_after_order
AFTER INSERT ON order_product
FOR EACH ROW
BEGIN
    UPDATE product
    SET stock = stock - NEW.quantity
    WHERE productID = NEW.productID;
END; //

DELIMITER ;
-- Event Scheduler MySQL'de zamanlayıcı görevi gören bir sistemdir.
SET GLOBAL event_scheduler = ON;
-- Kupon süresi geçmişse otomatik silme
CREATE EVENT IF NOT EXISTS delete_expired_coupons
ON SCHEDULE EVERY 1 DAY
DO
  DELETE FROM coupon WHERE expirationDate < CURDATE();

-- dk
-- renk size ve beden  filtreleme 
DELIMITER //

CREATE FUNCTION is_product_available(
    p_color VARCHAR(30),
    p_size VARCHAR(10),
    p_material VARCHAR(50)
)
RETURNS BOOLEAN
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE result BOOLEAN;

    SELECT EXISTS (
        SELECT 1
        FROM product
        WHERE (p_color IS NULL OR color = p_color)
          AND (p_size IS NULL OR size = p_size)
          AND (p_material IS NULL OR material = p_material)
          AND stock > 0
    ) INTO result;

    RETURN result;
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER trg_check_stock_before_cart_insert
BEFORE INSERT ON cart_item
FOR EACH ROW
BEGIN
    DECLARE current_stock INT;

    -- şuanki stoğu al
    SELECT stock INTO current_stock
    FROM product
    WHERE productID = NEW.productID;

    -- istenen miktar fazla mı kontrolü
    IF NEW.quantity > current_stock THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Insufficient stock: Cannot add this quantity to the cart.';
    END IF;
END//

DELIMITER ;

-- aynı gün oynanıp oynanmadığını kontrol ediyor
DELIMITER $$

CREATE FUNCTION can_play_today(p_customerID INT, p_gameID INT)
RETURNS BOOLEAN
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE play_count INT;

    SELECT COUNT(*) INTO play_count
    FROM plays
    WHERE customerID = p_customerID 
      AND gameID = p_gameID 
      AND DATE(playTime) = CURDATE();

    RETURN play_count = 0;
END $$

DELIMITER ;


DELIMITER //
CREATE PROCEDURE add_to_cart(IN cartID INT, IN productID INT, IN quantity INT)
BEGIN
    INSERT INTO cart_item (cartID, productID, quantity)
    VALUES (cartID, productID, quantity)
    ON DUPLICATE KEY UPDATE quantity = quantity + quantity;
END //
DELIMITER ;
