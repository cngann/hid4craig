public interface PttDeviceInterface {
    public PTTDevice initPttDevices();
    public void start(PTTDevice device);
    public void stop(PTTDevice device);
}
