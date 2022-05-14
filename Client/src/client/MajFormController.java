package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

public class MajFormController implements Initializable {

    @FXML private Button closeButton;
    @FXML private Button addFileButton;
    @FXML private Text filenameText;
    @FXML private TextField titreField;
    @FXML private TextField artisteField;
    @FXML private TextField albumField;
    @FXML private AnchorPane errorPane;

    protected StreamServer serveurs = new StreamServer();
    private File addedFile;

    private static Song songToEdit;

    public MajFormController() {
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

    public void setSongToEdit(Song songToEdit) {
        this.songToEdit = songToEdit;
    }

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

    public void updateSong() throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
        errorPane.setVisible(false);
        String titre = titreField.getText();
        String artiste = artisteField.getText();
        String album = albumField.getText();
        int duration;

        if(!titre.isBlank() && !artiste.isBlank() && !album.isBlank() && songToEdit !=null){
            duration=(int) songToEdit.getDuration();
            if(addedFile!=null) {
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
                    serveurs.server1.uploadMP3(b, path);
                }*/
                byte[] bin = Files.readAllBytes(addedFile.toPath());
                serveurs.server1.uploadMP3(bin, songToEdit.getPath().split("/")[1]);

                MP3File f = (MP3File) AudioFileIO.read(addedFile);
                MP3AudioHeader mp3 = (MP3AudioHeader) f.getAudioHeader();
                System.out.println(mp3.getTrackLength());
                duration = mp3.getTrackLength();
            }

            if(songToEdit.getGenre() == 1) serveurs.server1.updateSong(songToEdit.getId(), titre, artiste, album, duration);
            if(songToEdit.getGenre() == 2) serveurs.server2.updateSong(songToEdit.getId(), titre, artiste, album, duration);
            loadHome();
        } else {
            System.out.println("Erreur form invalide");
            errorPane.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorPane.setVisible(false);
        if(songToEdit!=null){
            titreField.setText(songToEdit.getTitre());
            artisteField.setText(songToEdit.getArtiste());
            albumField.setText(songToEdit.getAlbum());
            filenameText.setText(songToEdit.getPath().split("/")[1]);
        }

    }
}
