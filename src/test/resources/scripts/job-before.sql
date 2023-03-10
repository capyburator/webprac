set search_path to webprac;

delete from job;

INSERT INTO "job"("job_id", "title", "description", "is_manager", "is_active")
VALUES
    (1, 'Генеральный директор', 'Руководит всей деятельностью организации', true, true),
    (2, 'Начальник отдела кадров', 'Принимает участие в разработке кадровой политики и кадровой стратегии предприятия.', true, true),
    (3, 'Главный бухгалтер', 'Обеспечивает составление отчета об исполнении бюджетов денежных средств и смет расходов, подготовку необходимой бухгалтерской и статистической отчетности, представление их в установленном порядке в соответствующие органы.', true, true),
    (4, 'Начальник IT отдела', 'Обеспечение стабильной и бесперебойной работы ИТ-инфраструктуры, ИТ-сервисов и телекоммуникаций, информационных систем', true, true),
    (5, 'Начальник отдела продаж', 'Управляет продажами на нескольких уровнях: общается с клиентами, решает конфликтные ситуации, организовывает работу команды, анализирует отчетность и продумывает стратегию роста.', true, true),
    (6, 'Бухгалтер', 'Ведет бухгалтерский и налоговый учет', false, true),
    (7, 'Java разработчик', 'Java; Maven; Spring (Core, MVC, Boot, Security, REST API); JDBC/Hibernate, JPA; PostgreSQL, MySQL;', false, true),
    (8, 'Angular Front-End разработчик', 'Angular, TypeScript, JavaScript ES6+, SCSS/CSS, HTML', false, false),
    (9, 'Python разработчик', 'Python; Django, Django REST; PostgreSQL, MySQL; JavaScript, CSS, HTML, BootStrap;', false, true),
    (10, 'Менеджер проектов', 'Общается с заказчиком, собирает требования к проекту, согласовывает бюджет и управляет им, ставит задачи исполнителям, следит за временем и ресурсами, извиняется перед заказчиком', false, false),
    (11, 'Тестировщик ПО', 'Контролирует качество разрабатываемых продуктов, разработывает автоматизированные тесты и прогоняет их, документирует найденные дефекты', false, false),
    (12, 'Специалист по ИБ', 'Предотвращение несанкционированного доступа, использования, раскрытия, искажения, изменения, исследования, записи или уничтожения информации', false, false);

alter sequence job_job_id_seq restart with 42;
