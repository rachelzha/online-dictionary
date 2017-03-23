-- MySQL dump 10.13  Distrib 5.7.13, for osx10.11 (x86_64)
--
-- Host: localhost    Database: userinfo
-- ------------------------------------------------------
-- Server version	5.7.13

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
-- Table structure for table `all_message`
--

DROP TABLE IF EXISTS `all_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `all_message` (
  `receiver` varchar(45) COLLATE utf8_bin NOT NULL,
  `sender` varchar(45) COLLATE utf8_bin NOT NULL,
  `photoname` varchar(45) COLLATE utf8_bin NOT NULL,
  `time` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `all_message`
--

LOCK TABLES `all_message` WRITE;
/*!40000 ALTER TABLE `all_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `all_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `likes` (
  `word` varchar(45) COLLATE utf8_bin NOT NULL,
  `youdao` int(11) NOT NULL DEFAULT '0',
  `baidu` int(11) NOT NULL DEFAULT '0',
  `jinshan` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`word`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES ('',0,0,0),('  hel',0,0,0),('??',0,0,0),('A',0,0,0),('D',0,0,0),('E',0,0,0),('HALLP',0,0,0),('KKK',0,0,0),('O',0,0,0),('P',0,0,0),('R',0,0,0),('Rachel',0,0,0),('S',0,0,0),('T',0,0,0),('YY',0,0,0),('Zarah',0,0,0),('a',0,0,0),('aaa',0,0,0),('again',1,0,0),('ah',0,0,0),('amazing',0,0,0),('apple',1,0,0),('axmoasimxisoas',0,0,0),('bing',0,0,0),('change',0,0,0),('clone',0,0,0),('daily',0,0,0),('ddd',0,0,0),('dele',0,0,0),('e',0,0,0),('f',0,0,0),('ff',0,0,0),('fffff',0,0,0),('g',0,0,0),('gg',0,0,0),('h',0,0,0),('ha',0,0,0),('hall',0,0,0),('hallo',0,0,0),('happy',0,0,0),('he',0,0,0),('hea',0,0,0),('hell',0,0,0),('hello',2,2,3),('hh',0,0,0),('hhh',0,0,0),('l',0,0,0),('mon',0,0,0),('o',0,0,0),('ok',0,0,0),('online',0,0,0),('orange',0,0,0),('play',0,0,1),('pleasure',0,0,0),('r',0,0,0),('record',0,0,1),('s',0,0,0),('sand',0,0,0),('stormy',0,0,0),('t',0,0,0),('try',0,0,0),('un',0,0,0),('unbelievable',0,0,0),('want',0,0,0),('word',0,0,0),('yes',0,0,0),('youdao',1,1,0),('yy',0,0,0);
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_likes`
--

