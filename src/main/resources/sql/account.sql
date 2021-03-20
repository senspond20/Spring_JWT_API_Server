-- DROP
SET foreign_key_checks = 0;
DROP TABLE  IF EXISTS `account_roles`;
DROP TABLE  IF EXISTS `account`;
DROP TABLE  IF EXISTS `account_detail`;
DROP TABLE  IF EXISTS `role`;
SET foreign_key_checks = 1;

-- CREATE

-- 회원가입시 필수적으로 필요한 
CREATE OR REPLACE TABLE `account` (
  `account_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(155)NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY (`email`)
);

-- 계속 추가
CREATE OR REPLACE TABLE `account_detail` (
  `account_id` bigint(20) NOT NULL,
  `user_name`  VARCHAR(80) COMMENT '유저이름',
  `sex`        CHAR(1) COMMENT '성별', 
  `creat_at`   TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '가입시간', 
  `update_at`  TIMESTAMP NULL DEFAULT current_timestamp on update current_timestamp COMMENT '정보수정시간',
  PRIMARY KEY (`account_id`),
  CONSTRAINT FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
);

CREATE OR REPLACE TABLE `role` (
  `role_id` INT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(80) DEFAULT NULL,
  `description` VARCHAR(155) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
);

CREATE TABLE `account_roles` (
  `account_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`account_id`,`role_id`),
  KEY (`role_id`),
  CONSTRAINT FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
);


INSERT INTO `role` (role_name,description) VALUES 
                            ('ADMIN','관리자 권한입니다'),
                            ('USER','일반 사용자 권한입니다'),
                            ('GUEST','방문자 권한입니다');


INSERT INTO `account` (email,password) VALUES 
                            ('senspond','1234'),
                            ('gegege','1234');

                  
INSERT INTO `account_roles`(account_id, role_id) VALUES(1,1),(1,2),(1,3),(2,2);

INSERT INTO `account_detail` (account_id, user_name,sex) VALUES(1,'아저씨','남');
INSERT INTO `account_detail` (account_id, user_name,sex) VALUES(2,'아줌마','여');

SELECT * FROM account_detail;
UPDATE `account_detail` SET user_name ='배짱이' WHERE account_id = 1;


SELECT * FROM account 
JOIN account_detail USING(account_id);


SELECT account_id, email, role_name FROM account_roles ar 
JOIN account a USING(account_id)
JOIN `role` r USING(role_id);

SELECT * FROM account 
JOIN account_detail USING(account_id)
JOIN account_roles USING(account_id)
JOIN `role` r USING(role_id);


SELECT account_id FROM account WHERE email ='senspond';
SELECT role_id FROM `role` WHERE role_name ='ADMIN';