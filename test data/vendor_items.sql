-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Mar 28, 2018 at 05:18 PM
-- Server version: 5.6.35
-- PHP Version: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `honeycomb`
--

--
-- Dumping data for table `vendor_items`
--

INSERT INTO `vendor_items` (`vendor_items_id`, `availability_status`, `item_id`, `vendor_id`) VALUES
(1, 'AVAILABLE', 1, 1),
(2, 'AVAILABLE', 2, 1),
(3, 'AVAILABLE', 3, 1),
(4, 'AVAILABLE', 4, 1),
(5, 'AVAILABLE', 5, 1),
(6, 'AVAILABLE', 6, 1),
(7, 'AVAILABLE', 7, 1),
(8, 'AVAILABLE', 8, 1),
(9, 'AVAILABLE', 9, 1),
(10, 'NOT_AVAILABLE', 10, 1),
(11, 'AVAILABLE', 1, 2),
(12, 'AVAILABLE', 2, 2),
(13, 'NOT_AVAILABLE', 3, 2),
(14, 'AVAILABLE', 4, 2),
(15, 'NOT_AVAILABLE', 5, 2),
(16, 'AVAILABLE', 6, 2),
(17, 'AVAILABLE', 7, 2),
(18, 'AVAILABLE', 8, 2),
(19, 'AVAILABLE', 9, 2),
(20, 'AVAILABLE', 10, 2),
(21, 'AVAILABLE', 1, 3),
(22, 'AVAILABLE', 2, 3),
(23, 'AVAILABLE', 3, 3),
(24, 'AVAILABLE', 4, 3),
(25, 'AVAILABLE', 5, 3),
(26, 'AVAILABLE', 6, 3),
(27, 'AVAILABLE', 7, 3),
(28, 'AVAILABLE', 8, 3),
(29, 'AVAILABLE', 9, 3),
(30, 'AVAILABLE', 10, 3),
(31, 'NOT_AVAILABLE', 1, 4),
(32, 'AVAILABLE', 2, 4),
(33, 'AVAILABLE', 3, 4),
(34, 'NOT_AVAILABLE', 4, 4),
(35, 'AVAILABLE', 5, 4),
(36, 'AVAILABLE', 6, 4),
(37, 'NOT_AVAILABLE', 7, 4),
(38, 'AVAILABLE', 8, 4),
(39, 'AVAILABLE', 9, 4),
(40, 'AVAILABLE', 10, 4);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
