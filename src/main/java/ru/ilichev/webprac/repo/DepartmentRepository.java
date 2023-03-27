package ru.ilichev.webprac.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ilichev.webprac.models.Department;
import ru.ilichev.webprac.models.Employee;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @Query(
        "SELECT DISTINCT s FROM Department d JOIN d.subsidiaries s WHERE d.id = :id AND s.active"
    )
    List<Department> findSubsidiariesById(@Param("id") Integer id);

    Optional<Department> findByName(String name);

    List<Department> findByActiveTrue();

    List<Department> findByActiveTrueAndNameLikeIgnoreCase(String namePattern);

    List<Department> findByParentIsNull();

    @Query(
        "SELECT e FROM Department d "
            + "JOIN d.history h "
            + "JOIN h.job j "
            + "JOIN h.employee e "
        + "WHERE "
            + "h.endDate IS NULL "
            + "AND j.manager "
            + "AND d.id = :id"
    )
    Employee findManagerById(@Param("id") Integer id);

    @Query(
        "SELECT e FROM Department d "
            + "JOIN d.history h "
            + "JOIN h.employee e "
        + "WHERE "
            + "h.endDate IS NULL "
            + "AND d.id = :id "
        + "ORDER BY e.fullName ASC"
    )
    List<Employee> findEmployeesById(@Param("id") Integer id);
}
