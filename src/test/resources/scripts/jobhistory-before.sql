set search_path to webprac;
delete from job_history cascade;

INSERT INTO "job_history"("job_id", "department_id", "employee_id")
VALUES
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3),
    (4, 4, 4),
    (5, 5, 5),
    (6, 3, 6),
    (7, 6, 7),
    (7, 6, 8),
    (7, 6, 9),
    (9, 6, 10),
    (9, 6, 11),
    (11, 7, 12);

update "job_history" set start_date = date '2023-03-09' where employee_id < 13;

INSERT INTO "job_history"("job_id", "department_id", "employee_id", "start_date", "end_date")
VALUES
    (2, 2, 14, date '2022-12-01', date '2022-12-01'),
    (12, 8, 14, CURRENT_DATE, null),
    (4, 4, 14, CURRENT_DATE, CURRENT_DATE),
    (3, 3, 14, CURRENT_DATE, CURRENT_DATE),
    (9, 6, 14, date '2023-02-01', date '2023-02-07'),
    (1, 1, 14, date '2022-12-31', date '2023-01-07'),
    (11, 7, 14, date '2023-02-10', date '2023-02-14'),
    (7, 6, 14, date '2023-01-08', date '2023-02-01');

INSERT INTO "job_history"("job_id", "department_id", "employee_id", "start_date", "end_date")
VALUES
    (7, 6, 13, date '2023-03-02', date '2023-03-12'),
    (9, 6, 13, date '2023-02-14', date '2023-03-02');