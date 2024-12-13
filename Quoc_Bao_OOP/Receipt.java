import java.time.LocalDateTime;
import java.util.ArrayList;

public class Receipt extends ReceiptIdManager {
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

    public static String form_SO(Object c) {
        StringBuilder tmp = new StringBuilder();
        int totalSpace = (39 - c.toString().length());
        int space_left = totalSpace / 2;
        int space_right = totalSpace - space_left;
        for (int i = 0; i < space_left; i++) {
            tmp.append(" ");
        }
        tmp.append(c.toString());
        for (int i = 0; i < space_right; i++) {
            tmp.append(" ");
        }
        return tmp.toString();
    }

    public static String form_SO(Object c, int space) {
        StringBuilder tmp = new StringBuilder();
        int totalSpace = (space - c.toString().length());
        int space_left = totalSpace / 2;
        int space_right = totalSpace - space_left;
        for (int i = 0; i < space_left; i++) {
            tmp.append(" ");
        }
        tmp.append(c.toString());
        for (int i = 0; i < space_right; i++) {
            tmp.append(" ");
        }
        return tmp.toString();
    }

    public static String border(int a) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < a; i++) {
            b.append("═");
        }
        return b.toString();
    }

    public static void showReceipt() {
        // System.out.println("ID: " + this.id);
        // System.out.println("Customer Name: " + this.cus.getName());
        // System.out.println("DateTime: " + this.dateTime);
        // System.out.println("Total Cost: " + this.totalCost);
        // System.out.println("Payment Method: " + this.paymentmethod.getMethodName());
        // System.out.println("Bookings: ");
        // for (Booking booking : this.bookings_choosed) {
        // booking.showBooking();
        // }
        System.out.print("╔" + border(39) + "╗" + "\n");
        System.out.print("║" + form_SO("RECEIPT") + "║" + "\n");
        System.out.print("╚" + border(39) + "╝" + "\n");

    }

    public static void main(String[] args) {
        Receipt.showReceipt();
    }
}
