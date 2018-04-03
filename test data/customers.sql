-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Apr 03, 2018 at 07:53 PM
-- Server version: 5.6.35
-- PHP Version: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `honeycomb`
--

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customer_id`, `email`, `first_name`, `last_name`, `mobile`, `signup_date`, `email_verification_status`, `mobile_verification_status`) VALUES
(1, 'u@u.com', 'user', 'user', '9876543210', '2018-03-28 20:22:00', 'NOT_VERIFIED', 'NOT_VERIFIED');
