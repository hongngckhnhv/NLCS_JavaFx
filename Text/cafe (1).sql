-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 25, 2023 at 04:02 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cafe`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `customer_id` int(100) NOT NULL,
  `prod_id` varchar(100) NOT NULL,
  `prod_name` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  `quantity` int(100) NOT NULL,
  `price` double NOT NULL,
  `date` date DEFAULT NULL,
  `image` varchar(500) NOT NULL,
  `em_username` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `customer_id`, `prod_id`, `prod_name`, `type`, `quantity`, `price`, `date`, `image`, `em_username`) VALUES
(46, 1, '01', 'Cơm tấm', 'Thức ăn ', 2, 50, '2023-11-23', 'D:\\\\JavaFX\\\\NLCS_JavaFx\\\\src\\\\main\\\\resources\\\\com\\\\example\\\\demo\\\\image\\\\Comtam.jpg', 'admin'),
(47, 1, '02', 'Bánh mì', 'Thức ăn ', 1, 20, '2023-11-23', 'D:\\\\\\\\JavaFX\\\\\\\\NLCS_JavaFx\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\com\\\\\\\\example\\\\\\\\demo\\\\\\\\image\\\\\\\\banh-mi.jpg', 'admin'),
(48, 2, '05', 'sinh tố sapoche', 'Đồ uống ', 1, 25, '2023-11-23', 'D:\\\\\\\\JavaFX\\\\\\\\NLCS_JavaFx\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\com\\\\\\\\example\\\\\\\\demo\\\\\\\\image\\\\\\\\stsapoche.jpg', 'admin'),
(49, 2, '03', 'Bò né', 'Thức ăn ', 1, 45, '2023-11-23', 'D:\\\\\\\\JavaFX\\\\\\\\NLCS_JavaFx\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\com\\\\\\\\example\\\\\\\\demo\\\\\\\\image\\\\\\\\bone.jpg', 'admin'),
(50, 2, '02', 'Bánh mì', 'Thức ăn ', 1, 20, '2023-11-23', 'D:\\\\\\\\JavaFX\\\\\\\\NLCS_JavaFx\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\com\\\\\\\\example\\\\\\\\demo\\\\\\\\image\\\\\\\\banh-mi.jpg', 'admin'),
(51, 3, '04', 'Matcha đá xay', 'Đồ uống ', 2, 80, '2023-11-24', 'D:\\\\\\\\JavaFX\\\\\\\\NLCS_JavaFx\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\com\\\\\\\\example\\\\\\\\demo\\\\\\\\image\\\\\\\\match.jpg', 'admin'),
(52, 3, '05', 'sinh tố sapoche', 'Đồ uống ', 2, 50, '2023-11-24', 'D:\\\\\\\\JavaFX\\\\\\\\NLCS_JavaFx\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\com\\\\\\\\example\\\\\\\\demo\\\\\\\\image\\\\\\\\stsapoche.jpg', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `question` varchar(100) NOT NULL,
  `answer` varchar(100) NOT NULL,
  `date` date DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `username`, `password`, `question`, `answer`, `date`, `email`) VALUES
(36, 'admin ', '12345678', 'Nghệ sĩ yêu thích của bạn là ai?', 'Kim Taeyeon', '2023-10-03', 'khanh@gmail.com'),
(38, 'vhnkhanh', '12345678', 'Món ăn yêu thích của bạn là gì ? ', 'bun cha', '2023-10-06', 'khanh@gmail.com'),
(40, 'admin1', '12345678', 'Món ăn yêu thích của bạn là gì ? ', 'com tam', '2023-11-14', 'khanh@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `prod_id` varchar(100) NOT NULL,
  `prod_name` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  `stock` int(100) NOT NULL,
  `price` double NOT NULL,
  `status` varchar(100) NOT NULL,
  `image` varchar(500) NOT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `prod_id`, `prod_name`, `type`, `stock`, `price`, `status`, `image`, `date`) VALUES
(6, '01', 'Cơm tấm', 'Thức ăn ', 2, 25, 'Có sẵn', 'D:\\JavaFX\\NLCS_JavaFx\\src\\main\\resources\\com\\example\\demo\\image\\Comtam.jpg', '2023-10-10'),
(11, '02', 'Bánh mì', 'Thức ăn ', 13, 20, 'Có sẵn', 'D:\\\\JavaFX\\\\NLCS_JavaFx\\\\src\\\\main\\\\resources\\\\com\\\\example\\\\demo\\\\image\\\\banh-mi.jpg', '2023-11-04'),
(12, '03', 'Bò né', 'Thức ăn ', 23, 45, 'Có sẵn', 'D:\\\\JavaFX\\\\NLCS_JavaFx\\\\src\\\\main\\\\resources\\\\com\\\\example\\\\demo\\\\image\\\\bone.jpg', '2023-11-04'),
(13, '04', 'Matcha đá xay', 'Đồ uống ', 45, 40, 'Có sẵn', 'D:\\\\JavaFX\\\\NLCS_JavaFx\\\\src\\\\main\\\\resources\\\\com\\\\example\\\\demo\\\\image\\\\match.jpg', '2023-11-04'),
(14, '05', 'sinh tố sapoche', 'Đồ uống ', 42, 25, 'Có sẵn', 'D:\\\\JavaFX\\\\NLCS_JavaFx\\\\src\\\\main\\\\resources\\\\com\\\\example\\\\demo\\\\image\\\\stsapoche.jpg', '2023-11-04'),
(15, '06', 'sinh tố dâu', 'Đồ uống ', 45, 25, 'Có sẵn', 'D:\\\\JavaFX\\\\NLCS_JavaFx\\\\src\\\\main\\\\resources\\\\com\\\\example\\\\demo\\\\image\\\\stdau.jpg', '2023-11-04'),
(16, '07', 'Cafe đen', 'Đồ uống ', 45, 18, 'Có sẵn', 'D:\\\\JavaFX\\\\NLCS_JavaFx\\\\src\\\\main\\\\resources\\\\com\\\\example\\\\demo\\\\image\\\\den.jpg', '2023-11-24'),
(17, '08', 'Cafe sữa', 'Đồ uống ', 45, 22, 'Có sẵn', 'D:\\\\JavaFX\\\\NLCS_JavaFx\\\\src\\\\main\\\\resources\\\\com\\\\example\\\\demo\\\\image\\\\sua.jpg', '2023-11-24'),
(18, '09', 'Bạc xỉu', 'Đồ uống ', 45, 25, 'Có sẵn', 'D:\\\\JavaFX\\\\NLCS_JavaFx\\\\src\\\\main\\\\resources\\\\com\\\\example\\\\demo\\\\image\\\\bacxiu.jpg', '2023-11-24'),
(19, '010', 'Trà chanh giã tay', 'Đồ uống ', 45, 25, 'Có sẵn', 'D:\\\\JavaFX\\\\NLCS_JavaFx\\\\src\\\\main\\\\resources\\\\com\\\\example\\\\demo\\\\image\\\\chanh.jpg', '2023-11-24'),
(20, '011', 'Trà sữa', 'Đồ uống ', 45, 25, 'Có sẵn', 'D:\\\\JavaFX\\\\NLCS_JavaFx\\\\src\\\\main\\\\resources\\\\com\\\\example\\\\demo\\\\image\\\\trasua.jpg', '2023-11-24'),
(21, '012', 'Cam ép', 'Thức ăn ', 45, 15, 'Có sẵn', 'D:\\\\JavaFX\\\\NLCS_JavaFx\\\\src\\\\main\\\\resources\\\\com\\\\example\\\\demo\\\\image\\\\cam.jpg', '2023-11-24');

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--

CREATE TABLE `receipt` (
  `id` int(11) NOT NULL,
  `customer_id` int(100) NOT NULL,
  `total` double NOT NULL,
  `date` date DEFAULT NULL,
  `em_username` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`id`, `customer_id`, `total`, `date`, `em_username`) VALUES
(17, 1, 70, '2023-11-23', 'admin'),
(18, 2, 90, '2023-11-23', 'admin'),
(19, 3, 130, '2023-11-24', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `receipt`
--
ALTER TABLE `receipt`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `receipt`
--
ALTER TABLE `receipt`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
