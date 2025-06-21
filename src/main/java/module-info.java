module org.example.fishermatenew {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.fishermatenew.controller to javafx.fxml;
    opens org.example.fishermatenew to javafx.fxml;
    exports org.example.fishermatenew;
    exports org.example.fishermatenew.controller to javafx.fxml;
}