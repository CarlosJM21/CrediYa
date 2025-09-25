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
                     password   TEXT            Not Null,
                     INDEX User_role (id_rol),
                     FOREIGN KEY (id_rol) REFERENCES Role(id) ON DELETE CASCADE
);

CREATE TABLE Token(
                     id             UUID PRIMARY    KEY DEFAULT (UUID()),
                     email 	        VARCHAR(100) 	Not Null,
                     token          TEXT         	Not Null,
                     tokenRefresh   TEXT         	Not Null,
                     isValid        BIT             Not Null,
                     createdAt      Date            Not Null DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO Role (id, name, description)
Values (1,'user', 'user general'),
       (2,'advisor', 'advisor of system'),
       (3,'admin', 'admin of system');

INSERT INTO User (id, name, lastname, birthdate, DNI, address, cellphone, email, id_rol, BaseSalary, password) VALUES
('fc747ae0-89fe-11f0-977b-8e4aa271cc7a', 'carlos', 'prueba', '2010-12-24', '1090200101', 'Cll 107', '3102001002', 'carlos_1@yopmail.com', 3, 12000000, '$2a$10$ix8nW7pZK6ypeZTrQH36muzLufw06VVLQk.xMrsEty8C.rzKBgCY2'),
('63c0943a-8a03-11f0-977b-8e4aa271cc7a', 'pedro', 'prueba', '2001-12-24', '1090500201', 'Cll 77 # 24 - 104', '3105003002', 'pedro1@yopmail.com', 1, 7000000, '$2a$10$4g.07EDRVk9L8GRvzL/ufOA98ePjMkDWEfljVWfpoZJodShPrCoHS');