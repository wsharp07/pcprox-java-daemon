import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CardReaderService implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    private PcProxAPI _api;

    public CardReaderService(PcProxAPI api) {
        _api = api;
    }

    @Override
    public void run() {
        logger.debug("Starting card reader thread");
        while(!Thread.currentThread().isInterrupted()) {

            String cardId = _api.getCardId();

            if (cardId.isEmpty() == false) {
                logger.info("Card Read: " + cardId);

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
