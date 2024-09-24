create table if not exists book (

    isbn varchar(255) primary key,
    name varchar(255) not null,
    genre varchar(255) not null,
    description varchar(255),
    author varchar(255) not null

)