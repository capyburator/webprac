package ru.ilichev.webprac.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ilichev.webprac.models.Department;
import ru.ilichev.webprac.models.JobHistory;
import ru.ilichev.webprac.repo.DepartmentRepository;
import ru.ilichev.webprac.repo.JobHistoryRepository;
import ru.ilichev.webprac.util.EntityFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentServiceDeleteLogicTest {
    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentDAO;

    @MockBean
    private JobHistoryRepository jobHistoryDAO;

    public void deleteInternalTest(
            List<Department> subsidiaries,
            List<JobHistory> currentJobs,
            List<JobHistory> exJobs,
            boolean checkIfNotActive,
            int saveNumberOfInvocations,
            int deleteNumberOfInvocations
    ) {
        Integer departmentId = 1;
        Department department = EntityFactory.newDepartment(departmentId);

        Mockito.when(departmentDAO.findById(departmentId)).thenReturn(Optional.of(department));
        Mockito.when(departmentDAO.findSubsidiariesById(departmentId)).thenReturn(subsidiaries);
        Mockito.when(jobHistoryDAO.findByDepartmentIdAndEndDateIsNull(departmentId)).thenReturn(currentJobs);
        Mockito.when(jobHistoryDAO.findByDepartmentIdAndEndDateIsNotNull(departmentId)).thenReturn(exJobs);

        departmentService.deleteById(departmentId);

        if (checkIfNotActive) {
            assertNotNull(department);
            assertFalse(department.isActive());
        }

        Mockito.verify(departmentDAO, Mockito.times(saveNumberOfInvocations)).save(department);
        Mockito.verify(departmentDAO, Mockito.times(deleteNumberOfInvocations)).deleteById(departmentId);
    }

    @Test
    public void deleteDisbandedWasJobsTest() {
        deleteInternalTest(
                Collections.emptyList(),
                Collections.emptyList(),
                List.of(EntityFactory.newJobHistory(1)),
                true,
                1,
                0
        );
    }

    @Test
    public void deleteDisbandedWasNotJobsTest() {
        deleteInternalTest(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                false,
                0,
                1
        );
    }

    @Test
    public void deleteHasCurrentJobsTest() {
        deleteInternalTest(
                Collections.emptyList(),
                List.of(EntityFactory.newJobHistory(1)),
                Collections.emptyList(),
                false,
                0,
                0
        );
    }

    @Test
    public void deleteHasSubsidiariesTest() {
        deleteInternalTest(
                List.of(EntityFactory.newDepartment(2)),
                Collections.emptyList(),
                Collections.emptyList(),
                false,
                0,
                0
        );
    }

    @Test
    public void deleteHasCurrentJobsAndSubsidiariesTest() {
        deleteInternalTest(
                List.of(EntityFactory.newDepartment(2)),
                List.of(EntityFactory.newJobHistory(1)),
                Collections.emptyList(),
                false,
                0,
                0
        );
    }
}