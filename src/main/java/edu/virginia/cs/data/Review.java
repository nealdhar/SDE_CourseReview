package edu.virginia.cs.data;

public class Review {
    private int id;
    private int studentId;
    private int courseId;
    private String message;
    private int rating;

    public Review(int id, int studentId, int courseId, String message, int rating) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.message = message;
        this.rating = rating;
    }

// Add getters and setters for all attributes
}