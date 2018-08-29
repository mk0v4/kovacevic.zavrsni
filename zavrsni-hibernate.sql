-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: zavrsni
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.21-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `analizacijene`
--

DROP TABLE IF EXISTS `analizacijene`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `analizacijene` (
  `id` bigint(20) NOT NULL,
  `datum_kreiranja` datetime DEFAULT NULL,
  `datum_promjene` datetime DEFAULT NULL,
  `obrisan` bit(1) NOT NULL,
  `grupa_norme` varchar(255) DEFAULT NULL,
  `jedinica_mjere` varchar(255) DEFAULT NULL,
  `koeficijent_firme` decimal(19,2) DEFAULT NULL,
  `opis` varchar(255) DEFAULT NULL,
  `oznaka_norme` varchar(255) DEFAULT NULL,
  `sveukupan_iznos` decimal(19,2) DEFAULT NULL,
  `ukupan_normativ_vremena` decimal(19,2) DEFAULT NULL,
  `ukupna_cijena_materijal` decimal(19,2) DEFAULT NULL,
  `ukupna_cijena_rad` decimal(19,2) DEFAULT NULL,
  `stavka_troskovnik_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm6otu8v2ctgdkucrlyf29mb2q` (`stavka_troskovnik_id`),
  CONSTRAINT `FKm6otu8v2ctgdkucrlyf29mb2q` FOREIGN KEY (`stavka_troskovnik_id`) REFERENCES `stavkatroskovnik` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `analizacijene`
--

LOCK TABLES `analizacijene` WRITE;
/*!40000 ALTER TABLE `analizacijene` DISABLE KEYS */;
/*!40000 ALTER TABLE `analizacijene` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `analizamaterijal`
--

DROP TABLE IF EXISTS `analizamaterijal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `analizamaterijal` (
  `id` bigint(20) NOT NULL,
  `datum_kreiranja` datetime DEFAULT NULL,
  `datum_promjene` datetime DEFAULT NULL,
  `obrisan` bit(1) NOT NULL,
  `cijena_materijal` decimal(19,2) DEFAULT NULL,
  `jedinica_mjere` varchar(255) DEFAULT NULL,
  `jedinicna_cijena_materijal` decimal(19,2) DEFAULT NULL,
  `kolicina` decimal(19,2) DEFAULT NULL,
  `analiza_cijene_id` bigint(20) DEFAULT NULL,
  `materijal_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKswujg0gc734d9w12oxdnpbahl` (`analiza_cijene_id`),
  KEY `FKempsyo0djgr6klia2q76bev49` (`materijal_id`),
  CONSTRAINT `FKempsyo0djgr6klia2q76bev49` FOREIGN KEY (`materijal_id`) REFERENCES `materijal` (`id`),
  CONSTRAINT `FKswujg0gc734d9w12oxdnpbahl` FOREIGN KEY (`analiza_cijene_id`) REFERENCES `analizacijene` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `analizamaterijal`
--

LOCK TABLES `analizamaterijal` WRITE;
/*!40000 ALTER TABLE `analizamaterijal` DISABLE KEYS */;
/*!40000 ALTER TABLE `analizamaterijal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `analizarad`
--

DROP TABLE IF EXISTS `analizarad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `analizarad` (
  `id` bigint(20) NOT NULL,
  `datum_kreiranja` datetime DEFAULT NULL,
  `datum_promjene` datetime DEFAULT NULL,
  `obrisan` bit(1) NOT NULL,
  `broj_operacije` tinyint(4) DEFAULT NULL,
  `cijena_vrijeme` decimal(19,2) DEFAULT NULL,
  `jedinicni_normativ_vremena` decimal(19,2) DEFAULT NULL,
  `opsis_operacije` varchar(255) DEFAULT NULL,
  `analiza_cijene_id` bigint(20) DEFAULT NULL,
  `rad_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2e3m4h5e7571ntys6mcujee25` (`analiza_cijene_id`),
  KEY `FKp37po39sbc0b46fvtqkur4bxm` (`rad_id`),
  CONSTRAINT `FK2e3m4h5e7571ntys6mcujee25` FOREIGN KEY (`analiza_cijene_id`) REFERENCES `analizacijene` (`id`),
  CONSTRAINT `FKp37po39sbc0b46fvtqkur4bxm` FOREIGN KEY (`rad_id`) REFERENCES `rad` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `analizarad`
--

LOCK TABLES `analizarad` WRITE;
/*!40000 ALTER TABLE `analizarad` DISABLE KEYS */;
/*!40000 ALTER TABLE `analizarad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (48),(48),(48),(48),(48),(48);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materijal`
--

DROP TABLE IF EXISTS `materijal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `materijal` (
  `id` bigint(20) NOT NULL,
  `datum_kreiranja` datetime DEFAULT NULL,
  `datum_promjene` datetime DEFAULT NULL,
  `obrisan` bit(1) NOT NULL,
  `cijena_ambalaza` decimal(19,2) DEFAULT NULL,
  `grupa_materijal` varchar(255) DEFAULT NULL,
  `jedinica_mjere_ambalaza` varchar(255) DEFAULT NULL,
  `kolicina_ambalaza` decimal(19,2) DEFAULT NULL,
  `opis` varchar(255) DEFAULT NULL,
  `oznaka` varchar(255) DEFAULT NULL,
  `proizvodac` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materijal`
--

LOCK TABLES `materijal` WRITE;
/*!40000 ALTER TABLE `materijal` DISABLE KEYS */;
INSERT INTO `materijal` VALUES (37,'2017-09-13 15:35:36','2017-09-13 15:35:36','\0',20.00,'Cement','kg',25.00,'Standardni mješani portland cement, visoka rana čvrstoća; Razred čvrstoće 32,5 MPa','CEM II/B-M (P-S) 32,5R','NEXE'),(38,'2017-09-13 15:36:13','2017-09-13 15:36:13','\0',20.00,'Vapno','kg',25.00,'Hidratizirano vapno','DL 80-30-S1','InterCAL'),(39,'2017-09-13 15:37:03','2017-09-13 15:37:03','\0',15.00,'Voda','m3',1.00,'Smatra se prikladnom za pripremu i ne treba se ispitivati','Pitka voda','Gradski vodovod'),(40,'2017-09-13 15:37:44','2017-09-13 15:37:44','\0',90.00,'Agregat','m3',1.00,'Riječni pijesak','Granulacija 0-4 mm','');
/*!40000 ALTER TABLE `materijal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rad`
--

DROP TABLE IF EXISTS `rad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rad` (
  `id` bigint(20) NOT NULL,
  `datum_kreiranja` datetime DEFAULT NULL,
  `datum_promjene` datetime DEFAULT NULL,
  `obrisan` bit(1) NOT NULL,
  `cijena` decimal(19,2) DEFAULT NULL,
  `grupa_radova` varchar(255) DEFAULT NULL,
  `kategorija_rad` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rad`
--

LOCK TABLES `rad` WRITE;
/*!40000 ALTER TABLE `rad` DISABLE KEYS */;
INSERT INTO `rad` VALUES (1,'2017-09-13 14:37:42','2017-09-13 14:48:48','\0',55.00,'Zidar','I'),(2,'2017-09-13 14:37:53','2017-09-13 14:37:53','\0',60.00,'Zidar','II'),(3,'2017-09-13 14:39:06','2017-09-13 14:39:06','\0',65.00,'Zidar','III'),(4,'2017-09-13 14:39:21','2017-09-13 14:39:21','\0',70.00,'Zidar','IV'),(5,'2017-09-13 14:41:03','2017-09-13 14:41:03','\0',75.00,'Zidar','V'),(6,'2017-09-13 14:44:26','2017-09-13 14:44:26','\0',80.00,'Zidar','VI'),(7,'2017-09-13 14:44:38','2017-09-13 14:44:38','\0',85.00,'Zidar','VII'),(8,'2017-09-13 14:44:53','2017-09-13 14:44:53','\0',90.00,'Zidar','VIII'),(9,'2017-09-13 14:45:12','2017-09-13 14:45:30','\0',50.00,'Radnik','I'),(10,'2017-09-13 14:45:22','2017-09-13 14:45:34','\0',52.50,'Radnik','II'),(11,'2017-09-13 14:45:54','2017-09-13 14:45:54','\0',55.00,'Radnik','III'),(12,'2017-09-13 14:46:09','2017-09-13 14:46:09','\0',57.50,'Radnik','IV'),(13,'2017-09-13 14:46:19','2017-09-13 14:46:19','\0',60.00,'Radnik','V'),(14,'2017-09-13 14:46:27','2017-09-13 14:46:27','\0',62.50,'Radnik','VI'),(15,'2017-09-13 14:46:37','2017-09-13 14:46:37','\0',65.00,'Radnik','VII'),(16,'2017-09-13 14:46:48','2017-09-13 14:46:48','\0',70.00,'Radnik','VIII');
/*!40000 ALTER TABLE `rad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stavkatroskovnik`
--

DROP TABLE IF EXISTS `stavkatroskovnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stavkatroskovnik` (
  `id` bigint(20) NOT NULL,
  `datum_kreiranja` datetime DEFAULT NULL,
  `datum_promjene` datetime DEFAULT NULL,
  `obrisan` bit(1) NOT NULL,
  `dodatan_opsi` varchar(255) DEFAULT NULL,
  `kolicina_troskovnik` decimal(19,2) DEFAULT NULL,
  `ukupna_cijena` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stavkatroskovnik`
--

LOCK TABLES `stavkatroskovnik` WRITE;
/*!40000 ALTER TABLE `stavkatroskovnik` DISABLE KEYS */;
/*!40000 ALTER TABLE `stavkatroskovnik` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-13 18:11:04
