
public class wifiService extends Service {

    public wifiService(String name, double pricepersession) {
        super(name, pricepersession);
    }

    @Override
    public double calculateCost(int sessions) {
        return pricepersession * sessions;
    }
}
