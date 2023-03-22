package ru.ilichev.webprac.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ilichev.webprac.models.Department;
import ru.ilichev.webprac.models.Employee;
import ru.ilichev.webprac.models.Job;
import ru.ilichev.webprac.service.DepartmentService;
import ru.ilichev.webprac.service.EmployeeService;
import ru.ilichev.webprac.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/departments")
public class DepartmentsController {
    private final DepartmentService departmentService;

    private final EmployeeService employeeService;

    @Autowired
    public DepartmentsController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping()
    public String index(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        model.addAttribute("departments", departmentService.findByNameLike(filter));
        model.addAttribute("filter", filter);
        return "departments/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Department department = departmentService.getById(id);
        if (department == null) {
            throw new ResponseStatusException(NOT_FOUND, "Department with id `" + id + "` not found");
        }
        List<Employee> employees = departmentService.getEmployeesById(id);
        List<Job> jobs = employees.stream().map(e -> employeeService.getCurrentJobById(e.getId())).toList();
        Map<Job, Long> jobsCounted = jobs.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        model.addAttribute("department", department);
        model.addAttribute("subsidiaries", departmentService.getSubsidiariesById(id));
        model.addAttribute("manager", departmentService.getManagerById(id));
        model.addAttribute("employees", employees);
        model.addAttribute("jobs", jobs);
        model.addAttribute("jobsCounted", jobsCounted);
        model.addAttribute("isDisbanded", departmentService.isDisbandedById(id));

        return "departments/show";
    }

    @GetMapping("/new")
    public String newDepartment(@ModelAttribute("department") Department department) {
        return "departments/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("department") Department department) {
        departmentService.save(department);
        return "redirect:/departments";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Department department = departmentService.getById(id);
        if (department == null) {
            throw new ResponseStatusException(NOT_FOUND, "Department with id `" + id + "` not found");
        }
        model.addAttribute("department", department);
        return "departments/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute("department") Department department) {
        departmentService.update(department, id);
        return "redirect:/departments/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        departmentService.deleteById(id);
        return "redirect:/departments";
    }

    @GetMapping("/{id}/newSubsidiary")
    public String newSubsidiary(@PathVariable("id") Integer id, Model model) {
        Department department = departmentService.getById(id);
        if (department == null) {
            throw new ResponseStatusException(NOT_FOUND, "Department with id `" + id + "` not found");
        }
        Pair departmentAndSubsidiary = new Pair();
        model.addAttribute("department", department);
        model.addAttribute("potentialSubsidiaries", departmentService.getDepartmentsWithoutParent());
        model.addAttribute("departmentAndSubsidiary", departmentAndSubsidiary);
        return "departments/newSubsidiary";
    }

    @PostMapping("/newSubsidiary")
    public String assignNewSubsidiary(
            @ModelAttribute("departmentAndSubsidiary") Pair departmentAndSubsidiary
    ) {
        Integer id = departmentAndSubsidiary.getFirst();
        Integer subsidiaryId = departmentAndSubsidiary.getSecond();
        Department department = departmentService.getById(id);
        if (department == null) {
            throw new ResponseStatusException(NOT_FOUND, "Department with id `" + id + "` not found");
        }
        Department subsidiary = departmentService.getById(subsidiaryId);
        if (subsidiary == null) {
            throw new ResponseStatusException(NOT_FOUND, "Subsidiary with id `" + id + "` not found");
        }
        departmentService.addSubsidiary(department, subsidiary);
        return "redirect:/departments/" + id;
    }

    @PostMapping("/{id}/removeParent")
    public String removeParent(@PathVariable("id") Integer id) {
        Department department = departmentService.getById(id);
        if (department == null) {
            throw new ResponseStatusException(NOT_FOUND, "Department with id `" + id + "` not found");
        }
        departmentService.removeParent(department);
        return "redirect:/departments/" + id;
    }
}