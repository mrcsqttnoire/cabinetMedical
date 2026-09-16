package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import dao.PatienDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableView<Patient> tablePatient;

    @FXML
    private TableColumn<Patient, String> tablePatient_col_contact;

    @FXML
    private TableColumn<Patient, String> tablePatient_col_name;

    @FXML
    private TextField telPatient;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        mainController.Uppercase(nomPatient);
        mainController.Capitalize(prenPatient, adrsPatient);
        mainController.testDate(dateNaissPatient);
        patientShowData();
    }

    private String msg;
    private Alert.AlertType type;
    private LocalDate dateDuJour = LocalDate.now();

    @FXML
    public void ajouterPatient() {
        try {
            String nom = nomPatient.getText();
            String prenom = prenPatient.getText();
            // LocalDate dateNaiss = dateNaissPatient.getValue();
            LocalDate dateNaiss;
            String texteDate = dateNaissPatient.getEditor().getText();
            String adresse = adrsPatient.getText();
            String contact = telPatient.getText();

            if (!nom.isEmpty() && !prenom.isEmpty() && texteDate != null
                    || texteDate.isEmpty() && !adresse.isEmpty() && !contact.isEmpty()) {
                try {
                    dateNaiss = dateNaissPatient.getConverter().fromString(texteDate);
                    dateNaissPatient.setValue(dateNaiss);
                } catch (DateTimeParseException e) {
                    mainController.showAlert("Format de date invalide", Alert.AlertType.ERROR).show();
                    return;
                }

                if (dateNaiss.isAfter(dateDuJour)) {
                    mainController
                            .showAlert("La date de naissance ne peut pas être dans le futur !", Alert.AlertType.ERROR)
                            .show();
                    return;
                } else {
                    Patient patient = new Patient(nom, prenom, dateNaiss, contact, adresse);
                    PatienDao dao = new PatienDao();
                    if (dao.ajouterPatient(patient)) {
                        msg = "Ajout avec succès";
                        type = AlertType.INFORMATION;
                        mainController.showAlert(msg, type).show();
                        mainController.viderChamps(dateNaissPatient, nomPatient, prenPatient, adrsPatient, telPatient);
                        patientShowData();
                    }
                }
            } else {
                msg = "Veuillez remplir tous les champs";
                type = AlertType.WARNING;
                mainController.showAlert(msg, type).showAndWait();
            }
        } catch (Exception err) {
            mainController.showAlert("Erreur : " + err.getMessage(), Alert.AlertType.ERROR);
            err.printStackTrace();
        }

    }

    @FXML
    public void annuleNouveauPatient() {
        mainController.viderChamps(dateNaissPatient, nomPatient, prenPatient, adrsPatient, telPatient);
    }

    public ObservableList<Patient> patientsList;

    public void patientShowData() {
        PatienDao dao = new PatienDao();
        patientsList = dao.patientGetData();

        tablePatient_col_name.setCellValueFactory(new PropertyValueFactory<>("NomPrenom"));
        tablePatient_col_contact.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        tablePatient.setItems(patientsList);
    }
}
