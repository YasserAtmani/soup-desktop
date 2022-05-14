package client;

import Demo.ServeursPrx;
import Demo.VLCPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;

public class StreamServer {

    protected static Communicator communicator = Util.initialize(null, "config.client");
    public static ServeursPrx server1 = ServeursPrx.checkedCast(communicator.stringToProxy("serveur1"));
    public static ServeursPrx server2 = ServeursPrx.checkedCast(communicator.stringToProxy("serveur2"));
    public static VLCPrx serverVLC = VLCPrx.checkedCast(communicator.stringToProxy("serveurVLC"));

    public StreamServer() {
    }

    public void play(){
        //serverVLC.playStream();
        System.out.println("play");
    }

    public void pause(){
        //serverVLC.pauseStream();
        System.out.println("pause");
    }

    public void stop(){
        //serverVLC.stopStream();
        System.out.println("stop");
    }
}
