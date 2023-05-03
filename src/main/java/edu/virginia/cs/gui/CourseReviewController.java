package edu.virginia.cs.gui;

import edu.virginia.cs.data.CourseReviewImplementation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseReviewController {

    CourseReviewImplementation courseReview = new CourseReviewImplementation();
    @FXML
    protected TextField login_name;
    @FXML
    protected PasswordField login_password;
    @FXML
    protected Label loginStatusLabel;

    @FXML
    public void login(TextField login_name, PasswordField login_password) {
        String userName = login_name.getText();
        String password = login_password.getText();
        String login = courseReview.login(userName, password);
        if (login.equals("Username not found")) {
            loginStatusLabel.setText("Username not found, please create a new user.");
        }
        if (login.equals("Password/username incorrect")) {
            loginStatusLabel.setText("Password and/or username is incorrect. Try again.");
        }
        if (login.equals("Login successful")) {
            loginStatusLabel.setText(""); // Clear the status label
            navigateToMainMenu();
        }
    }

    private void navigateToMainMenu() {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("/edu/virginia/cs/gui/main_menu-view.fxml"));
            Stage stage = (Stage) login_name.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}