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

-- 4. GAME TABLE
CREATE TABLE game (
    gameID INT AUTO_INCREMENT PRIMARY KEY,
    gameName VARCHAR(100),
    gameType VARCHAR(50),
    resetHours INT
);

-- 5. COUPON TABLE
CREATE TABLE coupon (
    couponID INT AUTO_INCREMENT PRIMARY KEY,
    couponCode VARCHAR(50) UNIQUE,
    discountAmount DECIMAL(5,2),
    expirationDate DATE,
    sellerID INT,
    gameID INT,
    FOREIGN KEY (sellerID) REFERENCES seller(sellerID),
    FOREIGN KEY (gameID) REFERENCES game(gameID)
);

-- 6. SELLER TABLE
CREATE TABLE seller (
    sellerID INT AUTO_INCREMENT PRIMARY KEY,
    sellerName VARCHAR(100) UNIQUE,
    experience INT,
    rating DECIMAL(3,2),
    shipmentID INT,
    FOREIGN KEY (shipmentID) REFERENCES shipping_company(shipmentID)
);

-- 7. SHIPPING_COMPANY TABLE
CREATE TABLE shipping_company (
    shipmentID INT AUTO_INCREMENT PRIMARY KEY,
    companyName VARCHAR(255) UNIQUE
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


-- ÖRNEK VERİ (İsteğe bağlı)
INSERT INTO customer VALUES (1, 'Ali Veli', '05321234567', 'ali@example.com', '12345', 175, 25, NULL);
INSERT INTO shipping_company VALUES (1, 'Yurtiçi Kargo');
INSERT INTO seller VALUES (1, 'OyunSatıcısı', 5, 4.7, 1);
INSERT INTO product VALUES (1, 'Oyun Mouse', 450.00, 'Siyah', 'Orta', 'Plastik', 100, 1);
INSERT INTO game VALUES (1, 'Spin Master', 'Luck', 24);


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
    DECLARE v_productID INT;

    SELECT productID INTO v_productID
    FROM product
    WHERE sellerID = p_sellerID AND productName = p_productName
    LIMIT 1;

    IF v_productID IS NOT NULL THEN
        -- Ürün zaten varsa stoğu artır
        UPDATE product
        SET stock = stock + p_quantity
        WHERE productID = v_productID;
    ELSE
        -- Yeni ürün ekle
        INSERT INTO product(productName, price, color, size, material, stock, sellerID)
        VALUES (p_productName, p_price, p_color, p_size, p_material, p_quantity, p_sellerID);
    END IF;
END //

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
CREATE FUNCTION GetSellerIncome(p_sellerID INT)
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

