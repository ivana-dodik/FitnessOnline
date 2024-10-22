-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema fitness_online_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema fitness_online_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fitness_online_db` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema web_shop_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema web_shop_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `web_shop_db` DEFAULT CHARACTER SET utf8mb3 ;
USE `fitness_online_db` ;

-- -----------------------------------------------------
-- Table `fitness_online_db`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitness_online_db`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `parent_category_id` INT NULL DEFAULT NULL,
  `name` VARCHAR(45) NOT NULL,
  `deleted` TINYINT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `fk_category_category_idx` (`parent_category_id` ASC) VISIBLE,
  CONSTRAINT `fk_category_category`
    FOREIGN KEY (`parent_category_id`)
    REFERENCES `fitness_online_db`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fitness_online_db`.`log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitness_online_db`.`log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date_time` DATETIME NOT NULL,
  `content` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fitness_online_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitness_online_db`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `activated` TINYINT NOT NULL,
  `deleted` TINYINT NOT NULL,
  `avatar_url` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fitness_online_db`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitness_online_db`.`message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `content` VARCHAR(500) NOT NULL,
  `is_read` TINYINT NOT NULL,
  `date_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_message_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_message_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `fitness_online_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fitness_online_db`.`program`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitness_online_db`.`program` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(225) NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  `price` DECIMAL(12,2) NOT NULL,
  `difficulty_level` ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED') NOT NULL,
  `duration_minutes` INT NOT NULL,
  `location` VARCHAR(45) NOT NULL,
  `deleted` TINYINT NOT NULL,
  `contact_phone` VARCHAR(45) NOT NULL,
  `instructor_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  `time_created` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_program_user1_idx` (`instructor_id` ASC) VISIBLE,
  INDEX `fk_program_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_program_user1`
    FOREIGN KEY (`instructor_id`)
    REFERENCES `fitness_online_db`.`user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_program_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `fitness_online_db`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fitness_online_db`.`image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitness_online_db`.`image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `image_url` VARCHAR(500) NOT NULL,
  `program_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_image_program1_idx` (`program_id` ASC) VISIBLE,
  CONSTRAINT `fk_image_program1`
    FOREIGN KEY (`program_id`)
    REFERENCES `fitness_online_db`.`program` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fitness_online_db`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitness_online_db`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `content` VARCHAR(100) NOT NULL,
  `program_id` INT NOT NULL,
  `date_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_comment_program1_idx` (`program_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `fitness_online_db`.`user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_comment_program1`
    FOREIGN KEY (`program_id`)
    REFERENCES `fitness_online_db`.`program` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fitness_online_db`.`participation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitness_online_db`.`participation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `program_id` INT NOT NULL,
  `payment_method` ENUM('CASH', 'CREDIT-CARD', 'PAYPAL') NOT NULL,
  `payment_details` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_participation_program1_idx` (`program_id` ASC) VISIBLE,
  INDEX `fk_participation_user1_idx` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_participation_program1`
    FOREIGN KEY (`program_id`)
    REFERENCES `fitness_online_db`.`program` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_participation_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `fitness_online_db`.`user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fitness_online_db`.`attribute`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitness_online_db`.`attribute` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `category_id` INT NOT NULL,
  `deleted` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_attribute_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_attribute_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `fitness_online_db`.`category` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fitness_online_db`.`program_attribute`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitness_online_db`.`program_attribute` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `program_id` INT NOT NULL,
  `attribute_id` INT NOT NULL,
  `value` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_program_attribute_program1_idx` (`program_id` ASC) VISIBLE,
  INDEX `fk_program_attribute_attribute1_idx` (`attribute_id` ASC) VISIBLE,
  CONSTRAINT `fk_program_attribute_program1`
    FOREIGN KEY (`program_id`)
    REFERENCES `fitness_online_db`.`program` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_program_attribute_attribute1`
    FOREIGN KEY (`attribute_id`)
    REFERENCES `fitness_online_db`.`attribute` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fitness_online_db`.`subscription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitness_online_db`.`subscription` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_subscription_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_subscription_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_subscription_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `fitness_online_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subscription_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `fitness_online_db`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fitness_online_db`.`activity_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitness_online_db`.`activity_log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `exercise_type` VARCHAR(45) NOT NULL,
  `duration_minutes` INT NOT NULL,
  `intensity` VARCHAR(45) NOT NULL,
  `results` VARCHAR(45) NOT NULL,
  `log_date` DATETIME NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_activity_log_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_activity_log_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `fitness_online_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fitness_online_db`.`user_message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitness_online_db`.`user_message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(500) NOT NULL,
  `is_read` TINYINT NOT NULL,
  `date_time` DATETIME NOT NULL,
  `sender_id` INT NOT NULL,
  `receiver_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_message_user1_idx` (`sender_id` ASC) VISIBLE,
  INDEX `fk_user_message_user2_idx` (`receiver_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_message_user1`
    FOREIGN KEY (`sender_id`)
    REFERENCES `fitness_online_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_message_user2`
    FOREIGN KEY (`receiver_id`)
    REFERENCES `fitness_online_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fitness_online_db`.`advisor_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitness_online_db`.`advisor_account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `is_admin` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;

USE `web_shop_db` ;

-- -----------------------------------------------------
-- Table `web_shop_db`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop_db`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `parent_category_id` INT NULL DEFAULT NULL,
  `name` VARCHAR(45) NOT NULL,
  `deleted` TINYINT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `fk_category_category1_idx` (`parent_category_id` ASC) VISIBLE,
  CONSTRAINT `fk_category_category1`
    FOREIGN KEY (`parent_category_id`)
    REFERENCES `web_shop_db`.`category` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `web_shop_db`.`attribute`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop_db`.`attribute` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `type` ENUM('TEXT', 'NUMBER', 'DATE') NOT NULL,
  `category_id` INT NOT NULL,
  `deleted` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_attribute_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_attribute_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `web_shop_db`.`category` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `web_shop_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop_db`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `email` VARCHAR(80) NOT NULL,
  `activated` TINYINT NOT NULL,
  `deleted` TINYINT NOT NULL,
  `avatar_url` VARCHAR(500) NULL DEFAULT NULL,
  `pin` VARCHAR(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 30
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `web_shop_db`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop_db`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  `price` DECIMAL(12,2) NOT NULL,
  `is_new` TINYINT NOT NULL,
  `location` VARCHAR(45) NOT NULL,
  `seller_id` INT NOT NULL,
  `deleted` TINYINT NOT NULL,
  `available` TINYINT NOT NULL,
  `contact_phone` VARCHAR(45) NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_user1_idx` (`seller_id` ASC) VISIBLE,
  INDEX `fk_product_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `web_shop_db`.`category` (`id`),
  CONSTRAINT `fk_product_user1`
    FOREIGN KEY (`seller_id`)
    REFERENCES `web_shop_db`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `web_shop_db`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop_db`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `content` VARCHAR(100) NOT NULL,
  `product_id` INT NOT NULL,
  `date_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_comment_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `web_shop_db`.`product` (`id`),
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `web_shop_db`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `web_shop_db`.`image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop_db`.`image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `image_url` VARCHAR(500) NOT NULL,
  `product_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_image_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_image_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `web_shop_db`.`product` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `web_shop_db`.`log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop_db`.`log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date_time` DATETIME NOT NULL,
  `content` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `web_shop_db`.`maintenance_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop_db`.`maintenance_account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `is_admin` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `web_shop_db`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop_db`.`message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `content` VARCHAR(500) NOT NULL,
  `is_read` TINYINT NOT NULL,
  `date_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_message_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_message_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `web_shop_db`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `web_shop_db`.`product_attribute`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop_db`.`product_attribute` (
  `product_id` INT NOT NULL,
  `attribute_id` INT NOT NULL,
  `value` VARCHAR(45) NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  INDEX `fk_proizvod_has_atribut_atribut1_idx` (`attribute_id` ASC) VISIBLE,
  INDEX `fk_proizvod_has_atribut_proizvod1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_proizvod_has_atribut_atribut1`
    FOREIGN KEY (`attribute_id`)
    REFERENCES `web_shop_db`.`attribute` (`id`),
  CONSTRAINT `fk_proizvod_has_atribut_proizvod1`
    FOREIGN KEY (`product_id`)
    REFERENCES `web_shop_db`.`product` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `web_shop_db`.`purchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_shop_db`.`purchase` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `buyer_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `payment_method` ENUM('CASH', 'CREDIT_CARD', 'BANK_TRANSFER') NOT NULL,
  `payment_details` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_purchase_user1_idx` (`buyer_id` ASC) VISIBLE,
  INDEX `fk_purchase_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_purchase_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `web_shop_db`.`product` (`id`),
  CONSTRAINT `fk_purchase_user1`
    FOREIGN KEY (`buyer_id`)
    REFERENCES `web_shop_db`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
