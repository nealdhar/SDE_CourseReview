package edu.virginia.cs.data;
import java.sql.*;
import java.util.*;

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
            throw new IllegalStateException(e);
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
            throw new IllegalStateException(e);
        }
    }


    @Override
    public void addStudents(Student student) {
        String sql = "INSERT INTO Students (Name, Password) VALUES (?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, student.getUsername());
            statement.setString(2, student.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public void addCourses(Course course) {
        String sql = "INSERT INTO Courses (Department, Catalog_Number) VALUES (?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, course.getDepartment());
            statement.setInt(2, course.getCatalogNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
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
    public void addReviews(Review review) {
        String sql = "INSERT INTO Reviews (Reviewer, Course, Message, Rating) VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, review.getReviewer().getUsername());
            statement.setString(2, review.getCourse().getDepartment() + ' ' + review.getCourse().getCatalogNumber());
            statement.setString(3, review.getReview_message());
            statement.setInt(4, review.getRating());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public List<String> getReviews(String courseName) {
        List<String> reviews = new ArrayList<>();
        String getReviewsByCourseQuery = "SELECT Message, Rating FROM Reviews WHERE Course = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(getReviewsByCourseQuery);
            statement.setString(1, courseName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                reviews.add(resultSet.getString("Message") + " Rating: " + resultSet.getInt("Rating"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return reviews;
    }


    @Override
    public List<Integer> getRatings(String courseName) {
        List<Integer> ratings = new ArrayList<>();
        String getRatingsByCourseQuery = "SELECT Rating FROM Reviews WHERE Course = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(getRatingsByCourseQuery);
            statement.setString(1, courseName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ratings.add(resultSet.getInt("Rating"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return ratings;
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
            throw new IllegalStateException(e);
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
            if (resultSet.next()) {
                course = new Course(resultSet.getString("Department"), resultSet.getInt("Catalog_Number"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
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
            throw new IllegalStateException(e);
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
            throw new IllegalStateException(e);
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
            //connection = null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
        public void createDatabaseData() {
            Student Neal = new Student("Neal", "nealdhar");
            Student Olivia = new Student("Liv", "livfountain");
            Student James = new Student("James", "jamesjin");
            addStudents(Neal); addStudents(Olivia); addStudents(James);
            Course cs3140 = new Course("CS",3140);
            Course dram2620 = new Course("DRAM", 2620);
            Course cs2130 = new Course("CS", 2130);
            addCourses(cs3140); addCourses(dram2620); addCourses(cs2130);
            Review dram2620_review_1 = new Review(Neal, dram2620, "Liked this class", 5);
            Review dram2620_review_2 = new Review(James, dram2620, "Mid class", 3);
            Review cs3140_review = new Review(Olivia, cs3140, "Lot of work", 4);
            Review cs2130_review = new Review(Neal, cs2130, "Didn't like", 1);
            addReviews(dram2620_review_1); addReviews(dram2620_review_2);
            addReviews(cs3140_review); addReviews(cs2130_review);

        }

}

