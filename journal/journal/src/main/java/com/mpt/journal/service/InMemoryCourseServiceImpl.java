package com.mpt.journal.service;

import com.mpt.journal.model.CourseModel;
import com.mpt.journal.repository.InMemoryCourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryCourseServiceImpl implements CourseService {

    private final InMemoryCourseRepository repository;

    public InMemoryCourseServiceImpl(InMemoryCourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CourseModel> findAllCourses() { return repository.findAllCourses(); }

    @Override
    public CourseModel findCourseById(int id) { return repository.findCourseById(id); }

    @Override
    public CourseModel addCourse(CourseModel course) { return repository.addCourse(course); }

    @Override
    public CourseModel updateCourse(CourseModel course) { return repository.updateCourse(course); }

    @Override
    public void deleteCourse(int id) { repository.deleteCourse(id); }

    @Override
    public void softDeleteCourse(int id) { repository.softDeleteCourse(id); }

    @Override
    public void deleteCourses(List<Integer> ids) { repository.deleteCourses(ids); }

    @Override
    public List<CourseModel> filterCourses(String name, String description) {
        return repository.filterCourses(name, description);
    }

    @Override
    public List<CourseModel> getCoursesByPage(int page, int pageSize) {
        return repository.getCoursesByPage(page, pageSize);
    }
}
