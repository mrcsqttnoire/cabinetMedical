package controller;

import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class prescriptionCardController {
    @FXML
    private FontIcon btnDelete;

    public void initialize(){
    }   

    @FXML
    private void supprimeLigne(MouseEvent event) {
        Node source = (Node) event.getSource();
        // System.out.println(source);
        Node mainContainer = source.getParent().getParent().getParent();
        Node VBox = source.getParent().getParent().getParent().getParent();
        if (mainContainer != null) {
            ((javafx.scene.layout.VBox) VBox).getChildren().remove(mainContainer);
        }
    }

}
