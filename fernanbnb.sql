-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: db
-- Tiempo de generación: 24-09-2023 a las 23:42:07
-- Versión del servidor: 8.1.0
-- Versión de PHP: 8.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `fernanbnb`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Administradores`
--

CREATE TABLE `Administradores` (
  `id` int NOT NULL,
  `nombre` varchar(250) NOT NULL,
  `clave` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `verificado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Administradores`
--

INSERT INTO `Administradores` (`id`, `nombre`, `clave`, `email`, `verificado`) VALUES
(3075152, 'Adrian', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'adrian@gmail.com', 1),
(3085177, 'Josemi', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'josemi@gmail.com', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `imagenesVivienda`
--

CREATE TABLE `imagenesVivienda` (
  `id` int NOT NULL,
  `imagen` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `imagenesVivienda`
--

INSERT INTO `imagenesVivienda` (`id`, `imagen`) VALUES
(1, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151571140909727824/h3e3w.png'),
(2, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151571141232705687/hsdcf.png'),
(3, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151571141538877480/hweef4.png'),
(4, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151571141815697478/hwef.png'),
(5, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151571142105120818/hascd.png'),
(6, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151571142386126919/hwervf.png'),
(7, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151571142709084242/hqed.png'),
(8, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151571143346634752/hasdc.png'),
(9, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151571144059654224/hqwde.png'),
(10, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151571144483274842/hwedc.png'),
(11, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151571247315046491/hewf.png'),
(12, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151571247633805382/hvfvv.png'),
(13, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151571247998701568/hfdv.png'),
(14, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151575436170625215/hsdcv.png'),
(15, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151575436535550043/hascd.png'),
(16, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151575436975939695/herg.png'),
(17, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151575437328273538/hdwver.png'),
(18, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151575437684768858/hefrv.png'),
(19, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151575438120984586/hv_sevf.png'),
(20, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151575438523629672/hpgthnp.png'),
(21, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151575438867570809/hedffvev.png'),
(22, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151575439307984977/hasdc.png'),
(37, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151837080536748082/h2dw.png'),
(38, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151837080830365756/hrgt.png'),
(39, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151837081241391235/herf.png'),
(40, 'https://cdn.discordapp.com/attachments/1151279463661850765/1151837081660837958/hjm.png');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Mensajes`
--

