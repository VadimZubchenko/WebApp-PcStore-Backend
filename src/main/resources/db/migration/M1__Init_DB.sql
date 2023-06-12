/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  vadimzubchenko
 * Created: 12.6.2023
 */

create table hibernate_sequence (
    next_val bigint
) engine=MyISAM;

insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );


create table customer (
    customerid bigint not null auto_increment, 
    address varchar(255) not null, 
    customer_name varchar(255) not null unique, 
    email varchar(255), 
    primary key (customerid)
)engine=MyISAM;

create table orders (
    orderid bigint not null auto_increment, 
    order_date datetime(6) not null, 
    total_price double precision not null, 
    primary key (orderid)
)engine=MyISAM;

create table staff (
    staffid bigint not null auto_increment, 
    login varchar(255) not null, 
    password varchar(255) not null, 
    role varchar(255) not null unique, 
    staff_name varchar(255) not null unique, 
    primary key (staffid)
)engine=MyISAM;

-- alter table orders 
--     add constraint order_customer_fk 
--     foreign key (customer_id) references customer (customerid);
-- 
-- alter table orders 
--     add constraint order_staff_fk 
--     foreign key (staff_id) references staff (staffid);

