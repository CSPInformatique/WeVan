ALTER TABLE `wevan-test`.`Branch` 
ADD COLUMN `active` TINYINT(1) NOT NULL DEFAULT 1 AFTER `name`;

ALTER TABLE `wevan-test`.`Branch` 
CHANGE COLUMN `active` `active` TINYINT(1) NOT NULL ;
