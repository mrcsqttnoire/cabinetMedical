package controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.consultation.Consultation;

public class historiqueController implements Initializable{
    @FXML
    private Label dateHist;

    @FXML
    private Label diagHist;

    @FXML
    private Label donneeHist;

    private static historiqueController instance;

    public static historiqueController getInstance(){
        return instance;
    }

    public void setData(Consultation c){
        if(c != null){
            dateHist.setText(c.getDateConsultation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            diagHist.setText(c.getDiagnostique());
            donneeHist.setText(c.getDonneeMed());

            // System.out.println(dateHist);
            // System.out.println(dateHist.getText());
        }
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        instance = this;        
    }
}
