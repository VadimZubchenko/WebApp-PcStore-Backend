/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  vadimzubchenko
 * Created: 11.9.2023
 */

create table ordersDetails (
    orderDetailID bigint not null auto_increment,
    orderDetailQuantity bigint not null,
    orderDetailPrice double precision not null,
primary key (orderDetailID)
)engine=InnoDB;

create table packageDetails (
    packageDetailID bigint not null auto_increment,
primary key (packageDetailID)
)engine=InnoDB;

create package (
    packageID bigint not null auto_increment,
    packageName varchar(255) not null,
    packagePrice double precision not null,
    packageQuantity bigint not null,
primary key (packageID)
)engine=InnoDB;

create part(
    partID bigint not null auto_increment,
    partName varchar(255) not null,
    partPrice double precision not null,
    stockQuantity bigint not null,
    partType varchar(255) not null,
    shelfNumber varchar(255) not null,
primary key (partID)
)engine=InnoDB;



