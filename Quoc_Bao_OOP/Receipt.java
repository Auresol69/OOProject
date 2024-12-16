import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Receipt extends ReceiptIdManager {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    protected Integer id;
    protected Customer cus;
    protected ArrayList<Booking> bookings_choosed;
    protected PaymentMethod paymentmethod;
    protected LocalDateTime dateTime;
    protected double totalCost = 0;

    public Receipt() {
        this.cus = new Customer();
        this.id = ReceiptIdManager.getNextId();
        this.bookings_choosed = new ArrayList<>();
        this.dateTime = LocalDateTime.now();
        this.totalCost = 0;
        cus.CongDiemTichLuy(this.totalCost);
    }

    public Receipt(Customer cus, ArrayList<Booking> bookings_choosed, PaymentMethod paymentmethod,
            LocalDateTime dateTime) {
        this.id = ReceiptIdManager.getNextId();
        this.cus = cus;
        this.bookings_choosed = (bookings_choosed != null) ? bookings_choosed : new ArrayList<>();
        this.paymentmethod = paymentmethod;
        this.dateTime = dateTime;
        this.totalCost = this.bookings_choosed.stream()
                .filter(booking -> booking != null)
                .mapToDouble(Booking::getPrice) // Lamda API Stream
                .sum();
        cus.CongDiemTichLuy(this.totalCost);
    }

    public Receipt(Customer cus, PaymentMethod paymentmethod, ArrayList<Booking> bookings_choosed) {
        this.id = ReceiptIdManager.getNextId();
        this.cus = cus;
        this.bookings_choosed = (bookings_choosed != null) ? bookings_choosed : new ArrayList<>();
        this.paymentmethod = paymentmethod;
        this.dateTime = LocalDateTime.now();
        this.totalCost = this.bookings_choosed.stream()
                .filter(booking -> booking != null)
                .mapToDouble(Booking::getPrice) // Lamda API Stream
                .sum();
        cus.CongDiemTichLuy(this.totalCost);
    }

    public PaymentMethod getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(PaymentMethod paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public Customer getCus() {
        return cus;
    }

    public void setCus(Customer cus) {
        this.cus = cus;
    }

    public ArrayList<Booking> getBookings_choosed() {
        return bookings_choosed;
    }

    public void setBookings_choosed(ArrayList<Booking> bookings_choosed) {
        this.bookings_choosed = (bookings_choosed != null) ? bookings_choosed : new ArrayList<>();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void main(String[] args) {
    }
}
