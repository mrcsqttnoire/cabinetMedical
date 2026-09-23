package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.ConsultationDao;
import dao.RendezVousDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import model.rendez_vous.RendezVous;
import stock.gestion.cabinet.medical.App;
import javafx.scene.Parent;
import javafx.scene.control.*;

public class dashboardController implements Initializable {
    @FXML
    private Label capitalCount;

    @FXML
    private Label consultationCount;

    @FXML
    private Label consultationsSemaines;

    @FXML
    private Label rdvCount;

    @FXML
    private VBox rdvDashboardContainer;

    public static  dashboardController instance;

    public dashboardController getInstance(){
        return instance;
    }

    public ObservableList<RendezVous> rdvList; 
    public void loadRendezVousDuJour() throws IOException {
        this.rdvList = new RendezVousDao().getRendezVousDuJour();
        rdvDashboardContainer.getChildren().clear();
        for(RendezVous data : rdvList){
            Parent cardParent = App.loadFXML("listRdvDashboar");
            rdvDashboardContainer.getChildren().add(cardParent);
            listRdvDashboardController.getInstance().setData(data, cardParent);
        }
    }

    public void loadCount(){
        rdvCount.setText(String.valueOf(rdvList.size()));

        consultationCount.setText(String.valueOf(
            new ConsultationDao().getConsultationJour().size()
        ));

        consultationsSemaines.setText(null);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            instance = this;
            loadRendezVousDuJour();
            loadCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
