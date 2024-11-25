
public class technicalSupportService extends Service {

    public technicalSupportService(String name, double pricepersession) {
        super(name, pricepersession);
    }

    @Override
    public double calculateCost(int sessions) {
        return pricepersession * sessions;
    }

}
