package controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;


import org.kordamp.ikonli.javafx.FontIcon;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import stock.gestion.cabinet.medical.App;

import java.io.IOException;

public class mainController implements Initializable {

    @FXML
    private Button ajouterRendezVous;

    @FXML
    private Button consultationBtn;

    @FXML
    private StackPane contentArea;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Label dateDuJour;

    @FXML
    private Button facturationBtn;

    @FXML
    private Button patientBtn;

    @FXML
    private TextField search;

    @FXML
    private FontIcon iconConsultaiton;

    @FXML
    private FontIcon iconDashboard;

    @FXML
    private FontIcon iconFac;

    @FXML
    private FontIcon iconPatient;

    @FXML
    private void fenDashboard() throws IOException {
        Parent dashboardView = App.loadFXML("dashboard");
        contentArea.getChildren().setAll(dashboardView);
        activeMenu(dashboardBtn, iconDashboard);
    }

    @FXML
    private void fenPatient() throws IOException {
        Parent patientView = App.loadFXML("patient");
        contentArea.getChildren().setAll(patientView);
        activeMenu(patientBtn, iconPatient);

    }

    @FXML
    private void fenCosultaition() throws IOException {
        Parent consultaionView = App.loadFXML("consultation");
        contentArea.getChildren().setAll(consultaionView);
        activeMenu(consultationBtn, iconConsultaiton);
    }

    @FXML
    private void fenFacturation() throws IOException {
        Parent facView = App.loadFXML("facturation");
        contentArea.getChildren().setAll(facView);
        activeMenu(facturationBtn, iconFac);
    }

    @FXML 
    private void fenRendezVous() throws IOException {
        Parent rendezVous = App.loadFXML("rendezVous");
        contentArea.getChildren().setAll(rendezVous);
    }

    private Button latestBtn;
    private FontIcon latestIcon;
    public void activeMenu(Button btn, FontIcon icon){
        btn.setTextFill(Color.web("#0F766E"));
        icon.setIconColor(Color.web("#0F766E")); 
        if (this.latestBtn != null && this.latestIcon != null && this.latestBtn != btn){
            this.latestBtn.setTextFill(Color.web("#3e4947"));
            this.latestIcon.setIconColor(Color.web("#3e4947")); 
        }
        this.latestBtn = btn;
        this.latestIcon = icon;
    }

    public static Alert showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(type.name());
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }

    public static  boolean confirmerAction(String message) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText(null);
        confirmation.setContentText(message);

        ButtonType btnOui = new ButtonType("Oui");
        ButtonType btnNon = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmation.getButtonTypes().setAll(btnOui, btnNon);

        return confirmation.showAndWait().filter(btn -> btn == btnOui).isPresent();
    }

    public static void Uppercase(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            field.setText(newValue.toUpperCase());
        });
    }

    public static void Capitalize(TextField... fields) {
        for (TextField field : fields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return;
                }
                String formatted = newValue.substring(0, 1).toUpperCase() + newValue.substring(1).toLowerCase();

                if (!newValue.equals(formatted)) {
                    field.setText(formatted);
                }

            });
        }
    }

    public static void testDate(DatePicker field) {
        field.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date != null && date.isAfter(LocalDate.now())) {

                    setDisable(true);

                    setStyle("-fx-background-color: #94A3B8;");
                }
            }
        });
    }

    public static void tesAntDate(DatePicker field) {
        field.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date != null && date.isBefore(LocalDate.now())) {

                    setDisable(true);

                    setStyle("-fx-background-color: #94A3B8;");
                }
            }
        });
    }

    public static void viderChamps(DatePicker dateField, Control... fields) {
        if (dateField != null) {
            dateField.setValue(null);
        }

        for (Control field : fields) {
            if (field instanceof TextInputControl){
                ((TextInputControl) field).clear();
            } else if(field instanceof ComboBox<?>){
                ((ComboBox<?>) field).setValue(null);
            }
        }

    }

    public void runTime() {
        new Thread() {
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy | hh:mm:ss a");
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(() -> {
                        dateDuJour.setText(format.format(new Date()));
                    });
                }

            }
        }.start();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        runTime();
        activeMenu(dashboardBtn, iconDashboard);
    }
}
