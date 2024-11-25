
public class coffeenteaService extends Service {

    public coffeenteaService(String name, double pricepersession) {
        super(name, pricepersession);
    }

    @Override
    public double calculateCost(int sessions) {
        return pricepersession * sessions;
    }

}
