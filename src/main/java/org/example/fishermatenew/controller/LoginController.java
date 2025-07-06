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
import javafx.scene.image.Image;
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

import static org.example.fishermatenew.dao.getData.role;

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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/fishermatenew/Admin.fxml")));
        //stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //CATCHING THE PREVOIUS STAGE AND CLOSE IT( log IN)
        Stage stage1 = (Stage)btnlogin.getScene().getWindow();
        stage1.close();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/org/example/fishermatenew/Images/login.png")));
        stage.setResizable(false);
        stage.setTitle("FiserMate");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void userpg(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/fishermatenew/User.fxml")));
        //stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //CATCHING THE PREVOIUS STAGE AND CLOSE IT( log IN)
        Stage stage1 = (Stage)btnlogin.getScene().getWindow();
        stage1.close();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/org/example/fishermatenew/Images/login.png")));
        stage.setTitle("FiserMate");

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void validateLogin(ActionEvent event) {
        DBconnection connectNow = new DBconnection();
        Connection connectDB = connectNow.getConnection();

        // Correct SQL query
        String verifyLogin = "SELECT role FROM login WHERE username = ? AND password = ?";

        try {
            Encryptor encryptor = new Encryptor();
            String a = password.getText();
            String encryptedPassword = encryptor.encryptString(a);

            PreparedStatement pstmt = connectDB.prepareStatement(verifyLogin);
            pstmt.setString(1, username.getText());
            pstmt.setString(2, encryptedPassword);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String u1 = username.getText();
                String u2 = rs.getString("role"); // Retrieve role as a string

                getData.username = u1;
                getData.role = u2;

                // Role-based navigation
                if ("ADMIN".equalsIgnoreCase(u2)) {
                    adminpg(event);
                } else if ("USER".equalsIgnoreCase(u2)) {
                    userpg(event);
                } else {
                    txterror.setText("Invalid role. Access denied.");
                }

                System.out.println("Logged in as: " + u1 + " with role: " + u2);

            } else {
                txterror.setText("Invalid login. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
