import java.time.LocalDateTime;
import java.util.ArrayList;

public class Receipt {
    protected ArrayList<Booking> bookings_choosed;
    protected LocalDateTime dateTime;
    protected double totalCost = 0;

    public Receipt() {
        this.bookings_choosed = new ArrayList<>();
        this.dateTime = LocalDateTime.now();
        this.totalCost = 0;
    }

    public Receipt(ArrayList<Booking> bookings_choosed, LocalDateTime dateTime) {
        this.bookings_choosed = (bookings_choosed != null) ? bookings_choosed : new ArrayList<>();
        this.dateTime = dateTime;
        this.totalCost = this.bookings_choosed.stream()
                .filter(booking -> booking != null)
                .mapToDouble(Booking::getPrice) // Lamda API Stream
                .sum();
    }

    public Receipt(ArrayList<Booking> bookings_choosed, double totalCost) {
        this.bookings_choosed = (bookings_choosed != null) ? bookings_choosed : new ArrayList<>();
        this.dateTime = LocalDateTime.now();
        this.totalCost = this.bookings_choosed.stream()
                .filter(booking -> booking != null)
                .mapToDouble(Booking::getPrice) // Lamda API Stream
                .sum();
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
}
