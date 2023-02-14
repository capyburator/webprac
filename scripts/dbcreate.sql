DROP SCHEMA IF EXISTS "webprac" CASCADE;
CREATE SCHEMA "webprac";

SET search_path TO "webprac";

CREATE TABLE "job" (
  "job_id" serial,
  "title" text not null,
  "description" text not null,
  "is_manager" boolean not null default false,
  "is_active" boolean not null default true,
  PRIMARY KEY ("job_id"),
  UNIQUE("title")
);

CREATE TABLE "department" (
  "department_id" serial,
  "name" text not null,
  "parent_id" int,
  "is_active" boolean not null default true,
  PRIMARY KEY ("department_id"),
  CONSTRAINT "FK_department.parent_id"
    FOREIGN KEY ("parent_id")
      REFERENCES "department"("department_id")
        ON DELETE RESTRICT,
  UNIQUE("name")
);

CREATE TABLE "employee" (
  "employee_id" serial,
  "full_name" text not null,
  "address" text not null,
  "phone_no" text not null,
  "birth_date" date not null,
  "education_level" text not null,
  "alma_mater" text,
  "email" text not null,
  PRIMARY KEY ("employee_id")
);

CREATE TABLE "job_history" (
  "job_history_id" serial,
  "job_id" int,
  "department_id" int,
  "employee_id" int,
  "start_date" date not null default CURRENT_DATE,
  "end_date" date default null,
  PRIMARY KEY ("job_history_id"),
  CONSTRAINT "FK_job_history.job_id"
    FOREIGN KEY ("job_id")
      REFERENCES "job"("job_id")
        ON DELETE RESTRICT,
  CONSTRAINT "FK_job_history.department_id"
    FOREIGN KEY ("department_id")
      REFERENCES "department"("department_id")
        ON DELETE RESTRICT,
  CONSTRAINT "FK_job_history.employee_id"
    FOREIGN KEY ("employee_id")
      REFERENCES "employee"("employee_id")
        ON DELETE CASCADE
);
