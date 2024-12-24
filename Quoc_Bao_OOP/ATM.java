public class ATM extends PaymentMethod {
    protected String cardNumber;
    protected String atmPin;

    public ATM(String cardNumber, Customer cus, String atmPin) {
        super("ATM", cus);
        this.cardNumber = cardNumber;
        this.atmPin = atmPin;
    }

    public ATM(Customer cus) {
        super("ATM", cus);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAtmPin() {
        return atmPin;
    }

    public void setAtmPin(String atmPin) {
        this.atmPin = atmPin;
    }

}
