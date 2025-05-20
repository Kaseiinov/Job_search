insert into responded_applicants(resume_id, vacancy_id, confirmation)
values
    ((select id from resumes where id = 2), (select id from vacancies where id = 2), false),
    ((select id from resumes where id = 4), (select id from vacancies where id = 4), true),
    ((select id from resumes where id = 5), (select id from vacancies where id = 10), true),
    ((select id from resumes where id = 6), (select id from vacancies where id = 6), true),
    ((select id from resumes where id = 8), (select id from vacancies where id = 9), true),
    ((select id from resumes where id = 1), (select id from vacancies where id = 1), false),
    ((select id from resumes where id = 12), (select id from vacancies where id = 11), true),
    ((select id from resumes where id = 10), (select id from vacancies where id = 9), true),
    ((select id from resumes where id = 7), (select id from vacancies where id = 12), true),
    ((select id from resumes where id = 7), (select id from vacancies where id = 7), true),
    ((select id from resumes where id = 14), (select id from vacancies where id = 9), true),
    ((select id from resumes where id = 2), (select id from vacancies where id = 4), true);