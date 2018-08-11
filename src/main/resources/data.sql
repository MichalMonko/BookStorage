
insert into image (full_size_path,miniature_size_path) values ('/images/test/full/javaBook.png','/images/test/min/javaBook.png');
insert into image (full_size_path,miniature_size_path) values ('/images/test/full/helloWorldBook.png','/images/test/min/helloWorldBook.png');

insert into book (image_id, title, description, tags,price) values (select id from image
where full_size_path='/images/test/full/javaBook.png',
 'Java Book', 'A book for java begginers', 'Java,Coding', 20.00);
insert into book (image_id, title, description, tags,price) values (select id from image
where full_size_path='/images/test/full/helloWorldBook.png'
, 'Hello World Book', 'A book for complete newbies', 'Coding,Tutorial,Newbie', 10.00);