INSERT INTO `wevan`.`branch` (`name`) VALUES ('Agence de Rennes');
INSERT INTO `wevan`.`branch` (`name`) VALUES ('Agence de Clermont-Ferrand');
INSERT INTO `wevan`.`branch` (`name`) VALUES ('Agence de Toulouse');

UPDATE `wevan`.`user` SET `username`='wevan', `password`='wevan' WHERE `username`='system';
INSERT INTO `wevan`.`user` (`username`, `password`, `branch`) VALUES ('rennes', 'rennes', '2');
INSERT INTO `wevan`.`user` (`username`, `password`, `branch`) VALUES ('clermont', 'clermont', '3');
INSERT INTO `wevan`.`user` (`username`, `password`, `branch`) VALUES ('toulouse', 'toulouse', '4');

INSERT INTO `wevan`.`userRole` (`user`, `role`) VALUES ('clermont', '2');
INSERT INTO `wevan`.`userRole` (`user`, `role`) VALUES ('rennes', '2');
INSERT INTO `wevan`.`userRole` (`user`, `role`) VALUES ('toulouse', '2');
INSERT INTO `wevan`.`userRole` (`user`, `role`) VALUES ('wevan', '1');