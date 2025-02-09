USE diy_projects;

DROP TABLE IF EXISTS project_category;
DROP TABLE IF EXISTS step;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS project;

CREATE TABLE project (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(128) NOT NULL,
    estimated_hours DECIMAL(7,2),
    actual_hours DECIMAL(7,2),
    difficulty INT,
    notes TEXT
);

CREATE TABLE category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(128) NOT NULL
);

CREATE TABLE material (
    material_id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT NOT NULL,
    material_name VARCHAR(128) NOT NULL,
    num_required INT,
    cost DECIMAL(7,2),
    FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE
);

CREATE TABLE step (
    step_id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT NOT NULL,
    step_text TEXT NOT NULL,
    step_order INT NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE
);

CREATE TABLE project_category (
    project_id INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (project_id, category_id),
    FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE
);

SHOW TABLES;

CREATE TABLE material (
    material_id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT NOT NULL,
    material_name VARCHAR(128) NOT NULL,
    num_required INT,
    cost DECIMAL(7,2),
    FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE
);
CREATE TABLE step (
    step_id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT NOT NULL,
    step_text TEXT NOT NULL,
    step_order INT NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE
);
SHOW TABLES;
DESCRIBE project;
DESCRIBE category;
DESCRIBE material;
DESCRIBE step;
DESCRIBE project_category;
SHOW CREATE TABLE project_category;
INSERT INTO project (project_name, estimated_hours, actual_hours, difficulty, notes)
VALUES ('Build a Deck', 30.00, 35.00, 3, 'Large wooden deck project');

INSERT INTO category (category_name)
VALUES ('Construction');

INSERT INTO project_category (project_id, category_id)
VALUES (1, 1);
INSERT INTO project (project_name, estimated_hours, actual_hours, difficulty, notes)
VALUES ('Build a Deck', 30.00, 35.00, 3, 'Large wooden deck project');
INSERT INTO category (category_name)
VALUES ('Construction');
INSERT INTO project_category (project_id, category_id)
VALUES (1, 1);
SELECT * FROM project_category;
DESCRIBE project;
DESCRIBE category;
SHOW TABLES;
INSERT INTO project (project_name, estimated_hours, actual_hours, difficulty, notes)
VALUES ('Build a Deck', 30.00, 35.00, 3, 'Large wooden deck project');
INSERT INTO project_category (project_id, category_id)
VALUES (1, 1);
INSERT IGNORE INTO project_category (project_id, category_id)
VALUES (1, 1);
INSERT INTO project_category (project_id, category_id)
VALUES (1, 1)
ON DUPLICATE KEY UPDATE category_id = 1; -- Or any other update logic
INSERT INTO project_category (project_id, category_id)
VALUES (1, 1)
ON DUPLICATE KEY UPDATE category_id = 1; -- Or any other update logic
SELECT * FROM project_category;
SELECT * FROM project_category;
SELECT * FROM project_category;
INSERT INTO project_category (project_id, category_id)
VALUES (1, 2); -- Example of a new pair
INSERT INTO project_category (project_id, category_id)
VALUES (1, 1)
ON DUPLICATE KEY UPDATE category_id = category_id; -- Keeps the record unchanged
SELECT * FROM project_category;
SELECT * FROM project_category WHERE project_id = 1 AND category_id = 1;
INSERT INTO project_category (project_id, category_id)
VALUES (1, 1)
ON DUPLICATE KEY UPDATE category_id = 1;
SELECT * FROM project;
SELECT * FROM category;
SELECT * FROM material;
SELECT * FROM step;
SELECT * FROM project_category;
INSERT INTO material (project_id, material_name, num_required, cost)
VALUES (1, 'Wood Planks', 10, 100.00);
SELECT p.project_name, c.category_name, m.material_name, m.num_required
FROM project p
JOIN project_category pc ON p.project_id = pc.project_id
JOIN category c ON pc.category_id = c.category_id
JOIN material m ON p.project_id = m.project_id;

