package controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import model.rendez_vous.RendezVous;

public class listRdvDashboardController implements Initializable{
    @FXML
    private Label heureDboard;

    @FXML
    private Label motifDboard;

    @FXML
    private Label nomPrenomDboard;

    public static listRdvDashboardController instance;

    public static listRdvDashboardController getInstance(){
        return instance;
    }

    public void setData(RendezVous rdv, Parent card){
        if(rdv != null){
            ((Label) card.lookup("#heureDboard")).setText(rdv.getHeureRdv().format(DateTimeFormatter.ofPattern("hh:mm")));
            ((Label) card.lookup("#motifDboard")).setText(rdv.getMotif());
            ((Label) card.lookup("#nomPrenomDboard")).setText(rdv.getPatient().getNomPrenom());
        }
    }

    @FXML 
    // public void onConsulter() throws IOException{
    //     try {
    //         mainController.getInstance().fenCosultaition();
    //         consultationController.getInstance().assignerData(rdvSelecitonne, null);
    //         consultationController.getInstance().showHistorique(rdvSelecitonne.getPatient());
    //     } catch(Exception e){
    //         e.printStackTrace();
    //     }
    // }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        instance = this;        
    }

}
