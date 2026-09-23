package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dao.ConsultationDao;
import dao.FacturationDao;
import dao.RendezVousDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import model.facturation.Facturation;
import model.rendez_vous.RendezVous;
import stock.gestion.cabinet.medical.App;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
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

    @FXML
    private BarChart<String, Number> graphConsultation;

    public static dashboardController instance;

    public dashboardController getInstance() {
        return instance;
    }

    public ObservableList<RendezVous> rdvList;

    public void loadRendezVousDuJour() throws IOException {
        this.rdvList = new RendezVousDao().getRendezVousDuJour();
        rdvDashboardContainer.getChildren().clear();
        for (RendezVous data : rdvList) {
            Parent cardParent = App.loadFXML("listRdvDashboar");
            rdvDashboardContainer.getChildren().add(cardParent);
            // cardParent.setUserData(data);
            listRdvDashboardController.getInstance().setData(data, cardParent);
        }
    }

    public void loadCount() {
        rdvCount.setText(String.valueOf(rdvList.size()));

        consultationCount.setText(String.valueOf(
                new ConsultationDao().getConsultationJour().size()));
        
        int consultaionSemaine[] = new ConsultationDao().affluenceParJour();
        int SumWeekConsultation = 0;
        for(int i=0; i<5;i++){
            SumWeekConsultation += consultaionSemaine[i];
        }
        consultationsSemaines.setText(String.valueOf(SumWeekConsultation) + " consultationsgit");

        ObservableList<Facturation> facList = new FacturationDao().facturationGetData();
        // BigDecimal capitalDay = BigDecimal.ZERO;
        BigDecimal sum = BigDecimal.ZERO;
        for(Facturation fac : facList){
            if(fac.getDateFacture() != null && fac.getDateFacture().equals(LocalDate.now()) && fac.getMontant() != null){
                sum = sum.add(fac.getMontant());
            }
        }
        capitalCount.setText(String.valueOf(sum));
    }

    public void loadGraphe() {
        graphConsultation.getData().clear();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        String[] jours = { "Lun", "Mar", "Mer", "Jeu", "Ven" };
        int[] valeurs = new ConsultationDao().affluenceParJour();
        
        for (int i = 0; i < 5; i++) {
            serie.getData().add(new XYChart.Data<>(jours[i], valeurs[i]));
        }

        graphConsultation.getData().add(serie);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            instance = this;
            loadRendezVousDuJour();
            // facturationController.getInstance().listFacture();
            loadCount();
            loadGraphe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
