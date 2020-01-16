
-- If database exit delete it
DROP DATABASE IF EXISTS transmidb;

-- Create database
CREATE DATABASE IF NOT EXISTS transmidb
DEFAULT CHARACTER SET utf8
COLLATE utf8_spanish_ci;

-- Use database
USE transmidb;

-- Schedule table creation
CREATE TABLE `schedule` (
  `id` int(2) PRIMARY KEY AUTO_INCREMENT,
  `schedule` varchar(20) NOT NULL
);

-- Color table creation
CREATE TABLE `color` (
  `id` int(1) PRIMARY KEY AUTO_INCREMENT,
  `color` varchar(7) NOT NULL
);
-- Insert data to the color table
INSERT INTO `color` (`color`) VALUES ('#ff0e0e'), ('#133f84');







-- Oriantation table creation for the estations
CREATE TABLE `orientation` (
  `id` int(1) PRIMARY KEY AUTO_INCREMENT,
  `orientation` varchar(10) NOT NULL
);
-- Insert data to the orientation table
INSERT INTO `orientation` (`orientation`) VALUES ("null"), ("vertical"), ("horizontal");

-- Direction table creation for the estations
CREATE TABLE `direction` (
  `id` int(1) PRIMARY KEY AUTO_INCREMENT,
  `direction` varchar(5) NOT NULL
);
-- Insert data to the direction table
INSERT INTO `direction` (`direction`) VALUES ("null"), ("up"), ("down"), ("left"), ("right");

-- Text coordinates table for the map
CREATE TABLE `text_coordinates` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `xText` int(11) NOT NULL,
  `yText` int(11) NOT NULL,
  `wText` int(11) NOT NULL,
  `hText` int(11) NOT NULL
);
-- Insert to the TextCoordinates table
INSERT INTO `text_coordinates` (`xText`, `yText`, `wText`, `hText`) VALUES
  (930, 744, 45, 26),
  (884, 744, 34, 24),
  (774, 722, 82, 13),
  (784, 694, 72, 13),
  (808, 664, 49, 13),
  (794, 641, 51, 13),
  (856, 618, 46, 24),
  (839, 605, 47, 13),
  (821, 588, 40, 13),
  (802, 562, 48, 24),
  (782, 540, 74, 13),
  (782, 514, 54, 13),
  (782, 488, 76, 13),
  (782, 462, 86, 13),
  (715, 394, 42, 13),
  (702, 360, 52, 24),
  (716, 334, 38, 24)
;

-- Properties table with text and station coordinates and properties
CREATE TABLE `properties` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `colorFK` int(1) NOT NULL,
  `orientationFK` int(1) NOT NULL,
  `directionFK` int(1) NOT NULL,
  `textCoordinatesFK` int(11) NOT NULL,

  FOREIGN KEY (`colorFK`) REFERENCES `color` (`id`),
  FOREIGN KEY (`orientationFK`) REFERENCES `orientation` (`id`),
  FOREIGN KEY (`directionFK`) REFERENCES `direction` (`id`),
  FOREIGN KEY (`textCoordinatesFK`) REFERENCES `text_coordinates` (`id`)
);
-- Insert data to the Properties table
INSERT INTO `properties` (`x`, `y`, `colorFK`, `orientationFK`, `directionFK`, `textCoordinatesFK`) VALUES
  (940, 723, 1, 1, 1, 1),
  (900, 730, 1, 2, 3, 2),
  (870, 728, 1, 3, 4, 3),
  (870, 700, 1, 3, 4, 4),
  (870, 670, 1, 3, 4, 5),
  (859, 648, 1, 3, 4, 6),
  (843, 630, 1, 3, 5, 7),
  (825, 612, 1, 3, 5, 8),
  (807, 594, 1, 3, 5, 9),
  (788, 576, 1, 3, 5, 10),
  (768, 546, 1, 3, 5, 11),
  (768, 520, 1, 3, 5, 12),
  (768, 494, 1, 3, 5, 13),
  (768, 468, 1, 3, 5, 14),
  (761, 400, 1, 1, 1, 15),
  (768, 372, 1, 3, 4, 16),
  (768, 344, 1, 3, 4, 17)
;







-- Routes table creation
CREATE TABLE `routes` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT,
  `routeCode` varchar(5) NOT NULL,
  `routeName` varchar(30) NOT NULL
);
-- Insert data to the Routes table
INSERT INTO `routes` (`routeCode`, `routeName`) VALUES
  ('5', 'Calle 22'),
  ('M51', 'Museo nacional')
