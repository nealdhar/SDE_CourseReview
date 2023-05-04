package edu.virginia.cs.data;

import java.util.List;
public interface DatabaseManager {

    void connect();

    void createTables();

    void addStudents(Student student);

    void addCourses(Course course);

    void addReviews(Review review);

    List<String> getReviews(String courseName);
    List<Integer> getRatings(String courseName);
    Student getStudent(String userName);

    Course getCourse(String department, int catalog_number);

    void clear();

    void deleteTables();

    void disconnect();
}


