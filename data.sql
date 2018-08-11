use bookstorage;
insert into image (full_size_path,minature_size_path) value ('/images/test/full/javaBook.png','/images/test/min/javaBook.png');
insert into image (full_size_path,minature_size_path) value ('/images/test/full/helloWorldBook.png','/images/test/min/helloWorldBook.png');

insert into book (image_id, title, description, tags,price) value (0, 'Java Book', 'A book for java begginers', 'Java,Coding', 20.00);
insert into book (image_id, title, description, tags,price) value (1, 'Hello World Book', 'A book for complete newbies', 'Coding,Tutorial,Newbie', 10.00);