package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
public class DataBaseManagerImpl implements DatabaseManager {

    Connection connection;
    @Override
    public void connect() {
        String databaseFile = "Reviews.sqlite3";
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
                "Students (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name VARCHAR(255) NOT NULL, " +
                "Password VARCHAR(255) NOT NULL);";

        String createCoursesTable = "CREATE TABLE IF NOT EXISTS " +
                "Courses (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Department VARCHAR(255) NOT NULL, " +
                "Catalog_Number INTEGER NOT NULL);";

        String createReviewsTable = "CREATE TABLE IF NOT EXISTS " +
                "Reviews (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Reviewer INTEGER NOT NULL, " +
                "Course INTEGER NOT NULL, " +
                "Message TEXT NOT NULL, " +
                "Rating INTEGER NOT NULL, " +
                "FOREIGN KEY (Reviewer) REFERENCES Students(ID) ON DELETE CASCADE, " +
                "FOREIGN KEY (Course) REFERENCES Courses(ID) ON DELETE CASCADE);";
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
        String sql = "INSERT INTO Students (Name, Password) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Student student : students) {
                statement.setString(1, student.getUsername());
                statement.setString(2, student.getPassword());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCourses(List<Course> courses) {
        String sql = "INSERT INTO Courses (Department, Catalog_Number) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Course course : courses) {
                statement.setString(1, course.getDepartment());
                statement.setInt(2, course.getCatalogNumber());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUsernameTaken(String userName) {
        String usernameTakenQuery = "SELECT * FROM Students WHERE Name = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(usernameTakenQuery);
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int userExists = resultSet.getInt(1);
            return userExists > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void addReviews(List<Review> reviews) {
        String sql = "INSERT INTO Reviews (Reviewer, Course, Message, Rating) VALUES (?, ?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Review review : reviews) {
                statement.setString(1, review.getReviewer().getUsername());
                statement.setString(2, review.getCourse().getDepartment() + ' ' + review.getCourse().getCatalogNumber());
                statement.setString(3, review.getReview_message());
                statement.setInt(4, review.getRating());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getReviews(String courseName) {
        List<String> reviews = new ArrayList<>();
        String getReviewsByCourseQuery = "SELECT Message FROM Reviews WHERE Course = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(getReviewsByCourseQuery);
            statement.setString(1, courseName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                reviews.add(resultSet.getString("Message"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }

    @Override
    public Student getStudent(String userName) {
        String getStudentQuery = "SELECT * FROM Students WHERE Name = ?";
        Student student = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(getStudentQuery);
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("Password");
                student = new Student(userName, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    @Override
    public Course getCourse(String department, int catalog_number) {
        String getCourseQuery = "SELECT * FROM Courses WHERE Department = ? AND Catalog_Number = ?";
        Course course = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(getCourseQuery);
            statement.setString(1, department);
            statement.setInt(2, catalog_number);
            ResultSet resultSet = statement.executeQuery();
            course = new Course(department, catalog_number);

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
