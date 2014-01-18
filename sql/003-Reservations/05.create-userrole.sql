CREATE  TABLE `userrole` (
  `user` VARCHAR(45) NOT NULL ,
  `role` INT NOT NULL ,
  PRIMARY KEY (`user`, `role`) ,
  INDEX `fk_usrrol_user_idx` (`user` ASC) ,
  INDEX `fk_usrrol_rol_idx` (`role` ASC) ,
  CONSTRAINT `fk_usrrol_rol`
    FOREIGN KEY (`role` )
    REFERENCES `role` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);