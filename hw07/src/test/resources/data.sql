DELETE FROM books_genres;
DELETE FROM book_comments;
DELETE FROM books;

ALTER TABLE books ALTER COLUMN id RESTART WITH 1;
ALTER TABLE book_comments ALTER COLUMN id RESTART WITH 1;

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

INSERT INTO book_comments (text, book_id)
VALUES
    ('Terrifying and immersive!', 1),
    ('A true classic of cosmic horror.', 1),

    ('Incredible world-building!', 2),
    ('The best sci-fi I have ever read.', 2),

    ('A masterpiece of mystery.', 3),
    ('I never saw the twist coming!', 3);