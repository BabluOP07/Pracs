-- Create database
CREATE DATABASE IF NOT EXISTS Library;

USE Library;

-- Create Book table
CREATE TABLE Book (
    Book_id INT PRIMARY KEY,
    Book_name VARCHAR(100),
    Book_author VARCHAR(100)
);

-- Insert sample data
INSERT INTO Book VALUES (1, 'Java Programming', 'Herbert Schildt');
INSERT INTO Book VALUES (2, 'Database Systems', 'Ramez Elmasri');
INSERT INTO Book VALUES (3, 'Computer Networks', 'Andrew Tanenbaum');
INSERT INTO Book VALUES (4, 'Operating Systems', 'William Stallings');
INSERT INTO Book VALUES (5, 'Data Structures', 'Mark Allen Weiss');
