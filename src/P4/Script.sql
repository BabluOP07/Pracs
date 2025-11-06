
CREATE DATABASE IF NOT EXISTS productdb;

USE productdb;
CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL
);

INSERT INTO products (product_name, price) VALUES
('Laptop', 50000),
('Mouse', 500),
('Keyboard', 1500),
('Monitor', 15000),
('Headphones', 2500);

