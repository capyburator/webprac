package ru.ilichev.webprac.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ilichev.webprac.models.Department;
import ru.ilichev.webprac.models.Employee;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {
        "/scripts/department-before.sql",
        "/scripts/employee-before.sql",
        "/scripts/job-before.sql",
        "/scripts/jobhistory-before.sql"
    },
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(value = {
        "/scripts/jobhistory-after.sql",
        "/scripts/job-after.sql",
        "/scripts/employee-after.sql",
        "/scripts/department-after.sql"
    },
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
public class DepartmentServiceTest {
    @Autowired
    private DepartmentService departmentService;

    @Test
    public void findAllActiveTest() {
        List<Department> activeDepartments = departmentService.findAllActive();

        assertNotNull(activeDepartments);
        assertEquals(7, activeDepartments.size());
        assertTrue(activeDepartments.stream().allMatch(Department::isActive));

    }

    @Test
    public void saveTest() {
        Department department = Department.builder()
                .active(true)
                .name("Отдел логистики")
                .build();

        boolean saved = departmentService.save(department);
        assertTrue(saved);
        Department fetchedDepartment = departmentService.getById(42);

        assertNotNull(fetchedDepartment);
        assertEquals("Отдел логистики", department.getName());
    }

    @Test
    public void saveAlreadyExistsTest() {
        Department department = Department.builder()
                .active(true)
                .name("Совет директоров")
                .build();

        boolean saved = departmentService.save(department);
        assertFalse(saved);
    }

    @Test
    public void updateTest() {
        Department department = departmentService.getById(1);
        assertNotNull(department);
        assertEquals("Совет директоров", department.getName());

        department.setName("Совет великих директоров");
        departmentService.update(department, department.getId());

        Department fetchedDepartment = departmentService.getById(1);
        assertNotNull(fetchedDepartment);
        assertEquals("Совет великих директоров", fetchedDepartment.getName());
    }

    @Test
    public void filterTest() {
        List<Department> seDepartments = departmentService.findByNameLike("ПО");
        assertNotNull(seDepartments);
        assertEquals(1, seDepartments.size());
        assertTrue(seDepartments.stream().allMatch(d -> d.getName().equals("Отдел разработки ПО")));
    }

    @Test
    public void addSubsidiaryTest() {
        Integer id = 4;
        Integer subsidiaryId = 9;
        Department department = departmentService.getById(id);
        Department subsidiary = departmentService.getById(subsidiaryId);

        assertNull(subsidiary.getParent());

        departmentService.addSubsidiary(department, subsidiary);

        Department updatedSubsidiary = departmentService.getById(subsidiaryId);

        assertNotNull(updatedSubsidiary.getParent());
        assertEquals(updatedSubsidiary.getParent().getId(), id);

        List<Department> subsidiaries = departmentService.getSubsidiariesById(id);
        assertNotNull(subsidiaries);
        assertThat(subsidiaries, hasSize(3));
        assertThat(subsidiaries, hasItem(updatedSubsidiary));
    }

    @Test
    public void removeParentHasParentTest() {
        Integer parentId = 4;
        Integer id = 6;

        Department parent = departmentService.getById(parentId);
        Department department = departmentService.getById(id);

        assertNotNull(department.getParent());
        assertThat(department.getParent(), is(parent));

        departmentService.removeParent(department);

        Department updatedDepartment = departmentService.getById(id);
        assertNull(updatedDepartment.getParent());

        List<Department> subsidiaries = departmentService.getSubsidiariesById(parentId);
        assertNotNull(subsidiaries);
        assertThat(subsidiaries, not(hasItem(updatedDepartment)));
    }

    @Test
    public void removeParentHasNoParent() {
        Integer parentId = 4;
        Integer id = 9;

        Department department = departmentService.getById(id);
        List<Department> subsidiaries = departmentService.getSubsidiariesById(parentId);

        assertNull(department.getParent());

        departmentService.removeParent(department);

        Department updatedDepartment = departmentService.getById(id);

        assertNull(updatedDepartment.getParent());
        List<Department> newSubsidiaries = departmentService.getSubsidiariesById(parentId);
        assertThat(subsidiaries, is(newSubsidiaries));
    }

    @Test
    public void getManagerTest() {
        Employee manager = departmentService.getManagerById(3);

        assertNotNull(manager);
        assertEquals(3, manager.getId());

        Employee anotherManager = departmentService.getManagerById(8);
        assertNull(anotherManager);
    }

    @Test
    public void getEmployeesTest() {
        List<Employee> developers = departmentService.getEmployeesById(6);
        assertNotNull(developers);
        assertEquals(5, developers.size());

        List<Employee> hrs = departmentService.getEmployeesById(9);
        assertNotNull(hrs);
        assertTrue(hrs.isEmpty());
    }

    @Test
    public void getDepartmentsWithoutParentTest() {
        List<Department> departments = departmentService.getDepartmentsWithoutParent();
        assertNotNull(departments);
        assertEquals(2, departments.size());
    }
}