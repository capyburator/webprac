package ru.ilichev.webprac.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilichev.webprac.models.Department;
import ru.ilichev.webprac.models.Employee;
import ru.ilichev.webprac.repo.DepartmentRepository;
import ru.ilichev.webprac.repo.JobHistoryRepository;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentDAO;

    private final JobHistoryRepository jobHistoryDAO;

    @Autowired
    public DepartmentService(DepartmentRepository departmentDAO, JobHistoryRepository jobHistoryDAO) {
        this.departmentDAO = departmentDAO;
        this.jobHistoryDAO = jobHistoryDAO;
    }

    @Transactional(readOnly = true)
    public List<Department> findAllActive() {
        return departmentDAO.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public Department getById(Integer id) {
        return departmentDAO.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Department> getSubsidiariesById(Integer id) {
        return departmentDAO.findSubsidiariesById(id);
    }

    @Transactional(readOnly = true)
    public Employee getManagerById(Integer id) {
        return departmentDAO.findManagerById(id);
    }

    @Transactional(readOnly = true)
    public List<Employee> getEmployeesById(Integer id) {
        return departmentDAO.findEmployeesById(id);
    }

    @Transactional
    public boolean save(Department department) {
        if (departmentDAO.findByName(department.getName()).isEmpty()) {
            departmentDAO.save(department);
            return true;
        }
        return false;
    }

    @Transactional
    public void update(@NonNull Department department, Integer id) {
        Department departmentToUpdate = getById(id);
        departmentToUpdate.setName(department.getName());
        departmentDAO.save(departmentToUpdate);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (isDisbandedById(id)) {
            if (wasEmployeesById(id)) {
                Department department = getById(id);
                department.setActive(false);
                departmentDAO.save(department);
            } else {
                departmentDAO.deleteById(id);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Department> findByNameLike(String namePattern) {
        return departmentDAO.findByActiveTrueAndNameLikeIgnoreCase("%" + namePattern + "%");
    }

    @Transactional(readOnly = true)
    public boolean isDisbandedById(Integer id) {
        return getSubsidiariesById(id).isEmpty()
                && jobHistoryDAO.findByDepartmentIdAndEndDateIsNull(id).isEmpty();
    }

    @Transactional(readOnly = true)
    public boolean wasEmployeesById(Integer id) {
        return !jobHistoryDAO.findByDepartmentIdAndEndDateIsNotNull(id).isEmpty();
    }

    @Transactional
    public void addSubsidiary(@NonNull Department department, @NonNull Department subsidiary) {
        subsidiary.setParent(department);
        departmentDAO.save(subsidiary);
    }

    @Transactional(readOnly = true)
    public List<Department> getDepartmentsWithoutParent() {
        return departmentDAO.findByParentIsNull();
    }

    @Transactional
    public void removeParent(@NonNull Department department) {
        department.setParent(null);
        departmentDAO.save(department);
    }
}
