-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: fitness_online_db
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Dumping data for table `activity_log`
--

LOCK TABLES `activity_log` WRITE;
/*!40000 ALTER TABLE `activity_log` DISABLE KEYS */;
INSERT INTO `activity_log` VALUES (1,'Running',30,'Medium',70.00,'2024-04-20 15:19:54',1),(2,'Push ups',10,'Low',65.00,'2024-04-20 15:29:49',2),(3,'Swimming',15,'Low',68.00,'2024-04-18 12:30:00',2);
/*!40000 ALTER TABLE `activity_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `advisor_account`
--

LOCK TABLES `advisor_account` WRITE;
/*!40000 ALTER TABLE `advisor_account` DISABLE KEYS */;
INSERT INTO `advisor_account` VALUES (1,'Ivana','Dodik','admin','admin',1),(2,'Ivana','Dodik','ivana','ivana',0),(3,'Marko','Markovic','marko','marko',0);
/*!40000 ALTER TABLE `advisor_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
INSERT INTO `attribute` VALUES (1,'efficiency',1,0),(2,'endurance',1,0),(3,'muscle strength',2,0),(4,'motion',4,0),(5,'elasticity',4,0);
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,NULL,'Cardio',0),(2,NULL,'Strength',0),(3,NULL,'HIIT',0),(4,NULL,'Flexibility',0),(5,2,'Free weights',0),(6,2,'Gym machines',0);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,'This is my program, I\'m the instructor :)',1,'2024-04-20 15:11:56');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,'/images/programs/3/23Well.jpg',3),(2,'/images/programs/4/muscular-hispanic-man-doing-plank-on-the-beach-at-royalty-free-image-1680697280.jpg',4),(3,'/images/programs/5/squat.jpg',5);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` VALUES (1,'2024-04-20 14:58:09','Sending e-mail to: dodikivana99@gmail.com'),(2,'2024-04-20 15:11:24','Authenticating user with username: ana'),(3,'2024-04-20 15:11:56','Authenticating user with username: ana'),(4,'2024-04-20 15:14:42','Authenticating user with username: ana'),(5,'2024-04-20 15:17:20','Authenticating user with username: ana'),(6,'2024-04-20 15:19:11','Authenticating user with username: ana'),(7,'2024-04-20 15:19:11','Saved file: C:\\Users\\Ivana Dodik\\Documents\\FitnessOnlineResources\\images\\programs\\3'),(8,'2024-04-20 15:19:20','Authenticating user with username: ana'),(9,'2024-04-20 15:19:23','Authenticating user with username: ana'),(10,'2024-04-20 15:19:54','Authenticating user with username: ana'),(11,'2024-04-20 15:19:54','Authenticating user with username: ana'),(12,'2024-04-20 15:20:08','Authenticating user with username: ana'),(13,'2024-04-20 15:20:15','Authenticating user with username: ana'),(14,'2024-04-20 15:28:21','Sending e-mail to: dodikivana99@gmail.com'),(15,'2024-04-20 15:28:21','Saved file: C:\\Users\\Ivana Dodik\\Documents\\FitnessOnlineResources\\images\\users\\2'),(16,'2024-04-20 15:29:04','Authenticating user with username: ivana'),(17,'2024-04-20 15:29:07','Authenticating user with username: ivana'),(18,'2024-04-20 15:29:20','Authenticating user with username: ivana'),(19,'2024-04-20 15:29:21','Authenticating user with username: ivana'),(20,'2024-04-20 15:29:49','Authenticating user with username: ivana'),(21,'2024-04-20 15:29:49','Authenticating user with username: ivana'),(22,'2024-04-20 15:32:40','Authenticating user with username: ivana'),(23,'2024-04-20 15:32:40','Saved file: C:\\Users\\Ivana Dodik\\Documents\\FitnessOnlineResources\\images\\programs\\4'),(24,'2024-04-20 15:34:24','Authenticating user with username: ivana'),(25,'2024-04-20 15:34:24','Saved file: C:\\Users\\Ivana Dodik\\Documents\\FitnessOnlineResources\\images\\programs\\5'),(26,'2024-04-20 15:34:43','Authenticating user with username: ivana'),(27,'2024-04-20 15:34:45','Authenticating user with username: ivana'),(28,'2024-04-20 15:34:59','Authenticating user with username: ivana'),(29,'2024-04-20 15:34:59','Authenticating user with username: ivana'),(30,'2024-04-20 15:35:03','Authenticating user with username: ivana'),(31,'2024-04-20 15:35:04','Authenticating user with username: ivana'),(32,'2024-04-20 15:35:05','Authenticating user with username: ivana'),(33,'2024-04-20 15:35:06','Authenticating user with username: ivana'),(34,'2024-04-20 15:36:14','Authenticating user with username: ivana'),(35,'2024-04-20 15:36:44','Authenticating user with username: ivana'),(36,'2024-04-20 15:37:07','Authenticating user with username: ivana'),(37,'2024-04-20 15:37:11','Authenticating user with username: ivana'),(38,'2024-04-20 15:37:21','Authenticating user with username: ivana'),(39,'2024-04-20 15:37:22','Authenticating user with username: ivana'),(40,'2024-04-20 15:58:14','Authenticating user with username: ana'),(41,'2024-04-20 15:58:15','Authenticating user with username: ana'),(42,'2024-04-20 15:58:17','Authenticating user with username: ana'),(43,'2024-04-20 15:58:17','Authenticating user with username: ana'),(44,'2024-04-20 15:59:45','Saved file: C:\\Users\\Ivana Dodik\\Documents\\FitnessOnlineResources\\images\\users\\1'),(45,'2024-04-20 15:59:59','Authenticating user with username: ana'),(46,'2024-04-20 16:00:38','Authenticating user with username: ana'),(47,'2024-04-20 16:00:40','Authenticating user with username: ana'),(48,'2024-04-20 16:00:56','Authenticating user with username: ana'),(49,'2024-04-20 16:01:13','Authenticating user with username: ana'),(50,'2024-04-20 16:01:18','Authenticating user with username: ana'),(51,'2024-04-20 16:17:12','Authenticating user with username: ivana'),(52,'2024-04-20 16:17:17','Authenticating user with username: ivana'),(53,'2024-04-20 16:17:17','Authenticating user with username: ivana'),(54,'2024-04-20 16:17:45','Authenticating user with username: ivana'),(55,'2024-04-20 16:17:59','Authenticating user with username: ivana'),(56,'2024-04-20 16:21:16','Authenticating user with username: ivana'),(57,'2024-04-20 16:21:17','Authenticating user with username: ivana'),(58,'2024-04-20 16:21:43','Authenticating user with username: ivana'),(59,'2024-04-20 16:21:50','Authenticating user with username: ivana'),(60,'2024-04-20 16:21:51','Authenticating user with username: ivana'),(61,'2024-04-20 16:49:37','Authenticating user with username: ivana'),(62,'2024-04-20 16:55:20','Authenticating user with username: ivana'),(63,'2024-04-20 16:59:19','Authenticating user with username: ivana'),(64,'2024-04-20 17:07:10','Authenticating user with username: ivana'),(65,'2024-04-20 17:07:42','Authenticating user with username: ivana'),(66,'2024-04-20 17:17:22','Authenticating user with username: ivana'),(67,'2024-04-20 17:17:40','Authenticating user with username: ivana'),(68,'2024-04-20 17:19:18','Authenticating user with username: ivana'),(69,'2024-04-20 17:21:01','Authenticating user with username: ivana'),(70,'2024-04-20 17:39:58','Authenticating user with username: ivana'),(71,'2024-04-20 17:45:05','Authenticating user with username: ivana'),(72,'2024-04-20 17:51:25','Authenticating user with username: ivana'),(73,'2024-04-20 17:52:18','Authenticating user with username: ivana'),(74,'2024-04-20 17:57:27','Authenticating user with username: ivana'),(75,'2024-04-20 17:58:24','Authenticating user with username: ivana'),(76,'2024-04-20 17:58:39','Authenticating user with username: ivana'),(77,'2024-04-20 17:59:20','Authenticating user with username: ivana'),(78,'2024-04-20 18:14:22','Authenticating user with username: ivana'),(79,'2024-04-20 18:18:22','Authenticating user with username: ivana'),(80,'2024-04-20 18:19:41','Authenticating user with username: ivana'),(81,'2024-04-20 18:23:30','Authenticating user with username: ivana'),(82,'2024-04-20 18:25:08','Authenticating user with username: ivana'),(83,'2024-04-20 18:27:18','Authenticating user with username: ivana'),(84,'2024-04-20 18:32:45','Authenticating user with username: ivana'),(85,'2024-04-20 18:35:04','Authenticating user with username: ivana'),(86,'2024-04-20 18:38:03','Authenticating user with username: ivana'),(87,'2024-04-20 18:38:06','Authenticating user with username: ivana'),(88,'2024-04-20 18:38:12','Authenticating user with username: ivana'),(89,'2024-04-20 18:38:45','Authenticating user with username: ana'),(90,'2024-04-20 18:38:46','Authenticating user with username: ana'),(91,'2024-04-20 18:38:52','Authenticating user with username: ana'),(92,'2024-04-20 18:38:58','Authenticating user with username: ana'),(93,'2024-04-20 18:39:12','Authenticating user with username: ivana'),(94,'2024-04-20 18:39:16','Authenticating user with username: ivana');
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,1,'Hi',1,'2024-04-20 15:17:20'),(2,2,'I have some problems with my account, can you help me?',0,'2024-04-20 15:29:04');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `participation`
--

LOCK TABLES `participation` WRITE;
/*!40000 ALTER TABLE `participation` DISABLE KEYS */;
INSERT INTO `participation` VALUES (1,2,3,'CREDIT_CARD','123456789');
/*!40000 ALTER TABLE `participation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `program`
--

LOCK TABLES `program` WRITE;
/*!40000 ALTER TABLE `program` DISABLE KEYS */;
INSERT INTO `program` VALUES (1,'Cardio Blast','Intense cardio workout',20.00,'INTERMEDIATE',45,'Gym',0,0,'066123456',1,1,'2024-04-20 15:11:24'),(2,'Strength Sculpt','Program aimed at building muscle and strength.',25.00,'ADVANCED',60,'Gym',0,1,'066123456',1,2,'2024-04-20 15:14:42'),(3,'Flex Flow Yoga','Program focusing on relaxation, and mindfulness.',30.00,'BEGINNER',20,'Park ',0,1,'066123456',1,4,'2024-04-20 15:19:11'),(4,'Core Strength Challenge','Program for improving stability and posture.',30.00,'ADVANCED',30,'Home',0,1,'066336764',2,2,'2024-04-20 15:32:40'),(5,'My first program','First time exercising',5.00,'BEGINNER',30,'Home',0,0,'066336764',2,1,'2024-04-20 15:34:24');
/*!40000 ALTER TABLE `program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `program_attribute`
--

LOCK TABLES `program_attribute` WRITE;
/*!40000 ALTER TABLE `program_attribute` DISABLE KEYS */;
INSERT INTO `program_attribute` VALUES (1,1,1,'improved'),(2,1,2,'improved'),(3,2,3,'improved');
/*!40000 ALTER TABLE `program_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
INSERT INTO `subscription` VALUES (1,2,2),(2,2,1),(3,1,3);
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Ana','Anic','Banja Luk','ana','ana','dodikivana99@gmail.com',1,0,'/images/users/1/d02eccc250ce1a7713a3f3d991185b5a.jpg',NULL),(2,'Ivana','Dodik','Prijedor','ivana','ivana','dodikivana99@gmail.com',1,0,'/images/users/2/beautiful-cyberpunk-girl_1022967-3367.jpg',NULL),(3,'Petar','Pertovic','Banja Luk','petar','petar','dodikivana99@gmail.com',1,0,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_message`
--

LOCK TABLES `user_message` WRITE;
/*!40000 ALTER TABLE `user_message` DISABLE KEYS */;
INSERT INTO `user_message` VALUES (1,'hello',0,'2024-04-20 18:38:13',2,1),(2,'hi there',0,'2024-04-20 18:38:52',1,2);
/*!40000 ALTER TABLE `user_message` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-20 18:47:20
