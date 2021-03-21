
INSERT INTO `role` (role_id, role_name,description) VALUES 
                            (1, 'MASTER','모든 권한을 다 가진 슈퍼 권한 입니다'),
                            (2, 'ADMIN','관리자 권한 입니다'),
                            (3, 'USER','유저 권한 입니다'),
                            (4, 'V_READ','일반 조회 권한입니다.'),
                            (5, 'V_POST','글 작성 권한입니다'),
                            (6, 'V_FILE','파일 다운로드 권한입니다.');

INSERT INTO `account` (account_id, email,password) VALUES 
                            (1, 'senshig','1234'),
                            (2, 'gegege','1234');

INSERT INTO `account_roles`(account_id, role_id) VALUES(1,1),(1,2),(2,3),(2,2);
