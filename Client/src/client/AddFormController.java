package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class AddFormController implements Initializable {

    @FXML private Button closeButton;
    @FXML private Button addFileButton;
    @FXML private Text filenameText;
    @FXML private TextField titreField;
    @FXML private TextField artisteField;
    @FXML private TextField albumField;
    @FXML private RadioButton genreUrbain;
    @FXML private RadioButton genreAutre;
    @FXML private AnchorPane errorPane;

    protected StreamServer serveurs = new StreamServer();
    private String genre = "";
    private File addedFile;

    //private static Song songToEdit;

    public AddFormController() {
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

    public void loadAbout() throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("about.fxml"));
        Scene scene = new Scene(p);
        Stage appStage = (Stage) closeButton.getScene().getWindow();
        appStage.setResizable(false);
        appStage.setScene(scene);
        appStage.show();
        SongItem.gestion.setValue(false);
    }

    /*public void setSongToEdit(Song songToEdit) {
        this.songToEdit = songToEdit;
    }*/

    public void addFile() {

        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Fichiers audio", "*.mp3");
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(imageFilter);

        File file = fc.showOpenDialog(closeButton.getScene().getWindow());

        if(file!=null) {
            addedFile = file;
            filenameText.setText("");
            String affichage = "";
            String uri = file.toURI().toString();
            String filename = uri.substring( uri.lastIndexOf('/')+1);
            affichage+=(filename+"\n");
            System.out.println(file.toURI().toString());
            filenameText.setText(affichage);
        }
    }

    public void addSong() throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
        errorPane.setVisible(false);
        String titre = titreField.getText();
        String artiste = artisteField.getText();
        String album = albumField.getText();
        String path = filenameText.getText();

        if(!genre.isBlank() && !titre.isBlank() && !artiste.isBlank() && !album.isBlank() && !path.isBlank() && addedFile !=null){

            /*int chunkSize = 1000000; // chunk size to divide (1Mo)
            byte[] bin = Files.readAllBytes(addedFile.toPath());
            int rest = bin.length % chunkSize;
            int chunks = bin.length / chunkSize + (rest > 0 ? 1 : 0);
            byte[][] arrays = new byte[chunks][];
            for (int i = 0; i < (rest > 0 ? chunks - 1 : chunks); i++) {
                arrays[i] = Arrays.copyOfRange(bin, i * chunkSize, i * chunkSize + chunkSize);
            }
            if (rest > 0) {
                arrays[chunks - 1] = Arrays.copyOfRange(bin, (chunks - 1) * chunkSize, (chunks - 1) * chunkSize + rest);
            }

            for (byte[] b : arrays) {
                serveurs.server1.uploadMP3(b, filenameText.getText());
            }*/

            byte[] bin = Files.readAllBytes(addedFile.toPath());
            MP3File f = (MP3File) AudioFileIO.read(addedFile);
            MP3AudioHeader mp3 = (MP3AudioHeader) f.getAudioHeader();
            System.out.println(mp3.getTrackLength());
            int duration = mp3.getTrackLength();
            if(genre.equals("Musique urbaine")) {
                serveurs.server1.uploadMP3(bin, path);
                serveurs.server1.addSong(titre, artiste, album, duration, path);
            } else {
                serveurs.server2.uploadMP3(bin, path);
                serveurs.server2.addSong(titre, artiste, album, duration, path);
            }
            loadHome();
        } else {
            System.out.println("Erreur form invalide");
            errorPane.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorPane.setVisible(false);
        ToggleGroup group = new ToggleGroup();

        genreUrbain.setToggleGroup(group);
        genreAutre.setToggleGroup(group);

        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            // Has selection.
            if (group.getSelectedToggle() != null) {
                RadioButton button = (RadioButton) group.getSelectedToggle();
                genre = button.getText();
            }
        });

    }
}
