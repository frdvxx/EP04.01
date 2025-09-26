package com.mpt.journal.controller;

import com.mpt.journal.model.TeacherModel;
import com.mpt.journal.service.InMemoryTeacherServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final InMemoryTeacherServiceImpl service;

    public TeacherController(InMemoryTeacherServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public String getAllTeachers(Model model,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int pageSize) {
        List<TeacherModel> teachers = service.getTeachersByPage(page, pageSize);
        int total = service.findAllTeachers().size();
        int totalPages = (int)Math.ceil((double)total / pageSize);
        model.addAttribute("teachers", teachers);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "teacherList";
    }

    @GetMapping("/search")
    public String searchTeachers(Model model,
                                 @RequestParam(required = false) String firstName,
                                 @RequestParam(required = false) String lastName,
                                 @RequestParam(required = false) String subject) {
        List<TeacherModel> teachers = service.filterTeachers(firstName, lastName, subject);
        model.addAttribute("teachers", teachers);
        return "teacherList";
    }

    @PostMapping("/add")
    public String addTeacher(@RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam String subject) {
        service.addTeacher(new TeacherModel(0, firstName, lastName, subject));
        return "redirect:/teachers";
    }

    @PostMapping("/update")
    public String updateTeacher(@RequestParam int id,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String subject) {
        service.updateTeacher(new TeacherModel(id, firstName, lastName, subject));
        return "redirect:/teachers";
    }

    @PostMapping("/delete")
    public String deleteTeacher(@RequestParam int id) {
        service.deleteTeacher(id);
        return "redirect:/teachers";
    }

    @PostMapping("/softDelete")
    public String softDeleteTeacher(@RequestParam int id) {
        service.softDeleteTeacher(id);
        return "redirect:/teachers";
    }

    @PostMapping("/deleteMultiple")
    public String deleteMultipleTeachers(@RequestParam(name = "ids", required = false) List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) service.deleteTeachers(ids);
        return "redirect:/teachers";
    }
}
