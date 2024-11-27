import java.util.ArrayList;

public class BookingManager {
    protected static ArrayList<Booking> bookings;

    public static void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public static void removeBooking(int index) {
        bookings.remove(index);
    }

    public static void clearBooking() {
        bookings.clear();
    }

    public static ArrayList<Booking> getBookings() {
        return bookings;
    }

    public static void setBookings(ArrayList<Booking> bookings) {
        BookingManager.bookings = bookings;
    }

    // THAO TÁC
    public static void terminal() {

    }

    public static void findBookingByCustomer(Customer cus) {
        for (Booking booking : bookings) {
            if (booking.getCus() == cus)
                System.out.println(booking.getCus().XuatThongTin());
        }
    }
}
