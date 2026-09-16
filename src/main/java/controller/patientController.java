package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dao.PatienDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import model.patient_class.Patient;

public class patientController implements Initializable {

    @FXML
    private TextField adrsPatient;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private DatePicker dateNaissPatient;

    @FXML
    private TextField nomPatient;

    @FXML
    private AnchorPane patientPane;

    @FXML
    private TextField prenPatient;

    @FXML
    private TableView<?> tablePatient;

    @FXML
    private TableColumn<?, ?> tablePatient_col_contact;

    @FXML
    private TableColumn<?, ?> tablePatient_col_name;

    @FXML
    private TextField telPatient;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        mainController.Uppercase(nomPatient);
        mainController.Capitalize(prenPatient, adrsPatient);
        mainController.testDate(dateNaissPatient);
    }

    private String msg;
    private Alert.AlertType type;

    @FXML 
    public void ajouterPatient(){
        try{
            String nom = nomPatient.getText();
            String prenom = prenPatient.getText();
            LocalDate dateNaiss = dateNaissPatient.getValue();
            String adresse = adrsPatient.getText();
            String contact = telPatient.getText();

            if(!nom.isEmpty() && !prenom.isEmpty() && dateNaiss != null && !adresse.isEmpty() && !contact.isEmpty()){
                if (dateNaissPatient.getValue().isAfter(LocalDate.now())) {
                    mainController.showAlert("La date de naissance ne peut pas être dans le futur !", Alert.AlertType.ERROR).show();
                    return ;
                } else {
                    Patient patient = new Patient(nom, prenom, dateNaiss, contact, adresse);
                    PatienDao dao = new PatienDao();
                    if(dao.ajouterPatient(patient)){
                        msg = "Ajout avec succès";
                        type = AlertType.INFORMATION;
                        mainController.showAlert(msg, type).show();
                        mainController.viderChamps(dateNaissPatient, nomPatient, prenPatient, adrsPatient, telPatient);
                    }                 
                }
            } else {
                msg = "Veuillez remplir tous les champs";
                type = AlertType.WARNING;
                mainController.showAlert(msg, type).showAndWait();
            }
        } catch (Exception err){
            mainController.showAlert("Erreur : " + err.getMessage(), Alert.AlertType.ERROR);
            err.printStackTrace();
        }


    }

}
