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
            CardData data = new CardData(new Date(), "2950129512");

            mongoService.setCollection("card_reads");
            mongoService.Insert(createDBObject(data));
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

    private static DBObject createDBObject(CardData data) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
        docBuilder.append("cardSwipeDate", data.getCardSwipeDate());
        docBuilder.append("cardId", data.getCardId());
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
