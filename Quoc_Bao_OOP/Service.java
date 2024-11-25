
public abstract class Service {
    protected String name;
    protected double pricepersession;

    public Service() {
    }

    public Service(String name, double pricepersession) {
        this.name = name;
        this.pricepersession = pricepersession;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricepersession() {
        return pricepersession;
    }

    public void setpricepersession(double pricepersession) {
        this.pricepersession = pricepersession;
    }

    public abstract double calculateCost(int hours);

}
