SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `account_roles`;
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `role`;
SET foreign_key_checks = 1;


CREATE OR REPLACE TABLE `account` (
  `account_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(155)NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY (`email`)
);


CREATE OR REPLACE TABLE `role` (
  `role_id` INT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(80) DEFAULT NULL,
  `description` VARCHAR(155) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
);

CREATE OR REPLACE TABLE `account_roles` (
  `account_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`account_id`,`role_id`),
  KEY (`role_id`),
  CONSTRAINT FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
);


/* CREATE OR REPLACE TABLE IF NOT EXISTS test (
	TID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	TNAME VARCHAR(55) NOT NULL
); */
