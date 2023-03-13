package ru.ilichev.webprac.util;

import ru.ilichev.webprac.models.Department;
import ru.ilichev.webprac.models.Employee;
import ru.ilichev.webprac.models.Job;
import ru.ilichev.webprac.models.JobHistory;

import java.time.LocalDate;

public class EntityFactory {
    public static Job newJob(Integer id) {
        return Job.builder().id(id).active(true).title("").description("").build();
    }

    public static Employee newEmployee(Integer id) {
        return Employee.builder()
                .id(id)
                .email("test" + id + "@mail.ru")
                .fullName("")
                .address("")
                .phoneNo("")
                .birthDate(LocalDate.now())
                .educationLevel("")
                .almaMater("")
                .build();
    }

    public static Department newDepartment(Integer id) {
        return Department.builder()
                .id(id)
                .active(true)
                .name("" + id)
                .build();
    }

    public static JobHistory newJobHistory(Integer id) {
        return JobHistory.builder()
                .id(id)
                .job(EntityFactory.newJob(42))
                .employee(EntityFactory.newEmployee(42))
                .department(EntityFactory.newDepartment(42))
                .build();
    }
}
