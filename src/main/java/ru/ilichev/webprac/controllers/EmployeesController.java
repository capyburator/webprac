package ru.ilichev.webprac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ilichev.webprac.models.Employee;
import ru.ilichev.webprac.models.JobHistory;
import ru.ilichev.webprac.service.EmployeeService;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/employees")
public class EmployeesController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public String index(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        model.addAttribute("employees", employeeService.findByFullNameLike(filter));
        model.addAttribute("filter", filter);
        return "employees/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            throw new ResponseStatusException(NOT_FOUND, "Employee with id `" + id + "` not found");
        }
        List<JobHistory> jobHistoryList = employeeService.findAllJobHistoryById(id);
        model.addAttribute("historyList", jobHistoryList);
        model.addAttribute("employee", employee);
        List<Integer> jobDays = EmployeeService.getJobDays(jobHistoryList);
        model.addAttribute("totalDays", EmployeeService.getTotalDays(jobDays));
        model.addAttribute("jobDays", jobDays);
        model.addAttribute("hasCurrentJob", employeeService.hasCurrentJobById(id));

        return "employees/show";
    }

    @GetMapping("/new")
    public String newEmployee(@ModelAttribute("employee") Employee employee) {
        return "employees/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("employee") Employee employee) {
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @PostMapping("/filterTotalDays")
    public String filterTotalDays(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        List<Employee> employees;
        try {
            employees = employeeService.findByTotalDaysGraterThan(Integer.valueOf(filter));
        } catch (Exception e) {
            employees = employeeService.findAll();
        }
        model.addAttribute("employees", employees);
        return "employees/index";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            throw new ResponseStatusException(NOT_FOUND, "Employee with id `" + id + "` not found");
        }
        model.addAttribute("employee", employee);
        return "employees/edit";
    }

    @PatchMapping("/{id}")
    public String update(
            @PathVariable("id") Integer id,
            @ModelAttribute("employee") Employee employee
    ) {
        employeeService.update(employee, id);
        return "redirect:/employees/" + id;
    }


    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Integer id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }
}