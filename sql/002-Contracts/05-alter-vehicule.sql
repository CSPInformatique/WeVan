ALTER TABLE `vehicule` 
  ADD CONSTRAINT `fk_veh_bra`
  FOREIGN KEY (`branch` )
  REFERENCES `branch` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_veh_bra_idx` (`branch` ASC);
