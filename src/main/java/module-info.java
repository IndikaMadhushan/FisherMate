module org.example.fishermatenew {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.fishermatenew to javafx.fxml;
    exports org.example.fishermatenew;
}