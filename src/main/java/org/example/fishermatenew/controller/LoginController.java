package org.example.fishermatenew.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.fishermatenew.dao.DBconnection;
import org.example.fishermatenew.dao.getData;
import org.example.fishermatenew.config.Encryptor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class LoginController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button btnlogin;

    @FXML
    private PasswordField password;

    @FXML
    private Label txterror;

    @FXML
    private Button resetbtn;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField username;

    @FXML
    void reset(ActionEvent event) {

    }
    private Stage stage;
    private Scene scene;

    @FXML
    void userLoging(ActionEvent event) {
        if (!username.getText().isEmpty() && !password.getText().isEmpty()) {

            validateLogin(event);

        } else {
            txterror.setText("Please enter username and password");
        }

    }
    public void adminpg(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/fishermatenew/hello-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void userpg(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/fishermatenew/hello-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void validateLogin(ActionEvent event) {
        DBconnection connectNow = new DBconnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT COUNT(1) FROM login WHERE username = ? AND password = ?";

        try {
            Encryptor encryptor = new Encryptor();
            String a = password.getText();
            String encryptedPassword = encryptor.encryptString(a);

            PreparedStatement pstmt = connectDB.prepareStatement(verifyLogin);
            pstmt.setString(1, username.getText());
            pstmt.setString(2, encryptedPassword);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 1) {
                String u1 = username.getText();
                getData.username = u1;
                if (u1.equals("admin") && a.equals("admin")) {
                    adminpg(event);
                } else {
                    userpg(event);
                }
            } else {
                txterror.setText("Invalid login. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
