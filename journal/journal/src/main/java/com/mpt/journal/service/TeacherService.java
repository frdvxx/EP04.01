package com.mpt.journal.service;

import com.mpt.journal.model.TeacherModel;

import java.util.List;

public interface TeacherService {
    List<TeacherModel> findAllTeachers();
    TeacherModel findTeacherById(int id);
    TeacherModel addTeacher(TeacherModel teacher);
    TeacherModel updateTeacher(TeacherModel teacher);
    void deleteTeacher(int id);
    void softDeleteTeacher(int id);
    void deleteTeachers(List<Integer> ids);
    List<TeacherModel> filterTeachers(String firstName, String lastName, String subject);
    List<TeacherModel> getTeachersByPage(int page, int pageSize);
}