CREATE TABLE `Mensajes` (
  `id` int NOT NULL,
  `idEmisor` int DEFAULT NULL,
  `idReceptor` int DEFAULT NULL,
  `idVivienda` int DEFAULT NULL,
  `texto` text,
  `fechaEnvio` datetime DEFAULT NULL,
  `leido` tinyint(1) DEFAULT '0',
  `borradoEmisor` tinyint(1) DEFAULT '1',
  `borradoReceptor` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Mensajes`
--

INSERT INTO `Mensajes` (`id`, `idEmisor`, `idReceptor`, `idVivienda`, `texto`, `fechaEnvio`, `leido`, `borradoEmisor`, `borradoReceptor`) VALUES
(14, 1001177, 2033575, 4062394, 'a', '2023-09-22 12:31:13', 0, 0, 0),
(15, 1001177, 2033575, 4049062, '2', '2023-09-22 12:31:21', 0, 0, 0),
(16, 1001177, 2033575, 4049062, 'hola', '2023-09-22 12:31:31', 0, 0, 0),
(17, 1001177, 2033575, 4062394, 'que tal', '2023-09-22 12:31:43', 0, 0, 0),
(18, 1001177, 2029529, 4006477, 'juan', '2023-09-22 12:32:52', 0, 1, 0),
(19, 1001177, 2037774, 4097175, 'que tal', '2023-09-22 12:33:02', 1, 0, 1),
(20, 1001177, 2037774, 4029237, 'hola vice', '2023-09-22 12:33:10', 1, 0, 0),
(21, 1001177, 2037774, 4097175, 'que pasa', '2023-09-22 12:33:23', 1, 0, 0),
(22, 2037774, 1001177, 4097175, 'dime', '2023-09-24 18:13:15', 1, 0, 0),
(23, 1001177, 2037774, 4097175, 'nada', '2023-09-24 21:35:03', 1, 0, 0),
(24, 2037774, 1001177, 4097175, 'que', '2023-09-24 21:37:45', 0, 0, 0),
(25, 1001177, 2029529, 4006477, 'Muy buena estancia', '2023-09-25 00:39:06', 0, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Propietarios`
--

CREATE TABLE `Propietarios` (
  `id` int NOT NULL,
  `nombre` varchar(250) NOT NULL,
  `clave` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `verificado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Propietarios`
--

INSERT INTO `Propietarios` (`id`, `nombre`, `clave`, `email`, `verificado`) VALUES
(2029529, 'Juanito ', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'juan@gmail.com', 1),
(2033575, 'Felipe ', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'felipe@gmail.com', 1),
(2037774, 'Vicente ', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'vicente@gmail.com', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Reservas`
--

CREATE TABLE `Reservas` (
  `id` int NOT NULL,
  `idVivienda` int NOT NULL,
  `idUsuario` int NOT NULL,
  `fechaInicio` date NOT NULL,
  `numNoches` int NOT NULL,
  `precio` double NOT NULL,
  `ocupantes` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Reservas`
--

INSERT INTO `Reservas` (`id`, `idVivienda`, `idUsuario`, `fechaInicio`, `numNoches`, `precio`, `ocupantes`) VALUES
(5011019, 4097175, 1001177, '2023-06-26', 3, 190.5, 4),
(5024296, 4063415, -1, '2023-09-30', 2, 0, 0),
(5024409, 4097175, 1034045, '2023-12-03', 3, 190.5, 4),
(5024410, 4006477, 1001177, '2023-09-27', 1, 40, 1),
(5029648, 4063415, 1001177, '2023-09-23', 1, 42, 1),
(5040118, 4097175, 1001177, '2023-06-29', 5, 317.5, 4),
(5068666, 4029237, 1031761, '2023-10-03', 7, 280, 3),
(5094159, 4057887, 1031761, '2023-01-23', 10, 400, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Usuarios`
--

CREATE TABLE `Usuarios` (
  `id` int NOT NULL,
  `nombre` varchar(250) NOT NULL,
  `clave` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `verificado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Usuarios`
--

INSERT INTO `Usuarios` (`id`, `nombre`, `clave`, `email`, `verificado`) VALUES
(1001177, 'Paco', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'paco@gmail.com', 1),
(1001503, 'Maria', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'maria@gmail.com', 1),
(1031761, 'Cindy Nero', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'cindy@gmail.com', 1),
(1034045, 'eduardo', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'eduardo@gmail.com', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Viviendas`
--

CREATE TABLE `Viviendas` (
  `id` int NOT NULL,
  `idPropietario` int NOT NULL,
  `titulo` varchar(250) NOT NULL,
  `descripcion` varchar(250) NOT NULL,
  `localidad` varchar(250) NOT NULL,
  `provincia` varchar(250) NOT NULL,
  `maxOcupantes` int NOT NULL,
  `precioNoche` double NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Viviendas`
--

INSERT INTO `Viviendas` (`id`, `idPropietario`, `titulo`, `descripcion`, `localidad`, `provincia`, `maxOcupantes`, `precioNoche`, `visible`) VALUES
(4006477, 2029529, 'Casa de los Pitufos', 'Azul, preciosa y para gente bajita', 'Martos', 'Jaen', 20, 40, 1),
(4029237, 2037774, 'Caseria de las Delicias', 'Casa Rural', 'Álcala la Real', 'Jaén', 20, 40, 1),
(4049062, 2033575, 'Villa Churro', 'Pisito en la playa', 'Fuengirola', 'Málaga', 20, 40, 1),
(4057887, 2033575, 'Caserio Colesterol', 'Duplex reformado cerca de playa', 'Fuengirola', 'Málaga', 20, 40, 1),
(4062394, 2033575, 'Casa de la Peña', 'Caseron rocoso', 'Martos', 'Jaen', 20, 40, 1),
(4063415, 2037774, 'Mansión Fernando Herrera', 'Una casa tan espaciosa que podrías programar una aplicación de Flutter en cada habitación (¡y aún te sobraría espacio para más proyectos!)', 'Torredonjimeno', 'Jaén', 9, 42, 1),
(4077739, 2037774, 'Mansión Fernando Herrera', 'Una casa tan espaciosa que podrías programar una aplicación de Flutter en cada habitación (¡y aún te sobraría espacio para más proyectos!)', 'Torredonjimeno', 'Jaén', 9, 27, 0),
(4097175, 2037774, 'Paraíso del interior', 'Casa adosada y reformada', 'Martos', 'Jaen', 6, 63.5, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Administradores`
--
ALTER TABLE `Administradores`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `imagenesVivienda`
--
ALTER TABLE `imagenesVivienda`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `imagen` (`imagen`);

--
-- Indices de la tabla `Mensajes`
--
ALTER TABLE `Mensajes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idVivienda` (`idVivienda`),
  ADD KEY `idVivienda_2` (`idVivienda`);

--
-- Indices de la tabla `Propietarios`
--
ALTER TABLE `Propietarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `Reservas`
--
ALTER TABLE `Reservas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idVivienda` (`idVivienda`);

--
-- Indices de la tabla `Usuarios`
--
ALTER TABLE `Usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `Viviendas`
--
ALTER TABLE `Viviendas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idPropietario` (`idPropietario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `imagenesVivienda`
--
ALTER TABLE `imagenesVivienda`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT de la tabla `Mensajes`
--
ALTER TABLE `Mensajes`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Reservas`
--
ALTER TABLE `Reservas`
  ADD CONSTRAINT `Reservas_ibfk_2` FOREIGN KEY (`idVivienda`) REFERENCES `Viviendas` (`id`);

--
-- Filtros para la tabla `Viviendas`
--
ALTER TABLE `Viviendas`
  ADD CONSTRAINT `Viviendas_ibfk_1` FOREIGN KEY (`idPropietario`) REFERENCES `Propietarios` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
