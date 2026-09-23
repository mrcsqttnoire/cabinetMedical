package controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import model.facturation.Facturation;
import model.rendez_vous.RendezVous;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class listeFactureController implements Initializable {
    @FXML
    private AnchorPane btnDetailFac;

    @FXML
    private Label consultationId;

    @FXML
    private Label dateFac;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label nomPrenomFac;

    @FXML
    private Label numFac;

    public static listeFactureController instance;

    public static listeFactureController getInstance() {
        return instance;
    }

    public void setDataFac(Facturation fac, Parent card) {
        if (fac != null) {
            ((Label) card.lookup("#numFac")).setText("FAC-" + String.valueOf(fac.getId()));
            ((Label) card.lookup("#nomPrenomFac")).setText(fac.getConsultation().getPatient().getNomPrenom());
            ((Label) card.lookup("#dateFac"))
                    .setText(fac.getDateFacture().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            // ((Label) card.lookup("#mttFac")).setText(String.valueOf(fac.getMontant()) + "
            // Ar");
            ((Label) card.lookup("#consultationId")).setText("C-" + String.valueOf(fac.getConsultation().getId()));

            ((AnchorPane) card).setUserData(fac);

            // System.out.println(((Parent) card).getUserData());
            // System.out.println(((Parent) card));
        }
    }

    @FXML
    public void onVoirDetail(MouseEvent event) throws IOException {
        try {
            Node source = (Node) event.getSource();
            AnchorPane main = (AnchorPane) source.getParent();
            // System.out.println(main);
            Facturation facSelectionnne = (Facturation) main.getUserData();
            System.out.println(facSelectionnne);
            facturationController.getInstance().assignerData(facSelectionnne);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        instance = this;
    }
}
