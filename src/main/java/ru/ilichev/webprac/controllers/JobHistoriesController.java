package ru.ilichev.webprac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ilichev.webprac.models.Employee;
import ru.ilichev.webprac.models.JobHistory;
import ru.ilichev.webprac.service.DepartmentService;
import ru.ilichev.webprac.service.EmployeeService;
import ru.ilichev.webprac.service.JobHistoryService;
import ru.ilichev.webprac.service.JobService;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/jobHistories")
public class JobHistoriesController {
    private final JobHistoryService jobHistoryService;

    private final DepartmentService departmentService;

    private final JobService jobService;

    private final EmployeeService employeeService;

    @Autowired
    public JobHistoriesController(
            JobHistoryService jobHistoryService,
            DepartmentService departmentService,
            JobService jobService,
            EmployeeService employeeService
    ) {
        this.jobHistoryService = jobHistoryService;
        this.departmentService = departmentService;
        this.jobService = jobService;
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public String removeEmployeeFromCurrentJob(@PathVariable("id") Integer employeeId) {
        jobHistoryService.removeEmployeeFromCurrentJobById(employeeId);
        return "redirect:/employees/{id}";
    }

    @GetMapping("/new/{id}")
    public String newJobHistory(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            throw new ResponseStatusException(NOT_FOUND, "Employee with id `" + id + "` not found");
        }
        model.addAttribute("departments", departmentService.findAllActive());
        model.addAttribute("jobs", jobService.findAllActive());
        model.addAttribute("employee", employee);
        model.addAttribute("jobHistory", new JobHistory());
        return "jobHistories/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("jobHistory") JobHistory jobHistory) {
        jobHistoryService.save(jobHistory);
        return "redirect:/employees/" + jobHistory.getEmployee().getId();
    }
}
