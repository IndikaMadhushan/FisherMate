package org.example.fishermatenew.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.fishermatenew.models.HistoryRecord;

public class HistoryController {

    @FXML
    private TableView<HistoryRecord> historyTable;

    @FXML
    private TableColumn<HistoryRecord, String> colDate;

    @FXML
    private TableColumn<HistoryRecord, String> colLocation;

    @FXML
    private TableColumn<HistoryRecord, String> colTime;

    @FXML
    private TableColumn<HistoryRecord, Integer> colCrew;

    @FXML
    public void initialize() {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colCrew.setCellValueFactory(new PropertyValueFactory<>("crewMembers"));

        // Example data
        ObservableList<HistoryRecord> data = FXCollections.observableArrayList(
                new HistoryRecord("2025-07-10", "Galle", "08:00 AM", 4),
                new HistoryRecord("2025-07-11", "Matara", "10:30 AM", 3)
        );

        historyTable.setItems(data);
    }
}
