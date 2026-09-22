package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import dao.PatientDao;
import dao.RendezVousDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.patient_class.Patient;
import model.rendez_vous.RendezVous;

public class rendezVousController implements Initializable {
    @FXML
    private Label InfoContact;

    @FXML
    private Label InfoDate;

    @FXML
    private Label InfoHeure;

    @FXML
    private Label InfoNom;

    @FXML
    private Button ajputerRdv;

    @FXML
    private Button annulerRdv;

    @FXML
    private Button btnConsulter;

    @FXML
    private FontIcon btnDeleteRdv;

    @FXML
    private FontIcon btnModifierRdv;

    @FXML
    private TableColumn<RendezVous, String> columnContact;

    @FXML
    private TableColumn<RendezVous, String> columnHeure;

    @FXML
    private TableColumn<RendezVous, String> columnMotif;

    @FXML
    private TableColumn<RendezVous, String> columnNomPrenom;

    @FXML
    private ComboBox<Patient> comboPatient;

    @FXML
    private DatePicker dateRdv;

    @FXML
    private ComboBox<String> heureRdv;

    @FXML
    private TextArea motif;

    @FXML
    private TableView<RendezVous> tableRdv;

    @FXML
    private AnchorPane infoPaneRdv;

    @FXML
    private Label mainText;

    @FXML
    private Label mainTitle;

    @FXML
    private Button btnValider;
    
    @FXML
    private AnchorPane planingRdv;

    private static rendezVousController instance;

