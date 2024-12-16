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

    public static String yeelow(String x) {
        return "\033[33m" + x + "\033[0m";
    }

    public static String red(String x) {
        return "\033[31m" + x + "\033[0m";
    }

    public static String green(String x) {
        return "\033[32m" + x + "\033[0m";
    }

    static DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public home(String t) {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        boolean kt = true;
        account acc = null;

        do {
            do {
                System.out.println("0. Dang nhap");
                System.out.println("1. Thoat");

                do {
                    System.out.print(yeelow("Nhap lua chon  : "));
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (!(choice == 0 || choice == 1)) {
                        System.out.println(red("Sai"));
                    }
                } while (!(choice == 0 || choice == 1));

                if (choice == 1) {
                    return;
                } else {
                    System.out.println("Username : ");
                    String username = sc.nextLine();
                    System.out.println("password ");
                    String pass = sc.nextLine();
                    acc = accountManager.getaccount(username, pass);
                }

            } while (acc == null);

            do {

                System.out.println(yeelow("╔" + RoomManager.border(70) + "╗"));
                System.out.println(yeelow("║") + RoomManager.form_SO("OPTION", 70) + yeelow("║"));
                System.out.println(yeelow("╠" + RoomManager.border(70) + "╣"));
                System.out.println(yeelow("║") + RoomManager.form_option("0. Lich dat phong ", 70) + yeelow("║"));
                System.out.println(yeelow("║") + RoomManager.form_option("1. Phong hop", 70) + yeelow("║"));
                System.out.println(yeelow("║") + RoomManager.form_option("2. Dich vu ", 70) + yeelow("║"));
                System.out.println(yeelow("║") + RoomManager.form_option("3. Nhan vien", 70) + yeelow("║"));
                System.out.println(yeelow("║") + RoomManager.form_option("4. Khach hang ", 70) + yeelow("║"));
                System.out.println(yeelow("║") + RoomManager.form_option("5. Thong ke ", 70) + yeelow("║"));
                System.out.println(yeelow("║") + RoomManager.form_option("6. Hoa don ", 70) + yeelow("║"));
                System.out.println(yeelow("║") + RoomManager.form_option("7. Tai khoan ", 70) + yeelow("║"));
                System.out.println(yeelow("║") + RoomManager.form_option("8. Dang xuat ", 70) + yeelow("║"));
                System.out.println(yeelow("╚" + RoomManager.border(70) + "╝"));

                do {
                    System.out.println(yeelow("Nhap lua chon  : "));
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (!(choice >= 0 && choice <= 6)) {
                        System.out.println("Sai");
                    }
                } while (!(choice >= 0 && choice <= 6));
                LocalDate begin = null;
                LocalDate end = null;
                switch (choice) {
                    case 0:
                        BookingManager.terminal_booking();

                        break;
                    case 1:
                        RoomManager.terminal_room();

                        break;
                    case 2:
                        ServiceManager.termianl_service();

                        break;

                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        BookingManager.cham();
                        System.out.println(yeelow("╔" + RoomManager.border(70) + "╗"));
                        System.out.println(yeelow("║") + RoomManager.form_SO("OPTION", 70) + yeelow("║"));
                        System.out.println(yeelow("╠" + RoomManager.border(70) + "╣"));
                        System.out.println(yeelow("║") + RoomManager.form_option("0. Thong ke booking theo quy ", 70)
                                + yeelow("║"));
                        System.out.println(
                                yeelow("║") + RoomManager.form_option("1. Thong ke theo nam ", 70) + yeelow("║"));
                        System.out.println(
                                yeelow("║") + RoomManager.form_option("2. Thong ke theo thoi gian ", 70) + yeelow("║"));
                        System.out.println(yeelow("║") + RoomManager.form_option("3. Quay lai", 70) + yeelow("║"));
                        System.out.println(yeelow("╚" + RoomManager.border(70) + "╝"));

                        do {
                            System.out.print("Nhap lua chon cua ban: ");
                            choice = Integer.parseInt(sc.nextLine());
                            if (!(choice >= 0 && choice <= 3)) {
                                System.out.println("Nhap lai !!");
                            }
                        } while (!(choice >= 0 && choice <= 3));

                        if (choice == 0) {
                            System.out.println("Nhap nam");
                            int nam = Integer.parseInt(sc.nextLine());
                            RoomManager.thong_ke_theo_quy(nam);
                        } else if (choice == 1) {
                            RoomManager.thong_ke_theo_nam();
                        } else if (choice == 2) {
                            do {

                                System.out.println("Nhap thoi gian bat dau (dd/MM/yyyy)");
                                String b = sc.nextLine();
                                System.out.println("Nhap thoi gian ket thuc (dd/MM/yyyy):");
                                String e = sc.nextLine();
                                begin = LocalDate.parse(b, f);
                                end = LocalDate.parse(e, f);
                            } while (begin == null || end == null);
                            RoomManager.thong_ke_bydate(begin, end);
                            begin = null;
                            end = null;
                        }

                        break;
                    case 6:
                        ReceiptManager.terminal_receipt();
                        break;
                    case 7:
                        accountManager.terminal_account(acc);
                        break;
                    case 8:
                        kt = false;

                        break;

                    default:
                        throw new AssertionError();
                }
            } while (choice != 6);

        } while (kt);

    }

    public home() {
        StaffManager smm = new StaffManager();
        CustomerManager cm = new CustomerManager();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Staff mode = new Admin("bao", "123", true);
        smm.getStaffs().add(mode);
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
                                System.out.println("6. Thanh toan don dat phong");
                                System.out.println("7. Dang xuat");
                                System.out.println("8. Thoat");

                                boolean level3 = true;
                                int option2 = Integer.parseInt(sc.nextLine());
                                switch (option2) {
                                    case 1:
                                        smm.themNhanvien();
                                        break;
                                    case 2:
                                        do {
                                            System.out.println("1. Show danh sach nhan vien");
                                            System.out.println("2. Doi trang thai nhan vien");
                                            System.out.println("3. Them nhan vien");
                                            System.out.println("4. Xoa nhan vien");
                                            System.out.println("5. Sua thong tin nhan vien");
                                            System.out.println("6. Quay lai");
                                            System.out.println("7. Thoat");
                                            int option3 = Integer.parseInt(sc.nextLine());
                                            switch (option3) {
                                                case 1:
                                                    smm.xuatInfoTatCaStaff();
                                                    break;
                                                case 2:
                                                    System.out.print("Nhap sdt cua nhan vien muon chinh sua: ");
                                                    String sdt = sc.nextLine();
                                                    smm.switchStaffStatus(sdt);
                                                    break;
                                                case 3:
                                                    smm.themNhanvien();
                                                    break;
                                                case 4:
                                                    Staff staff_xoa = null;
                                                    System.out.print("Nhap sdt nhan vien ban muon xoa");
                                                    String phone_xoa = sc.nextLine();
                                                    staff_xoa = smm.timNhanVien(phone_xoa);
                                                    if (staff_xoa == null) {
                                                        System.out.println("Nhan vien khong ton tai!");
                                                    }
                                                    smm.removeStaff(staff_xoa);
                                                    break;
                                                case 5:
                                                    Staff staff_sua = null;
                                                    System.out.print("Nhap sdt nhan vien ban muon xoa");
                                                    String phone_sua = sc.nextLine();
                                                    staff_sua = smm.timNhanVien(phone_sua);
                                                    if (staff_sua == null) {
                                                        System.out.println("Nhan vien khong ton tai!");
                                                    }
                                                    smm.editStaff(staff_sua);
                                                    break;
                                                case 6:
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
                                        do {
                                            System.out.println("1. Show danh sach khach hang");
                                            System.out.println("2. Them khach hang");
                                            System.out.println("3. Xoa khach hang");
                                            System.out.println("4. Sua thong tin khach hang");
                                            System.out.println("5. Quay lai");
                                            System.out.println("6. Thoat");
                                            int option3 = Integer.parseInt(sc.nextLine());
                                            switch (option3) {
                                                case 1:
                                                    cm.xuatInfoTatCaCustomer();
                                                    break;
                                                case 2:
                                                    Customer cus = new Customer();
                                                    cus.setInfo();
                                                    CustomerManager.addCustomer(cus);
                                                    break;
                                                case 3:
                                                    Customer cus_xoa = null;
                                                    System.out.print("Nhap sdt khach hang ban muon xoa");
                                                    String phone_xoa = sc.nextLine();
                                                    cus_xoa = CustomerManager.get_cus(phone_xoa);
                                                    if (cus_xoa == null) {
                                                        System.out.println("Khach hang khong ton tai!");
                                                    }
                                                    cm.removeCustomer(cus_xoa);
                                                    break;
                                                case 4:
                                                    Customer cus_sua = null;
                                                    System.out.print("Nhap sdt khach hang ban muon xoa");
                                                    String phone_sua = sc.nextLine();
                                                    cus_sua = CustomerManager.get_cus(phone_sua);
                                                    if (cus_sua == null) {
                                                        System.out.println("Khach hang khong ton tai!");
                                                    }
                                                    cm.editCustomer(cus_sua);
                                                    break;
                                                case 5:
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

                                                    RoomManager.history_by_date(sd1, ed1);
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
                                                            RoomManager.thong_ke_theo_nam();
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
                                                            RoomManager.thong_ke_bydate(sd2, ed2);
                                                            break;
                                                        case 3:
                                                            RoomManager.thong_ke_theo_quy(option4);
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
                                                    RoomManager.show_calendar(sd2, ed2, size);
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
                                        BookingManager.thanhToan();
                                        break;
                                    case 7:
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
                        }
                        if (account.getStaff() instanceof ServiceTeamMember) {
                            System.out.println(GREEN + "Welcome " + account.getStaff().getName() + RESET);
                            LocalDateTime now = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String formattedDate = now.format(formatter);

                            System.out.println(GREEN + "Current time: " + formattedDate + RESET);
                            do {

                                System.out.println("1. Thong tin cua minh");
                                System.out.println("2. Quan ly dat phong");
                                System.out.println("3. Quan ly khach hang");
                                System.out.println("4. Thanh toan don dat phong");
                                System.out.println("5. Dang xuat");
                                System.out.println("6. Thoat");

                                boolean level3 = true;
                                int option2 = Integer.parseInt(sc.nextLine());
                                switch (option2) {
                                    case 1:
                                        System.out.println(account.getStaff().XuatThongTin());
                                        break;
                                    case 2:
                                        BookingManager.terminal();
                                        break;
                                    case 3:
                                        do {
                                            System.out.println("1. Show danh sach khach hang");
                                            System.out.println("2. Them khach hang");
                                            System.out.println("3. Xoa khach hang");
                                            System.out.println("4. Sua thong tin khach hang");
                                            System.out.println("5. Quay lai");
                                            System.out.println("6. Thoat");
                                            int option3 = Integer.parseInt(sc.nextLine());
                                            switch (option3) {
                                                case 1:
                                                    cm.xuatInfoTatCaCustomer();
                                                    break;
                                                case 2:
                                                    Customer cus = new Customer();
                                                    cus.setInfo();
                                                    CustomerManager.addCustomer(cus);
                                                    break;
                                                case 3:
                                                    Customer cus_xoa = null;
                                                    System.out.print("Nhap sdt khach hang ban muon xoa");
                                                    String phone_xoa = sc.nextLine();
                                                    cus_xoa = CustomerManager.get_cus(phone_xoa);
                                                    if (cus_xoa == null) {
                                                        System.out.println("Khach hang khong ton tai!");
                                                    }
                                                    cm.removeCustomer(cus_xoa);
                                                    break;
                                                case 4:
                                                    Customer cus_sua = null;
                                                    System.out.print("Nhap sdt khach hang ban muon xoa");
                                                    String phone_sua = sc.nextLine();
                                                    cus_sua = CustomerManager.get_cus(phone_sua);
                                                    if (cus_sua == null) {
                                                        System.out.println("Khach hang khong ton tai!");
                                                    }
                                                    cm.editCustomer(cus_sua);
                                                    break;
                                                case 5:
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
                                    case 4:
                                        BookingManager.thanhToan();
                                        break;
                                    case 5:
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