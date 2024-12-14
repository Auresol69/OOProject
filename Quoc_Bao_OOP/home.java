import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class home {

    public static final String RESET = "\033[0m"; // Reset màu
    public static final String RED = "\033[0;31m"; // Đỏ
    public static final String GREEN = "\033[0;32m"; // Xanh lá
    public static final String YELLOW = "\033[0;33m"; // Vàng
    public static final String BLUE = "\033[0;34m"; // Xanh dương

    public home(String t) {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        boolean kt = true;
        account account = null;
        ArrayList<account> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/account.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split("#");
                String username = str[0];
                String password = str[1];
                int quyen = Integer.parseInt(str[2]);

                account ac = new account(username, password, quyen);
                System.out.println(ac.toString());
                list.add(ac);

            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        do {
            do {
                System.out.println("0. Dang nhap");
                System.out.println("1. Thoat");

                do {
                    System.out.print("Nhap lua chon : ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (!(choice >= 0 && choice <= 1)) {
                        System.out.println("Loi, Nhap lai");
                    }
                } while (!(choice >= 0 && choice <= 1));

                if (choice == 1) {
                    return;
                } else {

                    System.out.print("User name   : ");
                    String username = sc.nextLine();
                    System.out.print("password   : ");
                    String password = sc.nextLine();

                    for (account acc : list) {
                        if (username.trim().equals(acc.getUsername()) && password.trim().equals(acc.getPassword())) {
                            kt = false;
                            account = acc;
                        }
                    }
                }
            } while (kt);

            RoomManager rmng = new RoomManager();
            StaffManager smng = new StaffManager();
            CustomerManager cmng = new CustomerManager();
            ServiceManager svmng = new ServiceManager();

            if (account.getQuyen() == 0) {
                int luachon = 0;
                kt = true;
                boolean ktt = true;
                do {
                    System.out.println("0. Them");
                    System.out.println("1. Sua ");
                    System.out.println("2. Xoa");
                    System.out.println("3. thong ke");
                    System.out.println("4. Dang xuat");

                    do {
                        System.out.print("Nhap lua chon : ");
                        choice = sc.nextInt();
                        sc.nextLine();
                        if (!(choice >= 0 && choice <= 3)) {
                            System.out.println("Loi, Nhap lai");
                        }
                    } while (!(choice >= 0 && choice <= 3));

                    switch (choice) {
                        case 0:
                            do {
                                System.out.println("0. them lich");
                                System.out.println("1. them nhan vien");
                                System.out.println("2. them khach hang");
                                System.out.println("3. them dich vu ");
                                System.out.println("4. them phong");
                                System.out.println("5. Quay lai");

                                do {
                                    System.out.print("Nhap lua chon : ");
                                    choice = sc.nextInt();
                                    sc.nextLine();
                                    if (!(luachon >= 0 && luachon <= 5)) {
                                        System.out.println("Loi, Nhap lai");
                                    }
                                } while (!(luachon >= 0 && luachon <= 5));

                                switch (luachon) {
                                    case 1:
                                        System.out.println("Nhap ten nhan vien ");
                                        String name = sc.nextLine();
                                        System.out.println("nhap so dien thoai cua nhan vien ");
                                        String sdt = sc.nextLine();
                                        // kiem tra sdt da co chua
                                        // them staff
                                        break;
                                    case 2:
                                        System.out.println("Nhap so dien thoai cua khach hang");
                                        String sdt_kh = sc.nextLine();
                                        if (cmng.check(sdt_kh)) {
                                            System.out.println("So dien thoai da duoc su dung");
                                        } else {
                                            System.out.println("Nhap ten khach hang ");
                                            String ten = sc.nextLine();
                                            System.out.println("Nhap gioi tinh cua khach hang || 0. Nam     1.Nu");
                                            int gioitinh = sc.nextInt();
                                            sc.nextLine();
                                            do {
                                                System.out.print("Nhap lua chon : ");
                                                choice = sc.nextInt();
                                                sc.nextLine();
                                                if (!(luachon >= 0 && luachon <= 1)) {
                                                    System.out.println("Loi, Nhap lai");
                                                }
                                            } while (!(luachon >= 0 && luachon <= 1));

                                            if (luachon == 0) {
                                                Customer cus = new Customer(ten, ten, true);
                                                cmng.addCustomer(cus);
                                            } else {
                                                Customer cus = new Customer(ten, ten, false);
                                                cmng.addCustomer(cus);
                                            }
                                            System.out.println("Da tao thanh cong khach hang");
                                        }

                                        break;

                                    case 3:
                                        System.out.println("Cac dich vu da co ");
                                        for (Service sv : svmng.getavailableServices()) {
                                            System.out.println(sv.getName() + "  " + sv.getPricepersession());
                                        }

                                        System.out.println("Nhap ten dich vu");
                                        String ten = sc.nextLine();
                                        System.out.println("Nhap gia ");
                                        String gianhap = sc.nextLine();

                                        Service sv = new Service(ten, Double.parseDouble(gianhap));
                                        System.out.println("Da them thanh cong dich vu");
                                        break;

                                    case 4:
                                        rmng.show_list_room();

                                        System.out.println("Nhap ten phong : ");
                                        String ten_phong = sc.nextLine();
                                        System.out.println("Nhap kich co ");
                                        int size_phong = sc.nextInt();
                                        sc.nextLine();
                                        System.out.println("nhap gia phong ");
                                        String giaphongvao = sc.nextLine();
                                        System.out.println("nhap tang");
                                        int tang = sc.nextInt();
                                        sc.nextLine();
                                        double giaphong = Double.parseDouble(giaphongvao);
                                        if (ten_phong.contains("Vip") || ten_phong.contains("vip")) {
                                            Room r = new Vip_room(ten_phong, size_phong, 1, giaphong, tang);
                                        } else {
                                            Room r = new Standard_room(ten_phong, size_phong, 1, giaphong, tang);
                                        }
                                        System.out.println("Them Phong thanh cong ");

                                        break;

                                    case 5:
                                        ktt = false;
                                        break;
                                    default:
                                        System.out.println("Lua chon khong hop le ");
                                }

                                break;
                            } while (ktt);

                        case 1:
                            do {
                                System.out.println("0. Sua thong tin lich dat phong");
                                System.out.println("1. Sua thong tin nhan vien");
                                System.out.println("2. Sua thong tin khach hang");
                                System.out.println("3. Sua thong tin dich vu");
                                System.out.println("4. Sua thong tin phong hop");
                                System.out.println("5. Quay lai");

                                do {
                                    System.out.print("Nhap lua chon : ");
                                    choice = sc.nextInt();
                                    sc.nextLine();
                                    if (!(luachon >= 0 && luachon <= 5)) {
                                        System.out.println("Loi, Nhap lai");
                                    }
                                } while (!(luachon >= 0 && luachon <= 5));

                                switch (luachon) {
                                    case 0:
                                        // sua thong tin dat phogn
                                        break;
                                    case 1:
                                        // sua thong tin nhan vien
                                        break;
                                    case 2:
                                        // sua thong tin khach hang
                                        System.out.println("Nhap sdt khach hang ");
                                        String sdt_kh = sc.nextLine();
                                        if (cmng.check(t)) {
                                            Customer cus = cmng.get_cus(sdt_kh);
                                            System.out.println("Nhap so ten moi cua khach hang ");
                                            String tenkh = sc.nextLine();
                                            System.out.println("Nhap so dien thoai moi cua khach hang ");
                                            sdt_kh = sc.nextLine();
                                            cus.setName(tenkh);
                                            cus.setPhoneNumber(sdt_kh);

                                            System.out.println("Cap nhat thong tin thanh cong ");
                                        } else {
                                            System.out.println("khach hang khong ton tai");
                                        }
                                        break;
                                    case 3:
                                        // sua thong tin dich vu
                                        break;
                                    case 4:
                                        // sua thong tin Room
                                        break;

                                    default:
                                        throw new AssertionError();
                                }

                            } while (luachon == 5);

                            break;
                        case 2:
                            do {
                                System.out.println("0. Xoa lich dat phong");
                                System.out.println("1. Xoa thong tin nhan vien");
                                System.out.println("2. Xoa thong tin khach hang");
                                System.out.println("3. Xoa thong tin dich vu");
                                System.out.println("4. Xoa thong tin phong hop");
                                System.out.println("5. Quay lai");

                                do {
                                    System.out.print("Nhap lua chon : ");
                                    choice = sc.nextInt();
                                    sc.nextLine();
                                    if (!(luachon >= 0 && luachon <= 5)) {
                                        System.out.println("Loi, Nhap lai");
                                    }
                                } while (!(luachon >= 0 && luachon <= 5));

                                switch (luachon) {
                                    case 0:
                                        // Xoa thong tin dat phong
                                        break;
                                    case 1:
                                        // xoa thong tin nhan vien
                                        break;
                                    case 2:
                                        // sua thong tin khach hang
                                        System.out.println("Nhap sdt khach hang ");
                                        String sdt_kh = sc.nextLine();
                                        if (cmng.check(t)) {
                                            Customer cus = cmng.get_cus(sdt_kh);
                                            cmng.customers.remove(cus);

                                            System.out.println("Da xoa khach hang thanh cong ");
                                        } else {
                                            System.out.println("Da xoa khach hang thanh cong");
                                        }
                                        break;
                                    case 3:
                                        // xao thong tin dich vu
                                        System.out.println("Cac dich vu da co ");
                                        for (Service sv : svmng.getavailableServices()) {
                                            System.out.println(sv.getName() + "  " + sv.getPricepersession());
                                        }
                                        System.out.println("Nhap ten dich vu muon xoa ");
                                        String ten_dv = sc.nextLine();
                                        for (Service sv : svmng.getavailableServices()) {
                                            if (sv.getName().equalsIgnoreCase(ten_dv)) {
                                                svmng.getavailableServices().remove(sv);
                                            }
                                        }
                                        break;
                                    case 4:
                                        // Xoa thong tin Room

                                        rmng.show_list_room();
                                        System.out.println("Nhap ten phong ");
                                        String ten_phong = sc.nextLine();
                                        if (rmng.check_room(ten_phong)) {
                                            rmng.getList_room().remove(rmng.get_room(ten_phong));
                                        }

                                        break;

                                    default:
                                        throw new AssertionError();
                                }
                            } while (luachon == 5);

                            break;

                        case 3:
                            do {
                                System.out.println("0. Thong ke all ");
                                System.out.println("1. Thong ke theo quy");
                                System.out.println("2. Thong ke theo nam ");
                                System.out.println("3. Quay lai ");

                                do {
                                    System.out.print("Nhap lua chon : ");
                                    choice = sc.nextInt();
                                    sc.nextLine();
                                    if (!(luachon >= 0 && luachon <= 5)) {
                                        System.out.println("Loi, Nhap lai");
                                    }
                                } while (!(luachon >= 0 && luachon <= 5));

                                switch (luachon) {
                                    case 0:
                                        rmng.thong_ke();
                                        break;
                                    case 1:

                                        System.out.println("Nhap nam : ");
                                        int nam = sc.nextInt();
                                        sc.nextLine();
                                        rmng.thong_ke_theo_quy(nam);
                                        break;
                                    case 2:
                                        rmng.thong_ke_theo_nam();
                                        break;
                                    default:
                                        throw new AssertionError();
                                }

                            } while (luachon == 3);
                            break;
                        default:
                            throw new AssertionError();
                    }

                    if (choice == 0) {
                        kt = true;
                    }
                } while (choice == 0);
            }
        } while (kt);

    }

    public home() {
        RoomManager rm = new RoomManager();
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
                                                    cm.addCustomer(cus);
                                                    break;
                                                case 3:
                                                    Customer cus_xoa = null;
                                                    System.out.print("Nhap sdt khach hang ban muon xoa");
                                                    String phone_xoa = sc.nextLine();
                                                    cus_xoa = cm.get_cus(phone_xoa);
                                                    if (cus_xoa == null) {
                                                        System.out.println("Khach hang khong ton tai!");
                                                    }
                                                    cm.removeCustomer(cus_xoa);
                                                    break;
                                                case 4:
                                                    Customer cus_sua = null;
                                                    System.out.print("Nhap sdt khach hang ban muon xoa");
                                                    String phone_sua = sc.nextLine();
                                                    cus_sua = cm.get_cus(phone_sua);
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
                                                    cm.addCustomer(cus);
                                                    break;
                                                case 3:
                                                    Customer cus_xoa = null;
                                                    System.out.print("Nhap sdt khach hang ban muon xoa");
                                                    String phone_xoa = sc.nextLine();
                                                    cus_xoa = cm.get_cus(phone_xoa);
                                                    if (cus_xoa == null) {
                                                        System.out.println("Khach hang khong ton tai!");
                                                    }
                                                    cm.removeCustomer(cus_xoa);
                                                    break;
                                                case 4:
                                                    Customer cus_sua = null;
                                                    System.out.print("Nhap sdt khach hang ban muon xoa");
                                                    String phone_sua = sc.nextLine();
                                                    cus_sua = cm.get_cus(phone_sua);
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
        home h = new home("t");
    }
}
