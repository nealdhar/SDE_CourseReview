package edu.virginia.cs.gui;

import edu.virginia.cs.data.CourseReviewImplementation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class CourseReviewController implements Initializable {

    CourseReviewImplementation courseReview = new CourseReviewImplementation();
    @FXML
    protected TextField login_name;
    @FXML
    protected PasswordField login_password;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Other things to initialize GUI
        courseReview.connectDatabase();
        courseReview.createTables();
    }

    @FXML
    public void login(TextField login_name, PasswordField login_password) {
        String userName = login_name.getText();
        String password = login_password.getText();
        String login = courseReview.login(userName, password);
        if (login.equals("Username not found")) {
            // Show message in GUI that username is not found, please create new user.
        }
        if (login.equals("Password/username incorrect")) {
            // Show message in GUI that password/and or username is incorrect. Try again
        }
        if (login.equals("Login successful")) {
            // Login is successful, immediately show main menu
        }
    }

}
