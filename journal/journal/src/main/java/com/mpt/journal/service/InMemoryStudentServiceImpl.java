package com.mpt.journal.service;

import com.mpt.journal.model.StudentModel;
import com.mpt.journal.repository.InMemoryStudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryStudentServiceImpl implements StudentService {

    private final InMemoryStudentRepository studentRepository;

    public InMemoryStudentServiceImpl(InMemoryStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentModel> findAllStudent() {
        return studentRepository.findAllStudents();
    }

    @Override
    public StudentModel findStudentById(int id) {
        return studentRepository.findStudentById(id);
    }

    @Override
    public StudentModel addStudent(StudentModel student) {
        return studentRepository.addStudent(student);
    }

    @Override
    public StudentModel updateStudent(StudentModel student) {
        return studentRepository.updateStudent(student);
    }

    @Override
    public void deleteStudent(int id) {
        studentRepository.deleteStudent(id);
    }

    // Логическое удаление
    public void softDeleteStudent(int id) {
        studentRepository.softDeleteStudent(id);
    }

    // Множественное удаление
    public void deleteStudents(List<Integer> ids) {
        for (Integer id : ids) {
            studentRepository.deleteStudent(id);
        }
    }

    // Поиск по имени
    public List<StudentModel> findStudentsByName(String name) {
        return studentRepository.findStudentsByName(name);
    }

    // Фильтрация по 3 критериям
    public List<StudentModel> filterStudents(String name, String lastName, String firstName) {
        return studentRepository.filterStudents(name, lastName, firstName);
    }

    // Пагинация
    public List<StudentModel> getStudentsByPage(int page, int pageSize) {
        return studentRepository.getStudentsByPage(page, pageSize);
    }
}
