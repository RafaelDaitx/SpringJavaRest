-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.33 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.5.0.6677
-- --------------------------------------------------------


-- Dumping database structure for rest_with_spring_boot_erudio
CREATE DATABASE IF NOT EXISTS `rest_with_spring_boot_erudio` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `rest_with_spring_boot_erudio`;

-- Dumping structure for table rest_with_spring_boot_erudio.person
CREATE TABLE IF NOT EXISTS `person` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `address` varchar(100) NOT NULL,
    `first_name` varchar(80) NOT NULL,
    `gender` varchar(6) NOT NULL,
    `last_name` varchar(80) NOT NULL,
    `birthDay` date DEFAULT NULL,
    PRIMARY KEY (`id`)
);
