
Insert Into Status (id,name,description) values
(1,'pending','pending approval'),
(2,'approved','loan approved'),
(3,'rejected','loan rejected'),
(4,'canceled','loan canceled')

Insert Into loantype (id,minAmount,maxAmount,rateType,rate,autoValid) values
(1,'mortgage',100000000,300000000,'EA',9.8,false),
(2,'payroll' ,1000000,10000000,'EA',8.0,false),
(3,'student' ,800000,3000000,'EA',16.5,false),
