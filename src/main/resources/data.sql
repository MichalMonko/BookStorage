
insert into book (title, description,price)
values ('Java Book', 'A book for java begginers',  20.00);
insert into book_tags(book_id, tag)
values(1,'java');
insert into book_tags(book_id, tag)
values(1,'coding');

insert into book (title, description,price,image_link)
values ('Hello World Book', 'A book for complete newbies',  10.00,'localhost/api/image/image.jpg');
insert into book_tags(book_id, tag)
values(2,'coding');
insert into book_tags(book_id, tag)
values(2,'tutorial');
insert into book_tags(book_id, tag)
values(2,'newbie');
