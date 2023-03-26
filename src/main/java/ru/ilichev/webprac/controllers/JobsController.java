package ru.ilichev.webprac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ilichev.webprac.models.Job;
import ru.ilichev.webprac.service.JobService;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/jobs")
public class JobsController {
    private final JobService jobService;

    @Autowired
    public JobsController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping()
    public String index(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        model.addAttribute("jobs", jobService.findByTitleLike(filter));
        model.addAttribute("filter", filter);
        return "jobs/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Job job = jobService.getById(id);
        if (job == null) {
            throw new ResponseStatusException(NOT_FOUND, "Job with id `" + id + "` not found");
        }
        model.addAttribute("job", job);
        model.addAttribute("employees", jobService.findCurrentEmployeesById(id));
        model.addAttribute("isIncumbent", jobService.isIncumbentById(id));
        return "jobs/show";
    }

    @GetMapping("/new")
    public String newJob(@ModelAttribute("job") Job job, Model model) {
        model.addAttribute("alreadyExists", false);
        return "jobs/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("job") Job job, Model model) {
        if (!jobService.save(job)) {
            model.addAttribute("alreadyExists", true);
            return "jobs/new";
        }
        return "redirect:/jobs";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Job job = jobService.getById(id);
        if (job == null) {
            throw new ResponseStatusException(NOT_FOUND, "Job with id `" + id + "` not found");
        }
        model.addAttribute("job", job);
        return "jobs/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute("job") Job job) {
        jobService.update(job, id);
        return "redirect:/jobs/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        jobService.deleteById(id);
        return "redirect:/jobs";
    }
}