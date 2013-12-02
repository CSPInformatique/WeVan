CREATE  TABLE `wevan`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) );

INSERT INTO `wevan`.`role` (`id`, `name`) VALUES ('1', 'ADMIN');
INSERT INTO `wevan`.`role` (`id`, `name`) VALUES ('2', 'BRANCH_OWNER');
