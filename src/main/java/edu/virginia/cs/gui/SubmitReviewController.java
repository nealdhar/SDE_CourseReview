package edu.virginia.cs.gui;

import edu.virginia.cs.data.CourseReviewImplementation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SubmitReviewController implements Initializable{

    CourseReviewImplementation courseReview = new CourseReviewImplementation();
    @FXML
    private TextField courseNameTextField;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private TextField ratingTextField;
    @FXML
    private Button submitReviewButton;
    @FXML
    private Button backToMainMenuButton;
    @FXML
    private Label submitReviewStatusLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageTextArea.setWrapText(true);
        submitReviewButton.setOnAction((event) -> {
            submitReview(courseNameTextField, messageTextArea, ratingTextField);
        });
        backToMainMenuButton.setOnAction((event) -> {
            backToMainMenu();
        });

    }

    @FXML
    public void submitReview(TextField courseNameTextField, TextArea messageTextField, TextField ratingTextField) {
        String courseName = courseNameTextField.getText();
        String message = messageTextField.getText();
        String rating = ratingTextField.getText();
        if (courseName.isEmpty() || message.isEmpty() || rating.isEmpty()) {
            submitReviewStatusLabel.setTextFill(Color.web("#6f0202"));
            submitReviewStatusLabel.setText("Please enter all fields to submit a review.");
            return;
        }
        courseReview.connectDatabase();
        String[] splitCourseName = courseName.split(" ");
        if (splitCourseName.length == 2) {
            String department = splitCourseName[0];
            String catalogNum = splitCourseName[1];
            UserName userName = UserName.getInstance();
            String username = userName.getUsername();
            String submit = courseReview.submitReview(username, department, catalogNum, message, rating);
            if (submit.equals("Review added successfully")) {
                submitReviewStatusLabel.setTextFill(Color.web("#2840b5"));
                submitReviewStatusLabel.setText("Review submitted! You may submit another or return to Main Menu.");
                courseNameTextField.setText("");
                messageTextField.setText("");
                ratingTextField.setText("");
            }
            if (submit.equals("You have already reviewed this course.")) {
                System.out.println("Already reviewed course");
                submitReviewStatusLabel.setTextFill(Color.web("#6f0202"));
                submitReviewStatusLabel.setText("You have already reviewed this course. Please review a different course or return to the Main Menu.");
                courseNameTextField.setText("");
                messageTextField.setText("");
                ratingTextField.setText("");
            }
            if (submit.equals("Invalid rating")) {
                submitReviewStatusLabel.setTextFill(Color.web("#6f0202"));
                submitReviewStatusLabel.setText("Invalid rating. Please rate 1-5.");
                ratingTextField.setText("");
            }
            else if (submit.equals("Invalid course format")) {
                submitReviewStatusLabel.setTextFill(Color.web("#6f0202"));
                submitReviewStatusLabel.setText("Invalid course name format. Please enter a valid course.");
                courseNameTextField.setText("");
            }
        }
        courseReview.closeDatabase();
    }

    @FXML
    public void backToMainMenu() {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("/edu/virginia/cs/gui/main_menu_view.fxml"));
            Stage stage = (Stage) backToMainMenuButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}