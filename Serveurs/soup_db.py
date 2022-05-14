import sqlite3
import pandas as pd


def setupDB1():
      conn = sqlite3.connect('soup_database1.db') 
      c = conn.cursor()

      c.execute('''
            CREATE TABLE IF NOT EXISTS tracks
            ([titre] TEXT, [artiste] TEXT, [album] TEXT, [duration] INTEGER, [path] TEXT, [genre] INTEGER)
            ''')
                        
      c.execute('''
            INSERT INTO tracks (titre, artiste, album, duration, path, genre)

                  VALUES
                  ('Tarkov', 'Freeze Corleone', 'LMF', 230, 'tracks_serveur1/tarkov.mp3', 1)
            ''')

      c.execute('''
            INSERT INTO tracks (titre, artiste, album, duration, path, genre)

                  VALUES
                  ('Chen Laden', 'Freeze Corleone', 'LMF', 199, 'tracks_serveur1/17-Chen-Laden.mp3', 1)
            ''')

      conn.commit()

      c.execute('''
            SELECT * from tracks
            ''') 

def setupDB2():
      conn = sqlite3.connect('soup_database2.db') 
      c = conn.cursor()

      c.execute('''
            CREATE TABLE IF NOT EXISTS tracks
            ([titre] TEXT, [artiste] TEXT, [album] TEXT, [duration] INTEGER, [path] TEXT, [genre] INTEGER)
            ''')
                        
      c.execute('''
            INSERT INTO tracks (titre, artiste, album, duration, path, genre)

                  VALUES
                  ('Something In The Way', 'Nirvana', 'Nevermind', 230, 'tracks_serveur2/something-in-the-way.mp3', 2)
            ''')

      conn.commit()
def main():
      setupDB1()
      setupDB2()

def getTrackPath(id, dbname):
      conn = sqlite3.connect(dbname) 
      c = conn.cursor()
      c.execute("select path from tracks where rowid = ?", (id,))
      df = pd.DataFrame(c.fetchall(), columns=(['path']))
      return df.iloc[0]['path']

def displayTracksPath(dbname):
      conn = sqlite3.connect(dbname) 
      c = conn.cursor()
      c.execute("select path from tracks")
      df = pd.DataFrame(c.fetchall(), columns=(['path']))
      print("df = ", df) 

def insertSong(titre, artiste, album, duration, path, dbname, genre):
      conn = sqlite3.connect(dbname) 
      c = conn.cursor()
      c.execute("INSERT INTO tracks (titre, artiste, album, duration, path, genre) VALUES (?, ?, ?, ?, ?, ?)", (titre, artiste, album, duration, path, genre))
      conn.commit()

def deleteSong(id, dbname):
      conn = sqlite3.connect(dbname) 
      c = conn.cursor()
      c.execute("DELETE FROM tracks where rowid = ?", (id,))
      conn.commit()

def majSong(id, titre, artiste, album, duration, dbname):
      conn = sqlite3.connect(dbname) 
      c = conn.cursor()
      c.execute("UPDATE tracks SET titre = ?, artiste = ?, album = ?, duration = ? WHERE rowid = ?", (titre, artiste, album, duration, id))
      conn.commit()

def getAll(dbname):
      conn = sqlite3.connect(dbname) 
      c = conn.cursor()
      c.execute('''
            SELECT rowid, titre, artiste, album, duration, path, genre from tracks
            ''')
      return c.fetchall()        

if __name__ == '__main__':
        main()
        s = getAll("soup_database2.db")
        for c in s:
              print(c)