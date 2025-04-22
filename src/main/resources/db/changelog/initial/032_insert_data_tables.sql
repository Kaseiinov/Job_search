insert into users(name, email, password, phone_number, account_type, enabled)
values
    ('Islam', 'izlax@gmail.com', '$2a$10$gil65dz5pfK3nFwdNXHAleszEMwZYxkr8S1LmE8etspPOm6Yf/xZO', '700179056', 'APPLICANT', true),
    ('Anvar', 'izlax1@gmail.com', '$2a$10$gil65dz5pfK3nFwdNXHAleszEMwZYxkr8S1LmE8etspPOm6Yf/xZO', '700179056', 'APPLICANT', true),
    ('Sanjar', 'izlax2@gmail.com', '$2a$10$gil65dz5pfK3nFwdNXHAleszEMwZYxkr8S1LmE8etspPOm6Yf/xZO', '700179056', 'APPLICANT', true),
    ('Ivan', 'izlax3@gmail.com', '$2a$10$gil65dz5pfK3nFwdNXHAleszEMwZYxkr8S1LmE8etspPOm6Yf/xZO', '700179056', 'EMPLOYER', true),
    ('Vasa', 'izlax4@gmail.com', '$2a$10$gil65dz5pfK3nFwdNXHAleszEMwZYxkr8S1LmE8etspPOm6Yf/xZO', '700179056', 'EMPLOYER', true);

insert into usr_roles(usr_id, role_id)
values
    (1, 6),
    (2, 6),
    (3, 6),
    (4, 7),
    (5, 7);

insert into vacancies(name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date)
values
    ('name1', 'qweqweqwe', 1, 123, 2, 5, true, 4, '2025-04-22 17:53:32.608124'),
    ('name2', 'qweqweqwe', 2, 123, 2, 5, true, 5, '2025-04-22 17:53:32.608124'),
    ('name3', 'qweqweqwe', 1, 123, 2, 5, true, 4, '2025-04-22 17:53:32.608124'),
    ('name4', 'qweqweqwe', 2, 123, 2, 5, true, 4, '2025-04-22 17:53:32.608124'),
    ('name5', 'qweqweqwe', 1, 123, 2, 5, true, 5, '2025-04-22 17:53:32.608124'),
    ('name6', 'qweqweqwe', 2, 123, 2, 5, true, 5, '2025-04-22 17:53:32.608124'),
    ('name7', 'qweqweqwe', 1, 123, 2, 5, true, 4, '2025-04-22 17:53:32.608124'),
    ('name8', 'qweqweqwe', 1, 123, 2, 5, true, 5, '2025-04-22 17:53:32.608124'),
    ('name9', 'qweqweqwe', 2, 123, 2, 5, true, 5, '2025-04-22 17:53:32.608124'),
    ('name10', 'qweqweqwe', 1, 123, 2, 5, true, 4, '2025-04-22 17:53:32.608124'),
    ('name11', 'qweqweqwe', 2, 123, 2, 5, true, 4, '2025-04-22 17:53:32.608124'),
    ('name12', 'qweqweqwe', 1, 123, 2, 5, true, 5, '2025-04-22 17:53:32.608124'),
    ('name13', 'qweqweqwe', 2, 123, 2, 5, true, 5, '2025-04-22 17:53:32.608124'),
    ('name14', 'qweqweqwe', 2, 123, 2, 5, true, 4, '2025-04-22 17:53:32.608124'),
    ('name15', 'qweqweqwe', 1, 123, 2, 5, true, 4, '2025-04-22 17:53:32.608124');

insert into resumes(applicant_id, name, category_id, salary, is_active, created_date)
values
    (1, 'name1', 1, 123, true, '2025-04-22 17:53:32.608124'),
    (2, 'name2', 2, 123, true, '2025-04-22 17:53:32.608124'),
    (3, 'name3', 1, 123, true, '2025-04-22 17:53:32.608124'),
    (3, 'name4', 2, 123, true, '2025-04-22 17:53:32.608124'),
    (3, 'name5', 1, 123, true, '2025-04-22 17:53:32.608124'),
    (1, 'name6', 2, 123, true, '2025-04-22 17:53:32.608124'),
    (2, 'name7', 1, 123, true, '2025-04-22 17:53:32.608124'),
    (3, 'name8', 2, 123, true, '2025-04-22 17:53:32.608124'),
    (1, 'name9', 1, 123, true, '2025-04-22 17:53:32.608124'),
    (2, 'name10', 2, 123, true, '2025-04-22 17:53:32.608124'),
    (3, 'name11', 1, 123, true, '2025-04-22 17:53:32.608124'),
    (1, 'name12', 2, 123, true, '2025-04-22 17:53:32.608124'),
    (2, 'name13', 1, 123, true, '2025-04-22 17:53:32.608124'),
    (3, 'name14', 2, 123, true, '2025-04-22 17:53:32.608124'),
    (1, 'name15', 1, 123, true, '2025-04-22 17:53:32.608124');

