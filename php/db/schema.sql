create database if not exists camareros;

use camareros;

create table platos (
    nombre varchar(100) primary key,
    precio decimal(5,2)
);

create table mesas (
    id int primary key
);

create table pedidos (
    mesa int,
    plato varchar(100),
    cantidad int,
    primary key (mesa, plato),
    foreign key (mesa) references mesas(id),
    foreign key (plato) references platos(nombre)
);

insert into platos (nombre, precio) values
('Ensalada', 5.50),
('Pizza', 8.00),
('Hamburguesa', 7.00),
('Pasta', 6.50),
('Sopa', 4.00);

insert into mesas (id) values
(1),
(2),
(3),
(4),
(5);