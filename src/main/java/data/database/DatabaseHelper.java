import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    private static final String DB_NAME = "Reviews.sqlite3";
    //private static final String DB_URL = "jdbc:sqlite:" + DB_NAME;

    public static Connection connect() {
    public static Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database");
            e.printStackTrace();
            return null;
        }
    }

    public static void createTablesIfNeeded() {
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            String studentsTable = "CREATE TABLE IF NOT EXISTS Students (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL" +
                    ");";

            String coursesTable = "CREATE TABLE IF NOT EXISTS Courses (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "department TEXT NOT NULL," +
                    "catalog_number TEXT NOT NULL" +
                    ");";

            String reviewsTable = "CREATE TABLE IF NOT EXISTS Reviews (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "student_id INTEGER NOT NULL," +
                    "course_id INTEGER NOT NULL," +
                    "message TEXT NOT NULL," +
                    "rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5)," +
                    "FOREIGN KEY(student_id) REFERENCES Students(id) ON DELETE CASCADE," +
                    "FOREIGN KEY(course_id) REFERENCES Courses(id) ON DELETE CASCADE" +
                    ");";

            statement.execute(studentsTable);
            statement.execute(coursesTable);
            statement.execute(reviewsTable);

        } catch (SQLException e) {
            System.err.println("Error creating tables");
            e.printStackTrace();
        }
    }
}