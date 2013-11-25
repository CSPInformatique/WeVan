ALTER TABLE `wevan`.`user` ADD COLUMN `branch` INT NOT NULL DEFAULT 1  AFTER `type` , 
  ADD CONSTRAINT `fk_usr_bra`
  FOREIGN KEY (`branch` )
  REFERENCES `wevan`.`branch` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_usr_bra_idx` (`branch` ASC) ;

ALTER TABLE `wevan`.`user` DROP FOREIGN KEY `fk_usr_bra` ;
ALTER TABLE `wevan`.`user` CHANGE COLUMN `branch` `branch` INT(11) NOT NULL  , 
  ADD CONSTRAINT `fk_usr_bra`
  FOREIGN KEY (`branch` )
  REFERENCES `wevan`.`branch` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;