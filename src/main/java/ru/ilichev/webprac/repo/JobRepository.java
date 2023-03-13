package ru.ilichev.webprac.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ilichev.webprac.models.Employee;
import ru.ilichev.webprac.models.Job;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findByActiveTrue();

    List<Job> findByActiveTrueAndTitleLikeIgnoreCase(String titlePattern);

    @Query(
        "SELECT e FROM Job j "
            + "JOIN j.history h "
            + "JOIN h.employee e "
        + "WHERE "
            + "h.endDate IS NULL "
            + "AND j.id = :id "
        + "ORDER BY e.fullName ASC"
    )
    List<Employee> findCurrentEmployeesById(@Param("id") Integer id);

    @Query(
        "SELECT e FROM Job j "
            + "JOIN j.history h "
            + "JOIN h.employee e "
        + "WHERE "
            + "h.endDate IS NOT NULL "
            + "AND j.id = :id"
    )
    List<Employee> findExEmployeesById(@Param("id") Integer id);
}
