package client;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SongItem implements Initializable {

    protected StreamServer serveurs = new StreamServer();

    @FXML private AnchorPane itemSong;
    @FXML private Button playItem;
    @FXML private Button delButton;
    @FXML private Button editButton;
    @FXML private Label songTitle;
    @FXML private Text songDetails;

    public static BooleanProperty gestion = new SimpleBooleanProperty(false);

    private Song song;

    public void playSong() {
        System.out.println("Playing " + songTitle.getText() + "...");
        PlayerController.currentSongDuration.setValue(song.duration);
        PlayerController.currentSong.setValue(null);
        PlayerController.currentSong.setValue(song);
        serveurs.serverVLC.playSong(song.getId(), song.getGenre());
    }

    public void setData(Song s) {
        song = s;
        songTitle.setText(s.titre);
        songDetails.setText(s.artiste + " - " + s.album);
    }

    public void delSong() throws IOException {
        if(song.getGenre()==1) serveurs.server1.delSong(song.getId(), song.getPath());
        if(song.getGenre()==2) serveurs.server2.delSong(song.getId(), song.getPath());
        Parent p = FXMLLoader.load(getClass().getResource("player.fxml"));
        Scene scene = new Scene(p);
        Stage appStage = (Stage) editButton.getScene().getWindow();
        appStage.setResizable(false);
        appStage.setScene(scene);
        appStage.show();
    }

    public void updateSong() throws IOException {
        MajFormController mc = new MajFormController();
        mc.setSongToEdit(this.song);
        Parent p = FXMLLoader.load(getClass().getResource("majForm.fxml"));
        Scene scene = new Scene(p);
        Stage appStage = (Stage) editButton.getScene().getWindow();
        appStage.setResizable(false);
        appStage.setScene(scene);
        appStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playItem.setVisible(false);
        itemSong.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                itemSong.setStyle("-fx-background-color:-fx-grayHover;");
                playItem.setVisible(true);
            } else {
                itemSong.setStyle("-fx-background-color:-fx-gray;");
                playItem.setVisible(false);
            }
        });
        editButton.setVisible(gestion.getValue());
        delButton.setVisible(gestion.getValue());
        gestion.addListener(((observableValue, oldValue, newValue) -> {
            System.out.println(newValue.toString());
            editButton.setVisible(newValue);
            delButton.setVisible(newValue);
        }));
    }
}
