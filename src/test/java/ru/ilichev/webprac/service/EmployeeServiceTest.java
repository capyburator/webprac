package ru.ilichev.webprac.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ilichev.webprac.models.Employee;
import ru.ilichev.webprac.models.Job;
import ru.ilichev.webprac.models.JobHistory;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application.properties")
@Sql(value = {
        "/scripts/department-before.sql",
        "/scripts/employee-before.sql",
        "/scripts/job-before.sql",
        "/scripts/jobhistory-before.sql"
},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(value = {
        "/scripts/jobhistory-after.sql",
        "/scripts/job-after.sql",
        "/scripts/employee-after.sql",
        "/scripts/department-after.sql"
},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
public class EmployeeServiceTest {
    @Autowired
    EmployeeService employeeService;

    @Test
    public void findAllTest() {
        List<Employee> employees = employeeService.findAll();
        assertNotNull(employees);
        assertEquals(15, employees.size());
    }

    @Test
    public void saveTest() {
        Employee employee = Employee.builder()
                .fullName("Капустин Николай Юрьевич")
                .email("kapustin@fa.cs.msu.ru")
                .phoneNo("+7 (495) 939-08-36")
                .educationLevel("Высшее")
                .almaMater("МГУ")
                .birthDate(LocalDate.EPOCH)
                .address("Факультет ВМК МГУ имени М. В. Ломоносова")
                .build();

        employeeService.save(employee);

        Employee kapustin = employeeService.getById(42);
        assertNotNull(kapustin);
        assertEquals("Капустин Николай Юрьевич", kapustin.getFullName());
    }

    @Test
    public void updateTest() {
        Employee ceo = employeeService.getById(1);
        ceo.setFullName("Полосин Алексей Андреевич");
        employeeService.update(ceo, ceo.getId());

        Employee polosin = employeeService.getById(1);
        assertNotNull(polosin);
        assertEquals("Полосин Алексей Андреевич", polosin.getFullName());
    }

    @Test
    public void deleteEmployeeHadNoJobTest() {
        employeeService.deleteById(13);
        Employee employee = employeeService.getById(13);
        assertNull(employee);
    }

    @Test
    public void deleteEmployeeHadJobTest() {
        List<JobHistory> historyList = employeeService.findAllJobHistoryById(12);
        assertNotNull(historyList);
        assertFalse(historyList.isEmpty());

        employeeService.deleteById(12);
        assertNull(employeeService.getById(12));
    }

    @Test
    public void hasCurrentJobTest() {
        assertTrue(employeeService.hasCurrentJobById(1));
        assertFalse(employeeService.hasCurrentJobById(13));
    }

    @Test
    public void filterTest() {
        Assertions.assertThat(employeeService.findByFullNameLike("АлеКсЕЙ")).hasSize(2);
        Assertions.assertThat(employeeService.findByFullNameLike("")).hasSize(15);
        Assertions.assertThat(employeeService.findByFullNameLike("Ксеалей")).hasSize(0);
    }

    @Test
    public void filterDaysTest() {
        Assertions.assertThat(employeeService.findByTotalDaysGraterThan(2)).hasSize(14);
    }

    @Test
    public void findJobHistoryHasHistory() {
        List<JobHistory> smelJobHistory = employeeService.findAllJobHistoryById(14);
        Assertions.assertThat(smelJobHistory)
                .extracting(JobHistory::getJob)
                .extracting(Job::getId)
                .containsExactly(12, 3, 4, 11, 9, 7, 1, 2);
    }

    @Test
    public void findJobHistoryHasNoHistory() {
        List<JobHistory> mashJobHistory = employeeService.findAllJobHistoryById(15);
        assertTrue(mashJobHistory.isEmpty());
    }
}
