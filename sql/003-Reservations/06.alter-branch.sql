DELETE FROM `wevan`.`user`;

CREATE  TABLE `wevan`.`branch` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) );

ALTER TABLE `wevan`.`branch` CHANGE COLUMN `name` `name` VARCHAR(45) NULL  , ADD COLUMN `addressNumber` VARCHAR(45) NULL  AFTER `name` , ADD COLUMN `addressStreet` VARCHAR(45) NULL  AFTER `addressNumber` , ADD COLUMN `postalCode` VARCHAR(45) NULL  AFTER `addressStreet` , ADD COLUMN `city` VARCHAR(45) NULL  AFTER `postalCode` , ADD COLUMN `country` VARCHAR(45) NULL  AFTER `city` ;

INSERT INTO `wevan`.`branch` (`name`) VALUES ('Agence de Paris');
INSERT INTO `wevan`.`branch` (`name`) VALUES ('Agence de Rennes');
INSERT INTO `wevan`.`branch` (`name`) VALUES ('Agence de Clermont-Ferrand');
INSERT INTO `wevan`.`branch` (`name`) VALUES ('Agence de Toulouse');

ALTER TABLE `wevan`.`user` ADD COLUMN `branch` INT NOT NULL  AFTER `type` , 
  ADD CONSTRAINT `fk_usr_bra`
  FOREIGN KEY (`branch` )
  REFERENCES `wevan`.`branch` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_usr_bra_idx` (`branch` ASC) ;

INSERT INTO `wevan`.`user` (`username`, `password`, `branch`) VALUES ('paris', 'paris', '1');
INSERT INTO `wevan`.`user` (`username`, `password`, `branch`) VALUES ('rennes', 'rennes', '2');
INSERT INTO `wevan`.`user` (`username`, `password`, `branch`) VALUES ('clermont', 'clermont', '3');
INSERT INTO `wevan`.`user` (`username`, `password`, `branch`) VALUES ('toulouse', 'toulouse', '4');

INSERT INTO `wevan`.`userRole` (`user`, `role`) VALUES ('clermont', '2');
INSERT INTO `wevan`.`userRole` (`user`, `role`) VALUES ('rennes', '2');
INSERT INTO `wevan`.`userRole` (`user`, `role`) VALUES ('toulouse', '2');
INSERT INTO `wevan`.`userRole` (`user`, `role`) VALUES ('paris', '1');

UPDATE `wevan`.`branch` SET `addressNumber`='33', `addressStreet`='Avenue Léon Gambetta', `postalCode`='92120', `city`='Montrouge', `country`='France' WHERE `id`='1';
UPDATE `wevan`.`branch` SET `addressNumber`='1', `addressStreet`=' ', `postalCode`=' ', `city`='Rennes', `country`='France' WHERE `id`='2';
UPDATE `wevan`.`branch` SET `addressNumber`='1', `addressStreet`=' ', `postalCode`=' ', `city`='Clermont-Ferrand', `country`='France' WHERE `id`='3';
UPDATE `wevan`.`branch` SET `addressNumber`='1', `addressStreet`=' ', `postalCode`=' ', `city`='Toulouse', `country`='France' WHERE `id`='4';

ALTER TABLE `wevan`.`branch` ADD COLUMN `phone` VARCHAR(45) NULL  AFTER `country` ;

UPDATE `wevan`.`branch` SET `phone`='+33 (0) 1 47 35 69 96' WHERE `id`='1';
UPDATE `wevan`.`branch` SET `phone`=' ' WHERE `id`='2';
UPDATE `wevan`.`branch` SET `phone`=' ' WHERE `id`='3';
UPDATE `wevan`.`branch` SET `phone`=' ' WHERE `id`='4';

ALTER TABLE `wevan`.`branch` CHANGE COLUMN `name` `name` VARCHAR(45) NOT NULL  , CHANGE COLUMN `addressNumber` `addressNumber` VARCHAR(45) NOT NULL  , CHANGE COLUMN `addressStreet` `addressStreet` VARCHAR(45) NOT NULL  , CHANGE COLUMN `postalCode` `postalCode` VARCHAR(45) NOT NULL  , CHANGE COLUMN `city` `city` VARCHAR(45) NOT NULL  , CHANGE COLUMN `country` `country` VARCHAR(45) NOT NULL  , CHANGE COLUMN `phone` `phone` VARCHAR(45) NOT NULL  ;

UPDATE `wevan`.`branch` SET `addressNumber`='La', `addressStreet`='Roche', `postalCode`='22630', `city`='Evran', `phone`='+33 (0) 2 96 86 53 52' WHERE `id`='2';
UPDATE `wevan`.`branch` SET `addressStreet`='rue Denis Papin', `postalCode`='63540', `city`='Romagnat', `phone`='+33 (0) 9 81 01 99 20' WHERE `id`='3';
UPDATE `wevan`.`branch` SET `addressNumber`='13', `addressStreet`='impasse de la Flambère', `postalCode`='31300', `phone`='+33 (0) 5 67 22 51 10' WHERE `id`='4';
