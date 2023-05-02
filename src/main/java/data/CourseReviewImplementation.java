package data;
import java.util.*;

public class CourseReviewImplementation {

    DataBaseManagerImpl dataBaseManager = new DataBaseManagerImpl();
    private List<Student> students;
    private List<Course> courses;
    private List<Review> reviews;

    public CourseReviewImplementation(List<Student> students, List<Course> courses, List<Review> reviews) {
        this.students = students;
        this.courses = courses;
        this.reviews = reviews;
    }

    public static void main(String[] args) {
    }

    public boolean login(String userName, String password) {
        Student student = dataBaseManager.getStudent(userName);
        String storedPassword = student.getPassword();
        if (storedPassword != password) {
            System.out.println("Password and/or username is incorrect.");
            return false;
        }
        return true;
    }
    public void createUser(int ID, String userName, String password, String confirmPassword) {
        if (dataBaseManager.isUsernameTaken(userName)) {
            System.out.println("Username is already found in database.");
        }
        else if (password == confirmPassword) {
            students.add(new Student(ID, userName, password));
        }
    }

    public void submitReview(String userName, String department, String catalogNum, String reviewMessage, String courseRating) {
        if (department.length() > 4) {
            System.out.println("Invalid department name, must be 4 letters or fewer.");
        }
        if (catalogNum.length() != 4) {
            System.out.println("Invalid catalog number, must be 4 digits.");
        }
        if (courseRating.length() > 1) {
            System.out.println("Invalid rating, please rate 1-5");
        }
        department.toUpperCase();
        int catalog_number = Integer.parseInt(catalogNum);
        int rating = Integer.parseInt(courseRating);
        Course course = new Course(0, department, catalog_number);
        Student student = dataBaseManager.getStudent(userName);
        reviews.add(new Review(0, student, course, reviewMessage, rating));
    }
    public void seeReviews(Review review) {

    }
}
