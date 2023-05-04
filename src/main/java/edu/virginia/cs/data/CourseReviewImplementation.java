package edu.virginia.cs.data;
import edu.virginia.cs.data.DataBaseManagerImpl;
import edu.virginia.cs.data.Student;
import edu.virginia.cs.data.Course;
import edu.virginia.cs.data.Review;

import java.text.DecimalFormat;
import java.util.*;

public class CourseReviewImplementation {

    DataBaseManagerImpl dataBaseManager = new DataBaseManagerImpl();

   // public static void main(String[] args) {

//        courseReview.createTables();
//        String userName = "ogf9uhy";
//        String password = "mypassword";
//        String confirmPassword = "mypassword";
//        courseReview.login(userName, password);
//        courseReview.createUser(userName, password, confirmPassword);
//        String department = "CS";
//        String catalog_number = "3100";
//        String message = "Professor Brunelle is my favorite in the department.";
//        String rating = "5";
//        courseReview.submitReview(userName, department, catalog_number, message, rating);
//        String courseName = "CS 3100";
//        courseReview.seeReviews(courseName);
//        courseReview.clearDatabase();
//        courseReview.closeDatabase();
    //}


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
        dataBaseManager.connect();
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
    public void createUser(String userName, String password, String confirmPassword) {
        if (dataBaseManager.isUsernameTaken(userName)) {
            System.err.println("Username is already found in database, please try logging in.");
        }
        else if (password == confirmPassword) {
            Student student = new Student(userName, password);
            dataBaseManager.addStudents(student);
        }
    }



    public void submitReview(String userName, String department, String catalogNum, String reviewMessage, String courseRating) {
        if (department.length() > 4) {
            System.err.println("Invalid department name, must be 4 letters or fewer.");
            return;
        }
        if (catalogNum.length() != 4) {
            System.err.println("Invalid catalog number, must be 4 digits long.");
            return;
        }
        for (char c : catalogNum.toCharArray()) {
            if (!Character.isDigit(c)) {
                System.err.println("Invalid catalog number, must be all digits.");
                return;
            }
        }
        if (courseRating.length() > 1) {
            System.err.println("Invalid rating, please rate 1-5");
            return;
        }
        department.toUpperCase();
        int catalog_number = Integer.parseInt(catalogNum);
        int rating = Integer.parseInt(courseRating);
        Course course = dataBaseManager.getCourse(department, catalog_number);
        if (course == null) {
            dataBaseManager.addCourses(new Course(department, catalog_number));
        }
        Student student = dataBaseManager.getStudent(userName);
        Review review = new Review(student, course, reviewMessage, rating);
        dataBaseManager.addReviews(review);
    }


    public void seeReviews(String courseName) {
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


    public String courseAverage(String courseName) {
        List<Integer> ratings = dataBaseManager.getRatings(courseName);
        int sum = 0;
        for (int rating : ratings) {
            sum += rating;
        }
        DecimalFormat df = new DecimalFormat("#.#");
        double course_average = (double) sum / ratings.size();
        return df.format(course_average);
    }

    public List<String> getReviews(String courseName) {
        String[] splitCourseName = courseName.split(" ");
        String department = splitCourseName[0];
        String catalogNumber = splitCourseName[1];
        String course = department + ' ' + catalogNumber;
        List<String> reviews = dataBaseManager.getReviews(course);
        return reviews;
    }


}

