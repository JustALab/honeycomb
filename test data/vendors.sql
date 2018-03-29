-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Mar 28, 2018 at 04:59 PM
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
-- Dumping data for table `vendors`
--

INSERT INTO `vendors` (`vendor_id`, `address`, `contact_email`, `contact_mobile`, `vendor_name`) VALUES
(1, 'Thiruvanmiyur', 'vendora@gmail.com', '9876543210', 'Vendor A'),
(2, 'Karapakkam', 'vendorb@gmail.com', '9876543210', 'Vendor B'),
(3, 'Sholinganallur', 'vendorc@gmail.com', '9876543210', 'Vendor C'),
(4, 'Navallur', 'vendord@gmail.com', '9876543210', 'Vendor D');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
