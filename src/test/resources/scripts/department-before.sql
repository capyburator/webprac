set search_path to webprac;

delete from department;

insert into department(department_id, "name", parent_id, is_active)
values
    (1, 'Совет директоров', NULL, true),
    (2, 'Отдел кадров', 1, true),
    (3, 'Отдел бухгалтерии', 1, false),
    (4, 'Отдел IT', 1, true),
    (5, 'Отдел продаж', 1, true),
    (6, 'Отдел разработки ПО', 4, true),
    (7, 'Отдел тестирования ПО', 4, false),
    (8, 'Отдел информационной безопасности', 4, true),
    (9, 'Отдел HR', null, true);

alter sequence department_department_id_seq restart with 42;
