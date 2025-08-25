CREATE DATABASE auth;

USE auth;

CREATE TABLE Role(
                     id          Integer PRIMARY KEY AUTO_INCREMENT,
                     name 	     VARCHAR(100) 	Not Null,
                     description VARCHAR(100) 	Not Null
);

CREATE TABLE User(
                     id         BINARY(16) PRIMARY KEY DEFAULT (UUID()),
                     name 	    VARCHAR(100) 	Not Null,
                     lastname 	VARCHAR(100) 	Not Null,
                     birthdate 	Date 		    Not Null,
                     DNI 	    VARCHAR(50) 	Not Null,
                     address 	VARCHAR(200) 	Not Null,
                     cellphone 	VARCHAR(33) 	Not Null,
                     email 	    VARCHAR(150) 	Not Null,
                     id_rol     Integer         Not Null,
                     BaseSalary BIGINT 		    Not null,
                     INDEX User_role (id_rol),
                     FOREIGN KEY (id_rol) REFERENCES Role(id) ON DELETE CASCADE
);