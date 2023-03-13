package ru.ilichev.webprac.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ilichev.webprac.WebpracApplication;
import ru.ilichev.webprac.models.Employee;
import ru.ilichev.webprac.models.Job;
import ru.ilichev.webprac.repo.JobRepository;
import ru.ilichev.webprac.util.EntityFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebpracApplication.class)
public class JobServiceDeleteLogicTest {
    @Autowired
    private JobService jobService;

    @MockBean
    private JobRepository jobDAO;

    public void deleteInternal(
            List<Employee> currentEmployees,
            List<Employee> exEmployees,
            boolean checkIfNotActive,
            int saveNumberOfInvocations,
            int deleteNumberOfInvocations
    ) {
        Integer jobId = 1;
        Job job = EntityFactory.newJob(jobId);

        Mockito.when(jobDAO.findCurrentEmployeesById(jobId)).thenReturn(currentEmployees);
        Mockito.when(jobDAO.findExEmployeesById(jobId)).thenReturn(exEmployees);
        Mockito.when(jobDAO.findById(jobId)).thenReturn(Optional.of(job));

        jobService.deleteById(jobId);

        if (checkIfNotActive) {
            assertFalse(job.isActive());
        }

        Mockito.verify(jobDAO, Mockito.times(saveNumberOfInvocations)).save(job);
        Mockito.verify(jobDAO, Mockito.times(deleteNumberOfInvocations)).deleteById(jobId);

    }

    @Test
    public void deleteIsNotIncumbentWasIncumbentTest() {
        deleteInternal(
                Collections.emptyList(),
                List.of(EntityFactory.newEmployee(1)),
                true,
                1,
                0
        );
    }

    @Test
    public void deleteIsNotIncumbentWasNotIncumbentTest() {
        deleteInternal(
                Collections.emptyList(),
                Collections.emptyList(),
                false,
                0,
                1
        );
    }

    @Test
    public void deleteIsIncumbentTest() {
        deleteInternal(
                List.of(EntityFactory.newEmployee(1)),
                Collections.emptyList(),
                false,
                0,
                0
        );
    }
}

