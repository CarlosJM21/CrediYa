CREATE DATABASE application;

USE application;

CREATE TABLE loantype(
                     id          Integer PRIMARY KEY AUTO_INCREMENT,
                     typeName    VARCHAR(50) Not Null
                     minAmount 	 BIGINT 	 Not Null,
                     maxAmount   BIGINT 	 Not Null,
                     rateType    VARCHAR(5)  Not Null,
                     rate        DOUBLE      Not Null,
                     autoValid   BIT         Not Null
);

CREATE TABLE status(
                     id          Integer PRIMARY KEY AUTO_INCREMENT,
                     name 	     VARCHAR(100) 	 Not Null,
                     description VARCHAR(250) 	 Not Null
);

CREATE TABLE applications(
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