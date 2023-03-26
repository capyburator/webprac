package ru.ilichev.webprac.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilichev.webprac.models.Employee;
import ru.ilichev.webprac.models.Job;
import ru.ilichev.webprac.repo.JobRepository;

import java.util.List;

@Service
public class JobService {
    private final JobRepository jobDAO;

    @Autowired
    public JobService(JobRepository jobDAO) {
        this.jobDAO = jobDAO;
    }

    @Transactional(readOnly = true)
    public List<Job> findAllActive() {
        return jobDAO.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public List<Employee> findCurrentEmployeesById(Integer id) {
        return jobDAO.findCurrentEmployeesById(id);
    }

    @Transactional(readOnly = true)
    public List<Employee> findExEmployeesById(Integer id) {
        return jobDAO.findExEmployeesById(id);
    }

    @Transactional(readOnly = true)
    public Job getById(Integer id) {
        return jobDAO.findById(id).orElse(null);
    }

    @Transactional
    public boolean save(@NonNull Job job) {
        if (jobDAO.findByTitle(job.getTitle()).isEmpty()) {
            jobDAO.save(job);
            return true;
        }
        return false;
    }

    @Transactional
    public void update(@NonNull Job job, Integer id) {
        Job jobToUpdate = getById(id);
        jobToUpdate.setTitle(job.getTitle());
        jobToUpdate.setDescription(job.getDescription());
        jobToUpdate.setManager(job.isManager());
        jobDAO.save(jobToUpdate);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!isIncumbentById(id)) {
            if (wasIncumbentById(id)) {
                Job job = getById(id);
                job.setActive(false);
                jobDAO.save(job);
            } else {
                jobDAO.deleteById(id);
            }
        }
    }

    @Transactional(readOnly = true)
    public boolean isIncumbentById(Integer id) {
        return !findCurrentEmployeesById(id).isEmpty();
    }

    @Transactional(readOnly = true)
    public boolean wasIncumbentById(Integer id) {
        return !findExEmployeesById(id).isEmpty();
    }

    @Transactional(readOnly = true)
    public List<Job> findByTitleLike(String titlePattern) {
        return jobDAO.findByActiveTrueAndTitleLikeIgnoreCase("%" + titlePattern + "%");
    }
}
