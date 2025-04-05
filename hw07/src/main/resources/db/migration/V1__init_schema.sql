create table authors (
    id bigserial,
    full_name varchar(255) not null,
    primary key (id)
);

create table genres (
    id bigserial,
    name varchar(255) not null,
    primary key (id)
);

create table books (
    id bigserial,
    title varchar(255) not null,
    author_id bigint not null references authors (id) on delete cascade,
    primary key (id)
);

create table book_comments (
   id bigserial,
   text varchar(255) not null ,
   book_id bigint not null references books(id) on delete cascade,
   primary key (id)
);

create table books_genres (
    book_id bigint references books(id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (book_id, genre_id)
);