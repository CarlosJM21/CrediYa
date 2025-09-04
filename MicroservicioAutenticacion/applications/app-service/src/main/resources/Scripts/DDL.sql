CREATE DATABASE auth;

USE auth;

CREATE TABLE Role(
                     id          Integer PRIMARY KEY AUTO_INCREMENT,
                     name 	     VARCHAR(100) 	Not Null,
                     description VARCHAR(100) 	Not Null
);

CREATE TABLE User(
                     id         UUID PRIMARY    KEY DEFAULT (UUID()),
                     name 	    VARCHAR(100) 	Not Null,
                     lastname 	VARCHAR(100) 	Not Null,
                     birthdate 	Date 		    Not Null,
                     DNI 	    VARCHAR(50) 	Not Null,
                     address 	VARCHAR(200) 	Not Null,
                     cellphone 	VARCHAR(33) 	Not Null,
                     email 	    VARCHAR(150) 	Not Null,
                     id_rol     Integer         Not Null,
                     BaseSalary BIGINT 		    Not null,
                     password   VARCHAR(MAX)    Not Null,
                     INDEX User_role (id_rol),
                     FOREIGN KEY (id_rol) REFERENCES Role(id) ON DELETE CASCADE
);

CREATE TABLE Token(
                     id             UUID PRIMARY    KEY DEFAULT (UUID()),
                     email 	        VARCHAR(100) 	Not Null,
                     token          VARCHAR(MAX) 	Not Null,
                     tokenRefresh   VARCHAR(MAX) 	Not Null,
                     isValid        BIT             Not Null,
                     createdAt      Date            Not Null DEFAULT CURRENT_TIMESTAMP
);