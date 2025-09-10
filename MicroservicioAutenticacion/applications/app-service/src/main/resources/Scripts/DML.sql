USE auth;

INSERT INTO Role (id, name, description)
Values (1,'user', 'user general'),
       (2,'advisor', 'advisor of system')
       (3,'admin', 'admin of system');

 //Auth
 INSERT INTO auth.User (id, name, lastname, birthdate, DNI, address, cellphone, email, id_rol, BaseSalary, password) VALUES ('fc747ae0-89fe-11f0-977b-8e4aa271cc7a', 'carlos', 'prueba', '2010-12-24', '1090200101', 'Cll 107', '3102001002', 'carlos_1@yopmail.com', 3, 12000000, '$2a$10$ix8nW7pZK6ypeZTrQH36muzLufw06VVLQk.xMrsEty8C.rzKBgCY2');
 INSERT INTO auth.User (id, name, lastname, birthdate, DNI, address, cellphone, email, id_rol, BaseSalary, password) VALUES ('63c0943a-8a03-11f0-977b-8e4aa271cc7a', 'pedro', 'prueba', '2001-12-24', '1090500201', 'Cll 77 # 24 - 104', '3105003002', 'pedro1@yopmail.com', 1, 7000000, '$2a$10$4g.07EDRVk9L8GRvzL/ufOA98ePjMkDWEfljVWfpoZJodShPrCoHS');