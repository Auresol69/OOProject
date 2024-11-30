import java.util.ArrayList;

public class BookingManager {
    protected static ArrayList<Booking> bookings;

    static {
        bookings = new ArrayList<>();
    }

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

    public static void findBookingByPhoneNumber(String phonenumber) {
        boolean found = false;

        for (Booking booking : bookings) {
            if (booking != null && booking.getCus() != null &&
                    phonenumber.equals(booking.getCus().getPhoneNumber())) {
                System.out.println(booking.getCus().XuatThongTin());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No booking found with the phone number: " + phonenumber);
        }
    }

}