;

-- Stations table creation
CREATE TABLE `stations` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `stationName` varchar(30) NOT NULL,
  `propertiesFK` int(11) NOT NULL,

  FOREIGN KEY (`propertiesFK`) REFERENCES `properties` (`id`)
);
-- Insert data to the Stations table
INSERT INTO `stations` (`stationName`, `propertiesFK`) VALUES
  ('Portal_Américas', 1),
  ('Patio_Bonito', 2),
  ('Biblioteca Tintal', 3),
  ('Transversal 86', 4),
  ('Banderas', 5),
  ('Mandalay', 6),
  ('Mundo_Aventura', 7),
  ('Marsella', 8),
  ('Pradera', 9),
  ('Américas_KR 53A', 10),
  ('Puente Aranda', 11),
  ('Carrera 43', 12),
  ('Zona Industrial', 13),
  ('CDS - Carrera 32', 14),
  ('Ricaurte', 15),
  ('San Façón_- KR 22', 16),
  ('De la_Sabana', 17)
;

-- Line table creation
CREATE TABLE `lines` (
  `id` int(2) PRIMARY KEY AUTO_INCREMENT,
  `lineName` varchar(30) NOT NULL,
  `lineCode` varchar(1) NOT NULL,
  `colorFK` int(1) NOT NULL,
  `coordinates` varchar(80) NOT NULL,
  
  FOREIGN KEY (`colorFK`) REFERENCES `color` (`id`)
);
-- Insert data into Line table
INSERT INTO `lines` (`lineName`, `lineCode`, `colorFK`, `coordinates`) VALUES
  ('Americas', 'F', 1, '940 730 870 730 870 730 870 660 868 655 770 560 768 555 768 300'),
  ('Caracas', 'A', 2, '825 296 385 296')
;

-- Stops table cration
CREATE TABLE `stops` (
  `id` int(3) PRIMARY KEY AUTO_INCREMENT,
  `stop` int(2) NOT NULL,
  `routeFK` int(3) NOT NULL,
  `stationFK` int(11) NOT NULL,
  `lineFK` int(2) NOT NULL,
  
  FOREIGN KEY (`routeFK`) REFERENCES `Routes` (`id`),
  FOREIGN KEY (`stationFk`) REFERENCES `Stations` (`id`),
  FOREIGN KEY (`lineFK`) REFERENCES `Lines` (`id`)
);
-- Insert data into Stops table
INSERT INTO `stops` (`stop`, `routeFK`, `stationFK`, `lineFK`) VALUES
  (1, 1, 1, 1),
  (2, 1, 2, 1),
  (3, 1, 3, 1),
  (4, 1, 4, 1),
  (5, 1, 5, 1),
  (6, 1, 6, 1),
  (7, 1, 7, 1),
  (8, 1, 8, 1),
  (9, 1, 9, 1),
  (10, 1, 10, 1),
  (11, 1, 11, 1),
  (12, 1, 12, 1),
  (13, 1, 13, 1),
  (14, 1, 14, 1),
  (15, 1, 15, 1),
  (16, 1, 16, 1),
  (17, 1, 17, 1),

  (1, 2, 1, 1),
  (2, 2, 2, 1),
  (3, 2, 5, 1),
  (4, 2, 7, 1),
  (5, 2, 9, 1),
  (6, 2, 10, 1)
;







-- First Name table creation
CREATE TABLE `firstname` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT,
  `firstName` varchar(25) NOT NULL
);
-- Insert some random names to the First Name table
INSERT INTO `firstname` (`firstName`) VALUES ("Miguel"), ("Alejandro"), ("Liliana"), ("Camila");

-- Last Name table creation
CREATE TABLE `Lastname` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT,
  `lastName` varchar(25) NOT NULL
);
-- Insert some random names to the Last Name table
INSERT INTO `LastName` (`lastName`) VALUES ("Sanchez"), ("Castaneda"), ("Rodriguez"), ("Gutierrez");


-- Users table creation
CREATE TABLE `users` (
  `id` int(4) PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `firstNameFK` int(5) NOT NULL,
  `lastNameFK` int(5) NOT NULL,
  
  FOREIGN KEY (`firstNameFK`) REFERENCES `FirstName` (`id`),
  FOREIGN KEY (`lastNameFK`) REFERENCES `LastName` (`id`)
);