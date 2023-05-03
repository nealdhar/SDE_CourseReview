package edu.virginia.cs.gui;

import edu.virginia.cs.data.CourseReviewImplementation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import edu.virginia.cs.data.CourseReviewImplementation;


import java.util.List;

public class SeeReviewsController {

    CourseReviewImplementation courseReview = new CourseReviewImplementation();

    @FXML
    private TextField courseNameTextField;

    @FXML
    private Button viewReviewsButton;

    @FXML
    private Label courseAverageLabel;

    @FXML
    private ListView<String> reviewsListView;

    @FXML
    private Button backToMainMenuButton;

    public void seeReviews(String courseName) {
        List<String> reviews = getReviews(courseName);
        if (reviews.isEmpty()) {
            System.out.println("There are no reviews for this course.");
        }
        for (String review : reviews) {
            System.out.println(review);
        }
        String course_average = courseAverage(courseName);
        System.out.println("Average Course Rating: " + course_average);
    }

    @FXML
    public void viewReviews() {
        String courseName = courseNameTextField.getText();
        List<String> reviews = courseReview.getReviews(courseName);
        reviewsListView.setItems(FXCollections.observableArrayList(reviews));
        String courseAverage = courseReview.courseAverage(courseName);
        courseAverageLabel.setText("Average Course Rating: " + courseAverage);
    }

    @FXML
    public void backToMainMenu() {
        // Navigate back to the main menu
    }
}