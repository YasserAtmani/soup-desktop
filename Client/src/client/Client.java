package client;

import Demo.ServeursPrx;
import Demo.VLCPrx;

public class Client
{
    public static void main(String[] args) {
        ServeursPrx server1;
        ServeursPrx server2;
        VLCPrx serverVLC;
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "config.client"))
        {
            server1 = ServeursPrx.checkedCast(communicator.stringToProxy("serveur1"));
            server2 = ServeursPrx.checkedCast(communicator.stringToProxy("serveur2"));
            serverVLC = VLCPrx.checkedCast(communicator.stringToProxy("serveurVLC"));

            server1.sayHelloServeurs();
            server2.sayHelloServeurs();
            serverVLC.sayHelloVLC();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}