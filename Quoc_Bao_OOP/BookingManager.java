import java.util.ArrayList;

public class BookingManager {
    protected ArrayList<Booking> bookings;

    public BookingManager() {
        bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void removeBooking(int index) {
        bookings.remove(index);
    }

    public void clearBooking() {
        bookings.clear();
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    public void findBookingByCustomer(Customer cus) {
        for (Booking booking : bookings) {
            if (booking.getCus() == cus)
                System.err.println(booking.getCus());
        }
    }
}
