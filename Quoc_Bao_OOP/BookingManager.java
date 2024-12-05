import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BookingManager {
    protected static ArrayList<Booking> bookings;

    static {
        bookings = new ArrayList<>();
    }

    public static void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public static boolean removeBooking(int index) {
        if (index < 0 || index >= bookings.size())
            return false;
        bookings.remove(index);
        return true;
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

    public static void printList() {
        for (Booking b : bookings) {
            System.out.println(b.getCus().XuatThongTin() + " " + "id: " + b.getId());
        }
    }

    // THAO TÁC

    public static void terminal() {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to Booking Terminal:");
            System.out.println("1. Add new booking");
            System.out.println("2. Remove booking");
            System.out.println("3. Clear all bookings");
            System.out.println("4. List all bookings");
            System.out.println("5. Find booking by phone number");
            System.out.println("0. Exit");
            System.out.println("Nhập một số nguyên (0 để thoát): ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Bạn phải nhập một số nguyên!");
                continue;
            }
            switch (choice) {
                case 1:
                    Booking tmp = new Booking();
                    tmp.setInfo();
                    break;
                case 2:
                    System.out.println("Nhập ID của đặt chỗ cần xóa:");
                    int id_remove;
                    try {
                        id_remove = Integer.parseInt(sc.nextLine());
                        if (BookingManager.removeBooking(id_remove - 1)) {
                            System.out.println("Đã xóa đặt chỗ thành công!");
                        } else {
                            System.out.println("Không tìm thấy đặt chỗ với ID này.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: ID phải là số nguyên!");
                    }
                    break;
                case 3:
                    BookingManager.clearBooking();
                    break;
                case 4:
                    BookingManager.printList();
                    break;
                case 5:
                    String num;
                    num = sc.nextLine();
                    BookingManager.findBookingByPhoneNumber(num);
                    break;
                case 0:
                    break;
                default:
                    break;
            }
            if (choice == 0) {
                System.out.println("Exiting...");
                break;
            }
        }
    }

    public static void findBookingByPhoneNumber(String phonenumber) {
        boolean found = false;

        for (Booking booking : bookings) {
            if (booking != null && booking.getCus() != null &&
                    phonenumber.equals(booking.getCus().getPhoneNumber())) {
                System.out.println(booking.getCus().XuatThongTin() + " " + "id: " + booking.getId());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No booking found with the phone number: " + phonenumber);
        }
    }

}
