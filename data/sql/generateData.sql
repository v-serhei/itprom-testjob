INSERT INTO Department (name, note, parent_department_id)
VALUES ('IT Department', 'Responsible for IT operations', NULL),
       ('Development Department', 'Responsible for software development', NULL),
       ('HR Department', 'Responsible for human resources management', NULL),
       ('Finance Department', 'Responsible for financial tasks', NULL),
       ('Marketing Department', 'Responsible for marketing activities', NULL),
       ('Software Engineering Team', 'Responsible for software development', 2),
       ('Quality Assurance Team', 'Responsible for quality assurance', 2),
       ('Recruitment Team', 'Responsible for recruitment process', 3),
       ('Payroll Team', 'Responsible for payroll management', 4),
       ('Advertising Team', 'Responsible for advertising campaigns', 5);

INSERT INTO Profession (name, note)
VALUES ('Software Engineer', 'Responsible for software development tasks'),
       ('QA Engineer', 'Responsible for quality assurance tasks'),
       ('HR Specialist', 'Responsible for HR tasks'),
       ('Accountant', 'Responsible for financial tasks'),
       ('Marketing Specialist', 'Responsible for marketing activities');


INSERT INTO Employee (first_name, last_name, middle_name, profession_id, department_id, note)
VALUES ('John', 'Doe', 'Smith', 1, 6, 'Senior Software Engineer'),
       ('Jane', 'Smith', 'Johnson', 2, 7, 'QA Engineer'),
       ('Michael', 'Johnson', 'Brown', 3, 8, 'HR Specialist'),
       ('Emily', 'Brown', NULL, 4, 9, 'Accountant'),
       ('David', 'Wilson', NULL, 5, 10, 'Marketing Specialist'),
       ('Jessica', 'Lee', NULL, 1, 6, 'Software Engineer'),
       ('Christopher', 'Taylor', NULL, 2, 7, 'QA Engineer'),
       ('Sarah', 'Martinez', NULL, 3, 8, 'HR Specialist'),
       ('Daniel', 'Anderson', NULL, 4, 9, 'Accountant'),
       ('Michelle', 'Garcia', NULL, 5, 10, 'Marketing Specialist'),
       ('Sophia', 'Brown', NULL, 1, 6, 'Software Engineer'),
       ('Emma', 'Wilson', NULL, 2, 7, 'QA Engineer'),
       ('Olivia', 'Martinez', NULL, 3, 8, 'HR Specialist'),
       ('Ava', 'Anderson', NULL, 4, 9, 'Accountant'),
       ('Isabella', 'Garcia', NULL, 5, 10, 'Marketing Specialist');
