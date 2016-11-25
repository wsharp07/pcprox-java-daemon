import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonController;

import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Date;

public class AppConsole {
    private static App _app;
    public static void main(String[] args) {


        try {
            MongoService mongoService = new MongoService("pcprox");
            System.out.println("Connected? " + mongoService.IsConnected());

            mongoService.setCollection("card_reads");
            mongoService.Insert(createDBObject(new Date(), "4D0029418248"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        /*Runtime.getRuntime().addShutdownHook( new Thread() {
            @Override
            public void run() {
                System.out.println("Application shutdown");
                try {
                    _app.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("Running console test");
        try {
            _app = new App();
            _app.init(new ConsoleDaemonContext());
            _app.start();
        } catch (Exception e) {
            e.printStackTrace();
        } */
    }

    private static DBObject createDBObject(Date cardSwipeDate, String cardId) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
        docBuilder.append("cardSwipeDate", cardSwipeDate);
        docBuilder.append("cardId", cardId);
        return docBuilder.get();
    }
}

class ConsoleDaemonContext implements DaemonContext {

    @Override
    public DaemonController getController() {
        return null;
    }

    @Override
    public String[] getArguments() {
        return new String[0];
    }
}
