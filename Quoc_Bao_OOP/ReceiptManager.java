import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReceiptManager {
    public static final String RESET = "\033[0m"; // Reset màu
    public static final String RED = "\033[0;31m"; // Đỏ
    public static final String GREEN = "\033[0;32m"; // Xanh lá
    public static final String YELLOW = "\033[0;33m"; // Vàng
    public static final String BLUE = "\033[0;34m"; // Xanh dương

    public static String yeelow(String x) {
        return "\033[33m" + x + "\033[0m";
    }

    public static String red(String x) {
        return "\033[31m" + x + "\033[0m";
    }

    public static String green(String x) {
        return "\033[32m" + x + "\033[0m";
    }

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    protected static ArrayList<Receipt> receipts;

    static {
        receipts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/receipt.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lines = line.split("#");
                int id = Integer.parseInt(lines[0]);
                Customer cus = CustomerManager.get_cus(lines[1]);
                if (cus == null) {
                    cus = new Customer("####", "####", false);
                }
                ArrayList<Booking> booking_choosed = new ArrayList<Booking>();
                String[] bookings = lines[2].split("\\$");
                for (int i = 0; i < bookings.length; i++) {
                    Booking booking = BookingManager.get_booking_byID(Integer.parseInt(bookings[i]));
                    if (booking == null) {
                        booking = new Booking(-1, 0, -1, cus, null, null);
                    }
                    booking_choosed.add(booking);
                }
                PaymentMethod paymentmethod = PaymentmethodManager.getPaymentMethodById(Integer.parseInt(lines[3]));
                LocalDateTime dateTime = LocalDateTime.parse(lines[4], formatter);
                double totalCost = Double.parseDouble(lines[5]);
                Receipt receipt = new Receipt(id, cus, booking_choosed, paymentmethod, dateTime, totalCost);
                ReceiptManager.addReceipt(receipt);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
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

    public static void addReceipt(Receipt rc) {
        if (rc != null && receipts.contains(rc)) {
            receipts.add(rc);
            writeToFile();
        }
    }

    public static void writeToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./Quoc_Bao_OOP/data/receipt.txt"))) {
            for (Receipt rc : receipts) {
                StringBuilder sb = new StringBuilder();
                sb.append(rc.getId()).append("#");
                sb.append(rc.getCus().getPhoneNumber()).append("#");
                for (int i = 0; i < rc.getBookings_choosed().size(); i++) {
                    sb.append(rc.getBookings_choosed().get(i).getId()).append("$");
                    if (i < rc.getBookings_choosed().size() - 1) {
                        sb.append("$");
                    }
                }
                sb.append("#");
                sb.append(rc.getPaymentmethod().getId()).append("#");
                sb.append(rc.getDateTime().format(formatter)).append("#");
                sb.append(rc.getTotalCost());
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public static void showReceipt(Receipt rc) {

        if (rc == null) {
            System.out.println("Khong tim thay hoa don nay!");
            return;
        }

        String formattedDate = rc.getDateTime().format(formatter);
        System.out.print(green("╔" + border(39) + "╗" + "\n"));
        System.out.print(green("║" + form_SO("RECEIPT") + "║" + "\n"));
        System.out.print(green("╠" + border(39) + "╣" + "\n"));
        System.out.print(green("║" + form_SO("Date: " + formattedDate) + "║" + "\n"));
        System.out.print(green("║" + form_SO("BookingID:", 20) + form_SO("Price:", 19) + "║" + "\n"));

        if (rc.bookings_choosed != null)
            for (Booking booking : rc.bookings_choosed) {
                System.out
                        .print(green(
                                "║" + form_SO(booking.getId(), 20) + form_SO(booking.getPrice(), 19) + "║" + "\n"));
            }
        System.out.print(green("║" + form_SO("Total Cost: " + rc.getTotalCost()) + "║" + "\n"));
        System.out.print(green("║" + form_SO("PaymentMethod: " + rc.getPaymentmethod().getMethodName()) + "║" + "\n"));
        System.out.println(green("╚" + border(39) + "╝"));
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
        System.out.print(green("╔" + border(39) + "╗" + "\n"));
        System.out.print(green("║" + form_SO("RECEIPT") + "║" + "\n"));
        System.out.print(green("╠" + border(39) + "╣" + "\n"));
        System.out.print(green("║" + form_SO("Date: " + formattedDate) + "║" + "\n"));
        System.out.print(green("║" + form_SO("BookingID:", 20) + form_SO("Price:", 19) + "║" + "\n"));

        if (rc.bookings_choosed != null)
            for (Booking booking : rc.bookings_choosed) {
                System.out
                        .print(green(
                                "║" + form_SO(booking.getId(), 20) + form_SO(booking.getPrice(), 19) + "║" + "\n"));
            }
        System.out.print(green("║" + form_SO("Total Cost: " + rc.getTotalCost()) + "║" + "\n"));
        System.out.print(green("║" + form_SO("PaymentMethod: " + rc.getPaymentmethod().getMethodName()) + "║" + "\n"));
        System.out.println(green("╚" + border(39) + "╝"));
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
