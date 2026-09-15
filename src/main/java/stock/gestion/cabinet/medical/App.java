package stock.gestion.cabinet.medical;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    // public void init() throws Exception {
    //     Font regular = Font.loadFont(getClass().getResourceAsStream("/stock/gestion/cabinet/medical/assets/fonts/OpenSans/OpenSans-Regular.ttf"), 14);
    //     System.out.println(regular != null ? "Chargée : " + regular.getName() : "ÉCHEC : Poppins-Regular introuvable");
    // }

    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("main"), 1280, 990);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/FXML/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}