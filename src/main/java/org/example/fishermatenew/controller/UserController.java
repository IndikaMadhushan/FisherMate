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
import javafx.util.Callback;
import org.example.fishermatenew.HelloApplication;
import org.example.fishermatenew.config.Encryptor;
import org.example.fishermatenew.dao.DBconnection;
import org.example.fishermatenew.dao.getData;
import javafx.scene.text.Text;
import org.example.fishermatenew.models.DateTimeIntergration;
import org.example.fishermatenew.models.FinalDecision;
import org.example.fishermatenew.models.Inputs;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserController implements Initializable {
    @FXML
    private TextArea iDcrews;

    @FXML
    private TextArea iDmaxdays;

    @FXML
    private Button iDenter;

    @FXML
    private Button iDreset;

    @FXML
    private TextArea iDresults;

    @FXML
    private ChoiceBox<String> iDlocation;

    @FXML
    private DatePicker iDdate;

    @FXML
    private ChoiceBox<String> iDtime;

    @FXML
    private Label idCrewMessage;

    @FXML
    private Label idMaxDayMessage;

    @FXML
    private Label idMainMessage;

    @FXML
    private AnchorPane adminpane;

    @FXML
    private Button boatrides;

    @FXML
    private Button btnclose;

    @FXML
    private Button btnlogout;

    @FXML
    private Button btnreset;

    @FXML
    private Button btnupdate;

    @FXML
    private Button changepwd;

    @FXML
    private Button history;

    @FXML
    private AnchorPane historypane;

    @FXML
    private AnchorPane pwdpane;

    @FXML
    private AnchorPane ridespane;

    @FXML
    private Label lblconfirm;

    @FXML
    private PasswordField txtconfirmpwd;

    @FXML
    private Text txtnew;

    @FXML
    private PasswordField txtnewpwd;

    @FXML
    private Label username;
    @FXML
    private AnchorPane registerform;
    // In UserController.java

    @FXML
    void update(ActionEvent event) throws NoSuchAlgorithmException {
        Encryptor encryptor = new Encryptor();
        String newPassword = txtnewpwd.getText();
        String confirmPassword = txtconfirmpwd.getText();

        // Password strength validation
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!newPassword.matches(passwordPattern)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Weak Password");
            alert.setHeaderText(null);
            alert.setContentText("Password must be at least 8 characters long and include uppercase, lowercase, digit, and special character.");
            alert.showAndWait();
            return;
        }

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
        String encryptedPassword = encryptor.encryptString(newPassword);


        DBconnection connectNow = new DBconnection();
        Connection connectDB = connectNow.getConnection();
        String updateQuery = "UPDATE login SET password = ? WHERE username = ?";

        try (PreparedStatement pstmt = connectDB.prepareStatement(updateQuery)) {
            pstmt.setString(1, encryptedPassword);
            pstmt.setString(2, getData.username);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Password updated successfully!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update password. User not found.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while updating the password.");
            alert.showAndWait();
        }

        txtconfirmpwd.clear();
        txtnewpwd.clear();

    }

    public void close(ActionEvent event){
        Stage stage = (Stage) btnclose.getScene().getWindow();
        stage.close();
    }
    @FXML
    void reset(ActionEvent event) {
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
    /*public void handleEnterButton() {
        String location = iDlocation.getValue();
        LocalDate date = iDdate.getValue();
        String time = iDtime.getValue();
        String crewsText = iDcrews.getText().trim();
        String maxDaysText = iDmaxdays.getText().trim();

        // Validate all required fields are filled
        if (location == null || date == null || time == null || crewsText.isEmpty() || maxDaysText.isEmpty()) {
            idMainMessage.setText("❌ Please fill in all the fields.");
            return;
        }

        int crews, maxDays;

        // Validate crews input
        try {
            crews = Integer.parseInt(crewsText);
            if (crews < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            iDcrews.clear();
            idCrewMessage.setText("❌ Enter a valid number (0 or more) for crew members.");
            return;
        }

        // Validate maxDays input
        try {
            maxDays = Integer.parseInt(maxDaysText);
            if (maxDays < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            iDmaxdays.clear();
            idMaxDayMessage.setText("❌ Enter a valid number (0 or more) for max days.");
            return;
        }

        if(date.equals(LocalDate.now())){
            if(Integer.parseInt(maxDaysText) > 3){
                idMaxDayMessage.setText("❌ Max days should be equal to or less than 3 days.");
                return;
            }
        }
        if(date.equals(LocalDate.now().plusDays(1))){
            if(Integer.parseInt(maxDaysText) > 2){
                idMaxDayMessage.setText("❌ Max days should be equal to or less than 2 days.");
                return;
            }
        }
        if(date.equals(LocalDate.now().plusDays(2))){
            if(Integer.parseInt(maxDaysText) > 1){
                idMaxDayMessage.setText("❌ Max days should be equal to or less than 1 days.");
                return;
            }
        }

        // If all inputs are valid, process them
        idMaxDayMessage.setText("");
        idCrewMessage.setText("");
        idMainMessage.setText("");

        Inputs inputData = new Inputs();
        inputData.setLocation(location);
        DateTimeIntergration.datetimeintegration(date.toString(), time, inputData);
        inputData.setNoOfCrewMembers(crews);
        inputData.setMaxDays(maxDays);

        String result = FinalDecision.finalDecision(inputData);
        iDresults.setText(result);
    }


    @FXML
    public void handleResetButton() {
        iDcrews.clear();
        iDmaxdays.clear();
        iDresults.clear();
        iDlocation.setValue(null);
        iDdate.setValue(null);
        iDtime.setValue(null);
        idMaxDayMessage.setText("");
        idCrewMessage.setText("");
        idMainMessage.setText("");
    }
*/

    public void onclicklogout(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
            Parent root = fxmlLoader.load(); // Load the FXML and get the Parent object
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNIFIED);
            registerStage.setScene(new Scene(root, 300, 500)); // Pass the Parent object to the Scene
            registerStage.getIcons().add(new Image(getClass().getResourceAsStream("/org/example/fishermatenew/Images/login.png")));

            registerStage.setTitle("FisherMate");
            registerStage.setResizable(false);
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        displaUsername();
       /* iDlocation.getItems().addAll(
                "Galle", "Matara", "Hambantota", "Trincomalee", "Jaffna",
                "Negombo", "Colombo", "Batticaloa", "Kalpitiya"
        );

        iDtime.getItems().addAll(
                "00:00:00", "03:00:00", "06:00:00", "09:00:00",
                "12:00:00", "15:00:00", "18:00:00", "21:00:00"
        );

        iDdate.setDayCellFactory(getDateCellFactory());
        // Prevent typing in the DatePicker's editor
        iDdate.getEditor().setDisable(true);
        iDdate.getEditor().setOpacity(1); // Keep it visually visible (not greyed out)


        // Validate selected date
        */
    }
    public void switchform(ActionEvent event){
          if (event.getSource() == history) {
            registerform.setVisible(false);
            historypane.setVisible(true);
            ridespane.setVisible(false);
            pwdpane.setVisible(false);

        } else if (event.getSource() == boatrides) {
            registerform.setVisible(false);
            historypane.setVisible(false);
            ridespane.setVisible(true);
            pwdpane.setVisible(false);

            // Load the interface.fxml into ridespane
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fishermatenew/interface.fxml"));
                Node interfaceView = loader.load(); // This loads the FXML content

                // Clear previous content and add the new FXML content
                ridespane.getChildren().clear();
                ridespane.getChildren().add(interfaceView);

                // Optional: Anchor the loaded node to fill the ridespane
                AnchorPane.setTopAnchor(interfaceView, 0.0);
                AnchorPane.setBottomAnchor(interfaceView, 0.0);
                AnchorPane.setLeftAnchor(interfaceView, 0.0);
                AnchorPane.setRightAnchor(interfaceView, 0.0);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
          else if (event.getSource()== changepwd) {
              pwdpane.setVisible(true);
              historypane.setVisible(false);
              ridespane.setVisible(false);
          }
    }
    /*private Callback<DatePicker, DateCell> getDateCellFactory() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null && !empty &&
                        (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusDays(2)))) {

                    setDisable(true); // Fully disable the date (prevents selection)


                }
            }
        };
    }*/
    public void displaUsername() {
        if (getData.username != null && !getData.username.isEmpty()) {
            System.out.println("Username found: " + getData.username); // Debug log
            username.setText(getData.username); // Display the username
        } else {
            System.out.println("No username found in getData");
        }
    }

}
