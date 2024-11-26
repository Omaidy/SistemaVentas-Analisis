-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 26, 2024 at 03:18 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sistemav`
--

-- --------------------------------------------------------

--
-- Table structure for table `empleados`
--

CREATE TABLE `empleados` (
  `id_empleado` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `cargo` varchar(255) NOT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `empleados`
--

INSERT INTO `empleados` (`id_empleado`, `nombre`, `apellido`, `cargo`, `fecha_creacion`) VALUES
(2, 'pedro', 'alimaña', 'vendedor', '2024-11-27 03:09:20');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `id` int(11) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `usuario` varchar(255) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `actividad` tinyint(1) DEFAULT 1,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `id_empleado`, `usuario`, `contrasena`, `actividad`, `fecha_creacion`) VALUES
(3, 2, 'prueba', '12345678', 0, '2024-11-23 03:11:58');

-- --------------------------------------------------------

--
-- Table structure for table `productos`
--

CREATE TABLE `productos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `talla` varchar(10) DEFAULT NULL,
  `stock` int(11) DEFAULT 0,
  `precio` decimal(10,2) NOT NULL,
  `categoria` varchar(100) DEFAULT NULL,
  `descripcion` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `productos`
--

INSERT INTO `productos` (`id`, `nombre`, `talla`, `stock`, `precio`, `categoria`, `descripcion`) VALUES
(1, 'Camiseta', 'M', 100, 19.99, 'Ropa', 'Camiseta de algodón 100% de alta calidad'),
(2, 'Jeans', 'L', 49, 49.99, 'Ropa', 'Jeans de corte clásico para hombre'),
(3, 'Zapatos deportivos', '42', 29, 89.99, 'Calzado', 'Zapatos deportivos de running, color negro');

-- --------------------------------------------------------

--
-- Table structure for table `sesiones`
--

CREATE TABLE `sesiones` (
  `id_sesion` int(11) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `fecha_inicio` datetime NOT NULL,
  `fecha_cierre` datetime DEFAULT NULL,
  `duracion` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sesiones`
--

INSERT INTO `sesiones` (`id_sesion`, `id_empleado`, `fecha_inicio`, `fecha_cierre`, `duracion`) VALUES
(1, 2, '2024-11-22 23:37:22', NULL, NULL),
(2, 2, '2024-11-22 23:38:47', '2024-11-22 23:38:49', 2),
(3, 2, '2024-11-22 23:50:19', NULL, NULL),
(4, 2, '2024-11-22 23:54:23', NULL, NULL),
(5, 2, '2024-11-22 23:55:19', '2024-11-22 23:55:20', 0),
(6, 2, '2024-11-22 23:58:51', '2024-11-22 23:59:01', 0),
(7, 2, '2024-11-23 00:01:14', '2024-11-23 00:01:23', 0),
(8, 2, '2024-11-23 00:03:03', '2024-11-23 00:03:04', 0),
(9, 2, '2024-11-23 00:04:25', '2024-11-23 00:04:34', 0),
(10, 2, '2024-11-23 00:06:11', '2024-11-23 00:06:18', 7),
(11, 2, '2024-11-23 00:11:29', '2024-11-23 00:11:44', 14),
(13, 2, '2024-11-25 14:12:09', '2024-11-25 14:12:56', 46),
(14, 2, '2024-11-25 14:13:26', '2024-11-25 14:14:35', 69),
(18, 2, '2024-11-25 14:35:16', '2024-11-25 14:35:31', 15);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`id_empleado`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_empleado` (`id_empleado`);

--
-- Indexes for table `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sesiones`
--
ALTER TABLE `sesiones`
  ADD PRIMARY KEY (`id_sesion`),
  ADD KEY `id_empleado` (`id_empleado`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `empleados`
--
ALTER TABLE `empleados`
  MODIFY `id_empleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `sesiones`
--
ALTER TABLE `sesiones`
  MODIFY `id_sesion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `login`
--
ALTER TABLE `login`
  ADD CONSTRAINT `login_ibfk_1` FOREIGN KEY (`id_empleado`) REFERENCES `empleados` (`id_empleado`);

--
-- Constraints for table `sesiones`
--
ALTER TABLE `sesiones`
  ADD CONSTRAINT `sesiones_ibfk_1` FOREIGN KEY (`id_empleado`) REFERENCES `empleados` (`id_empleado`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
