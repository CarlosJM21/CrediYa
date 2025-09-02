USE auth;

CREATE TABLE Role(
                     id          Integer PRIMARY KEY AUTO_INCREMENT,
                     name 	     VARCHAR(100) 	Not Null,
                     description VARCHAR(100) 	Not Null
);

 ALTER TABLE User ADD CONSTRAINT fk_User_Role FOREIGN KEY (id_rol) REFERENCES Role(id) ON DELETE CASCADE