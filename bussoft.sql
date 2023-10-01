-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 26-05-2022 a las 22:34:09
-- Versión del servidor: 8.0.17
-- Versión de PHP: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bussoft`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `al_almacen`
--

CREATE TABLE `al_almacen` (
  `al_id` int(11) NOT NULL,
  `al_cantidad` int(11) NOT NULL,
  `pr_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cr_carrito`
--

CREATE TABLE `cr_carrito` (
  `cr_id` int(11) NOT NULL,
  `cr_cantidad` float NOT NULL,
  `cr_precio` float NOT NULL,
  `cr_costo` float NOT NULL,
  `pr_id` int(11) NOT NULL,
  `vt_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cr_carrito`
--

INSERT INTO `cr_carrito` (`cr_id`, `cr_cantidad`, `cr_precio`, `cr_costo`, `pr_id`, `vt_id`) VALUES
(34, 1, 80, 68, 1000000, 6),
(35, 2, 80, 68, 1000000, 7),
(36, 2, 100, 80, 1000001, 7),
(37, 1, 80, 68, 1000000, 8),
(38, 20, 80, 68, 1000000, 9),
(39, 1, 80, 68, 1000000, 10),
(40, 1, 80, 68, 1000000, 11),
(41, 6, 80, 68, 1000000, 12),
(42, 3, 80, 68, 1000000, 13),
(43, 1, 100, 80, 1000001, 13),
(44, 1, 100, 80, 1000001, 14),
(45, 1, 80, 68, 1000000, 15),
(46, 2, 80, 68, 1000000, 16),
(47, 1, 100, 80, 1000001, 16),
(48, 2, 80, 68, 1000000, 17),
(49, 2, 100, 80, 1000001, 17),
(50, 2, 80, 68, 1000000, 18),
(51, 1, 100, 80, 1000001, 18),
(52, 2, 80, 68, 1000000, 19),
(53, 2, 10, 5, 1000002, 20),
(54, 1, 80, 68, 1000000, 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dp_datospersona`
--

