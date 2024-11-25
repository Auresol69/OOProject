
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Receipt {
    protected String customerName;
    protected LocalDateTime dateTime;
    protected double totalCost;

    public Receipt(String customerName, LocalDateTime dateTime, Service[] services) {
        this.customerName = customerName;
        this.dateTime = dateTime;
    }

}
