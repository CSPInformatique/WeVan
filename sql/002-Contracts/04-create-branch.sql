CREATE  TABLE `wevan`.`branch` (
  `id` INT NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) );
  
INSERT INTO `wevan`.`branch` (`id`, `name`) VALUES ('1', 'Agence de Paris');
  
ALTER TABLE `wevan`.`branch` CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT  ;
