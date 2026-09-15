package controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
