package edu.virginia.cs.data;

import java.sql.*;
import java.util.List;
import java.io.File;

public class DataBaseManagerImpl implements DatabaseManager {

    Connection connection;
    @Override
    public void connect() {
        String databaseFile = "Reviews.sqlite3";
        File file = new File(databaseFile);
        String databaseURL = "jdbc:sqlite:" + databaseFile;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseURL);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createTables() {
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

    }

    @Override
    public void deleteTables() {

    }

    @Override
    public void disconnect() {

    }
}
