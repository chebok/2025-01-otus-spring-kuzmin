INSERT INTO authors(full_name)
VALUES
    ('H.P. Lovecraft'),
    ('Isaac Asimov'),
    ('Agatha Christie');

INSERT INTO genres(name)
VALUES
    ('Horror'),
    ('Science Fiction'),
    ('Detective'),
    ('Fantasy'),
    ('Mystery'),
    ('Cyberpunk');

INSERT INTO books(title, author_id)
VALUES
    ('The Call of Cthulhu', 1),
    ('Foundation', 2),
    ('Murder on the Orient Express', 3);

INSERT INTO books_genres(book_id, genre_id)
VALUES
    (1, 1), (1, 4),   -- The Call of Cthulhu (Horror, Fantasy)
    (2, 2), (2, 6),   -- Foundation (Science Fiction, Cyberpunk)
    (3, 3), (3, 5);   -- Murder on the Orient Express (Detective, Mystery)
