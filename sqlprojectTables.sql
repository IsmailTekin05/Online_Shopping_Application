-- SQL PROJECT: ONLINE SHOPPING SYSTEM

-- 1. CUSTOMER TABLE
CREATE TABLE customer (
    customerID INT PRIMARY KEY,
    customer_name VARCHAR(50),
    phone_number VARCHAR(11),
    email VARCHAR(100),
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
    gameID INT PRIMARY KEY,
    gameName VARCHAR(100),
    gameType VARCHAR(50),
    resetHours INT
);

-- 5. COUPON TABLE
CREATE TABLE coupon (
    couponID INT PRIMARY KEY,
    couponCode VARCHAR(50),
    discountAmount DECIMAL(5,2),
    expirationDate DATE,
    sellerID INT,
    gameID INT,
    FOREIGN KEY (sellerID) REFERENCES seller(sellerID),
    FOREIGN KEY (gameID) REFERENCES game(gameID)
);

-- 6. SELLER TABLE
CREATE TABLE seller (
    sellerID INT PRIMARY KEY,
    sellerName VARCHAR(100),
    experience INT,
    rating DECIMAL(3,2),
    shipmentID INT,
    FOREIGN KEY (shipmentID) REFERENCES shipping_company(shipmentID)
);

-- 7. SHIPPING_COMPANY TABLE
CREATE TABLE shipping_company (
    shipmentID INT PRIMARY KEY,
    companyName VARCHAR(255)
);

-- 8. PRODUCT TABLE
CREATE TABLE product (
    productID INT PRIMARY KEY,
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
    cartID INT PRIMARY KEY,
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
    comments TEXT,
    rating_date DATE,
    PRIMARY KEY (productID, customerID),
    FOREIGN KEY (productID) REFERENCES product(productID),
    FOREIGN KEY (customerID) REFERENCES customer(customerID)
);

-- 13. ORDER TABLE
CREATE TABLE orders (
    orderID INT PRIMARY KEY,
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
