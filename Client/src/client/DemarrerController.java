package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DemarrerController {

    @FXML private Button startStream;
    @FXML private Button closeButton;

    public void start() throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("player.fxml"));
        Scene scene = new Scene(p);
        Stage appStage = (Stage) startStream.getScene().getWindow();
        appStage.setResizable(false);
        appStage.setScene(scene);
        appStage.show();
    }

    public void close(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.setIconified(true);
    }

}
