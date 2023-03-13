package ru.ilichev.webprac.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilichev.webprac.models.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByFullNameLikeIgnoreCase(String fullNamePattern);
}
