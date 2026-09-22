package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dao.ConsultationDao;
import dao.PrescriptionDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.consultation.Consultation;
import model.patient_class.Patient;
import model.prescription.Prescription;
import model.rendez_vous.RendezVous;
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

    @FXML
    private TextField poidFields;

    @FXML
    private TextField temperatureField;

    @FXML
    private TextField tensionField;

    @FXML
    private TextArea diagField;

    public static consultationController getInstance() {
        return instance;
    }

    @FXML
    public void ajouterLignePrescription() throws IOException {
        Parent card = App.loadFXML("prescriptionCard");
        prescriptionContainer.getChildren().add(card);
    }

    RendezVous rdv;
    Patient patient;
    private boolean isRdv = false;

    public void assignerData(RendezVous rdv, Patient patient) {
        this.rdv = rdv;
        this.patient = patient;
        if (rdv != null) {
            isRdv = true;
            nomPatient.setText(this.rdv.getPatient().getNomPrenom());
            numPatient.setText(this.rdv.getPatient().getTelephone());
            adrsPatient.setText(this.rdv.getPatient().getAdresse());
        } else {
            nomPatient.setText(this.patient.getNomPrenom());
            numPatient.setText(this.patient.getTelephone());
            adrsPatient.setText(this.patient.getAdresse());
        }
    }

    private boolean validerPrescriptions() {
        if (prescriptionContainer.getChildren().isEmpty()) {
            mainController.showAlert("Veuillez ajouter au moins une prescription médicamenteuse.", AlertType.WARNING)
                    .showAndWait();
            return false;
        }

        for (Node cardPrescription : prescriptionContainer.getChildren()) {
            if (cardPrescription instanceof AnchorPane) {
                for (Node gridPane : ((AnchorPane) cardPrescription).getChildren()) {
                    if (gridPane instanceof GridPane) {
                        TextField tfMed = (TextField) gridPane.lookup("#medicamentPresc");
                        TextField tfDuree = (TextField) gridPane.lookup("#durePresc");
                        TextField tfInst = (TextField) gridPane.lookup("#instructionPresc");

                        if (tfMed == null || tfDuree == null || tfInst == null) {
                            mainController.showAlert("Erreur : Impossible de lire les champs de prescription.",
                                    AlertType.ERROR).showAndWait();
                            return false;
                        }

                        String medicament = tfMed.getText().trim();
                        String duree = tfDuree.getText().trim();
                        String instruction = tfInst.getText().trim();

                        if (medicament.isEmpty() || duree.isEmpty() || instruction.isEmpty()) {
                            mainController.showAlert("Veuillez remplir tous les champs de chaque prescription.",
                                    AlertType.WARNING).showAndWait();
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void enregistrerPrescriptions(Consultation consultation) {
        for (Node cardPrescription : prescriptionContainer.getChildren()) {
            if (cardPrescription instanceof AnchorPane) {
                for (Node gridPane : ((AnchorPane) cardPrescription).getChildren()) {
                    if (gridPane instanceof GridPane) {
                        TextField tfMed = (TextField) gridPane.lookup("#medicamentPresc");
                        TextField tfDuree = (TextField) gridPane.lookup("#durePresc");
                        TextField tfInst = (TextField) gridPane.lookup("#instructionPresc");

                        String medicament = tfMed.getText().trim();
                        String duree = tfDuree.getText().trim();
                        String instruction = tfInst.getText().trim();

                        Prescription p = new Prescription(medicament, duree, instruction, consultation);
                        // System.out.println(consultation.getId());
                        new PrescriptionDao().ajouterPrescription(p);
                    }
                }
            }
        }
    }

    @FXML
    public void onTerminerConsultation() {
        try {
            String tension = tensionField.getText().trim();
            String temperature = temperatureField.getText().trim();
            String poid = poidFields.getText().trim();
            String diag = diagField.getText().trim();
            LocalDate dateConsultation = LocalDate.now();

            if (tension.isEmpty() || temperature.isEmpty() || poid.isEmpty() || diag.isEmpty()) {
                mainController.showAlert("Veuillez compléter tous les champs de la consultation.", AlertType.WARNING)
                        .showAndWait();
                return;
            }

            if (!validerPrescriptions()) {
                return;
            }

            Consultation c;
            if (isRdv) {
                c = new Consultation(dateConsultation, diag, tension, temperature, poid, rdv.getPatient());
                c.setRendezVous(rdv);
            } else {
                c = new Consultation(dateConsultation, diag, tension, temperature, poid, patient);
            }

            if (new ConsultationDao().ajouterConsultation(c)) {
                ObservableList<Consultation> consultations = new ConsultationDao().consultationGetData();
                Consultation lastConsultation = consultations.get(consultations.size() - 1);
                // System.out.println(lastConsultation);
                enregistrerPrescriptions(lastConsultation);
                onAnnuler();
                mainController.showAlert("Consultation terminée avec succès", AlertType.INFORMATION).showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAnnuler() {
        mainController.viderChamps((DatePicker) null, tensionField, temperatureField, poidFields, diagField);
        prescriptionContainer.getChildren().clear();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        instance = this;
    }
}
