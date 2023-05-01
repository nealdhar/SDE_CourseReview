package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
public class DataBaseManagerImpl implements DatabaseManager {

    Connection connection;

    public static void main(String[] args) {
        DataBaseManagerImpl dataBaseManager = new DataBaseManagerImpl();
        dataBaseManager.connect();
        dataBaseManager.createTables();
//        dataBaseManager.deleteTables();
    }
    @Override
    public void connect() {
        String databaseFile = "Reviews.sqlite3";
        File file = new File(databaseFile);
        String databaseURL = "jdbc:sqlite:" + databaseFile;
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseURL);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createTables() {
        String createStudentsTable = "CREATE TABLE IF NOT EXISTS " +
                "Students (ID INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                "Name VARCHAR(255) NOT NULL, " +
                "Password VARCHAR(255) NOT NULL);";

        String createCoursesTable = "CREATE TABLE IF NOT EXISTS " +
                "Courses (ID INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                "Department VARCHAR(255) NOT NULL, " +
                "Catalog_Number INTEGER NOT NULL);";

        String createReviewsTable = "CREATE TABLE IF NOT EXISTS " +
                "Reviews (ID INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                "StudentID INTEGER NOT NULL, " +
                "CourseID INTEGER NOT NULL, " +
                "Message TEXT NOT NULL, " +
                "Rating INTEGER NOT NULL, " +
                "FOREIGN KEY (StudentID) REFERENCES Students(ID) ON DELETE CASCADE, " +
                "FOREIGN KEY (CourseID) REFERENCES Courses(ID) ON DELETE CASCADE);";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createStudentsTable);
            statement.execute(createCoursesTable);
            statement.execute(createReviewsTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addStudents(List<Student> students) {
        String sql = "INSERT INTO Students (ID, Name, Password) VALUES (?, ?, ?);";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Student student : students) {
                pstmt.setInt(1, student.getId_number());
                pstmt.setString(2, student.getUsername());
                pstmt.setString(3, student.getPassword());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCourses(List<Course> courses) {
        String sql = "INSERT INTO Courses (ID, Department, Catalog_Number) VALUES (?, ?, ?);";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Course course : courses) {
                pstmt.setInt(1, course.getId_number());
                pstmt.setString(2, course.getDepartment());
                pstmt.setInt(3, course.getCatalogNumber());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addReviews(List<Review> reviews) {
        String sql = "INSERT INTO Reviews (StudentID, CourseID, Message, Rating) VALUES (?, ?, ?, ?);";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Review review : reviews) {
                pstmt.setInt(1, review.getReviewer().getId_number());
                pstmt.setInt(2, review.getCourse().getId_number());
                pstmt.setString(3, review.getReview_message());
                pstmt.setInt(4, review.getRating());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getReviews(int courseID) {
        List<String> reviews = new ArrayList<>();
        String getReviewsByCourseQuery = String.format("""
        SELECT Message FROM Reviews WHERE CourseID = (%d);
        """, courseID);
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getReviewsByCourseQuery);
            while (resultSet.next()) {
                reviews.add(resultSet.getString("Message"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }

    @Override
    public Student getStudent(int studentID) {
        String getStudentIDQuery = String.format("""
                SELECT * FROM Students WHERE ID = (%d);
                """, studentID);
        Student student = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getStudentIDQuery);
            String name = resultSet.getString("Name");
            String password = resultSet.getString("Password");
            student = new Student(studentID, name, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    @Override
    public Course getCourse(int courseID) {
        String getCourseIDQuery = String.format("""
                SELECT * FROM Courses WHERE ID = (%d);
                """, courseID);
        Course course = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getCourseIDQuery);
            String department = resultSet.getString("Department");
            int catalog_number = resultSet.getInt("Catalog_Number");
            course = new Course(courseID, department, catalog_number);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return course;
    }

    @Override
    public void clear() {
        if (connection == null) {
            connect();
        }
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tablesData = metaData.getTables(null, null, null, new String[] {"TABLE"});
            if (!tablesData.next()) {
                return;
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table';");
            List<String> tables = new ArrayList<>();
            while (resultSet.next()) {
                tables.add(resultSet.getString(1));
            }
            for (String table : tables) {
                statement.executeUpdate("DELETE FROM " + table + ";");
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTables() {
        if (connection == null) {
            connect();
        }
        try {
            Statement statement = connection.createStatement();
            String deleteStudents = "DROP TABLE IF EXISTS Students;";
            String deleteCourses = "DROP TABLE IF EXISTS Courses;";
            String deleteReviews = "DROP TABLE IF EXISTS Reviews;";
            statement.executeUpdate(deleteStudents);
            statement.executeUpdate(deleteCourses);
            statement.executeUpdate(deleteReviews);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnect() {
        if (connection == null) {
            return;
        }
        try {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            connection.close();
            connection = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
