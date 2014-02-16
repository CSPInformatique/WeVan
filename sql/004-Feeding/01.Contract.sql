ALTER TABLE `wevan-test`.`contract` 
ADD COLUMN `creationDate` DATETIME NULL DEFAULT now() AFTER `branchId`,
ADD COLUMN `editionDate` DATETIME NULL DEFAULT now() AFTER `creationDate`,

ALTER TABLE `wevan-test`.`contract` 
CHANGE COLUMN `creationDate` `creationDate` DATETIME NOT NULL ,
CHANGE COLUMN `editionDate` `editionDate` DATETIME NOT NULL ;

ALTER TABLE `wevan-test`.`option` 
ADD COLUMN `contract` INT NOT NULL AFTER `active`;

ALTER TABLE `wevan-test`.`option` 
ADD INDEX `fk_option_contract_idx` (`contract` ASC);
ALTER TABLE `wevan-test`.`option` 
ADD CONSTRAINT `fk_option_contract`
  FOREIGN KEY (`contract`)
  REFERENCES `wevan-test`.`contract` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `wevan-test`.`option` 
ADD COLUMN `id` INT NOT NULL AUTO_INCREMENT FIRST,
ADD COLUMN `optioncol` VARCHAR(45) NULL AFTER `amount`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);

ALTER TABLE `wevan-test`.`contract` 
DROP COLUMN `options`,
CHANGE COLUMN `kilometers` `kilometersPackage` VARCHAR(45) NOT NULL ;

ALTER TABLE `wevan-test`.`contract` 
DROP FOREIGN KEY `fk_con_veh`;
ALTER TABLE `wevan-test`.`contract` 
CHANGE COLUMN `vehicule` `vehicule` INT(11) NULL ;
ALTER TABLE `wevan-test`.`contract` 
ADD CONSTRAINT `fk_con_veh`
  FOREIGN KEY (`vehicule`)
  REFERENCES `wevan-test`.`vehicule` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `wevan-test`.`contract` 
ADD COLUMN `reservationId` INT NULL AFTER `id`;
