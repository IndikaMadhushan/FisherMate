module org.example.fishermatenew {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.fishermatenew to javafx.fxml;
    exports org.example.fishermatenew;
}