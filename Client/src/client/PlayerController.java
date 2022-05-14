package client;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
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
import uk.co.caprica.vlcj.player.base.State;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class PlayerController implements Initializable {

    protected static LongProperty currentSongDuration = new SimpleLongProperty();
    protected static ObjectProperty currentSong = new SimpleObjectProperty<>();
    protected StreamServer serveurs = new StreamServer();
    protected static ArrayList<Demo.Song> fetched = new ArrayList<>();

    private String SLIDER_STYLE_FORMAT =
            "-slider-track-color: linear-gradient(to right, -fx-green 0%%, "
                    + "-fx-green  %1$f%%, silver %1$f%%, silver 100%%);";

    private static AudioPlayerComponent mediaPlayerComponent = new AudioPlayerComponent();
    @FXML private Button closeButton;
    @FXML private ImageView iconPlay;
    @FXML private ImageView soundIcon;
    @FXML private ImageView stopIcon;
    @FXML private Slider sliderSon;
    @FXML private Text timerDebut;
    @FXML private TextField searchBar;
    @FXML private Text timerFin;
    @FXML private ProgressBar progressTime;
    @FXML private FlowPane itemGallery;
    @FXML private Label titreCurrentSong;
    @FXML private Label detailsCurrentSong;

    public PlayerController() {
    }

    public void playStream(){
        //System.out.println("play");
        //streamServer.play();
        mediaPlayerComponent.mediaPlayer().controls().play();
    }

    public void pauseStream(){
        //System.out.println("pause");
        //streamServer.pause();
        mediaPlayerComponent.mediaPlayer().controls().pause();
    }

    public void stopStream(){
        //System.out.println("stop");
        //streamServer.stop();
        mediaPlayerComponent.mediaPlayer().controls().stop();
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

    public void loadForm() throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("addForm.fxml"));
        Scene scene = new Scene(p);
        Stage appStage = (Stage) closeButton.getScene().getWindow();
        appStage.setResizable(false);
        appStage.setScene(scene);
        appStage.show();
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

    public void gererCollection() {
        SongItem.gestion.setValue(true);
    }

    public void loadAbout() throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("about.fxml"));
        Scene scene = new Scene(p);
        Stage appStage = (Stage) closeButton.getScene().getWindow();
        appStage.setResizable(false);
        appStage.setScene(scene);
        appStage.show();
        SongItem.gestion.setValue(false);
    }

    public void stopHandler(){
        //streamServer.stop();
        mediaPlayerComponent.mediaPlayer().controls().stop();
        stopIcon.setOpacity(0.5);
        Image icon = new Image(getClass().getResourceAsStream("ico/white_0008_play-circle-sharp.png"));
        iconPlay.setPreserveRatio(true);
        iconPlay.setFitWidth(31);
        iconPlay.setFitHeight(37);
        iconPlay.setImage(icon);
    }

    public void playPauseHandler() {
        System.out.println(mediaPlayerComponent.mediaPlayer().status().state().toString());
        if (mediaPlayerComponent.mediaPlayer().status().state().toString().equals("PLAYING")){
            System.out.println("je dois mettre pause");
            mediaPlayerComponent.mediaPlayer().controls().pause();
        } else {
            System.out.println("je dois mettre play");
            mediaPlayerComponent.mediaPlayer().controls().play();
        }
    }

    public void muteAction() {
        double currentVolume = sliderSon.getValue();
        if(currentVolume > 0) {
            Image icon = new Image(getClass().getResourceAsStream("ico/white_0000_volume-mute-sharp.png"));
            soundIcon.setPreserveRatio(false);
            soundIcon.setFitWidth(18);
            soundIcon.setFitHeight(18);
            soundIcon.setImage(icon);
            sliderSon.setValue(0);
            mediaPlayerComponent.mediaPlayer().audio().setVolume(0);
        } else {
            Image icon = new Image(getClass().getResourceAsStream("ico/white_0003_volume-high-sharp.png"));
            soundIcon.setPreserveRatio(false);
            soundIcon.setFitWidth(18);
            soundIcon.setFitHeight(18);
            soundIcon.setImage(icon);
            sliderSon.setValue(100);
            mediaPlayerComponent.mediaPlayer().audio().setVolume(100);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetched.clear();
        ArrayList<Demo.Song>fetched1 = new ArrayList<>(Arrays.asList(serveurs.server1.getAllSongs()));
        ArrayList<Demo.Song>fetched2 = new ArrayList<>(Arrays.asList(serveurs.server2.getAllSongs()));
        for(Demo.Song s : fetched1){
            fetched.add(s);
        }
        for(Demo.Song s : fetched2){
            fetched.add(s);
        }
        /**
         * CSS du slider du volume
         */
        sliderSon.styleProperty().bind(Bindings.createStringBinding(() -> {
            double percentage = (sliderSon.getValue() - sliderSon.getMin()) / (sliderSon.getMax() - sliderSon.getMin()) * 100.0 ;
            return String.format(Locale.US, SLIDER_STYLE_FORMAT, percentage);
        }, sliderSon.valueProperty(), sliderSon.minProperty(), sliderSon.maxProperty()));

        /**
         * Listener sur le slider du volume + update de l'icon
         */
        sliderSon.valueProperty().addListener(new ChangeListener<>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if(newValue.doubleValue() == 0) {
                    Image icon = new Image(getClass().getResourceAsStream("ico/white_0000_volume-mute-sharp.png"));
                    soundIcon.setPreserveRatio(true);
                    soundIcon.setFitWidth(16);
                    soundIcon.setFitHeight(18);
                    soundIcon.setImage(icon);
                }

                if(newValue.doubleValue() > 0 && newValue.doubleValue() < 30) {
                    Image icon = new Image(getClass().getResourceAsStream("ico/white_0002_volume-low-sharp.png"));
                    soundIcon.setPreserveRatio(false);
                    soundIcon.setFitWidth(18);
                    soundIcon.setFitHeight(18);
                    soundIcon.setImage(icon);
                }

                if(newValue.doubleValue() >=30 && newValue.doubleValue() < 70) {
                    Image icon = new Image(getClass().getResourceAsStream("ico/white_0001_volume-medium-sharp.png"));
                    soundIcon.setPreserveRatio(false);
                    soundIcon.setFitWidth(18);
                    soundIcon.setFitHeight(18);
                    soundIcon.setImage(icon);
                }

                if(newValue.doubleValue() >= 70) {
                    Image icon = new Image(getClass().getResourceAsStream("ico/white_0003_volume-high-sharp.png"));
                    soundIcon.setPreserveRatio(false);
                    soundIcon.setFitWidth(18);
                    soundIcon.setFitHeight(18);
                    soundIcon.setImage(icon);
                }
                mediaPlayerComponent.mediaPlayer().audio().setVolume(newValue.intValue());
            }
        });

        mediaPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventListener() {
            @Override
            public void mediaChanged(MediaPlayer mediaPlayer, MediaRef mediaRef) {
                System.out.println("media changed");
            }

            @Override
            public void opening(MediaPlayer mediaPlayer) {

            }

            @Override
            public void buffering(MediaPlayer mediaPlayer, float v) {

            }

            @Override
            public void playing(MediaPlayer mediaPlayer) {
                Image icon = new Image(getClass().getResourceAsStream("ico/white_0010_pause-circle-sharp.png"));
                iconPlay.setPreserveRatio(true);
                iconPlay.setFitWidth(31);
                iconPlay.setFitHeight(37);
                iconPlay.setImage(icon);
            }

            @Override
            public void paused(MediaPlayer mediaPlayer) {
                Image icon = new Image(getClass().getResourceAsStream("ico/white_0008_play-circle-sharp.png"));
                iconPlay.setPreserveRatio(true);
                iconPlay.setFitWidth(31);
                iconPlay.setFitHeight(37);
                iconPlay.setImage(icon);

            }

            @Override
            public void stopped(MediaPlayer mediaPlayer) {

            }

            @Override
            public void forward(MediaPlayer mediaPlayer) {

            }

            @Override
            public void backward(MediaPlayer mediaPlayer) {

            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                System.out.println("finished");
            }

            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long l) {
                double t = currentSongDuration.doubleValue();
                Long s = TimeUnit.MILLISECONDS.toSeconds(l);
                int minutes = (s.intValue() % 3600) / 60;
                int seconds = s.intValue() % 60;

                String oldStr = timerDebut.getText();
                String timeString = String.format("%02d:%02d", minutes, seconds);
                timerDebut.setText(timeString);
                if(progressTime.getProgress() < 1.0 && !oldStr.equals(timeString) && !timerDebut.getText().equals(timerFin.getText())) {
                    if(t != 0){
                        progressTime.setProgress(progressTime.getProgress() + (1/t));
                    }
                }
                if((progressTime.getProgress() == 1.0 && timerDebut.getText().equals(timerFin.getText()))){
                    progressTime.setProgress(0);
                }
            }

            @Override
            public void positionChanged(MediaPlayer mediaPlayer, float v) {

            }

            @Override
            public void seekableChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void pausableChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void titleChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void snapshotTaken(MediaPlayer mediaPlayer, String s) {

            }

            @Override
            public void lengthChanged(MediaPlayer mediaPlayer, long l) {

            }

            @Override
            public void videoOutput(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void scrambledChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void elementaryStreamAdded(MediaPlayer mediaPlayer, TrackType trackType, int i) {

            }

            @Override
            public void elementaryStreamDeleted(MediaPlayer mediaPlayer, TrackType trackType, int i) {

            }

            @Override
            public void elementaryStreamSelected(MediaPlayer mediaPlayer, TrackType trackType, int i) {

            }

            @Override
            public void corked(MediaPlayer mediaPlayer, boolean b) {

            }

            @Override
            public void muted(MediaPlayer mediaPlayer, boolean b) {

            }

            @Override
            public void volumeChanged(MediaPlayer mediaPlayer, float v) {

            }

            @Override
            public void audioDeviceChanged(MediaPlayer mediaPlayer, String s) {

            }

            @Override
            public void chapterChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void error(MediaPlayer mediaPlayer) {

            }

            @Override
            public void mediaPlayerReady(MediaPlayer mediaPlayer) {

            }
        });

        if(!fetched.isEmpty()) {
            System.out.println("NOT EMPTY : " + fetched.size());
            itemGallery.getChildren().clear();
            for(Demo.Song s : fetched){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("songItem.fxml"));
                AnchorPane anchorPane = null;
                try {
                    anchorPane = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SongItem songItemController = fxmlLoader.getController();
                songItemController.setData(new Song(s));
                itemGallery.getChildren().add(anchorPane);
            }
            int minutesDuration = (fetched.get(0).duration % 3600) / 60;
            int secondsDuration = fetched.get(0).duration % 60;
            String durationString = String.format("%02d:%02d", minutesDuration, secondsDuration);
            timerFin.setText(durationString);
            titreCurrentSong.setText(fetched.get(0).titre);
            detailsCurrentSong.setText(fetched.get(0).artiste + " - " + fetched.get(0).album);
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                ArrayList<Demo.Song> filteredSongList= (ArrayList<Demo.Song>) fetched.stream().filter(song -> (song.titre+song.artiste+song.album).toLowerCase().contains(newValue.toLowerCase())).collect(Collectors.toList());
                itemGallery.getChildren().clear();
                for(Demo.Song s : filteredSongList) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("songItem.fxml"));
                    AnchorPane anchorPane = null;
                    try {
                        anchorPane = fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    SongItem songItemController = fxmlLoader.getController();
                    songItemController.setData(new Song(s));
                    itemGallery.getChildren().add(anchorPane);
                }
            });
        }

        /**
         * Thread qui lance l'ecoute du stream
         */
        Thread playerThread = new Thread(() -> {
            if(mediaPlayerComponent.mediaPlayer().status().state() != State.PLAYING) {
                mediaPlayerComponent.mediaPlayer().media().start("http://134.209.226.109:8080");
            }
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        playerThread.start();

        timerFin.setVisible(false);
        timerDebut.setVisible(false);
        progressTime.setVisible(false);

        currentSong.addListener((observable, old, newval) ->{
            if(newval!=null) {
                Song temp = (Song) newval;
                System.out.println(temp.getTitre());
                titreCurrentSong.setText(temp.getTitre());
                detailsCurrentSong.setText(temp.getArtiste() + " - " + temp.getAlbum());
            }
        });

    }
}
