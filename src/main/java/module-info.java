module stock.gestion.cabine.medical {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens stock.gestion.cabinet.medical to javafx.fxml;
    exports stock.gestion.cabinet.medical;
}
