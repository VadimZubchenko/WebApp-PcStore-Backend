/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  vadimzubchenko
 * Created: 11.6.2023
 */

create table hibernate_sequence (
    next_val bigint
) engine=MyISAM;

insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );

CREATE TABLE customer(
    customerid BIGINT NOT NULL AUTO_INCREMENT,
    address VARCHAR(64) NOT NULL,
    customer_name VARCHAR(64) NOT NULL UNIQUE,
    email VARCHAR(64) NOT NULL,
PRIMARY KEY(customerid)
) engine=MyISAM;

CREATE TABLE staff(
    staffid BIGINT NOT NULL AUTO_INCREMENT,
    login VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL,
    position VARCHAR(64) NOT NULL,
    staff_name VARCHAR(64) NOT NULL UNIQUE,
PRIMARY KEY(staffid)
) engine=MyISAM;

CREATE TABLE orders(
    orderid BIGINT NOT NULL AUTO_INCREMENT,
    order_date DATETIME(6) NOT NULL,
    total_price DOUBLE NOT NULL,
PRIMARY KEY(orderid)
) engine=MyISAM;


