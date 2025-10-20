CREATE DATABASE  IF NOT EXISTS `project3c` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `project3c`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: project3c
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_id` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `member_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` enum('ACTIVE','BANNED','INACTIVE') NOT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES ('M20251017-001','台北市中正區八德路一段1號','wangtony@giun.com.tw','王大明','q1w2','0933123147','ACTIVE'),('M20251017-002','新北市中和區立人街2號','leeday@date.com','李小天','zaqxsw','0911369258','ACTIVE'),('M20251017-003','台中市大肚區文昌一街10號','linsun@mimi.com.tw','林陽','12345678','0936258456','ACTIVE'),('M20251017-004','台南市仁德區中正路三段589號','chenbut@cos.com.tw','陳小美','qqq111','0963147147','ACTIVE'),('M20251017-005','桃園市桃園區寶慶路80號','mary@trtr.com','周大莉','12341234','0978142536','ACTIVE'),('M20251017-006','台北市文山區興隆路四段149號2 樓','may@hotpot.com.tw','謝美滿','pppp0000','0933142223','ACTIVE'),('M20251017-007','新竹市東區中央路229號3樓','tony@city.com.tw','鄭中天','o9o9o9o9','0978159753','ACTIVE'),('M20251017-008','高雄市苓雅區五福一路67號6樓','jason@kcg.com.tw','莊孝維','zzzzxxxx','0936258154','ACTIVE'),('M20251017-009','高雄市前鎮區中華五路656號1樓','sky@demo.com','呂天','11112222','0936369888','ACTIVE'),('M20251017-010','嘉義縣朴子市613152之159號2樓','augus@frw.com','曾友邦','aaaassss','0988123147','ACTIVE'),('M20251017-011','新北市中和區中山路三段122號5樓','Roy@taiwan.com.tw','沈清德','1234567890','0911222333','ACTIVE');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-17 11:27:10
