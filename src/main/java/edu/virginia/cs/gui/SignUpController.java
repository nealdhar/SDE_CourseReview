package edu.virginia.cs.gui;

import edu.virginia.cs.data.CourseReviewImplementation;
import edu.virginia.cs.data.DataBaseManagerImpl;
import edu.virginia.cs.data.DatabaseManager;
import edu.virginia.cs.data.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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
public class SignUpController implements Initializable{
    CourseReviewImplementation courseReview = new CourseReviewImplementation();
    @FXML
    protected Button sign_up_button;
    @FXML
    protected TextField signup_name;
    @FXML
    protected PasswordField password1;
    @FXML
    protected PasswordField password2;
    @FXML
    protected Label signUpErrorLabel;
    @FXML
    protected ActionEvent event;
    @FXML
    protected Button login_button;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sign_up_button.setOnAction(event -> {
            try {
                executeSignUp(event);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            });
        login_button.setOnAction((event) -> {
            navigateToLogIn();
        });
    }
    public void executeSignUp(ActionEvent e) throws IOException {
        createNewUser(signup_name, password1, password2);
    }
    @FXML
    public void createNewUser(TextField signup_name, TextField password1, TextField password2) throws IOException {
        String userName = signup_name.getText();
        String password = password1.getText();
        String confirmPassword = password2.getText();
        if (userName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }
        courseReview.connectDatabase();
        String createUser = courseReview.createUser(userName, password, confirmPassword);
        if (createUser.equals("Username already found in database")) {
            System.out.println("Username already found.");
            signUpErrorLabel.setText("Username already in database, please try logging in.");
        }
        if (createUser.equals("Passwords do not match")) {
            signUpErrorLabel.setText("Passwords do not match.");
        }
        else if (createUser.equals("User created successfully")) {
            navigateToMainMenu();
        }
        courseReview.closeDatabase();
    }
    @FXML
    public void navigateToMainMenu() {
        String loginName = signup_name.getText();
        UserName userName = UserName.getInstance();
        userName.setUsername(loginName);
        try {
            Pane root = FXMLLoader.load(CourseReviewController.class.getResource("/edu/virginia/cs/gui/main_menu_view.fxml"));
            Stage stage = (Stage) sign_up_button.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void navigateToLogIn() {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("/edu/virginia/cs/gui/login-view.fxml"));
            Stage stage = (Stage) login_button.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
