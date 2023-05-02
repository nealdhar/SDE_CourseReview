package data;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
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
