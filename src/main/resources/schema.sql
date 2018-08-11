
set foreign_key_checks =0;

drop table if exists image;
drop table if exists book;

create table image(
id int not null auto_increment primary key,
full_size_path varchar(45),
miniature_size_path varchar(45) )auto_increment=0;

create table book(
id int not null auto_increment primary key,
title varchar(45),
description varchar(1024),
tags varchar(255),
price float,
image_id int,
foreign key (image_id) references image(id) )auto_increment=0;

set foreign_key_checks =1;
