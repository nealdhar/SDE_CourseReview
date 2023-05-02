package edu.virginia.cs.data;

import edu.virginia.cs.gui.CourseReviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/edu/virginia/cs/gui/login-view.fxml"));
        Parent parent = fxmlLoader.load();
        CourseReviewController controller = fxmlLoader.getController();
        Scene scene = new Scene(parent);
        stage.setTitle("University of Virginia Course Review");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}