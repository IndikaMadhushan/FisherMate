module org.example.fishermatenew {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.json;
    requires itextpdf;

    opens org.example.fishermatenew.controller to javafx.fxml;
    opens org.example.fishermatenew to javafx.fxml;
    exports org.example.fishermatenew;
    exports org.example.fishermatenew.controller to javafx.fxml;
    exports org.example.fishermatenew.dao;
    opens org.example.fishermatenew.dao to javafx.fxml;
}