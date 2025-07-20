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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.example.fishermatenew.HelloApplication;
import org.example.fishermatenew.config.Encryptor;
import org.example.fishermatenew.dao.DBconnection;
import org.example.fishermatenew.dao.getData;
import org.example.fishermatenew.models.DateTimeIntergration;
import org.example.fishermatenew.models.FinalDecision;
import org.example.fishermatenew.models.Inputs;

import java.io.File;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane histroypage;
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
    private Button addnewuser;

    @FXML
    private AnchorPane adminpane;

    @FXML
    private Button boatrides;

    @FXML
    private Button btnclose;

    @FXML
    private Button btnlogout;

    @FXML
    private Button btnregister;

    @FXML
    private Button btnregister1;

    @FXML
    private PasswordField confirmpassword;

    @FXML
    private Button history;

    @FXML
    private AnchorPane historypane;

    @FXML
    private Label lblconfirmpassword;

    @FXML
    private Label lblfname;

    @FXML
    private Label lblfname1;

    @FXML
    private Label lblfname2;

    @FXML
    private Label lblfname3;

    @FXML
    private Label lblfname4;

    @FXML
    private Label lbllname;

    @FXML
    private Label lblmismatch;

    @FXML
    private Label lblpassword;

    @FXML
    private Label lbluname;

    @FXML
    private ImageView newUserImage;

    @FXML
    private PasswordField password1;

    @FXML
    private AnchorPane registerform;

    @FXML
    private AnchorPane ridespane;

    @FXML
    private TextField txtboat;

    @FXML
    private TextField txtcrew;

    @FXML
    private TextField txtfname;

    @FXML
    private TextField txtlicense;

    @FXML
    private TextField txtlname;

    @FXML
    private TextField txtport;

    @FXML
    private TextField txtuname;

    @FXML
    private Label username;

    DBconnection conn = new DBconnection();


    //    public void initialize(URL url, ResourceBundle resourceBundle){
//
//    }resourceBundle
    @FXML
    public void handleEnterButton() {
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
    Encryptor encryptor = new Encryptor();
    private Image image;
    private PreparedStatement PreparedStatement;

    public void close(ActionEvent event){
        Stage stage = (Stage) btnclose.getScene().getWindow();
        stage.close();
    }

    public void onClickRegister() throws NoSuchAlgorithmException {
        if(password1.getText().equals(confirmpassword.getText())){
            System.out.println("Password Matched");
            registerUser();

            // Show the Alert

        }else {
            lblmismatch.setText("Password Mismatch");
            //System.out.println("Password Mismatch");
        }
    }

//        public  void registerUser(){
//            DBconnection conn = new DBconnection();
//            Connection connectDB = conn.getConnection();
//
//            String firstname = txtfname.getText();
//            String lastname = txtlname.getText();
//            String username = txtuname.getText();
//            String password = password1.getText();
//            String uri = getData.path;
//            uri = uri.replace("\\", "\\\\");
//            String image = newUserImage.getText();
//
//            String insertFields = "INSERT INTO login (firstname, lastname, username, password,image) VALUES ('"+firstname+"','"+lastname+"','"+username+"','"+password+",'"+image+"'')";
//
//            try{
//                Statement stmt = connectDB.createStatement();
//                stmt.executeUpdate(insertFields);
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Registration Successful");
//                alert.setHeaderText(null);
//                alert.setContentText("Your registration was successful!");
//                alert.showAndWait();
//                Platform.exit();//remove stage after successful registration
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//        }

    public void reset(){
        txtfname.setText("");
        txtlname.setText("");
        txtuname.setText("");
        password1.setText("");
        confirmpassword.setText("");
        txtboat.setText("");
        txtcrew.setText("");
        txtport.setText("");
        txtlicense.setText("");
        newUserImage.setImage(null);

    }
    public void registerUser() throws NoSuchAlgorithmException {
//        DBconnection conn = new DBconnection();
        Connection connectDB = conn.getConnection();
        String role = "USER";
        String firstname = txtfname.getText();
        String lastname = txtlname.getText();
        String username = txtuname.getText();
        String boat = txtboat.getText();
        String crew = txtcrew.getText();
        String port = txtport.getText();
        String license = txtlicense.getText();
        String password2 = password1.getText();
        String password = encryptor.encryptString(password2);
        String uri = getData.path.replace("\\", "\\\\");
        // Escape backslashes for SQL


        String insertFields = "INSERT INTO login (firstname, lastname, username, password, image,crew,boat,license,port,role) VALUES (?, ?, ?, ?, ?, ?,?, ?, ?,?)";

        try {
            PreparedStatement pstmt = connectDB.prepareStatement(insertFields);
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.setString(5, uri);
            pstmt.setString(6, license);
            pstmt.setString(7, crew);
            pstmt.setString(8, boat);
            pstmt.setString(9, port);
            pstmt.setString(10, role);

            pstmt.executeUpdate();

            reset();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration Successful");
            alert.setHeaderText(null);
            alert.setContentText("Your registration was successful!");
            alert.showAndWait();

            //Platform.exit(); // Close the application after successful registration
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred during registration. Please try again.");
            alert.showAndWait();
        }
    }


    public void addNew(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Register.fxml"));
            Parent root = fxmlLoader.load(); // Load the FXML and get the Parent object
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 568)); // Pass the Parent object to the Scene
            registerStage.setTitle("Registration Page");
            registerStage.setResizable(false);
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
    public void history(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("history.fxml"));
            Parent root = fxmlLoader.load(); // Load the FXML and get the Parent object
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 568)); // Pass the Parent object to the Scene
            registerStage.setTitle("History Page");
            registerStage.setResizable(false);
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public void importImage(){
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(addnewuser.getScene().getWindow());

        if(file != null){
            getData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(),139, 125, false, true );
            newUserImage.setImage(image);
        }
    }

    //    public void displaUsername() {
