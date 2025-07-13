package org.example.fishermatenew.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResultsController {

    @FXML
    private TextArea iDresults;

    @FXML
    private Button DownloadPDF;
    @FXML
    private Button TryAgain;
    private MainController mainController;
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }
    public void initialize() {
        DownloadPDF.setOnAction(event -> downloadPdf());
        TryAgain.setOnAction(event -> {
            if (mainController != null) {
                mainController.showInputPaneAgain();
            }
        });
    }

    public void setResultText(String result) {
        iDresults.setText(result);
    }

    private void downloadPdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        fileChooser.setInitialFileName("FisherMate-Results.pdf");

        // Get the current window (stage)
        Stage stage = (Stage) DownloadPDF.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                document.add(new Paragraph(iDresults.getText()));
                document.close();
                System.out.println("PDF saved to: " + file.getAbsolutePath());
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
