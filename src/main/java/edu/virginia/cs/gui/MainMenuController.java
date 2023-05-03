package edu.virginia.cs.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button submit_review_button;
    @FXML
    private Button view_reviews_button;
    @FXML
    private Button logout_button;

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