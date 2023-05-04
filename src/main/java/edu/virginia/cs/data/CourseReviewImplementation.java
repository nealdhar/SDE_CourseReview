package edu.virginia.cs.data;

import java.text.DecimalFormat;
import java.util.*;

public class CourseReviewImplementation {

    DataBaseManagerImpl dataBaseManager = new DataBaseManagerImpl();

    public void connectDatabase() {
        dataBaseManager.connect();
    }
    public void createTables() {
        dataBaseManager.createTables();
    }
    public void closeDatabase() {
        dataBaseManager.disconnect();
    }
    public void clearDatabase() {
        dataBaseManager.clear();
    }

    public String login(String userName, String password) {
        Student student = dataBaseManager.getStudent(userName);
        if (student == null) {
            return "Username not found";
        }
        String storedPassword = student.getPassword();
        if (!storedPassword.equals(password)) {
            return "Password/username incorrect";
        }
        return "Login successful";
    }
    public String createUser(String userName, String password, String confirmPassword) {
        if (dataBaseManager.isUsernameTaken(userName)) {
            return "Username already found in database";
        }
        else if (password.equals(confirmPassword)) {
            Student student = new Student(userName, password);
            dataBaseManager.addStudents(student);
        }
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match";
        }
        return "User created successfully";
    }

    public String submitReview(String userName, String department, String catalogNum, String reviewMessage, String courseRating) {
        if (department.length() > 4) {
            System.err.println("Invalid department name, must be 4 letters or fewer.");
            return "Invalid course format";
        }
        for (char c : department.toCharArray()) {
            if (!Character.isUpperCase(c)) {
                return "Invalid course format";
            }
        }
        if (catalogNum.length() != 4) {
            System.err.println("Invalid catalog number, must be 4 digits long.");
            return "Invalid course format";
        }
        for (char c : catalogNum.toCharArray()) {
            if (!Character.isDigit(c)) {
                System.err.println("Invalid catalog number, must be all digits.");
                return "Invalid course format";
            }
        }
        if (courseRating.length() > 1) {
            System.err.println("Invalid rating, please rate 1-5");
            return "Invalid rating";
        }
        int catalog_number = Integer.parseInt(catalogNum);
        int rating = Integer.parseInt(courseRating);
        Course course = null;
        try {
            course = dataBaseManager.getCourse(department, catalog_number);
            if (course == null) {
                dataBaseManager.addCourses(new Course(department, catalog_number));
                Thread.sleep(1000);
                course = dataBaseManager.getCourse(department, catalog_number);
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting to retry.");
            return null;
        }
        Student student = dataBaseManager.getStudent(userName);
        Review review = new Review(student, course, reviewMessage,rating);
        if (dataBaseManager.userReviewedAlready(userName, review)) {
            return "You have already reviewed this course.";
        }
        dataBaseManager.addReviews(review);
        return "Review added successfully";
    }
        public void seeReviews (String courseName){
            String[] splitCourseName = courseName.split(" ");
            String department = splitCourseName[0];
            String catalogNumber = splitCourseName[1];
            if (department.length() > 4) {
                System.out.println("Invalid department name, must be 4 letters or fewer.");
            }
            if (catalogNumber.length() != 4) {
                System.out.println("Invalid catalog number, must be 4 digits long.");
            }
            for (char c : catalogNumber.toCharArray()) {
                if (!Character.isDigit(c)) {
                    System.out.println("Invalid catalog number, must be all digits.");
                }
            }
            String course = department + ' ' + catalogNumber;
            List<String> reviews = dataBaseManager.getReviews(course);
            if (reviews.isEmpty()) {
                System.out.println("There are no reviews for this course.");
            }
            for (String review : reviews) {
                System.out.println(review);
            }
            String course_average = courseAverage(course);
            System.out.println("Average Course Rating: " + course_average);
        }

        public String courseAverage (String courseName){
            List<Integer> ratings = dataBaseManager.getRatings(courseName);
            int sum = 0;
            for (int rating : ratings) {
                sum += rating;
            }
            DecimalFormat df = new DecimalFormat("#.#");
            double course_average = (double) sum / ratings.size();
            return df.format(course_average);
        }

        public List<String> getReviews (String courseName){
            String[] splitCourseName = courseName.split(" ");
            String department = splitCourseName[0];
            String catalogNumber = splitCourseName[1];
            String course = department + ' ' + catalogNumber;
            List<String> reviews = dataBaseManager.getReviews(course);
            return reviews;
        }


    }