//            String sql = "select username from login where username = ?";
//            DBconnection conn = new DBconnection();
//        System.out.println("connected");
//        try{
//            Connection connectDB = conn.getConnection();
//            PreparedStatement pstmt = connectDB.prepareStatement(sql);
//            pstmt.setString(1, username.getText());
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                getData.username = rs.getString("username");
//                username.setText(getData.username);
//            } else {
//                System.out.println("No username found");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
    public void displaUsername() {
        if (getData.username != null && !getData.username.isEmpty()) {
            System.out.println("Username found: " + getData.username); // Debug log
            username.setText(getData.username); // Display the username
        } else {
            System.out.println("No username found in getData");
        }
    }
    public void switchform(ActionEvent event){

        if(event.getSource()== addnewuser){
            registerform.setVisible(true);
            histroypage.setVisible(false);
            ridespane.setVisible(false);

        } else if (event.getSource() == history) {
            registerform.setVisible(false);
            histroypage.setVisible(true);
            ridespane.setVisible(false);

        }else if (event.getSource() == boatrides) {
            registerform.setVisible(false);
            histroypage.setVisible(false);
            ridespane.setVisible(true);

        }
    }

    
    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        iDenter.setOnAction(event -> dataToDB());
        displaUsername();
        iDlocation.getItems().addAll(
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

        //select which pane shows when the admin log in
        registerform.setVisible(true);
        ridespane.setVisible(false);
        histroypage.setVisible(false);

        //set history button click event
        history.setOnAction(event -> onHistoryBtnClick());

    }

    private void onHistoryBtnClick() {
        histroypage.setVisible(true);
        registerform.setVisible(false);
        ridespane.setVisible(false);

    }

    public void dataToDB() {
        String location = iDlocation.getValue();
        LocalDate date = iDdate.getValue();
        String time = iDtime.getValue();
        int crewMembers = Integer.parseInt(iDcrews.getText());
       // int noOfDays = Integer.parseInt(iDmaxdays.getText());

        if (location == null || date == null || time == null || crewMembers <= 0 ) {
            idMainMessage.setText("❌ Please fill in all the fields correctly.");
            return;
        }

        String sql = "INSERT INTO history (date, time, crewMembers, location) VALUES (?, ?, ?, ?)";

        try {
            Connection connection = conn.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, String.valueOf(date));
            pstmt.setString(2, time);
            pstmt.setString(3, String.valueOf(crewMembers));
            pstmt.setString(4, location);
            pstmt.executeUpdate();



            // Optional alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Boat ride added to history!");
            alert.showAndWait();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private Callback<DatePicker, DateCell> getDateCellFactory() {
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
    }
    }