CREATE TABLE `dp_datospersona` (
  `dp_id` int(11) NOT NULL,
  `dp_nombres` varchar(30) NOT NULL,
  `dp_apellidoPaterno` varchar(30) NOT NULL,
  `dp_apellidoMaterno` varchar(30) NOT NULL,
  `dp_fechaNacimiento` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `dp_datospersona`
--

INSERT INTO `dp_datospersona` (`dp_id`, `dp_nombres`, `dp_apellidoPaterno`, `dp_apellidoMaterno`, `dp_fechaNacimiento`) VALUES
(1, 'Jesus', 'Navarro', 'Salcido', '2002-11-19'),
(5, 'Vanessa Guadalupe', 'Ramirez', 'Sanchez', '2002-04-21');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ma_marcas`
--

CREATE TABLE `ma_marcas` (
  `ma_id` int(11) NOT NULL,
  `ma_nombre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ma_marcas`
--

INSERT INTO `ma_marcas` (`ma_id`, `ma_nombre`) VALUES
(17, 'Barcel'),
(18, 'Gamesa'),
(19, 'Nivea');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mp_metodos_pago`
--

CREATE TABLE `mp_metodos_pago` (
  `mp_id` int(11) NOT NULL,
  `mp_name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mp_metodos_pago`
--

INSERT INTO `mp_metodos_pago` (`mp_id`, `mp_name`) VALUES
(1, 'Efectivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pr_productos`
--

CREATE TABLE `pr_productos` (
  `pr_id` int(11) NOT NULL,
  `pr_codigoBarras` varchar(20) NOT NULL,
  `pr_nombre` varchar(30) NOT NULL,
  `pr_precio` float NOT NULL,
  `pr_costo` float NOT NULL,
  `pr_visible` tinyint(1) NOT NULL DEFAULT '0',
  `ma_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pr_productos`
--

INSERT INTO `pr_productos` (`pr_id`, `pr_codigoBarras`, `pr_nombre`, `pr_precio`, `pr_costo`, `pr_visible`, `ma_id`) VALUES
(999999, 'BARRSASA', 'NEW SA', 100, 220, 0, 17),
(1000000, 'NIVEA12', 'CREMA FACIAL ', 80, 68, 1, 19),
(1000001, '787281', 'NOSE', 100, 80, 1, 19),
(1000002, '7287218', 'CREMA', 10, 5, 1, 19);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tu_tiposusuario`
--

CREATE TABLE `tu_tiposusuario` (
  `tu_id` int(11) NOT NULL,
  `tu_tipo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tu_tiposusuario`
--

INSERT INTO `tu_tiposusuario` (`tu_id`, `tu_tipo`) VALUES
(1000, 'Super Administrador'),
(1001, 'Super Moderador'),
(1010, 'Administrador'),
(1011, 'Empleado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `us_usuarios`
--

CREATE TABLE `us_usuarios` (
  `us_id` int(11) NOT NULL,
  `us_usuario` varchar(15) NOT NULL,
  `us_email` varchar(30) NOT NULL,
  `us_clave` varchar(40) DEFAULT NULL,
  `us_fechaCreado` datetime DEFAULT CURRENT_TIMESTAMP,
  `dp_id` int(11) DEFAULT NULL,
  `tu_id` int(11) NOT NULL DEFAULT '1011'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `us_usuarios`
--

INSERT INTO `us_usuarios` (`us_id`, `us_usuario`, `us_email`, `us_clave`, `us_fechaCreado`, `dp_id`, `tu_id`) VALUES
(1, 'Jesus', 'jesussalcido2002@gmail.com', '12345678', '2022-05-16 10:59:44', 1, 1010),
(4, 'Vaniux', 'Vaniux@gmail.com', '12345', '2022-05-22 12:39:16', 5, 1011);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vt_ventas`
--

CREATE TABLE `vt_ventas` (
  `vt_id` int(11) NOT NULL,
  `vt_total` float DEFAULT NULL,
  `vt_completed` tinyint(1) NOT NULL DEFAULT '0',
  `vt_fecha` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `mp_id` int(11) DEFAULT NULL,
  `us_id_caja` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vt_ventas`
--

INSERT INTO `vt_ventas` (`vt_id`, `vt_total`, `vt_completed`, `mp_id`, `us_id_caja`) VALUES
(6, 80, 1, 1, 1),
(7, 360, 1, 1, 1),
(8, 80, 1, 1, 1),
(9, 1600, 1, 1, 1),
(10, 80, 1, 1, 1),
(11, 80, 1, 1, 1),
(12, 480, 1, 1, 1),
(13, 340, 1, 1, 1),
(14, 100, 1, 1, 1),
(15, 80, 1, 1, 1),
(16, 260, 1, 1, 1),
(17, 360, 1, 1, 1),
(18, 260, 1, 1, 1),
(19, 160, 1, 1, 1),
(20, 100, 1, 1, 1),
(21, NULL, 0, NULL, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `al_almacen`
--
ALTER TABLE `al_almacen`
  ADD PRIMARY KEY (`al_id`),
  ADD KEY `pr_id` (`pr_id`);

--
-- Indices de la tabla `cr_carrito`
--
ALTER TABLE `cr_carrito`
  ADD PRIMARY KEY (`cr_id`),
  ADD KEY `pr_id` (`pr_id`),
  ADD KEY `vt_id` (`vt_id`);

--
-- Indices de la tabla `dp_datospersona`
--
ALTER TABLE `dp_datospersona`
  ADD PRIMARY KEY (`dp_id`);

--
-- Indices de la tabla `ma_marcas`
--
ALTER TABLE `ma_marcas`
  ADD PRIMARY KEY (`ma_id`),
  ADD UNIQUE KEY `ma_nombre` (`ma_nombre`) USING BTREE;

--
-- Indices de la tabla `mp_metodos_pago`
--
ALTER TABLE `mp_metodos_pago`
  ADD PRIMARY KEY (`mp_id`);

--
-- Indices de la tabla `pr_productos`
--
ALTER TABLE `pr_productos`
  ADD PRIMARY KEY (`pr_id`),
  ADD UNIQUE KEY `pr_codigoBarras` (`pr_codigoBarras`),
  ADD KEY `ma_id` (`ma_id`);

--
-- Indices de la tabla `tu_tiposusuario`
--
ALTER TABLE `tu_tiposusuario`
  ADD PRIMARY KEY (`tu_id`);

--
-- Indices de la tabla `us_usuarios`
--
ALTER TABLE `us_usuarios`
  ADD PRIMARY KEY (`us_id`),
  ADD UNIQUE KEY `us_usuario` (`us_usuario`),
  ADD UNIQUE KEY `us_email` (`us_email`),
  ADD KEY `dp_id` (`dp_id`),
  ADD KEY `tu_id` (`tu_id`);

--
-- Indices de la tabla `vt_ventas`
--
ALTER TABLE `vt_ventas`
  ADD PRIMARY KEY (`vt_id`),
  ADD KEY `mp_id` (`mp_id`),
  ADD KEY `us_id_caja` (`us_id_caja`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `al_almacen`
--
ALTER TABLE `al_almacen`
  MODIFY `al_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `cr_carrito`
--
ALTER TABLE `cr_carrito`
  MODIFY `cr_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT de la tabla `dp_datospersona`
--
ALTER TABLE `dp_datospersona`
  MODIFY `dp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;

--
-- AUTO_INCREMENT de la tabla `ma_marcas`
--
ALTER TABLE `ma_marcas`
  MODIFY `ma_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `mp_metodos_pago`
--
ALTER TABLE `mp_metodos_pago`
  MODIFY `mp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `pr_productos`
--
ALTER TABLE `pr_productos`
  MODIFY `pr_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1000003;

--
-- AUTO_INCREMENT de la tabla `tu_tiposusuario`
--
ALTER TABLE `tu_tiposusuario`
  MODIFY `tu_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1012;

--
-- AUTO_INCREMENT de la tabla `us_usuarios`
--
ALTER TABLE `us_usuarios`
  MODIFY `us_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `vt_ventas`
--
ALTER TABLE `vt_ventas`
  MODIFY `vt_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `al_almacen`
--
ALTER TABLE `al_almacen`
  ADD CONSTRAINT `al_almacen_ibfk_1` FOREIGN KEY (`pr_id`) REFERENCES `pr_productos` (`pr_id`);

--
-- Filtros para la tabla `cr_carrito`
--
ALTER TABLE `cr_carrito`
  ADD CONSTRAINT `cr_carrito_ibfk_1` FOREIGN KEY (`pr_id`) REFERENCES `pr_productos` (`pr_id`),
  ADD CONSTRAINT `cr_carrito_ibfk_2` FOREIGN KEY (`vt_id`) REFERENCES `vt_ventas` (`vt_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Filtros para la tabla `pr_productos`
--
ALTER TABLE `pr_productos`
  ADD CONSTRAINT `pr_productos_ibfk_1` FOREIGN KEY (`ma_id`) REFERENCES `ma_marcas` (`ma_id`);

--
-- Filtros para la tabla `us_usuarios`
--
ALTER TABLE `us_usuarios`
  ADD CONSTRAINT `us_usuarios_ibfk_1` FOREIGN KEY (`dp_id`) REFERENCES `dp_datospersona` (`dp_id`),
  ADD CONSTRAINT `us_usuarios_ibfk_2` FOREIGN KEY (`tu_id`) REFERENCES `tu_tiposusuario` (`tu_id`);

--
-- Filtros para la tabla `vt_ventas`
--
ALTER TABLE `vt_ventas`
  ADD CONSTRAINT `vt_ventas_ibfk_1` FOREIGN KEY (`mp_id`) REFERENCES `mp_metodos_pago` (`mp_id`),
  ADD CONSTRAINT `vt_ventas_ibfk_2` FOREIGN KEY (`us_id_caja`) REFERENCES `us_usuarios` (`us_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
