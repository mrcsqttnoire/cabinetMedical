package controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import stock.gestion.cabinet.medical.App;

import java.io.IOException;

public class mainController implements Initializable{

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
    private void fenDashboard() throws IOException{
        Parent dashboardView = App.loadFXML("dashboard");
        contentArea.getChildren().setAll(dashboardView);
    }
    @FXML 
    private void fenPatient() throws IOException{
        Parent patientView = App.loadFXML("patient");
        contentArea.getChildren().setAll(patientView);
    }
    @FXML 
    private void fenCosultaiton() throws IOException{
        Parent consultaionView = App.loadFXML("consultaion");
        contentArea.getChildren().setAll(consultaionView);
    }
    @FXML 
    private void fenFacturation() throws IOException{
        Parent facView = App.loadFXML("facturaton");
        contentArea.getChildren().setAll(facView);
    }

    public static Alert showAlert(String message, Alert.AlertType type){
        Alert alert = new Alert(type );
        alert.setTitle(type.name());
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }

    public static void Uppercase(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            field.setText(newValue.toUpperCase());
        });
    }

    public static void Capitalize(TextField... fields) {
        for(TextField field: fields){
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                field.setText(newValue.substring(0, 1).toUpperCase() + newValue.substring(1).toLowerCase());
                if (newValue == null || newValue.isEmpty()) {
                    return;
                }
                String formatted = newValue.substring(0, 1).toUpperCase() + newValue.substring(1).toLowerCase();

                if (!newValue.equals(formatted)) {
                    field.setText(formatted);
                }

            });
        }
    }

    public static void testDate(DatePicker field){
        field.setDayCellFactory(param -> new DateCell() {
        @Override
        public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            
            if (date != null && date.isAfter(LocalDate.now())) {
                
                setDisable(true);
                
                setStyle("-fx-background-color: #ffcccc;");
            }
            }
       });
    }

    public static void viderChamps(DatePicker dateField, TextField... textFields) {
        if (dateField != null) {
            dateField.setValue(null);
        }

        for (TextField field : textFields) {
            field.clear();
        }
    }

    public void runTime(){
        new Thread(){
            public void run(){
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy | hh:mm:ss a");
                while (true) {
                    try{
                        Thread.sleep(1000);
                    } catch(Exception e){
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
    }
}
