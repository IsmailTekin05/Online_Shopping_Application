-- MySQL dump 10.13  Distrib 8.0.41, for macos15 (arm64)
--
-- Host: localhost    Database: project_database
-- ------------------------------------------------------
-- Server version	8.0.41


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `address_name` varchar(50) NOT NULL,
  `customerID` int NOT NULL,
  `city` varchar(50) DEFAULT NULL,
  `district` varchar(50) DEFAULT NULL,
  `street` varchar(50) DEFAULT NULL,
  `building_number` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`address_name`,`customerID`),
  KEY `customerID` (`customerID`),
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `cartID` int NOT NULL AUTO_INCREMENT,
  `customerID` int DEFAULT NULL,
  PRIMARY KEY (`cartID`),
  KEY `customerID` (`customerID`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `cartID` int NOT NULL,
  `productID` int NOT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`cartID`,`productID`),
  KEY `productID` (`productID`),
  CONSTRAINT `cart_item_ibfk_1` FOREIGN KEY (`cartID`) REFERENCES `cart` (`cartID`),
  CONSTRAINT `cart_item_ibfk_2` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cartitem_product`
--

DROP TABLE IF EXISTS `cartitem_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartitem_product` (
  `cartID` int NOT NULL,
  `productID` int NOT NULL,
  PRIMARY KEY (`cartID`,`productID`),
  KEY `productID` (`productID`),
  CONSTRAINT `cartitem_product_ibfk_1` FOREIGN KEY (`cartID`) REFERENCES `cart` (`cartID`),
  CONSTRAINT `cartitem_product_ibfk_2` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `couponID` int NOT NULL AUTO_INCREMENT,
  `couponCode` varchar(50) DEFAULT NULL,
  `discountAmount` decimal(5,2) DEFAULT NULL,
  `expirationDate` date DEFAULT NULL,
  `sellerID` int DEFAULT NULL,
  `gameID` int DEFAULT NULL,
  PRIMARY KEY (`couponID`),
  UNIQUE KEY `couponCode` (`couponCode`),
  KEY `sellerID` (`sellerID`),
  KEY `gameID` (`gameID`),
  CONSTRAINT `coupon_ibfk_1` FOREIGN KEY (`sellerID`) REFERENCES `seller` (`sellerID`),
  CONSTRAINT `coupon_ibfk_2` FOREIGN KEY (`gameID`) REFERENCES `game` (`gameID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customerID` int NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(50) DEFAULT NULL,
  `phone_number` varchar(11) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `height` int DEFAULT NULL,
  `age` int DEFAULT NULL,
  `referrerID` int DEFAULT NULL,
  PRIMARY KEY (`customerID`),
  UNIQUE KEY `phone_number` (`phone_number`),
  UNIQUE KEY `email` (`email`),
  KEY `referrerID` (`referrerID`),
  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`referrerID`) REFERENCES `customer` (`customerID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_product`
--

DROP TABLE IF EXISTS `customer_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_product` (
  `customerID` int NOT NULL,
  `productID` int NOT NULL,
  `quantity` int DEFAULT '1',
  PRIMARY KEY (`customerID`,`productID`),
  KEY `productID` (`productID`),
  CONSTRAINT `customer_product_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`),
  CONSTRAINT `customer_product_ibfk_2` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `favorites`
--

DROP TABLE IF EXISTS `favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorites` (
  `productID` int NOT NULL,
  `customerID` int NOT NULL,
  PRIMARY KEY (`productID`,`customerID`),
  KEY `customerID` (`customerID`),
  CONSTRAINT `favorites_ibfk_1` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`),
  CONSTRAINT `favorites_ibfk_2` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game` (
  `gameID` int NOT NULL AUTO_INCREMENT,
  `gameName` varchar(100) DEFAULT NULL,
  `gameType` varchar(50) DEFAULT NULL,
  `resetHours` int DEFAULT NULL,
  PRIMARY KEY (`gameID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invites`
--

DROP TABLE IF EXISTS `invites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invites` (
  `customerID` int NOT NULL,
  `referrerID` int NOT NULL,
  PRIMARY KEY (`customerID`,`referrerID`),
  KEY `referrerID` (`referrerID`),
  CONSTRAINT `invites_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`),
  CONSTRAINT `invites_ibfk_2` FOREIGN KEY (`referrerID`) REFERENCES `customer` (`customerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_product`
--
ALTER TABLE order_product ADD COLUMN priceAtTimeOfOrder DECIMAL(10, 2);
DROP TABLE IF EXISTS `order_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_product` (
  `orderID` int NOT NULL,
  `productID` int NOT NULL,
  PRIMARY KEY (`orderID`,`productID`),
  KEY `productID` (`productID`),
  CONSTRAINT `order_product_ibfk_1` FOREIGN KEY (`orderID`) REFERENCES `orders` (`orderID`),
  CONSTRAINT `order_product_ibfk_2` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `orderID` int NOT NULL AUTO_INCREMENT,
  `orderDate` date DEFAULT NULL,
  `customerID` int DEFAULT NULL,
  `address_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`orderID`),
  KEY `customerID` (`customerID`),
  KEY `address_name` (`address_name`,`customerID`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`address_name`, `customerID`) REFERENCES `address` (`address_name`, `customerID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_info`
--

DROP TABLE IF EXISTS `payment_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_info` (
  `cardNumber` varchar(20) NOT NULL,
  `expirationDate` varchar(25) DEFAULT NULL,
  `cvc` varchar(5) DEFAULT NULL,
  `card_owner` varchar(50) DEFAULT NULL,
  `cardName` varchar(50) DEFAULT NULL,
  `customerID` int DEFAULT NULL,
  PRIMARY KEY (`cardNumber`),
  KEY `customerID` (`customerID`),
  CONSTRAINT `payment_info_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `plays`
--

DROP TABLE IF EXISTS `plays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plays` (
  `customerID` int NOT NULL,
  `gameID` int NOT NULL,
  `playTime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`customerID`,`gameID`),
  KEY `gameID` (`gameID`),
  CONSTRAINT `plays_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`),
  CONSTRAINT `plays_ibfk_2` FOREIGN KEY (`gameID`) REFERENCES `game` (`gameID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `productID` int NOT NULL AUTO_INCREMENT,
  `productName` varchar(100) DEFAULT NULL,
  `price` decimal(5,2) DEFAULT NULL,
  `color` varchar(30) DEFAULT NULL,
  `size` varchar(10) DEFAULT NULL,
  `material` varchar(50) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `sellerID` int DEFAULT NULL,
  PRIMARY KEY (`productID`),
  KEY `sellerID` (`sellerID`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`sellerID`) REFERENCES `seller` (`sellerID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `productID` int NOT NULL,
  `customerID` int NOT NULL,
  `stars` int DEFAULT NULL,
  `comment` text,
  `rating_date` date DEFAULT NULL,
  PRIMARY KEY (`productID`,`customerID`),
  KEY `customerID` (`customerID`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`),
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `seller`
--

DROP TABLE IF EXISTS `seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seller` (
  `sellerID` int NOT NULL AUTO_INCREMENT,
  `sellerName` varchar(100) DEFAULT NULL,
  `experience` int DEFAULT NULL,
  `rating` decimal(3,2) DEFAULT NULL,
  `shipmentID` int DEFAULT NULL,
  PRIMARY KEY (`sellerID`),
  UNIQUE KEY `sellerName` (`sellerName`),
  KEY `shipmentID` (`shipmentID`),
  CONSTRAINT `seller_ibfk_1` FOREIGN KEY (`shipmentID`) REFERENCES `shipping_company` (`shipmentID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shipping_company`
--

DROP TABLE IF EXISTS `shipping_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_company` (
  `shipmentID` int NOT NULL AUTO_INCREMENT,
  `companyName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`shipmentID`),
  UNIQUE KEY `companyName` (`companyName`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'project_database'
--

--
-- Dumping routines for database 'project_database'
--
/*!50003 DROP FUNCTION IF EXISTS `can_play_today` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `can_play_today`(p_customerID INT, p_gameID INT) RETURNS tinyint(1)
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE play_count INT;

    SELECT COUNT(*) INTO play_count
    FROM plays
    WHERE customerID = p_customerID 
      AND gameID = p_gameID 
      AND DATE(playTime) = CURDATE();

    RETURN play_count = 0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `GetSellerIncome` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `GetSellerIncome`(p_sellerID INT) RETURNS decimal(10,2)
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE total_income DECIMAL(10,2);

    SELECT SUM(p.price * op.quantity)
    INTO total_income
    FROM order_product op
    JOIN product p ON op.productID = p.productID
    WHERE p.sellerID = p_sellerID;

    RETURN IFNULL(total_income, 0);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `GetShippingCompanyName` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `GetShippingCompanyName`(p_sellerID INT) RETURNS varchar(100) CHARSET utf8mb4
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE companyName VARCHAR(100);

    SELECT sc.companyName INTO companyName
    FROM shipping_company sc
    JOIN seller s ON s.shipmentID = sc.shipmentID
    WHERE s.sellerID = p_sellerID;

    RETURN companyName;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `is_valid_customer_login` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `is_valid_customer_login`(
    p_email VARCHAR(100),
    p_password VARCHAR(100)
) RETURNS tinyint(1)
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE result BOOLEAN;

    SELECT EXISTS (
        SELECT 1
        FROM customer
        WHERE email = p_email AND password = p_password
    ) INTO result;

    RETURN result;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `is_valid_seller_login` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `is_valid_seller_login`(
    p_sellerName VARCHAR(100),
    p_sellerID INT
) RETURNS tinyint(1)
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE result BOOLEAN;

    SELECT EXISTS (
        SELECT 1
        FROM seller
        WHERE sellerName = p_sellerName AND sellerID = p_sellerID
    ) INTO result;

    RETURN result;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `AddOrUpdateProductStock` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddOrUpdateProductStock`(
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
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_to_cart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_to_cart`(IN cartID INT, IN productID INT, IN quantity INT)
BEGIN
    INSERT INTO cart_item (cartID, productID, quantity)
    VALUES (cartID, productID, quantity)
    ON DUPLICATE KEY UPDATE quantity = quantity + quantity;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `checkout_cart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
DELIMITER //


CREATE PROCEDURE checkout_cart(
    IN p_cartID INT, 
    IN p_couponCode VARCHAR(50), 
    IN p_addressName VARCHAR(50)
)
BEGIN
    DECLARE v_customerID INT;
    DECLARE v_new_orderID INT;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK; -- Hata anında tüm yapılanları geri al
    END;

    START TRANSACTION;

    -- Sepetin sahibini (customerID) bul
    SELECT customerID INTO v_customerID FROM cart WHERE cartID = p_cartID;
    
    -- Müşteri veya adresi yoksa işlemi durdur
    IF v_customerID IS NULL OR p_addressName IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Customer or Address not found.';
        ROLLBACK;
    ELSE
        -- 1. Yeni bir sipariş oluştur (orders tablosuna ekle)
        -- DÜZELTME: Var olmayan 'totalPrice' sütunu kaldırıldı.
        INSERT INTO orders (orderDate, customerID, address_name) VALUES (CURDATE(), v_customerID, p_addressName);
        
        -- Az önce oluşturulan siparişin ID'sini al
        SET v_new_orderID = LAST_INSERT_ID();

        -- 2. Ürün Stoklarını Güncelle
        UPDATE product p
        JOIN cart_item ci ON p.productID = ci.productID
        SET p.stock = p.stock - ci.quantity
        WHERE ci.cartID = p_cartID;

        -- 3. Sepetteki Ürünleri Siparişe Kopyala
        -- DİKKAT: 'order_product' tablosunda 'quantity' olmadığı için miktar bilgisi kaybolur.
        INSERT INTO order_product (orderID, productID)
        SELECT v_new_orderID, productID FROM cart_item WHERE cartID = p_cartID;

        -- 4. Sepeti Temizle
        DELETE FROM cart_item WHERE cartID = p_cartID;

        -- 5. Kuponu Sil (Eğer kullanıldıysa)
        -- DÜZELTME: 'discountRate' yerine doğru sütun adı 'discountAmount' kullanıldı (gerçi burada sadece siliniyor).
        IF p_couponCode IS NOT NULL AND p_couponCode != '' THEN
            DELETE FROM coupon WHERE couponCode = p_couponCode;
        END IF;

        -- Her şey yolunda gittiyse tüm işlemleri kalıcı olarak kaydet
        COMMIT;
    END IF;

END //

DELIMITER ;



DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_customer` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_customer`(
    IN p_name VARCHAR(255),
    IN p_phone VARCHAR(20),
    IN p_email VARCHAR(255),
    IN p_password VARCHAR(255),
    IN p_height INT,
    IN p_age INT,
    IN p_referrer_id INT,
    OUT p_customer_id INT
)
BEGIN
    INSERT INTO customer (
        customer_name, phone_number, email, password,
        height, age, referrerID
    )
    VALUES (
        p_name, p_phone, p_email, p_password,
        p_height, p_age, p_referrer_id
    );

    SET p_customer_id = LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_seller` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_seller`(
    IN p_name VARCHAR(255),
    IN p_experience INT,
    IN p_rating DOUBLE,
    IN p_shipment_id INT,
    OUT p_seller_id INT
)
BEGIN
    INSERT INTO seller (sellerName, experience, rating, shipmentID)
    VALUES (p_name, p_experience, p_rating, p_shipment_id);

    SET p_seller_id = LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


-- İşlem bittikten sonra, veritabanı bütünlüğünü korumak için kontrolleri tekrar açıyoruz.
SET FOREIGN_KEY_CHECKS = 1;
INSERT INTO customer VALUES (1, 'Ali YILMAZ', '05321234567', 'ali@example.com', '12345', 175, 25, NULL);
Insert into customer VALUES (2, 'Ayşe BAĞCI', '05555546555' , 'ayse@example.com','12345' , 165,28,NULL);
Insert into customer VALUES (3, 'Betül SEVGI', '05534546525' , 'betul@example.com','12345' , 198,34,NULL);
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
INSERT INTO product VALUES (1, 'T-shirt', 160.00, 'Black', 'S', 'Cotton', 100, 1);
INSERT INTO product VALUES (2, 'T-shirt', 160.00, 'Black', 'M', 'Cotton', 100, 1);
INSERT INTO product VALUES (3, 'T-shirt', 160.00, 'Black', 'L', 'Cotton', 50, 1);
INSERT INTO product VALUES (4, 'T-shirt', 140.00, 'White', 'S', 'Cotton', 100, 1);
INSERT INTO product VALUES (5, 'T-shirt', 140.00, 'White', 'M', 'Cotton', 100, 1);
INSERT INTO product VALUES (6, 'T-shirt', 140.00, 'White', 'L', 'Cotton', 50, 1);
INSERT INTO product VALUES (7, 'T-shirt', 150.00, 'Blue', 'S', 'Cotton', 60, 1);
INSERT INTO product VALUES (8, 'T-shirt', 150.00, 'Blue', 'M', 'Cotton', 60, 1);
INSERT INTO product VALUES (9, 'T-shirt', 150.00, 'Blue', 'L', 'Cotton', 40, 1);
INSERT INTO product VALUES (10, 'T-shirt', 150.00, 'Green', 'S', 'Cotton', 60, 1);
INSERT INTO product VALUES (11, 'T-shirt', 150.00, 'Green', 'M', 'Cotton', 60, 1);
INSERT INTO product VALUES (12, 'T-shirt', 150.00, 'Green', 'L', 'Cotton', 40, 1);
INSERT INTO product VALUES (13, 'T-shirt', 150.00, 'Grey', 'S', 'Cotton', 70, 1);
INSERT INTO product VALUES (14, 'T-shirt', 150.00, 'Grey', 'M', 'Cotton', 70, 1);
INSERT INTO product VALUES (15, 'T-shirt', 150.00, 'Grey', 'L', 'Cotton', 50, 1);
INSERT INTO product VALUES (16, 'T-shirt', 155.00, 'Pink', 'XS', 'Cotton', 70, 1);
INSERT INTO product VALUES (17, 'T-shirt', 155.00, 'Pink', 'S', 'Cotton', 70, 1);
INSERT INTO product VALUES (18, 'T-shirt', 155.00, 'Pink', 'M', 'Cotton', 70, 1);
INSERT INTO product VALUES (19, 'T-shirt', 155.00, 'Pink', 'L', 'Cotton', 50, 1);
INSERT INTO product VALUES (20, 'T-shirt', 155.00, 'Pink', 'XL', 'Cotton', 30, 1);
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
INSERT INTO product VALUES (71, 'Jacket', 350.00, 'Blue', 'XS', 'Denim', 100, 4);
INSERT INTO product VALUES (72, 'Jacket', 350.00, 'Blue', 'S', 'Denim', 100, 4);
INSERT INTO product VALUES (73, 'Jacket', 350.00, 'Blue', 'M', 'Denim', 100, 4);
INSERT INTO product VALUES (74, 'Jacket', 350.00, 'Blue', 'L', 'Denim', 100, 4);
INSERT INTO product VALUES (75, 'Jacket', 350.00, 'Blue', 'XL', 'Denim', 100, 4);
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
INSERT INTO product VALUES (195, 'Toothbrush - Firm', 7.50, 'Green', 'Standard', 'Plastic', 30, 10); 
INSERT INTO product VALUES (196, 'Toothbrush - Soft', 7.50, 'Purple', 'Standard', 'Plastic', 40, 10);
INSERT INTO product VALUES (197, 'Toothbrush - Medium', 7.50, 'Purple', 'Standard', 'Plastic', 40, 10);
INSERT INTO product VALUES (198, 'Toothbrush - Firm', 7.50, 'Purple', 'Standard', 'Plastic', 40, 10);
INSERT INTO product VALUES (199, 'Travel Toothbrush', 8.50, 'Blue', 'Standard', 'Plastic', 60, 10);
INSERT INTO product VALUES (200, 'Travel Toothbrush', 8.50, 'Pink', 'Standard', 'Plastic', 60, 10);

INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Fiyatına göre çok iyi, tavsiye ederim.', 1, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kumaşı çok kaliteli ve rahat.', 2, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Beden biraz küçük geldi, iade edeceğim.', 2, 3);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Renk canlı, çok beğendim.', 3, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Yıkamada çekme yaptı, dikkat edin.', 3, 2);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Beyaz tişörtler arasında en iyisi.', 4, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Basit ama şık bir ürün.', 4, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kalıbı tam oldu, çok memnun kaldım.', 5, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Hızlı kargo, teşekkürler.', 5, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Malzeme kalitesi vasat.', 6, 2);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Renk görselden farklı geldi.', 6, 3);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Mavi rengi çok hoş.', 7, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Yazlık için ideal, terletmiyor.', 7, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Beklediğimden daha iyi çıktı.', 8, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Normal bir tişört, özel bir yanı yok.', 8, 3);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Beden tablosuna uyun, tam oluyor.', 9, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Biraz ince bir kumaşı var.', 9, 3);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Yeşil çok yakıştı, teşekkürler.', 10, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Günlük kullanım için uygun.', 10, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Rengi soldu yıkamadan sonra.', 11, 2);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok bekledim gelmesi için.', 11, 3);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kışın bile giyilebilir, o kadar kaliteli.', 12, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Her şeyine bayıldım!', 12, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Gri rengi çok asil duruyor.', 13, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Sade ve şık, her şeye uyuyor.', 13, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Tişörtün dokusu çok hoş.', 14, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Beklentimi karşılamadı.', 14, 2);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Büyük beden bulmak zordu, bu harika oldu.', 15, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Pamuklu olması çok iyi.', 15, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Pembe rengi çok tatlı.', 16, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Tam bir yaz ürünü.', 16, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kumaşı yumuşacık, çok rahat.', 17, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok kırışıyor.', 17, 3);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Hediye için aldım, çok beğenildi.', 18, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Umarım uzun süre dayanır.', 18, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Renk aynı, beden doğru.', 19, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Biraz pahalı buldum.', 19, 3);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'XL bedeni bile tam oturdu, teşekkürler.', 20, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Kullanışlı bir ürün.', 20, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kot pantolon efsane!', 21, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Rengi ve duruşu mükemmel.', 21, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Esnek yapısı sayesinde çok rahat.', 22, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Normalde S giyerim, bu da tam oldu.', 22, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Yüksek bel olması çok iyi.', 23, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Kalitesi beklediğimden iyi.', 23, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Mavi kot vazgeçilmezim oldu.', 24, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Paçası biraz uzun geldi.', 24, 3);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Tam beden, çok beğendim.', 25, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Yırtık detayları çok güzel.', 25, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Gri kotlar çok moda.', 26, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Her kombine uyuyor.', 26, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Rahatlığına bayıldım.', 27, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Biraz soluk bir gri.', 27, 3);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kotun kesimi çok başarılı.', 28, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Her mevsime uygun.', 28, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Yüksek bel ve dar paça, tam istediğim gibi.', 29, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok şık bir ürün.', 29, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Beklediğimden daha iyi.', 30, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Kumaşı tok, duruşu güzel.', 30, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Şortun rengi harika.', 31, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Yaz için ideal, hafif.', 31, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kumaşı pamuklu, terletmiyor.', 32, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Beden olarak tam oldu.', 32, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Çok beğendim, tam istediğim gibi.', 33, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Modeli çok şık.', 33, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Cepleri kullanışlı.', 34, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Denim kalitesi ortalama.', 34, 3);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Tam beden, çok rahat.', 35, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Fiyat performans ürünü.', 35, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Gri şort çok havalı.', 36, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Spor kombinler için ideal.', 36, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kumaşı esnek, hareket özgürlüğü sağlıyor.', 37, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Yazın severek giyiyorum.', 37, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Çok kaliteli duruyor.', 38, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Yüksek belli olması çok iyi.', 38, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Beklediğimden daha iyi çıktı.', 39, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Kesimi vücuda oturuyor.', 39, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Hızlı teslimat, teşekkürler.', 40, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Rengi görseldeki gibi.', 40, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Elbise çok şık, tam bir kurtarıcı parça.', 41, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Siyah rengi çok asil.', 41, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kumaşı ipek gibi, çok narin.', 42, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Özel günler için harika.', 42, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Modeli çok zarif.', 43, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Fiyatına göre çok kaliteli.', 43, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Çok rahat bir elbise.', 44, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Kırışmıyor, bu çok iyi.', 44, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Herkes nereden aldığımı sordu.', 45, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Tam bedeninizi alın.', 45, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Beyaz elbise yazın favorim.', 46, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok romantik bir hava katıyor.', 46, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'İnce ama iç göstermiyor.', 47, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok zarif ve şık.', 47, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kalıbı çok güzel oturuyor.', 48, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Nişan için aldım, çok yakıştı.', 48, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Her dolapta olması gereken bir parça.', 49, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Fiyatı uygun, kalitesi de iyi.', 49, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Çok hafif ve havadar.', 50, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Tam bir yaz akşamı elbisesi.', 50, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Pembe rengi çok tatlı.', 51, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Bahar aylarında harika duruyor.', 51, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kumaşı teni yormuyor.', 52, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok hoş bir model.', 52, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Tam bir prenses elbisesi.', 53, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok beğenildi.', 53, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Renk canlı, görseldeki gibi.', 54, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Yıkamadan sonra bozulma yapmadı.', 54, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Uzun boylular için ideal.', 55, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Şık ve rahat.', 55, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Yeşil elbise çok moda.', 56, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok canlı bir renk.', 56, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'İpek kumaşın kalitesi belli oluyor.', 57, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Özel bir davet için aldım.', 57, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Akşam yemekleri için ideal.', 58, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok tarz bir kesimi var.', 58, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Beden tablosu doğru.', 59, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Tam bir fiyat performans ürünü.', 59, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Çok rahat ve şık.', 60, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'İnce yapısına rağmen sıcak tutuyor.', 60, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Deri ceket efsane duruyor.', 61, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kalıbı tam, çok beğendim.', 62, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Gerçek deri, kalitesi tartışılmaz.', 62, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Her mevsime uygun.', 63, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok havalı bir duruşu var.', 63, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Motorcu ceketi gibi.', 64, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Umarım uzun ömürlü olur.', 64, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Fiyatına değer bir ürün.', 65, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Ceket çok güzel.', 65, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kahverengi deri ceket çok şık.', 66, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Günlük kullanıma uygun.', 66, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kalıbı tam, çok beğendim.', 67, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Rengi görseldeki gibi.', 67, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Her kombine uyuyor.', 68, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (1, 'Çok beğenildi.', 68, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Şık ve rahat.', 69, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Beklediğimden daha iyi çıktı.', 69, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (1, 'Tam beden, çok memnun kaldım.', 70, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kumaşı kaliteli.', 70, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Denim ceket çok havalı.', 71, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Mavi tonu çok güzel.', 71, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Her mevsim giyilir.', 72, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Kalıbı tam oldu.', 72, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kot ceketin kalitesi süper.', 73, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok kullanışlı.', 73, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Cepleri çok güzel.', 74, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Biraz ağır bir ceket.', 74, 3);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Beklediğimden daha iyi.', 75, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Kot pantolonlarla çok yakışıyor.', 75, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Beyaz ceket çok şık.', 76, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Yaz akşamları için ideal.', 76, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kumaşı hafif, terletmiyor.', 77, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok modern bir model.', 77, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Tam beden, çok rahat.', 78, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Fiyatı uygun.', 78, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Yüksek kalite.', 79, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok memnun kaldım.', 79, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Beklentimi karşıladı.', 80, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Şık ve kullanışlı.', 80, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Siyah etek kurtarıcı bir parça.', 81, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (1, 'Ofis şıklığı için ideal.', 81, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kumaşı çok güzel, dökümlü.', 82, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Tam beden, çok rahat.', 82, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (1, 'Çok şık duruyor.', 83, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Fiyatına göre çok iyi.', 83, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Her mevsime uygun.', 84, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Kırışmıyor, çok pratik.', 84, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Uzun boylulara da yakışır.', 85, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Kaliteli bir ürün.', 85, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Gri etek çok tarz.', 86, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (1, 'Denim etekler çok seviyorum.', 86, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kumaşı esnek, hareket özgürlüğü sağlıyor.', 87, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Günlük kullanım için ideal.', 87, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Çok rahat ve şık.', 88, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Tam beden, harika oldu.', 88, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Yüksek bel olması çok iyi.', 89, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok beğenildi.', 89, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Hızlı kargo, teşekkürler.', 90, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Kesimi çok güzel.', 90, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Şapka çok tatlı, siyah rengi her şeye uyuyor.', 91, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Günlük kullanım için ideal.', 91, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Beyaz şapka yazın vazgeçilmezi.', 92, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Kumaşı kaliteli.', 92, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Gri şapka çok şık duruyor.', 93, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Başımı tam sardı, çok rahat.', 93, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Mavi rengi çok canlı.', 94, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (1, 'Uygun fiyatlı ve kaliteli.', 94, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Yeşil şapka çok sevdim.', 95, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Her kombine uyuyor.', 95, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Pembe şapka çok sevimli.', 96, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Tam bir yazlık ürün.', 96, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Kahverengi şapka çok doğal duruyor.', 97, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Çok kullanışlı.', 97, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (1, 'Kırmızı şapka enerjik.', 98, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Fiyatına değer.', 98, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (2, 'Sarı şapka çok canlı.', 99, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Güneşten koruması iyi.', 99, 4);
INSERT INTO review (customerID, comment, productID, stars) VALUES (1, 'Mor şapka çok sıra dışı.', 100, 5);
INSERT INTO review (customerID, comment, productID, stars) VALUES (3, 'Herkese tavsiye ederim.', 100, 5); 
SET FOREIGN_KEY_CHECKS = 0; -- İlişki kontrollerini geçici olarak kapat.

TRUNCATE TABLE game;
TRUNCATE TABLE coupon; -- Bu yöntemle sıralamanın bir önemi kalmaz.

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO game (gameID, gameName, gameType) VALUES (1, 'Spin Wheel Game', 'Chance');
INSERT INTO game (gameID, gameName, gameType) VALUES (2, 'Dice Roll Game', 'Chance');
INSERT INTO game (gameID, gameName, gameType) VALUES (3, 'Gift Box Game', 'Chance');
INSERT INTO game (gameID, gameName, gameType) VALUES (4, 'Tic Tac Toe', 'Skill');