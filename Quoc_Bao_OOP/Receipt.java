import java.time.LocalDateTime;

public class Receipt {
    protected Customer customer;
    protected LocalDateTime dateTime;
    protected double totalCost = 0;

    public Receipt(Customer customer, LocalDateTime dateTime) {
        this.customer = customer;
        this.dateTime = dateTime;
    }
}
