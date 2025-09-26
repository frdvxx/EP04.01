package com.mpt.journal.controller;

import com.mpt.journal.model.CourseModel;
import com.mpt.journal.service.InMemoryCourseServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final InMemoryCourseServiceImpl service;

    public CourseController(InMemoryCourseServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public String getAllCourses(Model model,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int pageSize) {
        List<CourseModel> courses = service.getCoursesByPage(page, pageSize);
        int total = service.findAllCourses().size();
        int totalPages = (int)Math.ceil((double)total / pageSize);
        model.addAttribute("courses", courses);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "courseList";
    }

    @GetMapping("/search")
    public String searchCourses(Model model,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String description) {
        List<CourseModel> courses = service.filterCourses(name, description);
        model.addAttribute("courses", courses);
        return "courseList";
    }

    @PostMapping("/add")
    public String addCourse(@RequestParam String name,
                            @RequestParam String description) {
        service.addCourse(new CourseModel(0, name, description));
        return "redirect:/courses";
    }

    @PostMapping("/update")
    public String updateCourse(@RequestParam int id,
                               @RequestParam String name,
                               @RequestParam String description) {
        service.updateCourse(new CourseModel(id, name, description));
        return "redirect:/courses";
    }

    @PostMapping("/delete")
    public String deleteCourse(@RequestParam int id) {
        service.deleteCourse(id);
        return "redirect:/courses";
    }

    @PostMapping("/softDelete")
    public String softDeleteCourse(@RequestParam int id) {
        service.softDeleteCourse(id);
        return "redirect:/courses";
    }

    @PostMapping("/deleteMultiple")
    public String deleteMultipleCourses(@RequestParam(name = "ids", required = false) List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) service.deleteCourses(ids);
        return "redirect:/courses";
    }
}
