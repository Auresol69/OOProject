import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReceiptManager {
    protected static ArrayList<Receipt> receipts;

    static {
        receipts = new ArrayList<>();
    }

    public static ArrayList<Receipt> getReceipts() {
        return receipts;
    }

    public static void setReceipts(ArrayList<Receipt> receipts) {
        ReceiptManager.receipts = receipts;
    }

    public static void showReceipt1Cus() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter customer's ID: ");
        String phone_cus = sc.nextLine();
        List<Booking> filteredBookings = BookingManager.bookings.stream()
                .filter(booking -> booking.getCus().getPhoneNumber().equals(phone_cus)
                        && booking.getReceiptid() != null)
                .collect(Collectors.toList());
        if (filteredBookings.isEmpty()) {
            System.out.println("Khong co hoa don nao voi so dien thoai nay!");
            return;
        }
        System.out.println("Danh sach hoa don cua khach hang co so dien thoai " + phone_cus + ":");
        for (Booking booking : filteredBookings) {
            System.out.println("ID: " + booking.getId() + ", Ten khach hang: " + booking.getCus().getName()
                    + ", Receipt ID: " + booking.getReceiptid());
        }
    }
}
