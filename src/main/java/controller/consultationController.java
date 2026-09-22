package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.patient_class.Patient;
import stock.gestion.cabinet.medical.App;

public class consultationController implements Initializable {
    private static consultationController instance;
    @FXML
    private HBox addLigne;

    @FXML
    public VBox prescriptionContainer;

    @FXML
    private Label adrsPatient;

    @FXML
    private Label nomPatient;

    @FXML
    private Label numPatient;

    public static consultationController getInstance() {
        return instance;
    }
    @FXML
    public void ajouterLignePrescription() throws IOException {
        Parent card = App.loadFXML("prescriptionCard");
        prescriptionContainer.getChildren().add(card);
    }

    public void assignerData(Patient patient){
        nomPatient.setText(patient.getNomPrenom());
        numPatient.setText(patient.getTelephone());
        adrsPatient.setText(patient.getAdresse());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        instance = this;
    }
}
