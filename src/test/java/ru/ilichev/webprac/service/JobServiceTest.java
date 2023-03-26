package ru.ilichev.webprac.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ilichev.webprac.WebpracApplication;
import ru.ilichev.webprac.models.Job;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebpracApplication.class)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/scripts/job-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/scripts/job-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class JobServiceTest {
    @Autowired
    private JobService jobService;

    @Test
    public void findAllActiveTest() {
        List<Job> activeJobs = jobService.findAllActive();

        assertNotNull(activeJobs);
        assertEquals(8, activeJobs.size());
        assertTrue(activeJobs.stream().allMatch(Job::isActive));
    }

    @Test
    public void saveTest() {
        Job job = Job.builder()
                .active(true)
                .manager(true)
                .title("Начальник отдела разработки ПО")
                .description("Руководит отделом разработки ПО")
                .build();

        jobService.save(job);
        Job fetchedJob = jobService.getById(42);

        assertNotNull(fetchedJob);
        assertEquals("Начальник отдела разработки ПО", fetchedJob.getTitle());
    }

    @Test
    public void updateTest() {
        Job job = jobService.getById(1);
        assertNotNull(job);
        assertEquals("Генеральный директор", job.getTitle());

        job.setTitle("CEO");
        jobService.update(job, job.getId());

        Job fetchedJob = jobService.getById(1);
        assertNotNull(fetchedJob);
        assertEquals("CEO", fetchedJob.getTitle());
    }

    @Test
    public void filterTest() {
        List<Job> developers = jobService.findByTitleLike("разРАБ");
        assertNotNull(developers);
        assertEquals(2, developers.size());
    }
}