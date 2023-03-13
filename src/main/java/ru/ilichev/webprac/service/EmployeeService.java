package ru.ilichev.webprac.service;

import lombok.NonNull;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilichev.webprac.models.Employee;
import ru.ilichev.webprac.models.Job;
import ru.ilichev.webprac.models.JobHistory;
import ru.ilichev.webprac.repo.EmployeeRepository;
import ru.ilichev.webprac.repo.JobHistoryRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeDAO;

    private final JobHistoryRepository jobHistoryDAO;

    @Autowired
    public EmployeeService(EmployeeRepository employeeDAO, JobHistoryRepository jobHistoryDAO) {
        this.employeeDAO = employeeDAO;
        this.jobHistoryDAO = jobHistoryDAO;
    }

    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Transactional(readOnly = true)
    public Employee getById(Integer id) {
        return employeeDAO.findById(id).orElse(null);
    }

    @Transactional
    public void save(Employee employee) {
        employeeDAO.save(employee);
    }

    @Transactional
    public void update(@NonNull Employee employee, Integer id) {
        employee.setId(id);
        employeeDAO.save(employee);
    }

    @Transactional
    public void deleteById(Integer id) {
        employeeDAO.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<JobHistory> findAllJobHistoryById(Integer id) {
        Employee employee = getById(id);
        List<JobHistory> jobHistories = employee.getHistory();
        jobHistories.forEach(Hibernate::initialize);
        jobHistories.sort((h1, h2) -> {
            int startDateCmp = h2.getStartDate().compareTo(h1.getStartDate());
            if (startDateCmp != 0) {
                return startDateCmp;
            }
            if (h2.getEndDate() == null) {
                return 1;
            }
            if (h1.getEndDate() == null) {
                return -1;
            }
            return h2.getEndDate().compareTo(h1.getEndDate());
        });
        return jobHistories;
    }

    @Transactional(readOnly = true)
    public List<Employee> findByFullNameLike(String fullName) {
        return employeeDAO.findByFullNameLikeIgnoreCase("%" + fullName + "%");
    }

    @Transactional(readOnly = true)
    public List<Employee> findByTotalDaysGraterThan(Integer totalDays) {
        return findAll().stream()
                .filter(e -> getTotalDays(getJobDays(findAllJobHistoryById(e.getId()))) > totalDays)
                .toList();
    }

    @Transactional(readOnly = true)
    public Job getCurrentJobById(Integer id) {
        Optional<JobHistory> jobOpt = jobHistoryDAO.findByEmployeeIdAndEndDateIsNull(id);
        if (jobOpt.isPresent()) {
            Job job = jobOpt.get().getJob();
            Hibernate.initialize(job);
            return job;
        }

        return null;
    }
    @Transactional(readOnly = true)
    public boolean hasCurrentJobById(Integer id) {
        return getCurrentJobById(id) != null;
    }

    public static List<Integer> getJobDays(@NonNull List<JobHistory> jobHistoryList) {
        return jobHistoryList
                .stream()
                .map(h -> Period.between(
                            h.getStartDate(), h.getEndDate() != null ? h.getEndDate() : LocalDate.now()
                        )
                        .getDays()
                )
                .toList();
    }

    public static Integer getTotalDays(@NonNull List<Integer> jobDays) {
        return jobDays.stream().mapToInt(Integer::intValue).sum();
    }
}
