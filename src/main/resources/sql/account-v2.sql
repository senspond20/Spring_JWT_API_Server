-- DROP
SET foreign_key_checks = 0;
DROP TABLE  IF EXISTS `account`;
DROP TABLE  IF EXISTS `account_status`;
DROP TABLE  IF EXISTS `account_detail`;
DROP TABLE  IF EXISTS `account_roles`;
DROP TABLE  IF EXISTS `role`;
DROP TABLE  IF EXISTS `posts`;
DROP TABLE  IF EXISTS `posts_reply`;
DROP TABLE  IF EXISTS `category`;
DROP TABLE  IF EXISTS `tags`;
SET foreign_key_checks = 1;

SET @email = 1245;
INSERT INTO `account`(`email`,`password`) values(@email,'422');
INSERT INTO `account`(`email`,`password`) values('sefe','422');
SET @aid = (SELECT account_id FROM `account` WHERE `email` = @email);
INSERT INTO `account_status` (`account_id`) VALUES(@aid);
INSERT INTO `account_detail` (`account_id`) VALUES(@aid);

-- CREATE
-- 회원 가입시 필요한 계정 정보 테이블(회원가입시 email,password 만 요구)
CREATE OR REPLACE TABLE  `account` (
  `account_id` INT(11)       AUTO_INCREMENT  COMMENT '계정 고유키(PK)',
  `email`      VARCHAR(255)  NOT NULL        COMMENT '이메일(UK)',
  `password`   VARCHAR(255)  NOT NULL        COMMENT '비밀번호',
  PRIMARY KEY (`account_id`),
  UNIQUE KEY (`email`)
) COMMENT '사용자 계정(로그인 검증)';

-- 필요에 따라 컬럼 추가
CREATE OR REPLACE TABLE `account_detail`(
  `account_id` INT(11)        NOT NULL COMMENT '계정 고유키(PK/FK)',
  `username`   VARCHAR(80)    DEFAULT SUBSTR(UUID(),20) COMMENT '유저이름',
  `gendar`     CHAR(1)        DEFAULT NULL COMMENT '성별 (M:남성/F:여성)',
  `aboutme`    VARCHAR(550)   DEFAULT NULL COMMENT '자기소개', 
  PRIMARY KEY (`account_id`),
  CONSTRAINT CHECK (`gendar` IN('M','F')),
  CONSTRAINT FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE CASCADE
)COMMENT '사용자 상세 정보(사용자가 수정 가능)';

CREATE OR REPLACE TABLE `account_status` (
  `account_id` INT(11)     NOT NULL COMMENT '계정 고유키(PK/FK)',
  `is_enable`  TINYINT(1)  DEFAULT 1 COMMENT '활성화(1)/비활성화(0)',
  `warn_cnt`   TINYINT(1)  DEFAULT 0 COMMENT '경고 횟수(n회 누적시 빌활성화)',
  `stamp_at`   TIMESTAMP   COMMENT 'time stamp(utc)',
  `create_at`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 계정 활성날짜',
  `disable_at` DATETIME    DEFAULT NULL COMMENT '계정 정지 날짜',
  PRIMARY KEY (`account_id`),
  CONSTRAINT FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE CASCADE,
  CONSTRAINT CHECK (`is_enable` IN (1,0))
) COMMENT '사용자 상태정보(사용자가 수정 불가능)';
