ALTER TABLE `wevan`.`vehicule` 
  ADD CONSTRAINT `fk_veh_bra`
  FOREIGN KEY (`branch` )
  REFERENCES `wevan`.`branch` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_veh_bra_idx` (`branch` ASC);
