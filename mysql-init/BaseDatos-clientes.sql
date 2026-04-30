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
-- Create schema cliente_app
--

CREATE DATABASE IF NOT EXISTS cliente_app;
USE cliente_app;

--
-- Definition of table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `password` varchar(64) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `person_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_customers_people` (`person_id`),
  CONSTRAINT `FK_customers_people` FOREIGN KEY (`person_id`) REFERENCES `people` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customers`
--

/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` (`id`,`password`,`status`,`person_id`) VALUES 
 (9,'1234ss',1,9),
 (10,'5678',1,10),
 (12,'1245',1,13);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;


--
-- Definition of table `people`
--

DROP TABLE IF EXISTS `people`;
CREATE TABLE `people` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `gender` char(1) NOT NULL,
  `identification` varchar(10) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(14) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_unique_id` (`identification`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `people`
--

/*!40000 ALTER TABLE `people` DISABLE KEYS */;
INSERT INTO `people` (`id`,`name`,`gender`,`identification`,`address`,`phone`) VALUES 
 (9,'Jose Lema','M','1309876543','Otavalo sn y principal','098254785'),
 (10,'Marianela Montalvo','F','1309876542','Amazonas y NNUU ','097548965'),
 (13,'Juan Osorio','M','1309876541','13 junio y Equinoccial','098874587');
/*!40000 ALTER TABLE `people` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
