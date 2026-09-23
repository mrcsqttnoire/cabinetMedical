package controller;

import model.consultation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dao.FacturationDao;
import dao.PrescriptionDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import model.facturation.Facturation;
import model.patient_class.Patient;
import model.prescription.Prescription;
import model.rendez_vous.RendezVous;
import stock.gestion.cabinet.medical.App;

public class facturationController implements Initializable {

    @FXML
    private VBox listeFacContainer;

    @FXML
    private Label FacContact;

    @FXML
    private Text facDiag;

    @FXML
    private Label facMtt;

    @FXML
    private Label facNom;

    @FXML
    private VBox prescContainer;

    public static facturationController instance;

    public static facturationController getInstance() {
        return instance;
    }

    public static void createDataFacture(BigDecimal mtt, Consultation c) {
        try {
            Facturation fac = new Facturation(mtt, LocalDate.now(), "paye", c);
            new FacturationDao().ajouterFacture(fac);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Facturation> listData;

    public void listFacture() {
        try {
            this.listData = new FacturationDao().facturationGetData();
            listeFacContainer.getChildren().clear();
            for (Facturation fac : listData) {

                FXMLLoader loader = new FXMLLoader(App.class.getResource("views/FXML/listeFacture.fxml"));
                Parent cardHistorique = loader.load();

                listeFactureController controller = loader.getController();
                controller.setDataFac(fac, cardHistorique);

                listeFacContainer.getChildren().add(cardHistorique);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assignerData(Facturation fac) throws IOException {
        if (fac != null) {
            facNom.setText(fac.getConsultation().getPatient().getNomPrenom());
            FacContact.setText(fac.getConsultation().getPatient().getTelephone());
            facDiag.setText(fac.getConsultation().getDiagnostique());
            facMtt.setText(String.valueOf(fac.getMontant() + " Ar"));

            prescContainer.getChildren().clear();
            ObservableList<Prescription> prescriptions = new PrescriptionDao()
                    .prescriptionGetData(fac.getConsultation());
            // System.out.println(fac.getConsultation().getId());
            for (Prescription presc : prescriptions) {
                String textPresc = presc.getMedicament() + " | " + presc.getDuree() + " | " + presc.getInstruction();
                Label labPresc = new Label(textPresc);
                // System.out.println(textPresc);

                labPresc.setStyle("-fx-text-fill: #3E4947; -fx-font-family : \"Open Sans Regular\";\r\n" + //
                                        "    -fx-font-size : 16px; -fx-wrap-text : true");
                labPresc.setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
                prescContainer.getChildren().add(labPresc);
            }
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        listFacture();
        instance = this;
    }

}
