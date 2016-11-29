public class PcProxAPI {
    private boolean isConnected = false;
    private int numberOfDevices = 0;

    public enum LED_COLOR {
        NONE,
        GREEN,
        RED,
        AMBER
    }

    public boolean connect(){
        if (isConnected) return true;

        boolean rc = tryToConnect();
        if (rc) {
            numberOfDevices = PcProxSO.getDeviceCount();
            isConnected = true;
        }

        return numberOfDevices > 0;
    }

    private boolean tryToConnect(){
        PcProxSO.usbDisconnect();
        PcProxSO.setDeviceSearchType(0); // usb
        return PcProxSO.usbConnect() > 0;
    }

    public void disconnect() {
        PcProxSO.usbDisconnect();
    }

    public void setLEDColor(LED_COLOR color) {
        initSetLEDColor();
        switch(color) {
            case NONE:
                setLEDOff();
                break;
            case AMBER:
                setLEDColorAmber();
                break;
            case GREEN:
                setLEDColorGreen();
                break;
            case RED:
                setLEDColorRed();
                break;
            default:
                setLEDColorRed();
                break;
        }
    }

    private void initSetLEDColor() {
        PcProxSO.setAppCtrlsLED(1);
        PcProxSO.setLEDCtrlVolatile(1);
    }

    private void setLEDColorGreen() {
        PcProxSO.setRedLEDState(0);
        PcProxSO.setGrnLEDState(1);
    }

    private void setLEDColorRed() {
        PcProxSO.setRedLEDState(1);
        PcProxSO.setGrnLEDState(0);
    }

    private void setLEDColorAmber() {
        PcProxSO.setRedLEDState(1);
        PcProxSO.setGrnLEDState(1);
    }

    private void setLEDOff() {
        PcProxSO.setRedLEDState(0);
        PcProxSO.setGrnLEDState(0);
    }

    public LED_COLOR getLEDColor() {
        int greenLEDValue = PcProxSO.getGrnLEDState();
        int redLEDValue = PcProxSO.getRedLEDState();

        boolean redLEDIsOn = redLEDValue == 1;
        boolean greenLEDIsOn = greenLEDValue == 1;

        if (redLEDIsOn && greenLEDIsOn) {
            return LED_COLOR.AMBER;
        }

        if ((redLEDIsOn == false) && (greenLEDIsOn == false)) {
            return LED_COLOR.NONE;
        }

        if (redLEDIsOn && (greenLEDIsOn == false)) {
            return LED_COLOR.RED;
        }

        if ((redLEDIsOn == false) && greenLEDIsOn) {
            return LED_COLOR.GREEN;
        }

        return LED_COLOR.RED; // default
    }

    public int getLastError(){
        return PcProxSO.getLastLibErr();
    }

    public void beepNow(int count, int beepType) {
        PcProxSO.beepNow(count,beepType);
    }

    public String getCardId() {

        byte[] byteArray = new byte[36];

        PcProxSO.getQueuedId(1,1);

        for (int j=0;j<35;j++) {
            byteArray[j] = PcProxSO.getQueuedIdIndex(j);
        }

        int nbits = byteArray[32];
        if (nbits == 0) return "";

        return getHexString(byteArray);
    }

    private String getHexString(byte[] buf) {
        StringBuilder sb = new StringBuilder();

        for (int i=0;i<6; i++) {
            String formattedString = String.format("%02X ", buf[i] & 255);
            sb.append(formattedString);
        }

        return sb.toString();
    }
}
