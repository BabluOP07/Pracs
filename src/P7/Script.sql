-- Create database
CREATE DATABASE IF NOT EXISTS Electric_Bill;

USE Electric_Bill;

-- Create Bill table
CREATE TABLE Bill (
    consumer_id INT PRIMARY KEY AUTO_INCREMENT,
    consumer_name VARCHAR(100),
    bill_due_date DATE,
    bill_amount DOUBLE
);

-- Insert sample data
INSERT INTO Bill (consumer_name, bill_due_date, bill_amount) VALUES 
('Farhan Shaikh', '2025-12-15', 1500.00),
('Zeeshan Shaikh', '2025-12-10', 2300.50),
('Akram Khan', '2025-12-20', 1800.75),
('Lewis Hamilton', '2025-12-05', 950.25),
('Max Verstappen', '2025-12-18', 2100.00);
