package client;

import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uk.co.caprica.vlcj.media.MediaRef;
import uk.co.caprica.vlcj.media.TrackType;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AboutController implements Initializable {

    @FXML private Button closeButton;

    public AboutController() {
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

    public void loadHome() throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("player.fxml"));
        Scene scene = new Scene(p);
        Stage appStage = (Stage) closeButton.getScene().getWindow();
        appStage.setResizable(false);
        appStage.setScene(scene);
        appStage.show();
        SongItem.gestion.setValue(false);
    }

    public void loadForm() throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("addForm.fxml"));
        Scene scene = new Scene(p);
        Stage appStage = (Stage) closeButton.getScene().getWindow();
        appStage.setResizable(false);
        appStage.setScene(scene);
        appStage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
