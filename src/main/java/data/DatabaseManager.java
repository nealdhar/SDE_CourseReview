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

    Review getReviews(String courseName);

    void clear();

    void deleteTables();

    void disconnect();
}
