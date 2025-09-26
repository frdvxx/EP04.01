package com.mpt.journal.repository;

import com.mpt.journal.model.StudentModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryStudentRepository {
    private List<StudentModel> students = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);

    public StudentModel addStudent(StudentModel student) {
        student.setId(idCounter.getAndIncrement());
        students.add(student);
        return student;
    }

    public StudentModel updateStudent(StudentModel student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                students.set(i, student);
                return student;
            }
        }
        return null;
    }

    public void deleteStudent(int id) {
        students.removeIf(s -> s.getId() == id);
    }

    // Логическое удаление
    public void softDeleteStudent(int id) {
        students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .ifPresent(s -> s.setDeleted(true));
    }

    // Множественное удаление
    public void deleteStudents(List<Integer> ids) {
        students.removeIf(s -> ids.contains(s.getId()));
    }

    public List<StudentModel> findAllStudents() {
        return students.stream().filter(s -> !s.isDeleted()).collect(Collectors.toList());
    }

    public StudentModel findStudentById(int id) {
        return students.stream()
                .filter(s -> s.getId() == id && !s.isDeleted())
                .findFirst()
                .orElse(null);
    }

    // Поиск по имени
    public List<StudentModel> findStudentsByName(String name) {
        return students.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name) && !s.isDeleted())
                .collect(Collectors.toList());
    }

    // Фильтрация по 3 критериям
    public List<StudentModel> filterStudents(String name, String lastName, String firstName) {
        return students.stream()
                .filter(s -> !s.isDeleted())
                .filter(s -> name == null || s.getName().equalsIgnoreCase(name))
                .filter(s -> lastName == null || s.getLastName().equalsIgnoreCase(lastName))
                .filter(s -> firstName == null || s.getFirstName().equalsIgnoreCase(firstName))
                .collect(Collectors.toList());
    }

    public List<StudentModel> getStudentsByPage(int page, int pageSize) {
        List<StudentModel> allStudents = findAllStudents(); // только не удалённые
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, allStudents.size());
        if (fromIndex >= allStudents.size()) return new ArrayList<>();
        return allStudents.subList(fromIndex, toIndex);
    }

}
