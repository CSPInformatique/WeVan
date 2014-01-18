ALTER TABLE `user` DROP FOREIGN KEY `fk_usr_bra` ;
ALTER TABLE `user` CHANGE COLUMN `branch` `branch` INT(11) NOT NULL  , 
  ADD CONSTRAINT `fk_usr_bra`
  FOREIGN KEY (`branch` )
  REFERENCES `branch` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `user` DROP COLUMN `type` ;