-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ParkingLot
-- -----------------------------------------------------
-- deck Software development DB
DROP SCHEMA IF EXISTS `ParkingLot` ;

-- -----------------------------------------------------
-- Schema ParkingLot
--
-- deck Software development DB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ParkingLot` DEFAULT CHARACTER SET latin1 COLLATE latin1_spanish_ci ;
USE `ParkingLot` ;

-- -----------------------------------------------------
-- Table `ParkingLot`.`Transactions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ParkingLot`.`Transactions` ;

CREATE TABLE IF NOT EXISTS `ParkingLot`.`Transactions` (
  `trnId` INT NOT NULL AUTO_INCREMENT,
  `TktSer` VARCHAR(45) NOT NULL,
  `Brand` VARCHAR(45) NOT NULL,
  `Type` VARCHAR(45) NOT NULL,
  `Color` VARCHAR(45) NOT NULL,
  `TktInc` DATETIME NOT NULL,
  `TktOut` DATETIME NULL,
  `TsInc` TIMESTAMP NOT NULL DEFAULT current_timestamp,
  `TsOut` TIMESTAMP NULL on update current_timestamp,
  PRIMARY KEY (`trnId`),
  UNIQUE INDEX `trnId_UNIQUE` (`trnId` ASC) VISIBLE,
  UNIQUE INDEX `TktSer_UNIQUE` (`TktSer` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
