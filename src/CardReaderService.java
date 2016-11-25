import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.UnknownHostException;
import java.util.Date;

public class CardReaderService implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    private static final Logger cardLogger = LogManager.getLogger("cardId");
    private MongoService _mongoService;
    private PcProxAPI _api;

    public CardReaderService(PcProxAPI api) throws UnknownHostException {
        _api = api;
        _mongoService = new MongoService("pcprox");
        _mongoService.setCollection("cardReads");
    }

    @Override
    public void run() {
        logger.debug("Starting card reader thread");
        while(!Thread.currentThread().isInterrupted()) {

            String cardId = _api.getCardId();

            if (cardId.isEmpty() == false) {
                cardLogger.info("Card Read: " + cardId);
                CardData cardRead = new CardData(new Date(), cardId);
                _mongoService.Insert(CardData.createDBObject(cardRead));

                if (_api.getLastError() > 0) {
                    logger.warn("Lost reader connection");
                }
            }

            sleep(250);
        }
    }

    void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
