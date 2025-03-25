
INSERT INTO users (name, surname, age, email, password, phone_number, account_type)
VALUES
    ('Ivan', 'Ivanov', 25, 'ivan@applicant.com', 'pass123', '+79991234567', 'applicant'),
    ('Petr', 'Petrov', 40, 'petr@employer.com', 'pass456', '+79997654321', 'employer');


INSERT INTO categories (name)
VALUES
    ('IT'),
    ('Marketing');


INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, author_id)
VALUES
    ('Java Developer', 'Backend development in Java', 1, 150000, 2, 5, 2),
    ('Marketer', 'Promotion of IT products', 2, 90000, 1, 3, 2);


INSERT INTO resumes (applicant_id, name, category_id, salary)
VALUES
    (1, 'Fullstack Developer', 1, 120000),
    (1, 'Marketing Analyst', 2, 80000);


INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
VALUES
    (1, 3, 'Tech Corp', 'Junior Developer', 'Microservices development'),
    (2, 2, 'Digital Agency', 'Marketer', 'Market analysis');

INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree)
VALUES
    (1, 'MSU', 'Applied Informatics', '2015-09-01', '2019-06-30', 'Bachelor'),
    (2, 'HSE', 'Marketing', '2016-09-01', '2020-06-30', 'Bachelor');

INSERT INTO contact_types (type)
VALUES
    ('Email'),
    ('Phone');

INSERT INTO contacts_info (type_id, resume_id, contact_value)
VALUES
    (1, 1, 'ivan@applicant.com'),
    (2, 1, '+79991234567'),
    (1, 2, 'ivan.career@mail.com');

INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation)
VALUES
    (1, 1, TRUE),
    (2, 2, FALSE);

INSERT INTO messages (responded_applicant_id, content)
VALUES
    (1, 'Hello! I am interested in the Java Developer position'),
    (2, 'Test task has been sent');