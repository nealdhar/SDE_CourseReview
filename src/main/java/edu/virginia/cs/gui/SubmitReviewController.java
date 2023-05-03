package edu.virginia.cs.gui;

import edu.virginia.cs.data.CourseReviewImplementation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SubmitReviewController {

    CourseReviewImplementation courseReview = new CourseReviewImplementation();

    @FXML
    private TextField courseNameTextField;

    @FXML
    private TextField messageTextField;

    @FXML
    private TextField ratingTextField;

    @FXML
    private Button submitReviewButton;

    @FXML
    private Button backToMainMenuButton;

    @FXML
    private Label submitReviewStatusLabel;

    @FXML
    public void submitReview() {
        String courseName = courseNameTextField.getText();
        String message = messageTextField.getText();
        String rating = ratingTextField.getText();

        String[] splitCourseName = courseName.split(" ");
        if (splitCourseName.length == 2) {
            String department = splitCourseName[0];
            String catalogNum = splitCourseName[1];
            courseReview.submitReview("userName", department, catalogNum, message, rating);
            submitReviewStatusLabel.setText("Review submitted!");
        } else {
            submitReviewStatusLabel.setText("Invalid course name format.");
        }
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