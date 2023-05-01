package data;

import java.util.ArrayList;
import java.util.List;


public class InputProcessor {
    public List<Student> processStudents(List<String> studentInputs) {
        List<Student> students = new ArrayList<>();
        for (String input : studentInputs) {
            String[] parts = input.split(",");
            int idNumber = Integer.parseInt(parts[0]);
            String username = parts[1];
            String password = parts[2];
            students.add(new Student(idNumber, username, password));
        }
        return students;
    }

    public List<Course> processCourses(List<String> courseInputs) {
        List<Course> courses = new ArrayList<>();
        for (String input : courseInputs) {
            String[] parts = input.split(",");
            int idNumber = Integer.parseInt(parts[0]);
            String department = parts[1];
            int catalogNumber = Integer.parseInt(parts[2]);
            courses.add(new Course(idNumber, department, catalogNumber));
        }
        return courses;
    }

    public List<Review> processReviews(List<String> reviewInputs, List<Student> students, List<Course> courses) {
        List<Review> reviews = new ArrayList<>();
        for (String input : reviewInputs) {
            String[] parts = input.split(",");
            int idNumber = Integer.parseInt(parts[0]);
            int studentId = Integer.parseInt(parts[1]);
            int courseId = Integer.parseInt(parts[2]);
            String reviewMessage = parts[3];
            int rating = Integer.parseInt(parts[4]);

            Student reviewer = findStudentById(studentId, students);
            Course course = findCourseById(courseId, courses);

            if (reviewer != null && course != null) {
                reviews.add(new Review(idNumber, reviewer, course, reviewMessage, rating));
            }
        }
        return reviews;
    }

    private Student findStudentById(int id, List<Student> students) {
        for (Student student : students) {
            if (student.getId_number() == id) {
                return student;
            }
        }
        return null;
    }

    private Course findCourseById(int id, List<Course> courses) {
        for (Course course : courses) {
            if (course.getId_number() == id) {
                return course;
            }
        }
        return null;
    }
}
