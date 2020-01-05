/*
SQLyog Community v13.1.5  (64 bit)
MySQL - 10.1.37-MariaDB : Database - prenociste
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`prenociste` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `prenociste`;

/*Table structure for table `databasechangeloglock` */

DROP TABLE IF EXISTS `databasechangeloglock`;

CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `dobavljac` */

DROP TABLE IF EXISTS `dobavljac`;

CREATE TABLE `dobavljac` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) NOT NULL,
  `adresa` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Table structure for table `katalog` */

DROP TABLE IF EXISTS `katalog`;

CREATE TABLE `katalog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `datum` datetime NOT NULL,
  `dobavljacId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_d687c73024e95708f4fc92edb89` (`dobavljacId`),
  CONSTRAINT `FK_d687c73024e95708f4fc92edb89` FOREIGN KEY (`dobavljacId`) REFERENCES `dobavljac` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Table structure for table `porudzbenica` */

DROP TABLE IF EXISTS `porudzbenica`;

CREATE TABLE `porudzbenica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `datum` datetime NOT NULL,
  `dobavljacId` int(11) NOT NULL,
  `prenocisteId` int(11) NOT NULL,
  PRIMARY KEY (`id`,`dobavljacId`,`prenocisteId`),
  KEY `FK_572078214935f112fb82ece45e0` (`dobavljacId`),
  KEY `FK_dda917e2250cf9b234a214eb3eb` (`prenocisteId`),
  CONSTRAINT `FK_572078214935f112fb82ece45e0` FOREIGN KEY (`dobavljacId`) REFERENCES `dobavljac` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_dda917e2250cf9b234a214eb3eb` FOREIGN KEY (`prenocisteId`) REFERENCES `prenociste` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Table structure for table `prenociste` */

DROP TABLE IF EXISTS `prenociste`;

CREATE TABLE `prenociste` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) NOT NULL,
  `adresa` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Table structure for table `proizvod` */

DROP TABLE IF EXISTS `proizvod`;

CREATE TABLE `proizvod` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cena` int(11) NOT NULL,
  `naziv` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Table structure for table `stavka_kataloga` */

DROP TABLE IF EXISTS `stavka_kataloga`;

CREATE TABLE `stavka_kataloga` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) NOT NULL,
  `katalogId` int(11) NOT NULL,
  `proizvodId` int(11) NOT NULL,
  PRIMARY KEY (`id`,`katalogId`),
  UNIQUE KEY `IDX_467b8437b00c82aa98a6f23e4c` (`katalogId`,`proizvodId`),
  KEY `FK_b01493b794d523c74326017adc3` (`proizvodId`),
  CONSTRAINT `FK_0a2a0c52ab2fe9b31fd2f1032c1` FOREIGN KEY (`katalogId`) REFERENCES `katalog` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_b01493b794d523c74326017adc3` FOREIGN KEY (`proizvodId`) REFERENCES `proizvod` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Table structure for table `stavka_porudzbenice` */

DROP TABLE IF EXISTS `stavka_porudzbenice`;

CREATE TABLE `stavka_porudzbenice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kolicina` int(11) NOT NULL,
  `porudzbenicaId` int(11) NOT NULL,
  `stavkaKatalogaId` int(11) NOT NULL,
  PRIMARY KEY (`id`,`porudzbenicaId`),
  KEY `FK_0570cdd28674299d75589f86049` (`porudzbenicaId`),
  KEY `stavkaKatalogaId` (`stavkaKatalogaId`),
  CONSTRAINT `FK_0570cdd28674299d75589f86049` FOREIGN KEY (`porudzbenicaId`) REFERENCES `porudzbenica` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `stavka_porudzbenice_ibfk_1` FOREIGN KEY (`stavkaKatalogaId`) REFERENCES `stavka_kataloga` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
