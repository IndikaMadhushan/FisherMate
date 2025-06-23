package org.example.fishermatenew.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.fishermatenew.HelloApplication;
import org.example.fishermatenew.config.Encryptor;
import org.example.fishermatenew.dao.DBconnection;
import org.example.fishermatenew.dao.getData;
import org.w3c.dom.Text;

import java.awt.event.MouseEvent;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserController {

    @FXML
    private AnchorPane adminpane;

    @FXML
    private Button boatrides;

    @FXML
    private Button btnclose;

    @FXML
    private Button btnlogout;

    @FXML
    private Button history;

    @FXML
    private AnchorPane historypane;

    @FXML
    private AnchorPane ridespane;

    @FXML
    private AnchorPane pwdpane;


    @FXML
    private Button changepwd;

    @FXML
    private Label username;

    @FXML
    private Button btnreset;

    @FXML
    private Button btnupdate;

    @FXML
    private Text txtconfirm;

    @FXML
    private TextField txtconfirmpwd;

    @FXML
    private Text txtnew;

    @FXML
    private TextField txtnewpwd;

    @FXML
    void update(ActionEvent event) {
        String newPassword = txtnewpwd.getText();
        String confirmPassword = txtconfirmpwd.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in both fields.");
            alert.showAndWait();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Mismatch");
            alert.setHeaderText(null);
            alert.setContentText("New password and confirm password do not match.");
            alert.showAndWait();
            return;
        }

        // Here you would typically update the password in the database
        // For demonstration, we will just show a success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Password updated successfully!");
        alert.showAndWait();

        txtconfirmpwd.clear();
        txtnewpwd.clear();

    }

    @FXML
    void close(MouseEvent event) {

        txtconfirmpwd.setText("");
        txtnewpwd.setText("");

    }

    @FXML
    public void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to logout?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            onclicklogout(); // Redirect to the login page
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close(); // Close the current admin page
        } else  {
            System.out.println("Logout cancelled");

        }
    }


    @FXML
    public void switchform(ActionEvent event){

        if(event.getSource()== changepwd){
            pwdpane.setVisible(true);
            historypane.setVisible(false);
            ridespane.setVisible(false);

        } else if (event.getSource() == history) {
            pwdpane.setVisible(false);
            historypane.setVisible(true);
            ridespane.setVisible(false);

        }else if (event.getSource() == boatrides) {
            pwdpane.setVisible(false);
            historypane.setVisible(false);
            ridespane.setVisible(true);

        }
    }


    public void onclicklogout(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
            Parent root = fxmlLoader.load(); // Load the FXML and get the Parent object
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.TRANSPARENT);
            registerStage.setScene(new Scene(root, 300, 500)); // Pass the Parent object to the Scene
            registerStage.setTitle("Registration Page");
            registerStage.setResizable(false);
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
