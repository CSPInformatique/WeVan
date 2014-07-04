DELETE FROM `contract`;

ALTER TABLE `contract` ADD COLUMN `driver` INT NOT NULL AFTER `branchId` , ADD COLUMN `startDate` DATETIME NOT NULL AFTER `driver` , ADD COLUMN `endDate` DATETIME NOT NULL AFTER `startDate` , ADD COLUMN `kilometers` INT NOT NULL AFTER `endDate` , ADD COLUMN `vehicule` INT NOT NULL AFTER `kilometers` , ADD COLUMN `totalAmount` DOUBLE NOT NULL AFTER `vehicule` , ADD COLUMN `deductible` DOUBLE NOT NULL AFTER `totalAmount` , ADD COLUMN `deposit` DOUBLE NOT NULL AFTER `deductible` , 
 ADD CONSTRAINT `fk_con_dri`
 FOREIGN KEY (`driver` )
 REFERENCES `driver` (`id` )
 ON DELETE NO ACTION
 ON UPDATE NO ACTION, 
 ADD CONSTRAINT `fk_con_veh`
 FOREIGN KEY (`vehicule` )
 REFERENCES `vehicule` (`id` )
 ON DELETE NO ACTION
 ON UPDATE NO ACTION
, ADD INDEX `fk_con_dri_idx` (`driver` ASC) 
, ADD INDEX `fk_con_veh_idx` (`vehicule` ASC);