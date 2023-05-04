module hw7.coursereview.nd2pvz.ogf9uhy.jmj7db.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens edu.virginia.cs.gui to javafx.fxml;
    exports edu.virginia.cs.gui;
    exports edu.virginia.cs.data;
}