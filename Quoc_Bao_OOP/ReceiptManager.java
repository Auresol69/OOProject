import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReceiptManager {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

    public static void printReceipts() {
        for (Receipt receipt : ReceiptManager.receipts) {
            System.out.println(
                    "ReceiptID: " + receipt.getId() + " Bookings: " + receipt.getBookings_choosed() + " PaymentMethod: "
                            + receipt.getPaymentmethod() + " Total: " + receipt.getTotalCost() + " Customer: "
                            + receipt.getCus() + " Time: "
                            + receipt.getDateTime());
        }
    }

    public static void terminal_receipt() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("0. Hien thi danh sach hoa don");
            System.out.println("1. Hien thi hoa don theo khach hang");
            System.out.println("2. In hoa don");
            System.out.println("3. Quay lai");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 0:
                    ReceiptManager.printReceipts();
                    break;
                case 1:
                    ReceiptManager.showReceipt1Cus();
                    break;
                case 2:
                    ReceiptManager.showReceipt();
                    break;
                case 3:
                    return;
                default:
                    break;
            }
        }

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
        Scanner sc = new Scanner(System.in);
        showReceipt1Cus();
        System.out.print("Nhap so ReceiptID ma ban muon in: ");
        int id = Integer.parseInt(sc.nextLine());
        Receipt rc = ReceiptManager.receipts.stream().filter(receipt -> receipt.getId() == id).findFirst().orElse(null);

        if (rc == null) {
            System.out.println("Khong tim thay hoa don nay!");
            return;
        }

        String formattedDate = rc.getDateTime().format(formatter);
        System.out.print("╔" + border(39) + "╗" + "\n");
        System.out.print("║" + form_SO("RECEIPT") + "║" + "\n");
        System.out.print("╠" + border(39) + "╣" + "\n");
        System.out.print("║" + form_SO("Date: " + formattedDate) + "║" + "\n");
        System.out.print("║" + form_SO("BookingID:", 20) + form_SO("Price:", 19) + "║" + "\n");

        if (rc.bookings_choosed != null)
            for (Booking booking : rc.bookings_choosed) {
                System.out
                        .print("║" + form_SO(booking.getId(), 20) + form_SO(booking.getPrice(), 19) + "║" + "\n");
            }
        System.out.print("║" + form_SO("Total Cost: " + rc.getTotalCost()) + "║" + "\n");
        System.out.println("╚" + border(39) + "╝");
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
                    + ", Receipt ID: " + booking.getReceiptid() + ", Total: " + booking.getPrice());
        }
    }
}
