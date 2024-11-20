use class;
-- Create the admin table  
CREATE TABLE admin (  
    user VARCHAR(50) PRIMARY KEY,  
    password VARCHAR(255) NOT NULL  
);  

DROP TABLE IF EXISTS `admin`;

-- Insert admin records  
INSERT INTO admin (user, password) VALUES ('a', 'a123');  
INSERT INTO admin (user, password) VALUES ('b', 'b123');  
INSERT INTO admin (user, password) VALUES ('c', 'c123');  

-- Create the score table  
CREATE TABLE score (  
    id VARCHAR(20) PRIMARY KEY,  
    class VARCHAR(50) NOT NULL,  
    name VARCHAR(50) NOT NULL,  
    score DECIMAL(5, 2) NOT NULL  
);  

DROP TABLE IF EXISTS `score`;

-- Insert score records, ensuring ids are unique and names exist in admin  
INSERT INTO score (id, class, name, score) VALUES ('1', '1', 'e', 1);   
INSERT INTO score (id, class, name, score) VALUES ('2', '2', 'f', 2); 
INSERT INTO score (id, class, name, score) VALUES ('3', '3', 'g', 3);  
INSERT INTO score (id, class, name, score) VALUES ('4', '4', 'h', 4);