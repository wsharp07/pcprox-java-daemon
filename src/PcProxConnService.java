import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PcProxConnService implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    public boolean isConnected = false;
    public volatile boolean isRunning = true;
    private PcProxAPI _api;

    public PcProxConnService(PcProxAPI api) {
        _api = api;
    }

    @Override
    public void run() {
        logger.debug("Starting connection thread");
        while(isRunning) {
            try {
                if (isConnected) break;

                logger.info("** Please wait... Trying to connect to reader in thread **");

                if(_api.connect()) {
                    logger.info("Ready to accept card reads.");
                    _api.beepNow(2,0);
                    isConnected = true;
                    break;
                }

                Thread.currentThread().sleep(250);

            } catch (InterruptedException ex) {
                isRunning = false;
            }
        }
    }

}
