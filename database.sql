CREATE DATABASE produtoDbJdbc;

USE produtoDbJdbc;

CREATE TABLE produto(
                        prod_id int not null auto_increment,
                        prod_nome varchar(30) not null,
                        prod_descricao varchar(50),
                        primary key (prod_id)
);

CREATE TABLE mercado_alvo(
                             merc_id int not null auto_increment,
                             merc_nome varchar(30),
                             prod_id int,
                             primary key (merc_id),
                             foreign key (prod_id) references produto (prod_id)
);

CREATE TABLE tecnologia(
                           tec_id int not null auto_increment,
                           tec_nome varchar(30),
                           prod_id int,
                           primary key (tec_id),
                           foreign key (prod_id) references produto (prod_id)
);