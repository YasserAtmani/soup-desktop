package client;

import Demo.ServeursPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

public class ServeursCollection {

    protected String dest1 = "Serveur1:ssl -h 206.81.26.59 -p 10000";
    protected Communicator communicator1 = Util.initialize(null, "config.client.old");
    protected ObjectPrx base1 = communicator1.stringToProxy(dest1);
    public ServeursPrx serveur1 = ServeursPrx.checkedCast(base1);

    protected String dest2 = "Serveur2:ssl -h 206.81.26.59 -p 10001";
    protected Communicator communicator2 = Util.initialize(null, "config.client.old");
    protected ObjectPrx base2 = communicator1.stringToProxy(dest2);
    public ServeursPrx serveur2 = ServeursPrx.checkedCast(base2);

    private static ServeursCollection serveursCollection = null;

    public ServeursCollection() {
        super();
    }

    public static ServeursCollection getInstance() {
        synchronized (ServeursCollection.class) {
            if(serveursCollection == null) {
                serveursCollection = new ServeursCollection();
            }
        }
        return serveursCollection;
    }
}
