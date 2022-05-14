package client;

public class Song {

    private int id;
    protected String titre;
    protected String artiste;
    protected String path;
    protected String album;
    protected long duration;
    protected int genre;

    public Song(Demo.Song s){
        this.id = s.rowid;
        this.titre = s.titre;
        this.artiste = s.artiste;
        this.path = s.path;
        this.album = s.album;
        this.duration = s.duration;
        this.genre = s.genre;
    }

    public Song(int id, String titre, String artiste, String path, String album, long duration, int genre) {
        this.id = id;
        this.titre = titre;
        this.artiste = artiste;
        this.path = path;
        this.album = album;
        this.duration = duration;
        this.genre = genre;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", artiste='" + artiste + '\'' +
                ", path='" + path + '\'' +
                ", album='" + album + '\'' +
                ", duration=" + duration +
                ", genre=" + genre +
                '}';
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
