
set foreign_key_checks =0;

drop table if exists book;

create table book(
id int not null auto_increment primary key,
title varchar(45),
description varchar(1024),
tags varchar(255),
price float,
image_link varchar(45));

set foreign_key_checks =1;
