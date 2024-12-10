public class ATM extends PaymentMethod {
    protected String cardNumber;
    protected String atmPin;

    public ATM(String cardNumber, String atmPin) {
        super("ATM");
        this.cardNumber = cardNumber;
        this.atmPin = atmPin;
    }

    public ATM(Customer cus) {
        super("ATM");
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

    @Override
    public void ProcessPayment(double amount) {
        int length = cardNumber.length();
        String maskedCardNumber = "x".repeat(Math.max(0, length - 4)) + cardNumber.substring(length - 4);
        System.out.println("Processing ATM payment of $" + amount + " using card " + maskedCardNumber);
    }
}
