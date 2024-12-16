public class Momo extends PaymentMethod {
    protected String momoPin;
    protected String phoneNumber;

    public Momo(String momoPin, Customer cus, String phoneNumber) {
        super("Momo", cus);
        this.momoPin = momoPin;
        this.phoneNumber = phoneNumber;
    }

    public Momo(Customer cus) {
        super("Momo", cus);
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
