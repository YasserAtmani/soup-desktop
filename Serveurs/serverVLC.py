import signal
import sys
import Ice
Ice.loadSlice('Servers.ice')
import Demo
import vlc
from soup_db import getTrackPath, getAll, insertSong, deleteSong, majSong

# DOCUMENTATION : https://www.olivieraubert.net/vlc/python-ctypes/doc/
# help : https://forum.ubuntu-fr.org/viewtopic.php?id=2057412
# help : https://forum.videolan.org/viewtopic.php?t=90233

Instance = vlc.Instance()
player = Instance.media_player_new()
stream_option = "sout=#http{mux=ts,ttl=10,port=8080,sdp=http://:8080/}"

def setSong(media):
    Media = Instance.media_new(media)
    Media.add_option(stream_option)
    Media.get_mrl()
    player.set_media(Media)
    player.play()

class VlcI(Demo.VLC):
    def __init__(self, name):
        self.name = name

    def sayHelloVLC(self, current):
        print(self.name + " says Hello World VLC!")

    def playStream(self, context=None):
        print ("play")
        player.play()

    def pauseStream(self, context=None):
        print ("pause")
        player.pause()
        
    def stopStream(self, context=None):
        print ("stop")
        player.stop()
    
    def playSong(self, id, genre, context=None):
        print ("playing song["+str(id)+"] sur le serveur "+str(genre))
        if genre==1:
            setSong(getTrackPath(id, 'soup_database1.db'))
        if genre==2:
            setSong(getTrackPath(id, 'soup_database2.db'))
#
# Ice.initialize returns an initialized Ice communicator,
# the communicator is destroyed once it goes out of scope.
#
with Ice.initialize(sys.argv) as communicator:

    #
    # Install a signal handler to shutdown the communicator on Ctrl-C
    #
    signal.signal(signal.SIGINT, lambda signum, frame: communicator.shutdown())

    #
    # The communicator initialization removes all Ice-related arguments from argv
    #
    if len(sys.argv) > 1:
        print(sys.argv[0] + ": too many arguments")
        sys.exit(1)

    properties = communicator.getProperties()
    adapter = communicator.createObjectAdapter("vlcAdapter")
    id = Ice.stringToIdentity(properties.getProperty("Identity"))
    adapter.add(VlcI(properties.getProperty("Ice.ProgramName")), id)
    adapter.activate()
    communicator.waitForShutdown()