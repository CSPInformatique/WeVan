CREATE  TABLE `wevan`.`userRole` (
  `user` VARCHAR(45) NOT NULL ,
  `role` INT NOT NULL ,
  PRIMARY KEY (`user`, `role`) ,
  INDEX `fk_usrrol_user_idx` (`user` ASC) ,
  INDEX `fk_usrrol_rol_idx` (`role` ASC) ,
  CONSTRAINT `fk_usrrol_usr`
    FOREIGN KEY (`user` )
    REFERENCES `wevan`.`user` (`username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usrrol_rol`
    FOREIGN KEY (`role` )
    REFERENCES `wevan`.`role` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
