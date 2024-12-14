import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.script.ScriptEngineManager;

public class BookingManager {
    protected static ArrayList<Booking> bookings;

    static {
        bookings = new ArrayList<>();
    }

    public static void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public BookingManager(RoomManager rmng, CustomerManager cmng,ServiceManager svmng ){
        ArrayList<Booking> list = new ArrayList<>();
         try (BufferedReader br  = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/booking.txt"))){
            String line ;
            while((line = br.readLine())!=null){
            String[] str = line.split("#");
            int id = Integer.parseInt(str[0]);
            double price = Double.parseDouble(str[1]) ;
            int idreciep = Integer.parseInt(str[1]) ;
            Customer cus = Integer.parseInt(str[2]);

            String[] dichvu = str[3].split("$");
            ArrayList<Service> list_sv = new ArrayList<>();
            for (String t : dichvu){
                list_sv.add(svmng.getdichvu(t.trim()));
            }
            int tang = Integer.parseInt(str[4]) ;
            Room room;
            
            list.add(room);
    
        }
       } catch (Exception e) {
        // TODO: handle exception
       }
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
                        if (BookingManager.removeBooking(id_remove)) {
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

    public static void thanhToan() {
        CustomerManager cm = new CustomerManager();
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap so dien thoai nguoi dung de thanh toan: ");
        String phone_cus = sc.nextLine();
        // Loc booking theo sdtn
        List<Booking> filteredBookings = bookings.stream()
                .filter(booking -> booking.getCus().getPhoneNumber().equals(phone_cus)).collect(Collectors.toList());
        if (filteredBookings == null) {
            System.out.println("Khong tim thay Booking nao cho so dien thoai nay.");
            return;
        }
        // Hien thi Bookings
        filteredBookings
                .forEach(booking -> System.out.println("Id: " + booking.getId() + ", total: " + booking.getPrice()
                        + ", ReceiptID: " + booking.getReceiptid()));
        ArrayList<Booking> bookingList = new ArrayList<>();
        while (true) {
            try {
                System.out.print("Chon ID booking de thanh toan: ");
                int id_thanhtoan = Integer.parseInt(sc.nextLine());
                Booking bookingToPay = filteredBookings.stream().filter(booking -> booking.getId() == id_thanhtoan)
                        .findFirst().orElse(null);
                if (bookingToPay == null) {
                    System.out.println("Khong tim thay Booking voi ID nay.");
                    continue;
                }
                if (bookingList.contains(bookingToPay)) {
                    System.out.println("Booking nay da duoc them vao danh sach thanh toan.");
                } else {
                    bookingList.add(bookingToPay);
                    System.out.println("Da them Booking voi ID " + id_thanhtoan + " vao danh sach thanh toan.");
                }
                System.out.print("Ban co muon them ID booking khac khong? (1 = co, 0 = khong): ");
                int option = Integer.parseInt(sc.nextLine());
                if (option == 0) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so nguyen hop le.");
            }
        }
        if (!bookingList.isEmpty()) {
            Customer cus = cm.getCustomers().stream()
                    .filter(customer -> customer.getPhoneNumber().equals(phone_cus))
                    .findFirst().orElse(null);

            if (cus != null) {
                PaymentMethod paymentmethod = null;
                while (paymentmethod == null) {
                    System.out.println(" ╔═══════════════════════════════════╗");
                    System.out.println(" ║    CHON PHUONG THUC THANH TOAN    ║");
                    System.out.println(" ╠═══════════════════════════════════╣");
                    System.out.println(" ║  1. Cash                          ║");
                    System.out.println(" ║  2. Momo                          ║");
                    System.out.println(" ║  3. ATM                           ║");
                    System.out.println(" ╚═══════════════════════════════════╝");
                    System.out.print(" Nhập lựa chọn của bạn: ");
                    System.out.print("Lua chon: ");
                    int option = Integer.parseInt(sc.nextLine());
                    switch (option) {
                        case 1:
                            paymentmethod = new Cash();
                            break;
                        case 2:
                            System.out.print("Nhap Momo PIN: ");
                            String momoPin = sc.nextLine();
                            System.out.print("Nhap Momo phone: ");
                            String momoPhone = sc.nextLine();
                            paymentmethod = new Momo(momoPin, momoPhone);
                            break;
                        case 3:
                            System.out.print("Nhap so the ATM: ");
                            String cardNumber = sc.nextLine();
                            System.out.print("Nhap ATM PIN: ");
                            String atmPin = sc.nextLine();
                            paymentmethod = new ATM(cardNumber, atmPin);
                            break;
                        default:
                            System.out.println("Lua chon khong hop le!");
                            return;
                    }
                    paymentmethod.ProcessPayment(0); // de tam la 0 vi chua lay dc price
                }

                Receipt rc = new Receipt(cus, paymentmethod, bookingList);
                ReceiptManager.receipts.add(rc);
                for (Booking booking : bookingList) {
                    booking.setReceiptid(rc.getId());
                }
                System.out.println("Thanh toan thanh cong. Receipt ID: " + rc.getId());
                System.out.println("So hoa don cua ban la: " + rc.getId());
            } else {
                System.out.println("Khong tim thay khach hang voi so dien thoai: " + phone_cus);
            }
        } else {
            System.out.println("Khong co Booking nao duoc thanh toan.");
        }
        ReceiptManager.showReceipt1Cus();
    }
}
