package controller;

import model.consultation.*;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dao.FacturationDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import model.facturation.Facturation;
import stock.gestion.cabinet.medical.App;

public class facturationController implements Initializable {

    @FXML
    private VBox listeFacContainer;
    
    public static  void createDataFacture(BigDecimal mtt, Consultation c){
        try{
            Facturation fac = new Facturation(mtt, LocalDate.now(), "paye",c);
            new FacturationDao().ajouterFacture(fac);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private ObservableList<Facturation> listData;
    public void listFacture(){
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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        listFacture();
    }

}
