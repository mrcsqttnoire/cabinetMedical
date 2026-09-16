module stock.gestion.cabine.medical {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    opens stock.gestion.cabinet.medical to javafx.fxml;
    opens controller to javafx.fxml;
    opens model.patient_class to javafx.base;

    exports stock.gestion.cabinet.medical;
}
