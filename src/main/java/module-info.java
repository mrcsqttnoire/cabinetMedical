module stock.gestion.cabine.medical {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.ikonli.fontawesome5;

    opens stock.gestion.cabinet.medical to javafx.fxml;
    exports stock.gestion.cabinet.medical;
}
