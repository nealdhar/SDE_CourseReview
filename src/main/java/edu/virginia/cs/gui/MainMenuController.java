package edu.virginia.cs.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    protected Button submit_review_button;
    @FXML
    protected Button view_reviews_button;
    @FXML
    protected Button logout_button;
    @FXML
    protected Label welcome_label;
    public void initialize(URL location, ResourceBundle resources) {
        UserName userName = UserName.getInstance();
        String username = userName.getUsername();
        welcome_label.setText("Welcome, " + username + "!");
        submit_review_button.setOnAction((event) -> {
            navigateToSubmitReview();
        });
        view_reviews_button.setOnAction((event) -> {
            navigateToSeeReviews();
        });
        logout_button.setOnAction((event) -> {
            navigateToLogout();
        });
    }
    @FXML
    public void navigateToSubmitReview() {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("/edu/virginia/cs/gui/SubmitReview.fxml"));
            Stage stage = (Stage) submit_review_button.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void navigateToSeeReviews() {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("/edu/virginia/cs/gui/SeeReviews.fxml"));
            Stage stage = (Stage) view_reviews_button.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void navigateToLogout() {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("/edu/virginia/cs/gui/login-view.fxml"));
            Stage stage = (Stage) logout_button.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}