CREATE  TABLE `role` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) );

INSERT INTO `role` (`id`, `name`) VALUES ('1', 'ADMIN');
INSERT INTO `role` (`id`, `name`) VALUES ('2', 'BRANCH_OWNER');
