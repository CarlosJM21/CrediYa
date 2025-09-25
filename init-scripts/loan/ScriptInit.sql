USE application;

CREATE TABLE IF NOT EXISTS loantype(
                         id          Integer PRIMARY KEY AUTO_INCREMENT,
                         typeName    VARCHAR(50) Not Null,
                         minAmount 	 BIGINT 	 Not Null,
                         maxAmount   BIGINT 	 Not Null,
                         rateType    VARCHAR(5)  Not Null,
                         rate        DOUBLE      Not Null,
                         autoValid   BIT         Not Null
);

CREATE TABLE IF NOT EXISTS status(
                       id          Integer PRIMARY KEY AUTO_INCREMENT,
                       name 	     VARCHAR(100) 	 Not Null,
                       description VARCHAR(250) 	 Not Null
);

CREATE TABLE IF NOT EXISTS applications(
                             id          UUID PRIMARY    KEY DEFAULT (UUID()),
                             email 	     VARCHAR(150) 	 Not Null,
                             amount 	 BIGINT       	 Not Null,
                             term    	 Integer 	     Not Null,
                             id_status   Integer         Not Null,
                             id_loantype Integer         Not Null,
                             INDEX App_status (id_status),
                             FOREIGN KEY (id_status) REFERENCES status(id) ON DELETE CASCADE,
                             INDEX App_loantype (id_loantype),
                             FOREIGN KEY (id_loantype) REFERENCES loantype(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Token(
                      id             Integer PRIMARY KEY AUTO_INCREMENT,
                      email 	        VARCHAR(100) 	Not Null,
                      role           VARCHAR(20)		Not Null,
                      token          TEXT         	Not Null
);

Insert Into status (id,name,description) values
                                             (1,'pending','pending approval'),
                                             (2,'approved','loan approved'),
                                             (3,'rejected','loan rejected'),
                                             (4,'canceled','loan canceled');

Insert IGNORE Into loantype (id,typeName,minAmount,maxAmount,rateType,rate,autoValid) values
                                                                                   (1,'mortgage',100000000,300000000,'EA',9.8,false),
                                                                                   (2,'payroll' ,1000000,10000000,'EA',8.0,false),
                                                                                   (3,'student' ,800000,3000000,'EA',16.5,false);

INSERT IGNORE INTO Token (id, email, role, token) VALUES
                                               (5, 'carlos_1@yopmail.com', '3', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjYXJsb3NfMUB5b3BtYWlsLmNvbSIsInR5cGUiOiJiYXNpYyIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiIzIn1dLCJpYXQiOjE3NTczNjYxOTYsImV4cCI6MTc1NzM2OTc5Nn0.NkehmA2ZTodfOYCIv47MUY3oukaYJpJVaHVG8UevGQ4'),
                                               (13, 'pedro1@yopmail.com', '1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRybzFAeW9wbWFpbC5jb20iLCJ0eXBlIjoiYmFzaWMiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiMSJ9XSwiaWF0IjoxNzU3MzY3MDk0LCJleHAiOjE3NTczNzA2OTR9.l2lHpEPof494XrRrxODkY3hTnBzaKl3mBSMkzf074jg'),
                                               (14, 'pedro1@yopmail.com', '1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRybzFAeW9wbWFpbC5jb20iLCJ0eXBlIjoiYmFzaWMiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiMSJ9XSwiaWF0IjoxNzU3MzY3MDk0LCJleHAiOjE3NTczNzA2OTR9.l2lHpEPof494XrRrxODkY3hTnBzaKl3mBSMkzf074jg');