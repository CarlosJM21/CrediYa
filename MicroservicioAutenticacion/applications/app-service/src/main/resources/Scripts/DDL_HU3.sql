USE auth;

CREATE TABLE Role(
                     id          Integer PRIMARY KEY AUTO_INCREMENT,
                     name 	     VARCHAR(100) 	Not Null,
                     description VARCHAR(100) 	Not Null
);

 ALTER TABLE User ADD CONSTRAINT fk_User_Role FOREIGN KEY (id_rol) REFERENCES Role(id) ON DELETE CASCADE

 ALTER TABLE User ADD Column password VARCHAR(MAX) Not Null;

CREATE TABLE Token(
                     id             UUID PRIMARY    KEY DEFAULT (UUID()),
                     email 	        VARCHAR(100) 	Not Null,
                     token          TEXT         	Not Null,
                     tokenRefresh   TEXT         	Not Null,
                     isValid        BIT             Not Null
);