DROP TABLE IF EXISTS `personal_likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_likes` (
  `pusername` varchar(45) COLLATE utf8_bin NOT NULL,
  `pword` varchar(45) COLLATE utf8_bin NOT NULL,
  `youdao` int(11) NOT NULL DEFAULT '-1',
  `baidu` int(11) NOT NULL DEFAULT '-1',
  `jinshan` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`pusername`,`pword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_likes`
--

LOCK TABLES `personal_likes` WRITE;
/*!40000 ALTER TABLE `personal_likes` DISABLE KEYS */;
INSERT INTO `personal_likes` VALUES ('Rachel','',-1,-1,-1),('Rachel','  hel',-1,-1,-1),('Rachel','R',-1,-1,-1),('Rachel','Rachel',-1,-1,-1),('Rachel','Zarah',-1,-1,-1),('Rachel','apple',1,-1,-1),('Rachel','f',-1,-1,-1),('Rachel','g',-1,-1,-1),('Rachel','h',-1,-1,-1),('Rachel','ha',-1,-1,-1),('Rachel','hall',-1,-1,-1),('Rachel','hallo',-1,-1,-1),('Rachel','happy',-1,-1,-1),('Rachel','he',-1,-1,-1),('Rachel','hea',-1,-1,-1),('Rachel','hell',-1,-1,-1),('Rachel','hello',-1,1,1),('Rachel','hh',-1,-1,-1),('Rachel','l',-1,-1,-1),('Rachel','o',-1,-1,-1),('Rachel','orange',-1,-1,-1),('Rachel','play',-1,-1,-1),('Rachel','r',-1,-1,-1),('Rachel','s',-1,-1,-1),('Rachel','t',-1,-1,-1),('Rachel','word',-1,-1,-1),('Rachel','yes',-1,-1,-1),('Rachel','youdao',-1,-1,-1),('Rachel','yy',-1,-1,-1),('UU','E',-1,-1,-1),('UU','S',-1,-1,-1),('UU','T',-1,-1,-1),('UU','e',-1,-1,-1),('UU','happy',-1,-1,-1),('UU','hello',-1,-1,-1),('UU','hh',-1,-1,-1),('UU','play',-1,-1,-1),('UU','s',-1,-1,-1),('Z','A',-1,-1,-1),('Z','D',-1,-1,-1),('Z','E',-1,-1,-1),('Z','HALLP',-1,-1,-1),('Z','KKK',-1,-1,-1),('Z','O',-1,-1,-1),('Z','P',-1,-1,-1),('Z','S',-1,-1,-1),('Z','T',-1,-1,-1),('Z','YY',-1,-1,-1),('Z','a',-1,-1,-1),('Z','aaa',-1,-1,-1),('Z','again',1,-1,-1),('Z','amazing',-1,-1,-1),('Z','bing',-1,-1,-1),('Z','change',-1,-1,-1),('Z','daily',-1,-1,-1),('Z','ddd',-1,-1,-1),('Z','dele',-1,-1,-1),('Z','e',-1,-1,-1),('Z','f',-1,-1,-1),('Z','ff',-1,-1,-1),('Z','fffff',-1,-1,-1),('Z','h',-1,-1,-1),('Z','happy',-1,-1,-1),('Z','hello',-1,-1,1),('Z','hh',-1,-1,-1),('Z','hhh',-1,-1,-1),('Z','o',-1,-1,-1),('Z','ok',-1,-1,-1),('Z','online',-1,-1,-1),('Z','play',-1,-1,-1),('Z','pleasure',-1,-1,-1),('Z','s',-1,-1,-1),('Z','try',-1,-1,-1),('Z','un',-1,-1,-1),('Z','unbelievable',-1,-1,-1),('Z','want',-1,-1,-1),('Z','youdao',1,1,-1),('Zarah','??',-1,-1,-1),('Zarah','S',-1,-1,-1),('Zarah','again',-1,-1,-1),('Zarah','ah',-1,-1,-1),('Zarah','axmoasimxisoas',-1,-1,-1),('Zarah','clone',-1,-1,-1),('Zarah','gg',-1,-1,-1),('Zarah','h',-1,-1,-1),('Zarah','hello',1,1,1),('Zarah','mon',-1,-1,-1),('Zarah','o',-1,-1,-1),('Zarah','play',-1,-1,-1),('Zarah','pleasure',-1,-1,-1),('Zarah','s',-1,-1,-1),('Zarah','sand',-1,-1,-1),('Zarah','stormy',-1,-1,-1),('Zarah','want',-1,-1,-1),('Zarah','word',-1,-1,-1),('Zarah','youdao',-1,-1,-1),('rich','word',-1,-1,-1),('yyy','E',-1,-1,-1),('yyy','S',-1,-1,-1),('yyy','T',-1,-1,-1),('yyy','e',-1,-1,-1),('yyy','happy',-1,-1,-1),('yyy','hh',-1,-1,-1),('yyy','play',-1,-1,1),('yyy','record',-1,-1,1),('yyy','s',-1,-1,-1);
/*!40000 ALTER TABLE `personal_likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sentences`
--

DROP TABLE IF EXISTS `sentences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sentences` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sentence` char(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sentences`
--

LOCK TABLES `sentences` WRITE;
/*!40000 ALTER TABLE `sentences` DISABLE KEYS */;
INSERT INTO `sentences` VALUES (1,'Ever tried. Ever failed. No matter. Try Again. Fail again. Fail better.'),(2,'The great pleasure in life is doing what people say you cannot do.'),(3,'You raise me up,so I can stand on mountains .You raise me up,to walk on stormy seas. —《You raise me up》'),(4,'Just give yourself a chance.Fight the circumstance, Rising through, again!Keeping your head up to the sky!'),(5,'However big the problem, tell your heart,\" All is well, pal\".'),(6,'If you don\'t want to be a little ordinary sand, then try to be a shining star.'),(7,'All encounters are reunions after a long separation.');
/*!40000 ALTER TABLE `sentences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(45) CHARACTER SET utf8 NOT NULL,
  `password` varchar(45) COLLATE utf8_bin NOT NULL,
  `email` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('Amy','000000','a'),('Bloom','000000','a'),('Bob','000000','a'),('David','dd','a'),('Dunn','000000','a'),('ee','ee','a'),('Eli','000000','a'),('hhhh','llss','a'),('Jack','000000','a'),('Jim','000000','a'),('Judy','jj',''),('Mark','000000','a'),('Neil','000000','a'),('Rachel','rr','aa'),('Sara','000000','a'),('Southey','000000','a'),('Tyler','000000','a'),('UU','opop','a'),('yyy','111333','a'),('Z','123','a'),('zang','fengyun','a'),('Zarah','960706','2714359840@qq.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-18  0:10:38
