create table OPERATION (
    ID smallint not null primary key,
    CODE varchar(8) not null
);

insert into OPERATION(ID, CODE) values (1, 'TOPUP'), (2, 'EXCHANGE');