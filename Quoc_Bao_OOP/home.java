import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class home {

    public static final String RESET = "\033[0m"; // Reset màu
    public static final String RED = "\033[0;31m"; // Đỏ
    public static final String GREEN = "\033[0;32m"; // Xanh lá
    public static final String YELLOW = "\033[0;33m"; // Vàng
    public static final String BLUE = "\033[0;34m"; // Xanh dương

    public home() {
        RoomManager rm = new RoomManager();
        StaffManager sm = new StaffManager();
        CustomerManager cm = new CustomerManager();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Staff mode = new Admin("bao", "123", true);
        sm.getStaffs().add(mode);
        StaffAccount modeAccount = new StaffAccount("wuocpao", "123", mode);
        StaffAccount.accounts.put("wuocpao", modeAccount);
        mode.setStaffAccount(modeAccount);

        StaffAccount account = null;
        Scanner sc = new Scanner(System.in);
        boolean level1 = true;
        do {

            System.out.println("1. Dang nhap");
            System.out.println("2. Thoat");
            System.out.print("Chon chuc nang: ");

            boolean level2 = true;
            int option1 = Integer.parseInt(sc.nextLine());

            switch (option1) {
                case 1:
                    System.out.print("Username:");
                    String username = sc.nextLine();
                    System.out.print("Password:");
                    String password = sc.nextLine();
                    account = StaffAccount.signIn(username, password);

                    if (account != null) {
                        if (account.getStaff() instanceof Admin) {
                            System.out.println(GREEN + "Welcome " + account.getStaff().getName() + RESET);
                            LocalDateTime now = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String formattedDate = now.format(formatter);

                            System.out.println(GREEN + "Current time: " + formattedDate + RESET);

                            do {
                                System.out.println("1. Dang ky them nhan vien");
                                System.out.println("2. Quan ly nhan vien");
                                System.out.println("3. Quan ly khach hang");
                                System.out.println("4. Quan ly phong");
                                System.out.println("5. Quan ly dat phong");
                                System.out.println("6. Dang xuat");
                                System.out.println("7. Thoat");

                                boolean level3 = true;
                                int option2 = Integer.parseInt(sc.nextLine());
                                switch (option2) {
                                    case 1:
                                        sm.themNhanvien();
                                        break;
                                    case 2:
                                        sm.xuatInfoTatCaStaff();
                                        do {
                                            System.out.println("1. Doi trang thai nhan vien");
                                            System.out.println("2. Quay lai");
                                            System.out.println("3. Thoat");
                                            int option3 = Integer.parseInt(sc.nextLine());
                                            switch (option3) {
                                                case 1:
                                                    System.out.print("Nhap sdt cua nhan vien muon chinh sua: ");
                                                    String sdt = sc.nextLine();
                                                    sm.switchStaffStatus(sdt);
                                                    break;
                                                case 2:
                                                    level3 = false;
                                                    break;
                                                default:
                                                    account = null;
                                                    level1 = false;
                                                    level2 = false;
                                                    level3 = false;
                                                    break;
                                            }
                                        } while (level3);
                                        break;
                                    case 3:
                                        cm.xuatInfoTatCaCustomer();
                                        break;
                                    case 4:
                                        do {
                                            System.out.println("1. Lich su");
                                            System.out.println("2. Thong ke");
                                            System.out.println("3. Lich theo ngay");
                                            System.out.println("4. Quay lai");
                                            System.out.println("5. Thoat");
                                            int option3 = Integer.parseInt(sc.nextLine());
                                            switch (option3) {
                                                case 1:
                                                    System.out.println("Nhap ngay bat dau va ket thuc");

                                                    System.out.print("Start date: ");
                                                    String startDate1 = sc.nextLine();
                                                    LocalDate sd1 = LocalDate.parse(startDate1, f);

                                                    System.out.print("End date: ");
                                                    String endDate1 = sc.nextLine();
                                                    LocalDate ed1 = LocalDate.parse(endDate1, f);

                                                    rm.history_by_date(sd1, ed1);
                                                    break;
                                                case 2:
                                                    System.out.println("1. Thong ke theo nam");
                                                    System.out.println("2. Thong ke theo thoi gian");
                                                    System.out.println("3. Thong ke theo quy");
                                                    System.out.println("4. Quay lai");
                                                    System.out.println("5. Thoat");
                                                    int option4 = Integer.parseInt(sc.nextLine());
                                                    switch (option4) {
                                                        case 1:
                                                            rm.thong_ke_theo_nam();
                                                            break;
                                                        case 2:
                                                            System.out.println();
                                                            System.out.println("Nhap ngay bat dau va ket thuc");

                                                            System.out.print("Start date: ");
                                                            String startDate2 = sc.nextLine();
                                                            LocalDate sd2 = LocalDate.parse(startDate2, f);

                                                            System.out.print("End date: ");
                                                            String endDate2 = sc.nextLine();
                                                            LocalDate ed2 = LocalDate.parse(endDate2, f);
                                                            rm.thong_ke_bydate(sd2, ed2);
                                                            break;
                                                        case 3:
                                                            rm.thong_ke_theo_quy(option4);
                                                            break;
                                                        case 4:
                                                            break;
                                                        default:
                                                            break;
                                                    }
                                                    break;
                                                case 3:
                                                    System.out.println("Nhap ngay bat dau va ket thuc");

                                                    System.out.print("Start date: ");
                                                    String startDate2 = sc.nextLine();
                                                    LocalDate sd2 = LocalDate.parse(startDate2, f);

                                                    System.out.print("End date: ");
                                                    String endDate2 = sc.nextLine();
                                                    LocalDate ed2 = LocalDate.parse(endDate2, f);

                                                    System.out.print("Size: ");
                                                    int size = Integer.parseInt(sc.nextLine());
                                                    rm.show_calendar(sd2, ed2, size);
                                                    break;
                                                case 4:
                                                    level3 = false;
                                                    break;
                                                default:
                                                    account = null;
                                                    level1 = false;
                                                    level2 = false;
                                                    level3 = false;
                                                    break;
                                            }
                                        } while (level3);
                                        break;
                                    case 5:
                                        BookingManager.terminal();
                                        break;
                                    case 6:
                                        account = null;
                                        level2 = false;
                                        break;
                                    default:
                                        account = null;
                                        level1 = false;
                                        level2 = false;
                                        break;
                                }
                            } while (level2);
                        } else {
                            System.out.println(GREEN + "Welcome " + account.getStaff().getName() + RESET);
                            LocalDateTime now = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String formattedDate = now.format(formatter);

                            System.out.println(GREEN + "Current time: " + formattedDate + RESET);
                            do {
                                System.out.println("1. Thong tin cua minh");
                                System.out.println("2. Quan ly dat phong");
                                System.out.println("3. Dang xuat");
                                System.out.println("4. Thoat");
                                int option2 = Integer.parseInt(sc.nextLine());
                                switch (option2) {
                                    case 1:
                                        account.getStaff().XuatThongTin();
                                        break;
                                    case 2:
                                        BookingManager.terminal();
                                        break;
                                    case 3:
                                        account = null;
                                        level2 = false;
                                        break;
                                    default:
                                        account = null;
                                        level2 = false;
                                        level1 = false;
                                        break;
                                }
                            } while (level2);
                        }
                    }
                    break;
                default:
                    account = null;
                    level1 = false;
                    break;
            }

        } while (level1);

    }

    public static void main(String[] args) {
        home h = new home();
    }
}
