
set foreign_key_checks =0;

drop table if exists book;
drop table if exists book_tags;


create table book(
id int not null auto_increment primary key,
title varchar(45),
description varchar(1024),
-- tags varchar(255),
price float,
image_link varchar(45));

create table book_tags(
book_id int not null ,
tag varchar(45),
foreign key (book_id) references book(id));

set foreign_key_checks =1;
