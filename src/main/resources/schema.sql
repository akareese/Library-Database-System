CREATE DATABASE IF NOT EXISTS library_management;
USE library_management;

CREATE TABLE IF NOT EXISTS books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    isbn VARCHAR(50) UNIQUE,
    total_copies INT NOT NULL,
    available_copies INT NOT NULL
);

CREATE TABLE IF NOT EXISTS members (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS loans (
    loan_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    issue_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    CONSTRAINT fk_loans_book
        FOREIGN KEY (book_id) REFERENCES books(book_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_loans_member
        FOREIGN KEY (member_id) REFERENCES members(member_id)
        ON DELETE CASCADE
);