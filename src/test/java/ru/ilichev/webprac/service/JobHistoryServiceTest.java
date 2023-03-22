package ru.ilichev.webprac.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

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
public class JobHistoryServiceTest {
    @Autowired
    JobHistoryService jobHistoryService;

    @Autowired
    EmployeeService employeeService;

    @Test
    public void removeFromCurrentJobHasCurrentJob() {
        assertTrue(employeeService.hasCurrentJobById(1));
        jobHistoryService.removeEmployeeFromCurrentJobById(1);
        assertFalse(employeeService.hasCurrentJobById(1));
    }

    @Test
    public void removeFromCurrentJobHasNotCurrentJob() {
        assertFalse(employeeService.hasCurrentJobById(13));
        jobHistoryService.removeEmployeeFromCurrentJobById(13);
        assertFalse(employeeService.hasCurrentJobById(13));
    }
}