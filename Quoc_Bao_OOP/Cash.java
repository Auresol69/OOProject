public class Cash extends PaymentMethod {
    public Cash() {
        super("Cash");
    }

    @Override
    public void ProcessPayment(double amount) {
        System.out.println("Processing cash payment of $" + amount);
            System.out.println();
    }
}
