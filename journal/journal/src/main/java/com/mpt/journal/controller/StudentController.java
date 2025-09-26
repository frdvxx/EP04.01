package com.mpt.journal.controller;

import com.mpt.journal.model.StudentModel;
import com.mpt.journal.service.InMemoryStudentServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    private final InMemoryStudentServiceImpl studentService;

    public StudentController(InMemoryStudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String getAllStudents(Model model,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int pageSize) {
        List<StudentModel> students = studentService.getStudentsByPage(page, pageSize);

        int totalStudents = studentService.findAllStudent().size();
        int totalPages = (int) Math.ceil((double) totalStudents / pageSize);

        model.addAttribute("students", students);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "studentList";
    }


    @GetMapping("/students/search")
    public String searchStudents(Model model,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String lastName,
                                 @RequestParam(required = false) String firstName) {
        List<StudentModel> students = studentService.filterStudents(name, lastName, firstName);
        model.addAttribute("students", students);
        return "studentList";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam String name,
                             @RequestParam String lastName,
                             @RequestParam String firstName,
                             @RequestParam String middleName) {
        studentService.addStudent(new StudentModel(0, name, lastName, firstName, middleName));
        return "redirect:/students";
    }

    @PostMapping("/students/update")
    public String updateStudent(@RequestParam int id,
                                @RequestParam String name,
                                @RequestParam String lastName,
                                @RequestParam String firstName,
                                @RequestParam String middleName) {
        studentService.updateStudent(new StudentModel(id, name, lastName, firstName, middleName));
        return "redirect:/students";
    }

    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam int id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    @PostMapping("/students/softDelete")
    public String softDeleteStudent(@RequestParam int id) {
        studentService.softDeleteStudent(id);
        return "redirect:/students";
    }

    @PostMapping("/students/deleteMultiple")
    public String deleteMultipleStudents(@RequestParam(name = "ids", required = false) List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            studentService.deleteStudents(ids);
        }
        return "redirect:/students";
    }
}
