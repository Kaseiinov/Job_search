CREATE TABLE if not exists users
(
    id           INTEGER AUTO_INCREMENT PRIMARY KEY,
    name         TEXT        NOT NULL,
    surname      TEXT        NOT NULL,
    age          INTEGER,
    email        TEXT UNIQUE NOT NULL,
    password     TEXT        NOT NULL,
    phone_number VARCHAR(55),
    avatar       TEXT,
    account_type VARCHAR(50) NOT NULL
);

CREATE TABLE if not exists categories
(
    id        INTEGER AUTO_INCREMENT PRIMARY KEY,
    name      TEXT NOT NULL,
    parent_id INTEGER,
    CONSTRAINT fk_categories_parent FOREIGN KEY (parent_id)
        REFERENCES categories (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE if not exists vacancies
(
    id           INTEGER AUTO_INCREMENT PRIMARY KEY,
    name         TEXT    NOT NULL,
    description  TEXT,
    category_id  INTEGER,
    salary       REAL,
    exp_from     INTEGER,
    exp_to       INTEGER,
    is_active    BOOLEAN   DEFAULT TRUE,
    author_id    INTEGER NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_vacancies_category FOREIGN KEY (category_id)
        REFERENCES categories (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    CONSTRAINT fk_vacancies_author FOREIGN KEY (author_id)
        REFERENCES users (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE if not exists resumes
(
    id           INTEGER AUTO_INCREMENT PRIMARY KEY,
    applicant_id INTEGER NOT NULL,
    name         TEXT    NOT NULL,
    category_id  INTEGER,
    salary       REAL,
    is_active    BOOLEAN   DEFAULT TRUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_resumes_applicant FOREIGN KEY (applicant_id)
        REFERENCES users (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_resumes_category FOREIGN KEY (category_id)
        REFERENCES categories (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE if not exists responded_applicants
(
    id           INTEGER AUTO_INCREMENT PRIMARY KEY,
    resume_id    INTEGER NOT NULL,
    vacancy_id   INTEGER NOT NULL,
    confirmation BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_responded_resume FOREIGN KEY (resume_id)
        REFERENCES resumes (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_responded_vacancy FOREIGN KEY (vacancy_id)
        REFERENCES vacancies (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE if not exists messages
(
    id                     INTEGER AUTO_INCREMENT PRIMARY KEY,
    responded_applicant_id INTEGER NOT NULL,
    content                TEXT    NOT NULL,
    timestamp              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_messages_responded FOREIGN KEY (responded_applicant_id)
        REFERENCES responded_applicants (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE if not exists education_info
(
    id          INTEGER AUTO_INCREMENT PRIMARY KEY,
    resume_id   INTEGER NOT NULL,
    institution TEXT    NOT NULL,
    program     TEXT    NOT NULL,
    start_date  DATE    NOT NULL,
    end_date    DATE,
    degree      TEXT    NOT NULL,
    CONSTRAINT fk_education_resume FOREIGN KEY (resume_id)
        REFERENCES resumes (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE if not exists work_experience_info
(
    id               INTEGER AUTO_INCREMENT PRIMARY KEY,
    resume_id        INTEGER NOT NULL,
    years            INTEGER NOT NULL,
    company_name     TEXT    NOT NULL,
    position         TEXT    NOT NULL,
    responsibilities TEXT    NOT NULL,
    CONSTRAINT fk_work_experience_resume FOREIGN KEY (resume_id)
        REFERENCES resumes (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE if not exists contact_types
(
    id   INTEGER AUTO_INCREMENT PRIMARY KEY,
    type TEXT NOT NULL UNIQUE
);

CREATE TABLE if not exists contacts_info
(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    type_id INTEGER NOT NULL,
    resume_id INTEGER NOT NULL,
    contact_value TEXT NOT NULL,  -- Используем другое имя
    CONSTRAINT fk_contacts_type FOREIGN KEY (type_id)
        REFERENCES contact_types (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_contacts_resume FOREIGN KEY (resume_id)
        REFERENCES resumes (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

