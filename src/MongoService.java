import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

public class MongoService {
    private String _hostName;
    private int _port;
    private String _databaseName;
    private MongoClient _client;

    String collection;

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public boolean IsConnected() {
        if (_client == null) return false;

        try {
            _client.getDB("mydb");
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    public MongoService(String databaseName) throws UnknownHostException {
        this("localhost", 27017, databaseName);
    }
    public MongoService(String hostName, int port, String databaseName) throws UnknownHostException {
        _hostName = hostName;
        _port = port;
        _databaseName = databaseName;

        _client = new MongoClient(_hostName, _port);
    }

    public boolean Insert(DBObject record) {
        if (!IsConnected()) return false;
        if (this.getCollection().isEmpty()) return false;

        DB db = _client.getDB(_databaseName);
        DBCollection col = db.getCollection(getCollection());
        col.insert(record);

        return true;
    }



}
