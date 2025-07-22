package org.example.fishermatenew.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.fishermatenew.dao.DBconnection;
import org.example.fishermatenew.models.DateTimeIntergration;
import org.example.fishermatenew.models.FinalDecision;
import org.example.fishermatenew.models.Inputs;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainController implements Initializable {

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
    private AnchorPane afterEnter;

    @FXML
    private AnchorPane beforeEnter;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        beforeEnter.setVisible(true);
        afterEnter.setVisible(false);
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


        // Validate selected date

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


    @FXML
    public void handleEnterButton() {
        String location = iDlocation.getValue();
        LocalDate date = iDdate.getValue();
        String time = iDtime.getValue();
        String crewsText = iDcrews.getText().trim();
        String maxDaysText = iDmaxdays.getText().trim();

        if (location == null || date == null || time == null || crewsText.isEmpty() || maxDaysText.isEmpty()) {
            idMainMessage.setText("❌ Please fill in all the fields.");
            return;
        }

        int crews, maxDays;

        try {
            crews = Integer.parseInt(crewsText);
            if (crews < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            iDcrews.clear();
            idCrewMessage.setText("❌ Enter a valid number (0 or more) for crew members.");
            return;
        }

        try {
            maxDays = Integer.parseInt(maxDaysText);
            if (maxDays < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            iDmaxdays.clear();
            idMaxDayMessage.setText("❌ Enter a valid number (0 or more) for max days.");
            return;
        }

        if (date.equals(LocalDate.now()) && maxDays > 3) {
            idMaxDayMessage.setText("❌ Max days should be equal to or less than 3 days.");
            return;
        }
        if (date.equals(LocalDate.now().plusDays(1)) && maxDays > 2) {
            idMaxDayMessage.setText("❌ Max days should be equal to or less than 2 days.");
            return;
        }
        if (date.equals(LocalDate.now().plusDays(2)) && maxDays > 1) {
            idMaxDayMessage.setText("❌ Max days should be equal to or less than 1 day.");
            return;
        }

        // Prepare input
        Inputs inputData = new Inputs();
        inputData.setLocation(location);
        DateTimeIntergration.datetimeintegration(date.toString(), time, inputData);
        inputData.setNoOfCrewMembers(crews);
        inputData.setMaxDays(maxDays);

        saveToHistory(date, time, location, crews);

        // Get final result
        String result = FinalDecision.finalDecision(inputData);
        beforeEnter.setVisible(false);
        afterEnter.setVisible(true);
        // 🔄 Load results.fxml and send the result
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fishermatenew/Results.fxml"));
            Node interfaceView = loader.load(); // This loads the FXML content

            // Clear previous content and add the new FXML content
            afterEnter.getChildren().clear();
            afterEnter.getChildren().add(interfaceView);

            // Optional: Anchor the loaded node to fill the ridespane
            AnchorPane.setTopAnchor(interfaceView, 0.0);
            AnchorPane.setBottomAnchor(interfaceView, 0.0);
            AnchorPane.setLeftAnchor(interfaceView, 0.0);
            AnchorPane.setRightAnchor(interfaceView, 0.0);


            // Get the controller of results.fxml
            ResultsController resultsController = loader.getController();
            resultsController.setResultText(result);
            resultsController.setMainController(this);   // 👉 pass the result

            // Switch to results scene
            //Stage stage = (Stage) iDlocation.getScene().getWindow(); // or any other control's scene


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showInputPaneAgain() {
        afterEnter.setVisible(false);
        beforeEnter.setVisible(true);
        handleResetButton(); // optional: reset fields
    }

    public void saveToHistory(LocalDate date, String time, String location, int crews) {
        // Combine date and time into Timestamp
        String dateTimeString = date.toString() + " " + time; // "2025-07-21 03:00:00"
        Timestamp dateTime;
        try {
            dateTime = Timestamp.valueOf(dateTimeString);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid time format. Use HH:mm:ss (e.g., 03:00:00)");
            return;
        }

        String sql = "INSERT INTO history (date, location, time, crewMembers) VALUES (?, ?, ?, ?)";

        try {
            DBconnection db = new DBconnection();
            Connection conn = db.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setTimestamp(1, dateTime); // for 'date' column
            pstmt.setString(2, location);
            pstmt.setTimestamp(3, dateTime); // for 'time' column
            pstmt.setInt(4, crews);

            pstmt.executeUpdate();
            System.out.println("✅ Record saved to history table.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
