module Demo
{
    interface VLC
    {
        void playStream();
        void pauseStream();
        void stopStream();
    }

    interface Serveurs
    {
        void printString(string s);
        void playSong(int id);
    }
}