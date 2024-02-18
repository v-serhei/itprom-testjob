CREATE TABLE IF NOT EXISTS Profession
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    note VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS Department
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                 VARCHAR(255) NOT NULL UNIQUE,
    note                 VARCHAR(255),
    parent_department_id BIGINT,
    FOREIGN KEY (parent_department_id) REFERENCES Department (id)
);


CREATE TABLE IF NOT EXISTS Employee
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255) NOT NULL,
    middle_name   VARCHAR(255),
    profession_id BIGINT,
    department_id BIGINT,
    note          VARCHAR(255),
    FOREIGN KEY (profession_id) REFERENCES Profession (id),
    FOREIGN KEY (department_id) REFERENCES Department (id)
);
