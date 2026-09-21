package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import stock.gestion.cabinet.medical.App;

public class consultationController {
    @FXML
    private HBox addLigne;

    @FXML
    public VBox prescriptionContainer;

    @FXML 
    public void ajouterLignePrescription() throws IOException{
        Parent card = App.loadFXML("prescriptionCard");
        prescriptionContainer.getChildren().add(card);
    }
}
