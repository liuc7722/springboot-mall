-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: localhost    Database: my_system
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `created_date` timestamp NOT NULL,
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `count_down`
--

DROP TABLE IF EXISTS `count_down`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `count_down` (
  `id` int NOT NULL AUTO_INCREMENT,
  `deadline` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `count_down`
--

LOCK TABLES `count_down` WRITE;
/*!40000 ALTER TABLE `count_down` DISABLE KEYS */;
INSERT INTO `count_down` VALUES (1,'2023-12-03 00:00:00');
/*!40000 ALTER TABLE `count_down` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_verification_tokens`
--

DROP TABLE IF EXISTS `email_verification_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email_verification_tokens` (
  `token_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `verification_token` varchar(255) NOT NULL,
  `expiration_date` timestamp NOT NULL,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_verification_tokens`
--

LOCK TABLES `email_verification_tokens` WRITE;
/*!40000 ALTER TABLE `email_verification_tokens` DISABLE KEYS */;
INSERT INTO `email_verification_tokens` VALUES (1,23,'ff9f8ff7-29d3-4047-aaf8-37f8eda35e17','2023-12-14 07:39:23'),(2,24,'59e74d02-29c5-4c1a-92a6-3ad8dbb14d6f','2023-12-14 07:52:38'),(4,26,'2dc262dd-4be1-43aa-907b-57bf733131d1','2023-12-14 08:03:54'),(5,27,'43f6bcc0-d298-4c46-9d1c-f001dfd26b19','2023-12-14 08:08:06'),(6,28,'4fc4730e-5675-43fc-b122-d860e107a66d','2023-12-14 08:13:10'),(9,16,'437a764e-61c9-441d-bb51-621454c8e2b1','2023-12-14 09:39:35'),(10,25,'280ce2e3-5fca-434c-acaa-46b90d31e5d7','2023-12-15 08:52:13'),(12,21,'f311c6cb-e0e3-4f40-96af-55d306ed12d9','2023-12-15 09:25:30'),(13,29,'3faae419-8417-4ba6-89a3-eb7984c9fe8b','2023-12-15 09:53:13');
/*!40000 ALTER TABLE `email_verification_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `total_price` int NOT NULL,
  `order_date` timestamp NOT NULL,
  `shipping_address` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `transaction_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (95,16,5,'2023-12-14 17:28:06','HsinChu','SHIPPED','2023121502048112710'),(96,25,6000,'2023-12-14 20:39:14','HsinChu','SHIPPED','2023121502048117410'),(97,25,5000,'2023-12-14 20:42:25','HsinChu','SHIPPED','2023121502048117510'),(98,25,800,'2023-12-14 20:46:38','HsinChu','SHIPPED','2023121502048117610'),(99,25,350,'2023-12-14 20:48:07','HsinChu','SHIPPED','2023121502048117710'),(100,25,1500,'2023-12-14 20:49:08','HsinChu','SHIPPED','2023121502048117810');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `order_item_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`order_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (29,15,113,5,3000),(30,15,119,3,1800),(31,16,113,23,23),(32,17,113,23,23),(33,18,113,18,18),(34,19,113,18,18),(35,20,113,18,18),(36,21,113,18,18),(37,22,113,18,18),(38,23,113,18,18),(39,24,113,18,18),(40,25,113,18,18),(41,26,113,18,18),(42,27,113,18,18),(43,28,113,18,18),(44,29,113,18,18),(45,30,113,18,18),(46,31,113,18,18),(47,32,113,18,18),(48,33,113,18,18),(49,34,113,18,18),(50,35,113,18,18),(51,36,113,18,18),(52,37,113,18,18),(53,38,113,18,18),(54,39,113,18,18),(55,40,113,18,18),(56,41,113,18,18),(57,42,113,18,18),(58,43,113,18,18),(59,44,113,18,18),(60,45,113,18,18),(61,46,113,18,18),(62,47,113,18,18),(63,48,113,18,18),(64,49,113,18,18),(65,50,113,18,18),(66,51,113,18,18),(67,52,113,18,18),(68,53,113,18,18),(69,54,113,18,18),(70,55,113,18,18),(71,56,113,18,18),(72,57,113,18,18),(73,58,113,18,18),(74,59,113,18,18),(75,60,113,18,18),(76,61,113,18,18),(77,62,113,18,18),(78,63,113,18,18),(79,64,113,18,18),(80,65,113,18,18),(81,66,113,18,18),(82,67,113,18,18),(83,68,113,18,18),(84,69,113,18,18),(85,70,113,18,18),(86,71,113,18,18),(87,72,113,18,18),(88,73,113,18,18),(89,74,113,18,18),(90,75,113,18,18),(91,76,113,18,18),(92,77,113,18,18),(93,78,113,18,18),(94,79,113,18,18),(95,80,113,18,18),(96,81,157,3,3),(97,81,155,5,5),(98,82,157,3,3),(99,82,155,5,5),(100,82,154,3,3),(101,83,157,3,3),(102,83,155,5,5),(103,83,154,3,3),(104,84,113,18,18),(105,85,113,18,18),(106,86,113,18,18),(107,87,113,18,18),(108,88,113,18,18),(109,89,113,18,18),(110,90,155,7,7),(111,91,155,7,7),(112,92,155,5,5),(113,93,154,5,5),(114,94,154,5,5),(115,95,154,5,5),(116,96,180,4,6000),(117,97,178,1,5000),(118,98,179,1,800),(119,99,181,1,350),(120,100,180,1,1500);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `photo_url` varchar(512) DEFAULT NULL,
  `title` varchar(32) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `store_url` varchar(512) DEFAULT NULL,
  `store_name` varchar(32) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  `stock` int NOT NULL,
  `category` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (113,'https://images.unsplash.com/photo-1519027356316-9f99e11d8bac?q=80&w=3540&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','男性衣服1','好穿',500,'https://picsum.photos/200/300','storeName','2023-12-03 06:58:01','2023-12-14 16:22:04',830,'MAN_CLOTHES'),(119,'https://images.unsplash.com/photo-1499939667766-4afceb292d05?q=80&w=3546&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','女性衣服1','好穿',600,'https://picsum.photos/200/300','storeName','2023-12-07 12:57:18','2023-12-13 05:06:29',4,'WOMAN_CLOTHES'),(120,'https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?q=80&w=2124&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','女性衣服2','好穿',1500,'https://picsum.photos/200/300','storeName','2023-12-07 12:57:29','2023-12-07 12:57:29',10,'WOMAN_CLOTHES'),(122,'https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?q=80&w=3397&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','女性褲子1','好穿',1200,'https://picsum.photos/200/300','storeName','2023-12-07 12:58:15','2023-12-07 12:58:15',15,'WOMAN_CLOTHES'),(123,'https://images.unsplash.com/photo-1594633312681-425c7b97ccd1?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','女性褲子2','好穿',2500,'https://picsum.photos/200/300','storeName','2023-12-07 12:58:27','2023-12-07 12:58:27',30,'WOMAN_CLOTHES'),(124,'https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1?q=80&w=3540&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','戶外旅行','好穿',800,'https://picsum.photos/200/300','storeName','2023-12-07 12:59:17','2023-12-07 12:59:17',10,'TRAVEL'),(125,'https://plus.unsplash.com/premium_photo-1664361480872-6416aab14696?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','戶外旅行2','好用',1300,'https://picsum.photos/200/300','storeName','2023-12-07 12:59:40','2023-12-07 12:59:40',8,'TRAVEL'),(126,'https://images.unsplash.com/photo-1507537064587-464384459bb7?q=80&w=3540&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','手機架','好用',800,'https://picsum.photos/200/300','storeName','2023-12-07 13:00:29','2023-12-07 13:00:29',40,'TRANSPORTATION'),(127,'https://images.unsplash.com/photo-1691422828898-ad25cfe956be?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','行車記錄器','好用',1800,'https://picsum.photos/200/300','storeName','2023-12-07 13:00:43','2023-12-07 13:00:43',20,'TRANSPORTATION'),(128,'https://plus.unsplash.com/premium_photo-1680985551009-05107cd2752c?q=80&w=3432&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','手機殼1','好看',300,'https://picsum.photos/200/300','storeName','2023-12-07 13:01:07','2023-12-07 13:01:07',50,'MOBILE'),(129,'https://images.unsplash.com/photo-1523206489230-c012c64b2b48?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','手機殼2','好看',799,'https://picsum.photos/200/300','storeName','2023-12-07 13:01:10','2023-12-07 13:01:10',50,'MOBILE'),(130,'https://plus.unsplash.com/premium_photo-1680623400141-7e129b7c56d0?q=80&w=3540&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','手機殼3','好看',250,'https://picsum.photos/200/300','storeName','2023-12-07 13:01:10','2023-12-07 13:01:10',50,'MOBILE'),(131,'https://picsum.photos/200/300?random=5','鍵盤','手感好',1299,'https://picsum.photos/200/300','storeName','2023-12-07 13:02:51','2023-12-07 13:02:51',30,'COMPUTER'),(132,'https://images.unsplash.com/photo-1527443195645-1133f7f28990?q=80&w=3540&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','Studio Display','手感好',5999,'https://picsum.photos/200/300','storeName','2023-12-07 13:03:06','2023-12-07 13:03:06',20,'COMPUTER'),(133,'https://plus.unsplash.com/premium_photo-1683436791486-508249532f52?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','螢幕2','手感好',4999,'https://picsum.photos/200/300','storeName','2023-12-07 13:04:10','2023-12-07 13:03:10',20,'COMPUTER'),(134,'https://plus.unsplash.com/premium_photo-1687533706288-a5c920a87359?q=80&w=3388&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','螢幕3','手感好',3000,'https://picsum.photos/200/300','storeName','2023-12-07 13:05:10','2023-12-07 13:03:10',20,'COMPUTER'),(135,'https://images.unsplash.com/photo-1567690187548-f07b1d7bf5a9?q=80&w=2120&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','24吋光智慧護眼螢幕','手感好',7999,'https://picsum.photos/200/300','storeName','2023-12-07 13:07:10','2023-12-07 13:03:10',20,'COMPUTER'),(136,'https://images.unsplash.com/photo-1581539250439-c96689b516dd?q=80&w=2586&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','椅子','好坐',3000,'https://picsum.photos/200/300','storeName','2023-12-07 13:04:02','2023-12-07 13:04:02',30,'HOUSEHOLD'),(137,'https://images.unsplash.com/photo-1567538096630-e0c55bd6374c?q=80&w=3264&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','椅子2','好坐',1200,'https://picsum.photos/200/300','storeName','2023-12-07 13:04:03','2023-12-07 13:04:03',30,'HOUSEHOLD'),(138,'https://images.unsplash.com/photo-1549497538-303791108f95?q=80&w=3456&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','椅子3','好坐',1800,'https://picsum.photos/200/300','storeName','2023-12-07 13:04:03','2023-12-07 13:04:03',30,'HOUSEHOLD'),(139,'https://plus.unsplash.com/premium_photo-1673014201385-115f57893c99?q=80&w=3556&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','椅子4','好坐',600,'https://picsum.photos/200/300','storeName','2023-12-07 13:04:03','2023-12-07 13:04:03',30,'HOUSEHOLD'),(140,'https://plus.unsplash.com/premium_photo-1678559033839-aaf50cb4c843?q=80&w=3328&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','椅子5','好坐',800,'https://picsum.photos/200/300','storeName','2023-12-07 13:04:03','2023-12-07 13:04:03',30,'HOUSEHOLD'),(141,'https://images.unsplash.com/photo-1519947486511-46149fa0a254?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','椅子6','好坐',699,'https://picsum.photos/200/300','storeName','2023-12-07 13:04:03','2023-12-07 13:04:03',30,'HOUSEHOLD'),(142,'https://images.unsplash.com/photo-1601366533287-5ee4c763ae4e?q=80&w=3388&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','椅子7','好坐',1399,'https://picsum.photos/200/300','storeName','2023-12-07 13:04:04','2023-12-07 13:04:04',30,'HOUSEHOLD'),(143,'https://images.unsplash.com/photo-1598207951491-255eaf139751?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','口罩','好戴',3,'https://picsum.photos/200/300','storeName','2023-12-07 13:53:58','2023-12-07 13:53:58',500,'LIVIING'),(144,'https://images.unsplash.com/photo-1570784332176-fdd73da66f03?q=80&w=3542&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','馬克杯','好用',100,'https://picsum.photos/200/300','storeName','2023-12-07 13:54:18','2023-12-07 13:54:18',100,'LIVIING'),(145,'https://images.unsplash.com/photo-1600857062241-98e5dba7f214?q=80&w=3396&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','包包1','好用',999,'https://picsum.photos/200/300','storeName','2023-12-07 13:54:53','2023-12-07 13:54:53',20,'BOUTIQUE'),(146,'https://images.unsplash.com/photo-1575032617751-6ddec2089882?q=80&w=2819&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','包包2','好用',500,'https://picsum.photos/200/300','storeName','2023-12-07 13:54:54','2023-12-07 13:54:54',20,'BOUTIQUE'),(147,'https://plus.unsplash.com/premium_photo-1680390327010-09e627ebd475?q=80&w=3240&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','包包3','好用',2700,'https://picsum.photos/200/300','storeName','2023-12-07 13:55:54','2023-12-07 13:54:54',20,'BOUTIQUE'),(148,'https://images.unsplash.com/photo-1630343710506-89f8b9f21d31?q=80&w=3540&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','星空下的迷夢(書)','在遼闊的星空下，主角展開了一段奇妙的旅程。',300,'https://picsum.photos/200/300','storeName_1','2023-12-07 18:55:37','2023-12-07 18:55:37',26,'BOOK'),(149,'https://images.unsplash.com/photo-1543002588-bfa74002ed7e?q=80&w=2730&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','秘密的花園(書)','一本關於神秘花園裡的奇幻冒險。',250,'https://picsum.photos/200/300','storeName_2','2023-12-07 18:56:09','2023-12-07 18:56:09',36,'BOOK'),(150,'https://images.unsplash.com/photo-1541963463532-d68292c34b19?q=80&w=3388&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','時間的繪卷(書)','探討時間和記憶之間微妙的關係。',500,'https://picsum.photos/200/300','storeName_3','2023-12-07 18:56:30','2023-12-07 18:56:30',36,'BOOK'),(151,'https://images.unsplash.com/photo-1544947950-fa07a98d237f?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','遠方的呼喚(書)','一個關於遠方召喚和探險的故事。',450,'https://picsum.photos/200/300','storeName_5','2023-12-07 18:59:08','2023-12-07 18:59:08',17,'BOOK'),(152,'https://images.unsplash.com/photo-1544947950-fa07a98d237f?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA','包包4','描述',1350,'https://picsum.photos/200/300','storeName_5','2023-12-07 19:03:36','2023-12-07 19:03:36',17,'BOUTIQUE'),(153,'https://images.unsplash.com/photo-1560891958-68bb1fe7fb78?q=80&w=3024&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','時尚手提包','一款兼具實用性與時尚感的手提包，適合各種場合。',3550,'https://picsum.photos/200/300','storeName_6','2023-12-07 19:06:54','2023-12-07 19:06:54',13,'BOUTIQUE'),(154,'https://images.unsplash.com/photo-1512358958014-b651a7ee1773?q=80&w=3540&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','高級皮革錢夾','精選高級皮革製作，典雅耐用的錢夾。',2000,'https://picsum.photos/200/300','storeName_7','2023-12-07 19:07:04','2023-12-14 17:28:06',1,'BOUTIQUE'),(155,'https://images.unsplash.com/photo-1557080883-d9ce025fa255?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','經典太陽眼鏡','經典設計太陽眼鏡，適合任何臉型，提升您的風格。',999,'https://picsum.photos/200/300','storeName_8','2023-12-07 19:07:13','2023-12-14 16:41:01',271,'BOUTIQUE'),(156,'https://images.unsplash.com/photo-1539874754764-5a96559165b0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=800&q=80','奢華腕錶','奢華設計的腕錶，展現您的品味。',2000,'https://picsum.photos/200/300','storeName_9','2023-12-07 19:07:35','2023-12-07 19:07:35',20,'BOUTIQUE'),(157,'https://images.unsplash.com/photo-1611652022419-a9419f74343d?q=80&w=3388&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','精緻項鍊','精緻的項鍊，適合各種正式或休閒場合。',2500,'https://picsum.photos/200/300','storeName_10','2023-12-07 19:07:46','2023-12-14 09:30:56',294,'BOUTIQUE'),(158,'https://images.unsplash.com/photo-1495366691023-cc4eadcc2d7e?q=80&w=3413&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','男性衣服2','好穿',899,'https://picsum.photos/200/300','storeName','2023-12-03 07:58:01','2023-12-03 08:51:48',36,'MAN_CLOTHES'),(159,'https://images.unsplash.com/photo-1473966968600-fa801b869a1a?q=80&w=2000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','男性褲子1','好穿',699,'https://picsum.photos/200/300','storeName','2023-12-02 07:58:01','2023-12-03 08:51:48',36,'MAN_CLOTHES'),(160,'https://images.unsplash.com/photo-1534481909716-9a482087f27d?q=80&w=3415&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','男性褲子2','好穿',1399,'https://picsum.photos/200/300','storeName','2023-12-02 07:58:05','2023-12-03 08:51:48',36,'MAN_CLOTHES'),(162,'https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?q=80&w=3368&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','精緻眼鏡','精緻的眼鏡，適合各種正式或休閒場合。',2200,'storeUrl_10','storeName_10','2023-12-14 19:51:24','2023-12-14 19:51:24',15,'BOUTIQUE'),(163,'https://images.unsplash.com/photo-1664575600850-c4b712e6e2bf?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','Elegant Summer Dress','A lightweight, breezy summer dress perfect for warm days. Floral print, knee-length with a relaxed fit.',49,'storeUrl_10','storeName_10','2023-12-14 20:02:21','2023-12-14 20:02:21',120,'WOMAN_CLOTHES'),(164,'https://images.unsplash.com/photo-1483985988355-763728e1935b?q=80&w=3540&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','Chic Casual Blouse','A soft and comfortable blouse in a vibrant color, ideal for both office and casual wear.',35,'storeUrl_10','storeName_10','2023-12-14 20:02:33','2023-12-14 20:02:33',150,'WOMAN_CLOTHES'),(165,'https://images.unsplash.com/photo-1582623127508-3bbea01a4bb7?q=80&w=3456&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','Classic Denim Jeans','High-quality denim jeans with a classic fit, suitable for every day wear.',60,'storeUrl_10','storeName_10','2023-12-14 20:02:42','2023-12-14 20:02:42',200,'WOMAN_CLOTHES'),(166,'https://images.unsplash.com/photo-1592301933927-35b597393c0a?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','Stylish Leather Jacket','An edgy leather jacket that adds a touch of style to any outfit, perfect for cooler weather.',89,'storeUrl_10','storeName_10','2023-12-14 20:02:49','2023-12-14 20:02:49',90,'WOMAN_CLOTHES'),(167,'https://images.unsplash.com/photo-1614251056216-f748f76cd228?q=80&w=3072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','Cozy Knit Sweater','A warm and soft sweater, knitted with care, ideal for cozy days.',45,'storeUrl_10','storeName_10','2023-12-14 20:02:57','2023-12-14 20:02:57',130,'WOMAN_CLOTHES'),(168,'https://images.unsplash.com/photo-1618554754947-e01d5ce3c549?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','Formal Evening Gown','An elegant and sophisticated gown, perfect for special occasions and formal events.',120,'storeUrl_10','storeName_10','2023-12-14 20:03:03','2023-12-14 20:03:03',80,'WOMAN_CLOTHES'),(169,'https://images.unsplash.com/photo-1565849904461-04a58ad377e0?q=80&w=3436&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','高性能智能手機','最新款智能手機，擁有頂級處理器和長效電池。',4999,'storeUrl_10','storeName_10','2023-12-14 20:12:07','2023-12-14 20:12:07',50,'MOBILE'),(170,'https://images.unsplash.com/photo-1559650656-5d1d361ad10e?q=80&w=3376&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','耐用手機殼','堅固耐用的手機殼，提供全面的保護。',199,'storeUrl_10','storeName_10','2023-12-14 20:12:20','2023-12-14 20:12:20',200,'MOBILE'),(171,'https://images.unsplash.com/photo-1600490722773-35753aea6332?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','快速充電器','支援快速充電的充電器，充電更迅速。',299,'storeUrl_10','storeName_10','2023-12-14 20:12:26','2023-12-14 20:12:26',150,'MOBILE'),(172,'https://plus.unsplash.com/premium_photo-1675129779575-54b713ec81dc?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','無線耳機','高品質無線耳機，提供出色的音質體驗。',899,'storeUrl_10','storeName_10','2023-12-14 20:12:34','2023-12-14 20:12:34',100,'MOBILE'),(173,'https://images.unsplash.com/photo-1594731804139-d70328c07f4a?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','便攜式電源銀行','大容量便攜式電源銀行，隨時隨地充電。',599,'storeUrl_10','storeName_10','2023-12-14 20:12:41','2023-12-14 20:12:41',120,'MOBILE'),(174,'https://images.unsplash.com/photo-1624517542693-6f10f3c9dc40?q=80&w=2455&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','防水螢幕保護膜','高清防水螢幕保護膜，有效保護您的手機螢幕。',149,'storeUrl_10','storeName_10','2023-12-14 20:12:48','2023-12-14 20:12:48',180,'MOBILE'),(175,'https://images.unsplash.com/photo-1559563458-527698bf5295?q=80&w=3540&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','精緻真皮手袋','優質真皮製作，經典設計，適合各種場合。',2000,'storeUrl_10','storeName_10','2023-12-14 20:16:05','2023-12-14 20:16:05',50,'BOUTIQUE'),(176,'https://images.unsplash.com/photo-1473466676339-6141af006f8c?q=80&w=3432&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','高端時尚太陽鏡','時尚大方，防紫外線，為你的日常穿搭增添光彩。',500,'storeUrl_10','storeName_10','2023-12-14 20:16:15','2023-12-14 20:16:15',100,'BOUTIQUE'),(177,'https://plus.unsplash.com/premium_photo-1674499074000-9e1799b3748c?q=80&w=3538&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','手工製作絲巾','100%純絲，手工精緻，彰顯品味。',300,'storeUrl_10','storeName_10','2023-12-14 20:16:21','2023-12-14 20:16:21',80,'BOUTIQUE'),(178,'https://plus.unsplash.com/premium_photo-1681276170758-d6ca6e6e276a?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','奢華鑽石手鐲','精選鑽石，獨特設計，奢華典雅。',5000,'storeUrl_10','storeName_10','2023-12-14 20:16:27','2023-12-14 20:42:25',29,'BOUTIQUE'),(179,'https://images.unsplash.com/photo-1616406432452-07bc5938759d?q=80&w=3542&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','訂製男士皮鞋','量身定做，舒適耐穿，展現男士魅力。',800,'storeUrl_10','storeName_10','2023-12-14 20:16:33','2023-12-14 20:46:38',39,'BOUTIQUE'),(180,'https://images.unsplash.com/photo-1522312346375-d1a52e2b99b3?q=80&w=3442&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','高級女士手錶','精密工藝，優雅外觀，是時尚女性的最佳選擇。',1500,'storeUrl_10','storeName_10','2023-12-14 20:16:40','2023-12-14 20:49:08',55,'BOUTIQUE'),(181,'https://images.unsplash.com/photo-1523293182086-7651a899d37f?q=80&w=3540&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','限量版香水','獨特香氛，持久迷人，限量發售。',350,'storeUrl_10','storeName_10','2023-12-14 20:16:47','2023-12-14 20:48:07',44,'BOUTIQUE');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `google_id` int DEFAULT NULL,
  `facebook_id` int DEFAULT NULL,
  `line_id` int DEFAULT NULL,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `email` varchar(256) NOT NULL,
  `user_role` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email_verified` tinyint(1) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (16,NULL,NULL,NULL,'Judy','202cb962ac59075b964b07152d234b70','liuc7722@gmail.com','MON',1,'2023-12-06 07:07:13'),(17,NULL,NULL,NULL,'Andy','202cb962ac59075b964b07152d234b70','abc@gmail.com','MON',0,'2023-12-06 07:07:33'),(18,NULL,NULL,NULL,'Jack','202cb962ac59075b964b07152d234b70','abc@gmail.com','MON',0,'2023-12-06 07:07:40'),(20,NULL,NULL,NULL,'Tracy','202cb962ac59075b964b07152d234b70','liuc7722@gmail.com','MON',0,'2023-12-06 12:31:51'),(21,NULL,NULL,NULL,'John','202cb962ac59075b964b07152d234b70','liuc7722@gmail.com','MON',1,'2023-12-07 12:30:34'),(22,NULL,NULL,NULL,'Apple','202cb962ac59075b964b07152d234b70','liuc7722@gmail.com','MON',0,'2023-12-13 06:22:24'),(23,NULL,NULL,NULL,'Orange','202cb962ac59075b964b07152d234b70','liuc7722@gmail.com','MON',1,'2023-12-13 07:39:22'),(24,NULL,NULL,NULL,'banana','202cb962ac59075b964b07152d234b70','liuc7722@gmail.com','MON',0,'2023-12-13 07:52:37'),(25,NULL,NULL,NULL,'Aaron','202cb962ac59075b964b07152d234b70','liuc7722@gmail.com','MON',1,'2023-12-13 08:01:41'),(26,NULL,NULL,NULL,'Tommy','202cb962ac59075b964b07152d234b70','liuc7722@gmail.com','MON',0,'2023-12-13 08:03:53'),(27,NULL,NULL,NULL,'Damm','202cb962ac59075b964b07152d234b70','liuc7722@gmail.com','MON',1,'2023-12-13 08:08:06'),(28,NULL,NULL,NULL,'Zero','202cb962ac59075b964b07152d234b70','liuc7722@gmail.com','MON',1,'2023-12-13 08:13:10'),(29,NULL,NULL,NULL,'zzzzz','202cb962ac59075b964b07152d234b70','liuc7722@gmail.com','MON',0,'2023-12-14 09:53:13');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'my_system'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-15 11:44:47
