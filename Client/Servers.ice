module Demo
{
    struct Song {
        int rowid;
        string titre;
        string artiste;
        string album;
        int duration;
        string path;
        int genre;
    };

    sequence<Song> SongList;
    sequence<byte> ByteData;

    interface Serveurs
    {
        void sayHelloServeurs();
        void playSong(int id);
        SongList getAllSongs();
        void uploadMP3(ByteData data, string path);
        void addSong(string titre, string artiste, string album, int duration, string path);
        void delSong(int id, string path);
        void updateSong(int id, string titre, string artiste, string album, int duration);
    }

    interface VLC
    {
        void sayHelloVLC();
        void playStream();
        void pauseStream();
        void stopStream();
        void playSong(int id, int genre);
    }
}