    public static rendezVousController getInstance(){
        return instance;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            showRendezVous();
            chargerPatient();
            remplirHeure();
            mainController.tesAntDate(dateRdv);
            instance = this;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chargerPatient() throws IOException {
        ObservableList<Patient> patients = new PatientDao().patientGetData();
        comboPatient.setItems(patients);

        comboPatient.setConverter(new StringConverter<Patient>() {
            @Override
            public String toString(Patient patient) {
                return (patient != null) ? patient.getNom() + " " + patient.getPrenom() : "";
            }

            @Override
            public Patient fromString(String string) {
                return null;
            }
        });
    }

    public void remplirHeure() {
        heureRdv.getItems().clear();

        for (int i = 8; i < 18; i++) {
            heureRdv.getItems().add(String.format("%02d:00", i));
            heureRdv.getItems().add(String.format("%02d:30", i));
        }
    }

    @FXML
    public void onAjouter() {
        try {
            LocalDate date_Rdv;
            String texte_Date = dateRdv.getEditor().getText();
            String heure_Rdv = heureRdv.getValue();
            LocalTime parseHeureRdv = LocalTime.parse(heure_Rdv);
            String motif_Rdv = motif.getText();
            String status = "planifie";
            Patient patient = comboPatient.getValue();

            if (!texte_Date.isEmpty() && texte_Date != null && heure_Rdv != null && !motif_Rdv.isEmpty()
                    && patient != null) {
                try {
                    date_Rdv = dateRdv.getConverter().fromString(texte_Date);
                    dateRdv.setValue(date_Rdv);
                } catch (DateTimeParseException e) {
                    mainController.showAlert("Format de date invalide", Alert.AlertType.ERROR).show();
                    return;
                }
                if (date_Rdv.isBefore(LocalDate.now())) {
                    mainController
                            .showAlert("La date du rendez vous ne peut pas être dans le passé !", Alert.AlertType.ERROR)
                            .show();
                    return;
                } else {
                    RendezVous rendezVous = new RendezVous(date_Rdv, parseHeureRdv, motif_Rdv, status, patient);
                    if (new RendezVousDao().ajouterRdv(rendezVous)) {
                        showRendezVous();
                        mainController.showAlert("Rendez-vous ajouté avec succès", AlertType.INFORMATION).showAndWait();
                        mainController.viderChamps(dateRdv, heureRdv, motif, comboPatient);
                    }
                }
            } else {
                System.out.println(patient);
                mainController.showAlert("Veuillez remplir tous les champs", AlertType.WARNING).showAndWait();
            }

        } catch (Exception e) {
            mainController.showAlert(e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void onAnnuler() {
        try {
            btnValider.setVisible(false);
            mainController.viderChamps(dateRdv, heureRdv, motif, comboPatient);
            mainTitle.setText("Ajouter un rendez vous");
            mainText.setText("Enregistrement d'un nouveau rendez-vous");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<RendezVous> rdvList;

    public void showRendezVous() {
        rdvList = new RendezVousDao().rendezVousGetData();
        
        columnHeure.setCellValueFactory(cellData -> {
            RendezVous rdv = cellData.getValue();
            if (rdv != null){
                String stt = rdv.getStatus();
                if("planifie".equalsIgnoreCase(stt)){
                    String hrRdv = cellData.getValue().getHeureRdv().toString();
                    return new SimpleStringProperty(hrRdv);
                } else {
                    return new SimpleStringProperty(stt) ;
                }
            }
            return new SimpleStringProperty("");
        });
        // columnHeure.setCellValueFactory(new PropertyValueFactory<>("HeureRdv"));
        columnNomPrenom.setCellValueFactory(cellData -> {
            Patient p = cellData.getValue().getPatient();
            String nonmPrenom = p.getNomPrenom();
            return new SimpleStringProperty(nonmPrenom);
        });
        columnMotif.setCellValueFactory(new PropertyValueFactory<>("Motif"));
        columnContact.setCellValueFactory(cellData -> {
            Patient p = cellData.getValue().getPatient();
            String contactPatient = p.getTelephone();
            return new SimpleStringProperty(contactPatient);
        });

        tableRdv.setItems(rdvList);

        tableRdv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                infoPaneRdv.setDisable(false);
                afficheInfoRdv(newSelection);
            } else {
                infoPaneRdv.setDisable(true);
            }
        });
    }

    private RendezVous rdvSelecitonne;

    public void afficheInfoRdv(RendezVous rdv) {
        this.rdvSelecitonne = rdv;

        InfoNom.setText(rdv.getPatient().getNomPrenom());
        InfoContact.setText(rdv.getPatient().getTelephone());
        if("honore".equalsIgnoreCase(rdv.getStatus())){
            planingRdv.setDisable(true);
        } else {
            planingRdv.setDisable(false);
        }
        InfoDate.setText(rdv.getDateRdv().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        InfoHeure.setText(rdv.getHeureRdv().toString());
    }

    @FXML
    public void fileToView() {
        try {
            mainTitle.setText("Modifier un Rendez vous");
            mainText.setText("Modification des données du rendez-vous.");
            btnValider.setVisible(true);

            dateRdv.setValue(rdvSelecitonne.getDateRdv());
            motif.setText(rdvSelecitonne.getMotif());
            comboPatient.setValue(rdvSelecitonne.getPatient());
            heureRdv.setValue(rdvSelecitonne.getHeureRdv().toString());
        } catch (Exception e) {
            mainController.showAlert("Impossible de charger les données", AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void onValider() {
        try {
            LocalDate date_Rdv;
            String texte_Date = dateRdv.getEditor().getText();
            String heure_Rdv = heureRdv.getValue();
            LocalTime parseHeureRdv = LocalTime.parse(heure_Rdv);
            String motif_Rdv = motif.getText();
            String status = "planifié";
            Patient patient = comboPatient.getValue();

            if (!texte_Date.isEmpty() && texte_Date != null && heure_Rdv != null && !motif_Rdv.isEmpty()
                    && patient != null) {
                try {
                    date_Rdv = dateRdv.getConverter().fromString(texte_Date);
                    dateRdv.setValue(date_Rdv);
                } catch (DateTimeParseException e) {
                    mainController.showAlert("Format de date invalide", Alert.AlertType.ERROR).show();
                    return;
                }
                if (date_Rdv.isBefore(LocalDate.now())) {
                    mainController
                            .showAlert("La date du rendez vous ne peut pas être dans le passé !", Alert.AlertType.ERROR)
                            .show();
                    return;
                } else {
                    RendezVous rendezVous = new RendezVous(date_Rdv, parseHeureRdv, motif_Rdv, status, patient);
                    rendezVous.setId(rdvSelecitonne.getId());
                    System.out.println(rdvSelecitonne.getId());
                    if (new RendezVousDao().modifierRdv(rendezVous)) {
                        onAnnuler();
                        showRendezVous();
                        mainController.showAlert("Rendez-vous modifié avec succès", AlertType.INFORMATION)
                                .showAndWait();
                    }
                }
            } else {
                System.out.println(patient);
                mainController.showAlert("Veuillez remplir tous les champs", AlertType.WARNING).showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSupprimer() throws IOException{
        if(mainController.confirmerAction("Voulez-vous supprimer ce rendez-vous ?")){
            try{
                if(new RendezVousDao().supprimerRendezVous(rdvSelecitonne)){
                    rdvList.remove(rdvSelecitonne);
                    mainController.showAlert("Rendez-vous supprimé ave succès", AlertType.ERROR);
                    onAnnuler();
                }
            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    @FXML 
    public void onConsulter() throws IOException{
        try {
            mainController.getInstance().fenCosultaition();
            consultationController.getInstance().assignerData(rdvSelecitonne, null);
            consultationController.getInstance().showHistorique(rdvSelecitonne.getPatient());
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
