DELETE FROM books;

INSERT INTO authors(full_name)
VALUES
    ('Philip K. Dick'),
    ('Arthur Conan Doyle');

INSERT INTO books(title, author_id)
VALUES
    ('Do Androids Dream of Electric Sheep?', 4),
    ('The Hound of the Baskervilles', 5);

INSERT INTO books_genres(book_id, genre_id)
VALUES
    (4, 2), (4, 6),   -- Do Androids Dream of Electric Sheep? (Science Fiction, Cyberpunk)
    (5, 3), (5, 5);   -- The Hound of the Baskervilles (Detective, Mystery)
