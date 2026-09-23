package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class modalController implements Initializable{

    @FXML
    private Button annulerBtn;

    @FXML
    private TextField mttField;

    @FXML
    private Button validerMtt;

    private static modalController instance;

    public static modalController getInstance(){
        return instance;
    }

    public boolean isAnnuler = false;
    @FXML 
    public void onAnnuler(){
        annulerBtn.getScene().getWindow().hide();
        isAnnuler = true;
        isValide = false;
    }

    public String mtt;
    public boolean isValide = false;
    @FXML 
    public void onValider(){
        if(mttField.getText().trim().isEmpty()){
            mainController.showAlert("Veuiller entrer le montant de la consultation", AlertType.INFORMATION).showAndWait();
            isValide = false;
            return;
        }
        mtt = mttField.getText();
        isValide = true;
        validerMtt.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        instance = this;
        mainController.intField(mttField);
    }

}
