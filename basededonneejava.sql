-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  sam. 27 avr. 2019 à 10:55
-- Version du serveur :  10.1.38-MariaDB
-- Version de PHP :  7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `basededonneejava`
--

-- --------------------------------------------------------

--
-- Structure de la table `evaluation_table`
--

CREATE TABLE `evaluation_table` (
  `subject` varchar(30) COLLATE utf8_bin NOT NULL,
  `mark` float NOT NULL,
  `lecturer_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `index` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `lecturer_table`
--

CREATE TABLE `lecturer_table` (
  `id` int(11) NOT NULL,
  `name` varchar(30) COLLATE utf8_bin NOT NULL,
  `last_name` varchar(25) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `promotion_student`
--

CREATE TABLE `promotion_student` (
  `student_id` int(11) NOT NULL,
  `promotion_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `promotion_table`
--

CREATE TABLE `promotion_table` (
  `id` int(11) NOT NULL,
  `name` varchar(30) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `student_table`
--

CREATE TABLE `student_table` (
  `name` varchar(20) COLLATE utf8_bin NOT NULL,
  `id` int(11) NOT NULL,
  `last_name` varchar(30) COLLATE utf8_bin NOT NULL,
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `evaluation_table`
--
ALTER TABLE `evaluation_table`
  ADD PRIMARY KEY (`index`,`student_id`),
  ADD KEY `student_id` (`student_id`);

--
-- Index pour la table `lecturer_table`
--
ALTER TABLE `lecturer_table`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `promotion_student`
--
ALTER TABLE `promotion_student`
  ADD KEY `student_id` (`student_id`),
  ADD KEY `promotion_id` (`promotion_id`);

--
-- Index pour la table `promotion_table`
--
ALTER TABLE `promotion_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `promotion_table_name_uindex` (`name`);

--
-- Index pour la table `student_table`
--
ALTER TABLE `student_table`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `lecturer_table`
--
ALTER TABLE `lecturer_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `promotion_table`
--
ALTER TABLE `promotion_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `evaluation_table`
--
ALTER TABLE `evaluation_table`
  ADD CONSTRAINT `evaluation_table_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student_table` (`id`);

--
-- Contraintes pour la table `promotion_student`
--
ALTER TABLE `promotion_student`
  ADD CONSTRAINT `promotion_student_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student_table` (`id`),
  ADD CONSTRAINT `promotion_student_ibfk_2` FOREIGN KEY (`promotion_id`) REFERENCES `promotion_table` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
