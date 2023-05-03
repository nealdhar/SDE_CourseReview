package edu.virginia.cs.gui;

import edu.virginia.cs.data.CourseReviewImplementation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
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
        List<String> reviews = courseReview.getReviews(courseName);
        if (reviews.isEmpty()) {
            System.out.println("There are no reviews for this course.");
        }
        for (String review : reviews) {
            System.out.println(review);
        }
        String course_average = courseReview.courseAverage(courseName);
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
        try {
            Pane root = FXMLLoader.load(getClass().getResource("/edu/virginia/cs/gui/main_menu-view.fxml"));
            Stage stage = (Stage) backToMainMenuButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}