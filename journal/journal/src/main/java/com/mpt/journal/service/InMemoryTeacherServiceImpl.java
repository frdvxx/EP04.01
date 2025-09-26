package com.mpt.journal.service;

import com.mpt.journal.model.TeacherModel;
import com.mpt.journal.repository.InMemoryTeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryTeacherServiceImpl implements TeacherService {

    private final InMemoryTeacherRepository repository;

    public InMemoryTeacherServiceImpl(InMemoryTeacherRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TeacherModel> findAllTeachers() { return repository.findAllTeachers(); }

    @Override
    public TeacherModel findTeacherById(int id) { return repository.findTeacherById(id); }

    @Override
    public TeacherModel addTeacher(TeacherModel teacher) { return repository.addTeacher(teacher); }

    @Override
    public TeacherModel updateTeacher(TeacherModel teacher) { return repository.updateTeacher(teacher); }

    @Override
    public void deleteTeacher(int id) { repository.deleteTeacher(id); }

    @Override
    public void softDeleteTeacher(int id) { repository.softDeleteTeacher(id); }

    @Override
    public void deleteTeachers(List<Integer> ids) { repository.deleteTeachers(ids); }

    @Override
    public List<TeacherModel> filterTeachers(String firstName, String lastName, String subject) {
        return repository.filterTeachers(firstName, lastName, subject);
    }

    @Override
    public List<TeacherModel> getTeachersByPage(int page, int pageSize) {
        return repository.getTeachersByPage(page, pageSize);
    }
}
