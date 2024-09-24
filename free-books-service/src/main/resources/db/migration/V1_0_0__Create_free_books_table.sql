create table if not exists free_books (

    isbn varchar(255) primary key,
    borrowed_at timestamp,
    return_by timestamp

);