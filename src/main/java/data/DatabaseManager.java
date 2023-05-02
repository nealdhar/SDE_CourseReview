package data;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
public interface DatabaseManager {

    void connect();

    void createTables();

    void addStudents(List<Student> students);

    void addCourses(List<Course> courses);

    void addReviews(List<Review> reviews);

    List<String> getReviews(String courseName);
    Student getStudent(String userName);

    Course getCourse(String department, int catalog_number);

    void clear();

    void deleteTables();

    void disconnect();
}
