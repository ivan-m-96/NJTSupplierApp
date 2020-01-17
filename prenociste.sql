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

/*Data for the table `databasechangeloglock` */

insert  into `databasechangeloglock`(`ID`,`LOCKED`,`LOCKGRANTED`,`LOCKEDBY`) values 
(1,'\0',NULL,NULL);

/*Table structure for table `dobavljac` */

DROP TABLE IF EXISTS `dobavljac`;

CREATE TABLE `dobavljac` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) NOT NULL,
  `adresa` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `dobavljac` */

insert  into `dobavljac`(`id`,`naziv`,`adresa`) values 
(1,'Swisslion','Takovo'),
(4,'Imlek','Imlekova ulica');

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

/*Data for the table `katalog` */

insert  into `katalog`(`id`,`datum`,`dobavljacId`) values 
(1,'2019-08-29 18:58:32',1),
(2,'2019-08-30 20:50:08',1);

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `porudzbenica` */

/*Table structure for table `prenociste` */

DROP TABLE IF EXISTS `prenociste`;

CREATE TABLE `prenociste` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) NOT NULL,
  `adresa` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `prenociste` */

insert  into `prenociste`(`id`,`naziv`,`adresa`) values 
(1,'Prenociste','Prenociste 123');

/*Table structure for table `proizvod` */

DROP TABLE IF EXISTS `proizvod`;

CREATE TABLE `proizvod` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cena` int(11) NOT NULL,
  `naziv` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `proizvod` */

insert  into `proizvod`(`id`,`cena`,`naziv`) values 
(1,123,'Coko smoki'),
(2,132,'Plazma keks'),
(3,270,'Vitador Peanut butter');

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

/*Data for the table `stavka_kataloga` */

insert  into `stavka_kataloga`(`id`,`naziv`,`katalogId`,`proizvodId`) values 
(2,'Coko smoki',1,1),
(3,'Plazma',1,2),
(4,'Vitador Peanut butter',2,3);

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `stavka_porudzbenice` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`id`,`username`,`password`) values 
(1,'admin','$2a$10$ummMiwQhCj81BEdeKdReKeZIkEh5fcZSxU/zcoB.vkEQcQ2tXvdAO'),
(2,'user','$2a$10$6MqYGjZICGE8CkFlFMzrXuN5UML9VEWEc3idYEQRLZJ1cYwY9B4ve');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
