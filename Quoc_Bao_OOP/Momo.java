public class Momo extends PaymentMethod {
    protected String momoPin;
    protected String phoneNumber;

    public Momo(String momoPin, String phoneNumber) {
        super("Momo");
        this.momoPin = momoPin;
        this.phoneNumber = phoneNumber;
    }

    public Momo() {
        super("Momo");
    }

    @Override
    public void ProcessPayment(double amount) {
        System.out
                .println("Processing Momo payment of " + amount + "$" + " using phone number " + getPhoneNumber());
    }

    public String getMomoPin() {
        return momoPin;
    }

    public void setMomoPin(String momoPin) {
        this.momoPin = momoPin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
