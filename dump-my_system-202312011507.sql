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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `userrole` varchar(20) DEFAULT NULL,
  `emailverified` tinyint(1) DEFAULT NULL,
  `createddate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'your_username','your_password','your_email@example.com','your_userrole',0,'2023-11-28 09:32:03'),(2,'Tony','123','yahoo','ION',1,'2023-11-28 17:03:38'),(4,'Judy','456','Google','MAN',0,'2023-11-28 17:16:41'),(5,'aaron','123',NULL,NULL,NULL,'2023-11-30 05:17:37'),(6,'Ted','asdf','abc@gmail.com','normal',1,'2023-11-30 17:04:14'),(7,'Ana','qwer','abcd@gmail.com','normal',1,'2023-11-30 17:10:06'),(8,'Pon','qwer','adad','normal',1,'2023-11-30 17:10:16'),(9,'aaon','qwer','adad','normal',1,'2023-12-01 06:01:29');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (100,'https://tokyo-kitchen.icook.network/uploads/step/cover/1871054/4ce9749d6edd6206.jpg','茶碗蒸','好吃',500,'https://is1-ssl.mzstatic.com/image/thumb/Purple116/v4/a3/8a/42/a38a424b-0cad-32e1-b624-3d2e66f484a1/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/1200x630wa.png','真鮮',NULL,NULL,0,NULL),(103,'https://www.kongyen.com.tw/wp-content/uploads/2023/06/22.果酢日和_百香果-494x600.jpg','百香果汁','好吃',666,'https://deo.shopeemobile.com/shopee/shopee-pcmall-live-tw/assets/6b05141182eecfa218574f88e1e62cd4.jpg','我的商店4',NULL,NULL,0,NULL),(104,'https://www.kongyen.com.tw/wp-content/uploads/2023/06/22.果酢日和_百香果-494x600.jpg','百香果汁','好吃',888,'https://is1-ssl.mzstatic.com/image/thumb/Purple116/v4/a3/8a/42/a38a424b-0cad-32e1-b624-3d2e66f484a1/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/1200x630wa.png','真鮮',NULL,NULL,0,NULL),(105,'https://cdn.store-assets.com/s/888617/i/64094635.png','手提袋','輕便',9999,'https://deo.shopeemobile.com/shopee/shopee-pcmall-live-tw/assets/6b05141182eecfa218574f88e1e62cd4.jpg','我的商店3',NULL,NULL,0,NULL),(106,'abc','名稱','描述',100,'圖片網址','商店名稱',NULL,NULL,123,'種類'),(107,'abc','名稱','描述',100,'圖片網址','商店名稱',NULL,NULL,123,'種類'),(108,'abc','名稱','描述',100,'圖片網址','商店名稱',NULL,NULL,123,'種類');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-01 15:07:11
