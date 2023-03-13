package ru.ilichev.webprac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilichev.webprac.models.JobHistory;
import ru.ilichev.webprac.repo.JobHistoryRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class JobHistoryService {
    private final JobHistoryRepository jobHistoryDAO;

    @Autowired
    public JobHistoryService(JobHistoryRepository jobHistoryDAO) {
        this.jobHistoryDAO = jobHistoryDAO;
    }

    @Transactional
    public void removeEmployeeFromCurrentJobById(Integer employeeId) {
        Optional<JobHistory> currentJobOpt = jobHistoryDAO.findByEmployeeIdAndEndDateIsNull(employeeId);
        if (currentJobOpt.isPresent()) {
            JobHistory currentJob = currentJobOpt.get();
            currentJob.setEndDate(LocalDate.now());
            jobHistoryDAO.save(currentJob);
        }
    }

    @Transactional
    public void save(JobHistory jobHistory) {
        jobHistoryDAO.save(jobHistory);
    }
}
