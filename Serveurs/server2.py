import signal
import os
import sys
import Ice
from soup_db import getTrackPath, getAll, insertSong, deleteSong, majSong

Ice.loadSlice('Servers.ice')
import Demo

class ServeursI(Demo.Serveurs):
    dbname = 'soup_database2.db'
    def __init__(self, name):
        self.name = name

    def sayHelloServeurs(self, current=None):
        print(self.name + " says Hello World Serveurs!")

    def playSong(self, id, current=None):
        print("id = ", id)
        print(getTrackPath(id, ServeursI.dbname))

    def getAllSongs(self, current=None):
        fetched = getAll(ServeursI.dbname)
        songs = []
        for row in fetched:
            songs.append(Demo.Song(*row))
        return songs
    
    def uploadMP3(self, data, path, current=None):
        print("uploading ", path)
        songFile = open('tracks_serveur2/'+path, 'wb+')
        songFile.write(data)
        songFile.close()
    
    def addSong(self, titre, artiste, album, duration, path, current=None):
        print("adding song2 ", titre)
        insertSong(titre, artiste, album, duration,'tracks_serveur2/'+path, ServeursI.dbname, int(2))
    
    def delSong(self, id, path, current=None):
        print("deleting song ", path)
        if os.path.exists(path):
            print(path)
            os.remove(path)
        deleteSong(id, ServeursI.dbname)
    
    def updateSong(self, id, titre, artiste, album, duration, current=None):
        print("updating song[id = ",id,"]")
        majSong(id, titre, artiste, album, duration, ServeursI.dbname)
    
    

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
    adapter = communicator.createObjectAdapter("server2Adapter")
    id = Ice.stringToIdentity(properties.getProperty("Identity"))
    adapter.add(ServeursI(properties.getProperty("Ice.ProgramName")), id)
    adapter.activate()
    communicator.waitForShutdown()