-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Apr 01, 2018 at 02:47 PM
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
-- Dumping data for table `authority`
--

INSERT INTO `authority` (`id`, `name`) VALUES
(1, 'ROLE_CUSTOMER'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_VENDOR');

--
-- Dumping data for table `user`
--

INSERT INTO `users` (`id`, `email`, `enabled`, `firstname`, `lastpasswordresetdate`, `lastname`, `password`, `username`) VALUES
(1, 'admin@admin.com', b'1', 'admin', '2016-01-01 00:00:00', 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'a@a.com'),
(2, 'user@user.com', b'1', 'user', '2016-01-01 00:00:00', 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'u@u.com'),
(3, 'vendor@user.com', b'1', 'vendor', '2016-01-01 00:00:00', 'vendor', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'v@v.com');

--
-- Dumping data for table `user_authority`
--

INSERT INTO `user_authority` (`user_id`, `authority_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(3, 3);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
