SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema creditBoard
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema creditBoard
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `creditBoard` DEFAULT CHARACTER SET utf8 ;
USE `creditBoard` ;

CREATE TABLE IF NOT EXISTS `creditBoard`.`login` (
  id bigint NOT NULL AUTO_INCREMENT,
  email varchar(30) NOT NULL,
  senha varchar(30) NOT NULL,
  creation_date timestamp NOT NULL,
  username varchar(30) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `creditBoard`.`creation_history` (
	id bigint NOT NULL AUTO_INCREMENT,
	document_number varchar(64) NOT NULL,
	update_date timestamp NOT NULL,
	username varchar(30) NOT NULL,
	approved_amount decimal(20,5) NOT NULL,
	product varchar(30) NOT NULL,
	reason varchar(100) NOT NULL,
	PRIMARY KEY (id)
)
ENGINE = InnoDB;
