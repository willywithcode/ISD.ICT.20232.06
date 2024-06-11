

-- Drop table if exists CD
DROP TABLE IF EXISTS CD;

-- Table structure for CD
CREATE TABLE CD (id INT NOT NULL, artist VARCHAR(45) NOT NULL, recordLabel VARCHAR(45) NOT NULL, musicType VARCHAR(45) NOT NULL, releasedDate DATE NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
INSERT INTO CD VALUES (3, 'Unknown', 'Unknown', 'trap', '2024-01-02');
INSERT INTO CD VALUES (45, 'Unknown', 'Unknown', 'pop', '2024-01-15');
INSERT INTO CD VALUES (75, 'Singer', 'Music', 'trap', '2023-01-10');

-- Drop table if exists DVD
DROP TABLE IF EXISTS DVD;

-- Table structure for DVD
CREATE TABLE DVD (id INT NOT NULL, discType VARCHAR(45) NOT NULL, director VARCHAR(45) NOT NULL, runtime INT NOT NULL, studio VARCHAR(45) NOT NULL, subtitle VARCHAR(45) NOT NULL, releasedDate DATETIME NOT NULL, filmType VARCHAR(45) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
INSERT INTO DVD VALUES (2, 'horror', 'Mr horror', 5, 'Group 7', 'English', '2024-01-01', 'horror');