package org.example.fishermatenew.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.example.fishermatenew.models.DateTimeIntergration;
import org.example.fishermatenew.models.FinalDecision;
import org.example.fishermatenew.models.Inputs;

import java.net.URL;
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
}
