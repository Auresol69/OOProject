import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class home {

    public home() {
        RoomManager rm = new RoomManager();
        StaffManager sm = new StaffManager();
        CustomerManager cm = new CustomerManager();
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
                            System.out.println("Welcome " + account.getStaff().getName());
                            LocalDateTime now = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String formattedDate = now.format(formatter);

                            System.out.println("Current time: " + formattedDate);

                            do {
                                System.out.println("1. Dang ky them nhan vien");
                                System.out.println("2. Quan ly nhan vien");
                                System.out.println("3. Quan ly khach hang");
                                System.out.println("4. Quan ly phong");
                                System.out.println("5. Thong ke");
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
                                                    System.out.print("Nhap sdt cua nhan vien muon chinh sua:");
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
                                    case 6:
                                        account = null;
                                        level2 = false;
                                        break;
                                    case 7:
                                        account = null;
                                        level1 = false;
                                        level2 = false;
                                    default:
                                        break;
                                }
                            } while (level2);
                        } else {
                            System.out.println("Welcome " + account.getStaff().getName());
                            LocalDateTime now = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String formattedDate = now.format(formatter);

                            System.out.println("Current time: " + formattedDate);
                        }
                    }
                    break;
                case 2:
                    account = null;
                    level1 = false;
                    break;
                default:
                    break;
            }

        } while (level1);

    }

    public static void main(String[] args) {
        home h = new home();
    }
}
