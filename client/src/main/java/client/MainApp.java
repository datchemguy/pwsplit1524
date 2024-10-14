package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getClassLoader().getResource("page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Properties prop = new Properties();
        prop.load(MainApp.class.getClassLoader().getResourceAsStream("settings.properties"));
        Controller controller = fxmlLoader.getController();
        String host = prop.getProperty("host");
        try {
            int port = Integer.parseInt(prop.getProperty("port"));
            controller.setServer(new ServerClient(host, port));
        } catch(NumberFormatException _) {
            controller.setServer(new ServerClient(host));
        }

        stage.setTitle("PWSplit");
        stage.setScene(scene);
        stage.show();

        Platform.runLater(controller::refresh);
    }

    public static void main(String[] args) {
        launch();
    }
}