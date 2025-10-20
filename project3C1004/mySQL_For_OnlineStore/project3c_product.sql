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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` varchar(45) NOT NULL,
  `imageUrl` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `specification` varchar(1024) DEFAULT NULL,
  `category_id` varchar(45) NOT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('P20251014-001','/images/macbook_air_m3.jpg','MacBook Air M3',42900.00,'Apple M3 13.6吋，8GB RAM，512GB SSD，輕薄長效電力','C20251014-001'),('P20251014-002','/images/zenbook_14_oled.jpg','ASUS ZenBook 14 OLED',38900.00,'14吋 OLED，Intel i7，16GB RAM，1TB SSD','C20251014-001'),('P20251014-003','/images/swift_x14.jpg','Acer Swift X 14',37900.00,'RTX 3050 顯卡，AMD R7，16GB RAM，1TB SSD','C20251014-001'),('P20251014-004','/images/msi_prestige13.jpg','MSI Prestige 13 Evo',40900.00,'Intel i7，16GB RAM，1TB SSD，商務輕薄筆電','C20251014-001'),('P20251014-005','/images/hp_spectre_x360.jpg','HP Spectre x360',41900.00,'14吋觸控可翻轉，Intel i7，16GB RAM，512GB SSD','C20251014-001'),('P20251014-006','/images/imac_24_m3.jpg','Apple iMac 24吋',46900.00,'M3 晶片，8GB RAM，512GB SSD，一體成型設計','C20251014-002'),('P20251014-007','/images/rog_g15.jpg','ASUS ROG Strix G15',57900.00,'電競主機，RTX 4070，Ryzen 7，16GB RAM','C20251014-002'),('P20251014-008','/images/hp_pavilion_desktop.jpg','HP Pavilion Desktop',19900.00,'家用多功能桌機，Intel i5，8GB RAM，1TB HDD','C20251014-002'),('P20251014-009','/images/predator_orion7000.jpg','Acer Predator Orion 7000',86900.00,'RTX 4080，i9 處理器，高效能電競桌機','C20251014-002'),('P20251014-010','/images/ideacentre_5.jpg','Lenovo IdeaCentre 5',22900.00,'Intel i5，16GB RAM，512GB SSD','C20251014-002'),('P20251014-011','/images/iphone16pro.jpg','iPhone 16 Pro',42900.00,'A18 Pro 晶片，6.1吋 Super Retina XDR，256GB','C20251014-003'),('P20251014-012','/images/galaxy_s24.jpg','Samsung Galaxy S24',34900.00,'6.2吋 Dynamic AMOLED，Snapdragon 8 Gen3','C20251014-003'),('P20251014-013','/images/pixel9.jpg','Google Pixel 9',28900.00,'原生 Android，AI 功能強化，128GB','C20251014-003'),('P20251014-014','/images/zenfone10.jpg','ASUS Zenfone 10',25900.00,'5.9吋小尺寸旗艦，Snapdragon 8 Gen2','C20251014-003'),('P20251014-015','/images/xperia1_vi.jpg','Sony Xperia 1 VI',37900.00,'4K HDR OLED 顯示螢幕，三鏡頭相機','C20251014-003'),('P20251014-016','/images/airpods_pro2.jpg','AirPods Pro 2',7990.00,'主動降噪，空間音訊，MagSafe充電','C20251014-004'),('P20251014-017','/images/sony_wh1000xm5.jpg','Sony WH-1000XM5',10900.00,'無線藍牙耳罩式耳機，業界頂級降噪','C20251014-004'),('P20251014-018','/images/bose_qc_ultra.jpg','Bose QuietComfort Ultra',11900.00,'空間音效、舒適佩戴、長效電力','C20251014-004'),('P20251014-019','/images/jbl_660nc.jpg','JBL Live 660NC',6990.00,'主動降噪、長達50小時續航','C20251014-004'),('P20251014-020','/images/momentum4.jpg','Sennheiser Momentum 4',11990.00,'高音質藍牙耳機，60小時續航','C20251014-004'),('P20251014-021','/images/apple_watch_s10.jpg','Apple Watch Series 10',13900.00,'S10晶片，健康監測與AI助理整合','C20251014-005'),('P20251014-022','/images/galaxy_watch7.jpg','Samsung Galaxy Watch 7',10900.00,'AMOLED螢幕，支援血氧與心率偵測','C20251014-005'),('P20251014-023','/images/fitbit_charge6.jpg','Fitbit Charge 6',5490.00,'運動追蹤與睡眠分析功能','C20251014-005'),('P20251014-024','/images/garmin_venu3.jpg','Garmin Venu 3',15900.00,'GPS運動手錶，支援心率與壓力監測','C20251014-005'),('P20251014-025','/images/huawei_watch_gt4.jpg','Huawei Watch GT 4',7990.00,'高續航智慧手錶，支援藍牙通話','C20251014-005');
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

-- Dump completed on 2025-10-17 11:27:10
