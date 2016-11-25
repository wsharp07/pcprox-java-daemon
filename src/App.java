import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App implements Daemon {

    private PcProxConnService _pcProxConnService;
    private CardReaderService _cardReaderService;
    private ExecutorService executor;
    private static final Logger logger = LogManager.getLogger();

    private void loadLibs() {
        System.loadLibrary("hidapi-hidraw");
    }
    private void writeJavaLibPaths(){
        String javaLibPath = System.getProperty("java.library.path");
        String jnaLibPath = System.getProperty("jna.library.path");

        logger.debug("Java.library.path = '" + javaLibPath + "'");
        logger.debug("Jna.library.path = '" + jnaLibPath + "'");
    }

    public void run() throws ExecutionException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        executor.submit(_pcProxConnService).get();
        executor.submit(_cardReaderService);
        executor.shutdown();
    }

    @Override
    public void init(DaemonContext context) throws DaemonInitException, Exception {
        logger.info("Service initializing");
        _pcProxConnService = new PcProxConnService(new PcProxAPI());
        _cardReaderService = new CardReaderService(new PcProxAPI());
    }

    @Override
    public void start() throws Exception {
        logger.info("Service starting up");
        writeJavaLibPaths();
        loadLibs();

        try {
            run();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void stop() throws Exception {
        logger.info("Service shutting down");
        
        executor.shutdownNow();
    }

    @Override
    public void destroy() {
        logger.info("Service stopped");
    }
}
