CREATE  TABLE `branch` (
  `id` INT NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) );
  
INSERT INTO `branch` (`id`, `name`) VALUES ('1', 'Agence de Paris');
  
ALTER TABLE `branch` CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT  ;
