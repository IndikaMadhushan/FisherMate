package org.example.fishermatenew.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.fishermatenew.models.HistoryRecord;
import org.example.fishermatenew.dao.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    private ObservableList<HistoryRecord> historyList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colCrew.setCellValueFactory(new PropertyValueFactory<>("crewMembers"));

        loadHistoryData();
    }

    public void loadHistoryData() {
        historyList.clear();
        String sql = "SELECT date, time, crewMembers, location FROM history";

        try {
            DBconnection dBconnection = new DBconnection();
            Connection conn = dBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                historyList.add(new HistoryRecord(
                        rs.getString("date"),
                        rs.getString("location"),
                        rs.getString("time"),
                        rs.getInt("crewMembers")
                ));
            }
            historyTable.setItems(historyList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
