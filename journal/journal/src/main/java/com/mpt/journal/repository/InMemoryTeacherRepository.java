package com.mpt.journal.repository;

import com.mpt.journal.model.TeacherModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryTeacherRepository {
    private List<TeacherModel> teachers = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);

    public TeacherModel addTeacher(TeacherModel teacher) {
        teacher.setId(idCounter.getAndIncrement());
        teachers.add(teacher);
        return teacher;
    }

    public TeacherModel updateTeacher(TeacherModel teacher) {
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getId() == teacher.getId()) {
                teachers.set(i, teacher);
                return teacher;
            }
        }
        return null;
    }

    public void deleteTeacher(int id) { teachers.removeIf(t -> t.getId() == id); }

    public void softDeleteTeacher(int id) {
        teachers.stream().filter(t -> t.getId() == id).findFirst().ifPresent(t -> t.setDeleted(true));
    }

    public void deleteTeachers(List<Integer> ids) { teachers.removeIf(t -> ids.contains(t.getId())); }

    public List<TeacherModel> findAllTeachers() {
        return teachers.stream().filter(t -> !t.isDeleted()).collect(Collectors.toList());
    }

    public TeacherModel findTeacherById(int id) {
        return teachers.stream().filter(t -> t.getId() == id && !t.isDeleted()).findFirst().orElse(null);
    }

    public List<TeacherModel> filterTeachers(String firstName, String lastName, String subject) {
        return teachers.stream().filter(t -> !t.isDeleted())
                .filter(t -> firstName == null || t.getFirstName().equalsIgnoreCase(firstName))
                .filter(t -> lastName == null || t.getLastName().equalsIgnoreCase(lastName))
                .filter(t -> subject == null || t.getSubject().equalsIgnoreCase(subject))
                .collect(Collectors.toList());
    }

    public List<TeacherModel> getTeachersByPage(int page, int pageSize) {
        List<TeacherModel> all = findAllTeachers();
        int from = (page - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        if (from >= all.size()) return new ArrayList<>();
        return all.subList(from, to);
    }
}
