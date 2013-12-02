CREATE  TABLE `wevan`.`ContractAdditionalDriver` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `contract` INT NOT NULL ,
  `driver` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_conAddDri_con_idx` (`contract` ASC) ,
  INDEX `fk_conAddDri_dri_idx` (`driver` ASC) ,
  CONSTRAINT `fk_conAddDri_con`
    FOREIGN KEY (`contract` )
    REFERENCES `wevan`.`contract` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_conAddDri_dri`
    FOREIGN KEY (`driver` )
    REFERENCES `wevan`.`driver` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
 );