package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import dao.PatienDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
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
    private FontIcon btnDelete;

    @FXML
    private FontIcon btnEdit;

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

    @FXML
    private Label labAdrs;

    @FXML
    private Label labContact;

    @FXML
    private Label labDateNaiss;

    @FXML
    private Label labNom;

    @FXML
    private Label labPrenom;

    @FXML
    private AnchorPane infoPane;

        @FXML
    private Label libText;

    @FXML
    private Label libTitle;
    
    @FXML
    private Button btnValider;

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
        libTitle.setText("Nouveau patient");
        libText.setText("Enregistrement d'un nouvel individu dans la base de données médicale");
        mainController.viderChamps(dateNaissPatient, nomPatient, prenPatient, adrsPatient, telPatient);
        btnAjouter.setVisible(true);
        btnValider.setVisible(false);
    }

    public ObservableList<Patient> patientsList;

    public void patientShowData() {
        PatienDao dao = new PatienDao();
        patientsList = dao.patientGetData();

        tablePatient_col_name.setCellValueFactory(new PropertyValueFactory<>("NomPrenom"));
        tablePatient_col_contact.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        tablePatient.setItems(patientsList);

        tablePatient.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                infoPane.setDisable(false);
                afficheInfo(newSelection);
            }else{
                infoPane.setDisable(true);
            }
        });
    }

    private Patient patientSelectionne;
    public void afficheInfo(Patient patient){
        this.patientSelectionne = patient;

        labNom.setText(patient.getNom());
        labPrenom.setText(patient.getPrenom());
        labContact.setText(patient.getTelephone());
        labDateNaiss.setText(patient.getDateNaiss().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        labAdrs.setText(patient.getAdresse());
    }

    @FXML 
    public void fileToView(){
        libTitle.setText("Modifier un patient");
        libText.setText("Modification d'un individu de la base de données");

        nomPatient.setText(patientSelectionne.getNom());
        prenPatient.setText(patientSelectionne.getPrenom());
        telPatient.setText(patientSelectionne.getTelephone());
        dateNaissPatient.setValue(patientSelectionne.getDateNaiss());
        adrsPatient.setText(patientSelectionne.getAdresse());

        btnValider.setVisible(true);
        btnAjouter.setVisible(false);

    }
    @FXML 
    public void modifierPatient(){
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
                    patient.setId(patientSelectionne.getId());
                    PatienDao dao = new PatienDao();
                    if (dao.modifierPatient(patient)) {
                        msg = "Modification avec succès";
                        type = AlertType.INFORMATION;
                        mainController.showAlert(msg, type).show();
                        annuleNouveauPatient();
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
    public void supprimerPatient(){
        if(mainController.confirmerAction("Voulez-vous vraiment supprimer ?")){
            try{
                PatienDao dao = new PatienDao();
                if(dao.supprimerPatient(patientSelectionne)){
                    patientsList.remove(patientSelectionne);    
                    mainController.showAlert("Patient supprimé", Alert.AlertType.INFORMATION).show();
                }
            } catch (Exception err){
                err.printStackTrace();
            }
        }
        // System.out.println(patientSelectionne.getId());

    }
}
