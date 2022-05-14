//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.7
//
// <auto-generated>
//
// Generated from file `Servers.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Demo;

public interface Serveurs extends com.zeroc.Ice.Object
{
    void sayHelloServeurs(com.zeroc.Ice.Current current);

    void playSong(int id, com.zeroc.Ice.Current current);

    Song[] getAllSongs(com.zeroc.Ice.Current current);

    void uploadMP3(byte[] data, String path, com.zeroc.Ice.Current current);

    void addSong(String titre, String artiste, String album, int duration, String path, com.zeroc.Ice.Current current);

    void delSong(int id, String path, com.zeroc.Ice.Current current);

    void updateSong(int id, String titre, String artiste, String album, int duration, com.zeroc.Ice.Current current);

    /** @hidden */
    static final String[] _iceIds =
    {
        "::Demo::Serveurs",
        "::Ice::Object"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::Demo::Serveurs";
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_sayHelloServeurs(Serveurs obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        obj.sayHelloServeurs(current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_playSong(Serveurs obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        int iceP_id;
        iceP_id = istr.readInt();
        inS.endReadParams();
        obj.playSong(iceP_id, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getAllSongs(Serveurs obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        Song[] ret = obj.getAllSongs(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        SongListHelper.write(ostr, ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_uploadMP3(Serveurs obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        byte[] iceP_data;
        String iceP_path;
        iceP_data = istr.readByteSeq();
        iceP_path = istr.readString();
        inS.endReadParams();
        obj.uploadMP3(iceP_data, iceP_path, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_addSong(Serveurs obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_titre;
        String iceP_artiste;
        String iceP_album;
        int iceP_duration;
        String iceP_path;
        iceP_titre = istr.readString();
        iceP_artiste = istr.readString();
        iceP_album = istr.readString();
        iceP_duration = istr.readInt();
        iceP_path = istr.readString();
        inS.endReadParams();
        obj.addSong(iceP_titre, iceP_artiste, iceP_album, iceP_duration, iceP_path, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_delSong(Serveurs obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        int iceP_id;
        String iceP_path;
        iceP_id = istr.readInt();
        iceP_path = istr.readString();
        inS.endReadParams();
        obj.delSong(iceP_id, iceP_path, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_updateSong(Serveurs obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        int iceP_id;
        String iceP_titre;
        String iceP_artiste;
        String iceP_album;
        int iceP_duration;
        iceP_id = istr.readInt();
        iceP_titre = istr.readString();
        iceP_artiste = istr.readString();
        iceP_album = istr.readString();
        iceP_duration = istr.readInt();
        inS.endReadParams();
        obj.updateSong(iceP_id, iceP_titre, iceP_artiste, iceP_album, iceP_duration, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /** @hidden */
    final static String[] _iceOps =
    {
        "addSong",
        "delSong",
        "getAllSongs",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "playSong",
        "sayHelloServeurs",
        "updateSong",
        "uploadMP3"
    };

    /** @hidden */
    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return _iceD_addSong(this, in, current);
            }
            case 1:
            {
                return _iceD_delSong(this, in, current);
            }
            case 2:
            {
                return _iceD_getAllSongs(this, in, current);
            }
            case 3:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 5:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 6:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 7:
            {
                return _iceD_playSong(this, in, current);
            }
            case 8:
            {
                return _iceD_sayHelloServeurs(this, in, current);
            }
            case 9:
            {
                return _iceD_updateSong(this, in, current);
            }
            case 10:
            {
                return _iceD_uploadMP3(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
