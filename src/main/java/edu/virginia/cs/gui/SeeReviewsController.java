package edu.virginia.cs.gui;

import edu.virginia.cs.data.CourseReviewImplementation;
import edu.virginia.cs.data.DataBaseManagerImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SeeReviewsController implements Initializable {
    CourseReviewImplementation courseReview = new CourseReviewImplementation();
    DataBaseManagerImpl dataBaseManager = new DataBaseManagerImpl();
    @FXML
    protected TextField courseNameTextField;
    @FXML
    protected Button viewReviewsButton;
    @FXML
    protected Label courseAverageLabel;
    @FXML
    protected ListView<String> reviewsListView;
    @FXML
    protected Button backToMainMenuButton;
    @FXML

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewReviewsButton.setOnAction((event) -> {
            executeSeeReviews(event);
        });
        backToMainMenuButton.setOnAction((event) -> {
            backToMainMenu();
        });
    }
    public void executeSeeReviews(ActionEvent e) {
        seeReviews(courseNameTextField);
    }
    public void seeReviews(TextField courseNameTextField) {
        String courseName = courseNameTextField.getText();
        String[] splitCourseName = courseName.split(" ");
        int count = splitCourseName.length;
        if (count < 2) {
            courseAverageLabel.setText("Invalid course name format. Please enter a valid course.");
            return;
        }
        String department = splitCourseName[0];
        String catalogNumber = splitCourseName[1];
        if (department.length() > 4) {
            courseAverageLabel.setText("Invalid course name format. Please enter a valid course.");
            return;
        }
        if (catalogNumber.length() != 4) {
            courseAverageLabel.setText("Invalid course name format. Please enter a valid course.");
            return;
        }
        for (char c : catalogNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                courseAverageLabel.setText("Invalid course name format. Please enter a valid course.");
                return;
            }
        }
        String course = department + ' ' + catalogNumber;
        List<String> reviews = courseReview.getReviews(courseName);
        if (!dataBaseManager.courseExists(department, Integer.parseInt(catalogNumber))) {
            courseAverageLabel.setTextFill(Color.web("#650101"));
            courseAverageLabel.setText("There are currently no reviews for this course. Return to Main Menu or search for another course.");
            return;
        }
        ObservableList<String> observableList = FXCollections.observableArrayList(reviews);
        reviewsListView.setItems(observableList);
        String course_average = courseReview.courseAverage(courseName);
        courseAverageLabel.setTextFill(Color.web("#2840b5"));
        courseAverageLabel.setText("Average Course Rating: " + course_average);
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