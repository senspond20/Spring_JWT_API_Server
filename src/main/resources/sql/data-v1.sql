/* INSERT INTO TEST (TNAME) VALUES ('하나'),('나영'),('심이'),('오링'); */

INSERT INTO `role` (role_name,description) VALUES 
                            ('ADMIN','관리자 권한입니다'),
                            ('USER','일반 사용자 권한입니다'),
                            ('GUEST','방문자 권한입니다');


INSERT INTO `account` (email,password) VALUES 
                            ('senspond','1234'),
                            ('gegege','1234');

INSERT INTO `account_roles`(account_id, role_id) VALUES(1,1),(1,2),(1,3),(2,2);