package data;

import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializer {

    public static void main(String[] args) {
        DataBaseManagerImpl databaseManager = new DataBaseManagerImpl();
        databaseManager.connect();
        databaseManager.createTables();

        List<Student> students = new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();

        // Add sample data to the lists
        students.add(new Student(1,"JohnDoe", "password1"));
        students.add(new Student(2,"JaneDoe", "password2"));

        courses.add(new Course(1,"CS", 101));
        courses.add(new Course(2,"CS", 102));

        reviews.add(new Review(1,students.get(0), courses.get(0), "Great course!", 5));
        reviews.add(new Review(2,students.get(1), courses.get(0), "I learned a lot!", 4));

        // Use the database manager to add data to the database
        databaseManager.addStudents(students);
        databaseManager.addCourses(courses);
        databaseManager.addReviews(reviews);

        databaseManager.disconnect();
    }
}
