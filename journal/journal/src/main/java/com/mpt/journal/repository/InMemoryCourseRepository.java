package com.mpt.journal.repository;

import com.mpt.journal.model.CourseModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryCourseRepository {
    private List<CourseModel> courses = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);

    public CourseModel addCourse(CourseModel course) {
        course.setId(idCounter.getAndIncrement());
        courses.add(course);
        return course;
    }

    public CourseModel updateCourse(CourseModel course) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId() == course.getId()) {
                courses.set(i, course);
                return course;
            }
        }
        return null;
    }

    public void deleteCourse(int id) { courses.removeIf(c -> c.getId() == id); }

    public void softDeleteCourse(int id) {
        courses.stream().filter(c -> c.getId() == id).findFirst().ifPresent(c -> c.setDeleted(true));
    }

    public void deleteCourses(List<Integer> ids) { courses.removeIf(c -> ids.contains(c.getId())); }

    public List<CourseModel> findAllCourses() {
        return courses.stream().filter(c -> !c.isDeleted()).collect(Collectors.toList());
    }

    public CourseModel findCourseById(int id) {
        return courses.stream().filter(c -> c.getId() == id && !c.isDeleted()).findFirst().orElse(null);
    }

    public List<CourseModel> filterCourses(String name, String description) {
        return courses.stream().filter(c -> !c.isDeleted())
                .filter(c -> name == null || c.getName().equalsIgnoreCase(name))
                .filter(c -> description == null || c.getDescription().equalsIgnoreCase(description))
                .collect(Collectors.toList());
    }

    public List<CourseModel> getCoursesByPage(int page, int pageSize) {
        List<CourseModel> all = findAllCourses();
        int from = (page - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        if (from >= all.size()) return new ArrayList<>();
        return all.subList(from, to);
    }
}
