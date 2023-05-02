package edu.virginia.cs.gui;

import edu.virginia.cs.data.CourseReviewImplementation;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CourseReviewController implements Initializable {

    CourseReviewImplementation courseReview = new CourseReviewImplementation();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Other things to initialize GUI
        courseReview.connectDatabase();
        courseReview.createTables();
    }


}
