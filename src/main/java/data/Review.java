package data;

public class Review {
    private Student reviewer;
    private Course course;
    private String review_message;
    private int rating;

    public Review(Student reviewer, Course course, String review_message, int rating) {
        this.reviewer = reviewer;
        this.course = course;
        this.review_message = review_message;
        this.rating = rating;
    }

    public Student getReviewer() { return reviewer; }
    public void setReviewer(Student reviewer) { this.reviewer = reviewer; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public String getReview_message() { return review_message; }
    public void setReview_message(String review_message) { this.review_message = review_message; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}
