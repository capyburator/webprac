package ru.ilichev.webprac.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilichev.webprac.models.JobHistory;

import java.util.List;
import java.util.Optional;

public interface JobHistoryRepository extends JpaRepository<JobHistory, Integer> {
    Optional<JobHistory> findByEmployeeIdAndEndDateIsNull(Integer employeeId);

    List<JobHistory> findByDepartmentIdAndEndDateIsNull(Integer departmentId);

    List<JobHistory> findByDepartmentIdAndEndDateIsNotNull(Integer departmentId);
}
