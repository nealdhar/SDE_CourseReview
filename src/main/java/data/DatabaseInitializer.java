package data;

import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializer {

    public static void main(String[] args) {
        DataBaseManagerImpl databaseManager = new DataBaseManagerImpl();
        databaseManager.connect();
        databaseManager.createTables();
//        databaseManager.clear();
//        databaseManager.deleteTables();
//
//        List<Student> students = new ArrayList<>();
//        List<Course> courses = new ArrayList<>();
//        List<Review> reviews = new ArrayList<>();
//
//        students.add(new Student("JohnDoe", "password1"));
//        students.add(new Student("JaneDoe", "password2"));
//
//        courses.add(new Course("CS", 101));
//        courses.add(new Course("CS", 102));
//
//        reviews.add(new Review(students.get(0), courses.get(0),"Great course!", 5));
//        reviews.add(new Review(students.get(1), courses.get(1), "I learned a lot!", 4));
//        databaseManager.addStudents(students);
//        databaseManager.addCourses(courses);
//        databaseManager.addReviews(reviews);

//         Testing getting student info from ID
        Student student = databaseManager.getStudent("JaneDoe");
        System.out.println(student.getUsername());
        System.out.println(student.getPassword());

        // Testing getting course info from ID
        Course course = databaseManager.getCourse("CS", 101);
        System.out.println(course.getDepartment());
        System.out.println(course.getCatalogNumber());
//
//        // Testing getting reviews from CourseID
        List<String> review_messages = databaseManager.getReviews("CS 101");
        System.out.println(review_messages);

        databaseManager.disconnect();
    }
}
