CREATE TABLE `wevan-test`.`reservationNotification` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `reservationId` INT NOT NULL,
  `timestamp` DATETIME NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));