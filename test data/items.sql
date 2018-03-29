-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Mar 29, 2018 at 08:09 PM
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
-- Dumping data for table `items`
--

INSERT INTO `items` (`item_id`, `item_category`, `item_name`, `item_price`, `quantity_slab`) VALUES
(1, 'CAKE', 'Vanilla', 400, 1),
(2, 'CAKE', 'Strawberry', 450, 1),
(3, 'CAKE', 'Chocolate', 600, 1),
(4, 'CAKE', 'Choco Truffle', 800, 1),
(5, 'CAKE', 'Black Forest', 850, 1),
(6, 'CAKE', 'White Forest', 800, 1),
(7, 'PARTY_PACK', 'Party Hat', 20, 1),
(8, 'PARTY_PACK', 'Snow Spray', 80, 1),
(9, 'PARTY_PACK', 'Candle', 5, 1),
(10, 'PARTY_PACK', 'Fancy Candle', 20, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
