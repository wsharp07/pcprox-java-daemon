import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonController;

public class AppConsole {
    private static App _app;
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook( new Thread() {
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
        }
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
