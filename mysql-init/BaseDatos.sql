-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.24-MariaDB


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema banca_db
--

CREATE DATABASE IF NOT EXISTS banca_db;
USE banca_db;

--
-- Definition of table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `account_number` varchar(20) NOT NULL,
  `type` varchar(15) NOT NULL,
  `initial_balance` decimal(15,2) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `creation_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `customer_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_unique` (`account_number`),
  KEY `FK_accounts_customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `accounts`
--

/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` (`id`,`account_number`,`type`,`initial_balance`,`status`,`creation_date`,`update_date`,`customer_id`) VALUES 
 (5,'478758','Ahorro','1425.00',1,'2026-04-29 00:00:00','2026-04-29 00:00:00',9),
 (6,'225487','Corriente','700.00',1,'2026-04-29 00:00:00','2026-04-29 00:00:00',10),
 (7,'495878','Ahorro','150.00',1,'2026-04-29 00:00:00','2026-04-29 00:00:00',12),
 (8,'496825','Ahorros','0.00',1,'2026-04-29 00:00:00','2026-04-29 00:00:00',10),
 (9,'585545','Corriente','1000.00',1,'2026-04-29 00:00:00','2026-04-29 00:00:00',9);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;


--
-- Definition of table `movements`
--

DROP TABLE IF EXISTS `movements`;
CREATE TABLE `movements` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `type` varchar(15) NOT NULL,
  `amount` decimal(15,2) NOT NULL,
  `balance` decimal(15,2) NOT NULL,
  `account_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_movements_account` (`account_id`),
  CONSTRAINT `FK_movements_account` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `movements`
--

/*!40000 ALTER TABLE `movements` DISABLE KEYS */;
INSERT INTO `movements` (`id`,`date`,`type`,`amount`,`balance`,`account_id`) VALUES 
 (11,'2026-04-29 00:00:00','RETIRO','-575.00','1425.00',5),
 (12,'2026-04-29 00:00:00','DEPOSITO','600.00','700.00',6),
 (13,'2026-04-29 00:00:00','DEPOSITO','150.00','150.00',7),
 (14,'2026-04-29 00:00:00','RETIRO','-540.00','0.00',8);
/*!40000 ALTER TABLE `movements` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
