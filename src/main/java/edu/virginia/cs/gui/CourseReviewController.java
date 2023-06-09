package edu.virginia.cs.gui;

import edu.virginia.cs.data.CourseReviewImplementation;
import edu.virginia.cs.data.DataBaseManagerImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseReviewController implements Initializable {
    CourseReviewImplementation courseReview = new CourseReviewImplementation();
    DataBaseManagerImpl dataBaseManager = new DataBaseManagerImpl();
    @FXML
    protected TextField login_name;
    @FXML
    protected PasswordField login_password;
    @FXML
    protected Label loginErrorLabel;
    @FXML
    protected Button login_button;
    @FXML
    protected Button signup_button;
    @FXML
    protected ActionEvent event;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login_button.setOnAction((event) -> {
            try {
                executeLogin(event);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        signup_button.setOnAction((event) -> {
                navigateToSignUp();
        });
    }

    public void executeLogin(ActionEvent e) throws IOException {
        login(login_name, login_password);
    }
    @FXML
    public void login(TextField login_name, PasswordField login_password) throws IOException{
        String userName = login_name.getText();
        String password = login_password.getText();
        if (userName.isEmpty() || password.isEmpty()) {
            return;
        }
        courseReview.connectDatabase();
        String login = courseReview.login(userName, password);
        if (login.equals("Username not found")) {
            loginErrorLabel.setText("Username not found, please create a new user.");
        }
        if (login.equals("Password/username incorrect")) {
            loginErrorLabel.setText("Password and/or username is incorrect. Try again.");
        }
        if (login.equals("Login successful")) {
            navigateToMainMenu();
        }
        courseReview.closeDatabase();
    }

    private void navigateToMainMenu() {
        String loginName = login_name.getText();
        UserName userName = UserName.getInstance();
        userName.setUsername(loginName);

        try {
            Pane root = FXMLLoader.load(CourseReviewController.class.getResource("/edu/virginia/cs/gui/main_menu_view.fxml"));
            Stage stage = (Stage) login_button.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToSignUp() {
        try {
            Pane root = FXMLLoader.load(CourseReviewController.class.getResource("/edu/virginia/cs/gui/signup-view.fxml"));
            Stage stage = (Stage) signup_button.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}