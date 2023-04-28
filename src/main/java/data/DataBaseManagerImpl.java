package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

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

    }

    @Override
    public void addCourses(List<Course> courses) {

    }

    @Override
    public void addReviews(List<Review> reviews) {

    }

    @Override
    public Review getReviews(String courseName) {
        return null;
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
