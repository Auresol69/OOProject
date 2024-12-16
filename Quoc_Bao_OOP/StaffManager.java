import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class StaffManager {
    protected static ArrayList<Staff> staffs;
    protected int MaxFloor = 3;

    public StaffManager() {
        staffs = new ArrayList<>();
    }

    public ArrayList<Staff> getStaffs() {
        return staffs;
    }

    public static void setStaffs(ArrayList<Staff> staffs) {

        StaffManager.staffs = staffs;
    }

    public String spacing(Object c) {
        StringBuilder k = new StringBuilder();
        k.append("");
        int space_b = (35 - c.toString().length()) / 2;
        int space_a = 35 - c.toString().length() - space_b;
        for (int i = 0; i < space_b; i++) {
            k.append(" ");
        }
        k.append(c.toString());
        for (int i = 0; i < space_a; i++) {
            k.append(" ");
        }

        return k.toString();
    }

    public static String yeelow(String x) {
        return "\033[33m" + x + "\033[0m";
    }

    public static String red(String x) {
        return "\033[31m" + x + "\033[0m";
    }

    public static String green(String x) {
        return "\033[32m" + x + "\033[0m";
    }

    public String spacing(Object c, int n) {
        StringBuilder k = new StringBuilder();
        k.append("");
        int space_b = (n - c.toString().length()) / 2;
        int space_a = n - c.toString().length() - space_b;
        for (int i = 0; i < space_b; i++) {
            k.append(" ");
        }
        k.append(c.toString());
        for (int i = 0; i < space_a; i++) {
            k.append(" ");
        }

        return k.toString();
    }

    DecimalFormat form_tien = new DecimalFormat("#,###.00");

    public static String createBorderLine(int a) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < a; i++) {
            b.append("═");
        }
        return b.toString();
    }

    public static String border_thuong(int a) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < a; i++) {
            b.append("-");
        }
        return b.toString();
    }

    public ArrayList<Integer> layDanhSachTangChuaCoAdmin(int MaxFloor) {

        Set<Integer> tangDaCoAdmin = new HashSet<>();
        for (Staff nv : staffs) {
            if (nv instanceof Admin) {
                tangDaCoAdmin.add(nv.getFloor());
            }
        }

        // Danh sách các tầng chưa có Admin
        ArrayList<Integer> tangChuaCoAdmin = new ArrayList<>();
        for (int i = 1; i <= MaxFloor; i++) {
            if (!tangDaCoAdmin.contains(i)) {
                tangChuaCoAdmin.add(i);
            }
        }

        return tangChuaCoAdmin;
    }

    public int soLuongPhucVuTrenTang(int floor) {
        int count = 0;
        for (Staff nv : staffs) {
            if (nv instanceof ServiceTeamMember && nv.getFloor() == floor) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<Integer> layDanhSachTangChuaCoNhanVienPhucVu(int MaxFloor) {
        ArrayList<Integer> tangChuaCoNhanVienPhucVu = new ArrayList<>();
        for (int i = 1; i <= MaxFloor; i++) {
            if (soLuongPhucVuTrenTang(i) < 5) {
                tangChuaCoNhanVienPhucVu.add(i);
            }
        }
        return tangChuaCoNhanVienPhucVu;
    }

    public void removeStaff(Staff staff) {
        if (staffs.contains(staff)) {
            staffs.remove(staff);

            // Xóa StaffAccount tương ứng với nhân viên này
            String username = staff.getStaffAccount().getUsername();
            if (StaffAccount.accounts.containsKey(username)) {
                StaffAccount.accounts.remove(username);
                System.out.println("Đã xóa tài khoản của nhân viên: " + username);
            } else {
                System.out.println("Không tìm thấy tài khoản liên kết với nhân viên.");
            }
        } else {
            System.out.println("Nhân viên không tồn tại trong danh sách.");
        }
    }

    public void editStaff(Staff staff) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ban muon chinh sua cai gi?");
        System.out.println("1. Name");
        System.out.println("2. Phonenumber");
        System.out.println("3. Sex");
        System.out.println("4. Username");
        System.out.println("5. Password");
        int option = -1;
        while (true) {
            try {
                System.out.print("Nhap lua chon: ");
                option = Integer.parseInt(sc.nextLine());
                if (option >= 1 && option <= 3) {
                    break;
                }
                System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 3.");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
            }
        }

        switch (option) {
            case 1:
                System.out.print("Nhap ten: ");
                String name = sc.nextLine();
                staff.setName(name);
                break;
            case 2:
                System.out.print("Nhap so dien thoai: ");
                String phonenumber = sc.nextLine();
                StaffManager smm = new StaffManager();
                if (smm.timNhanVien(phonenumber) != null) {
                    System.out.println("So dien thoai nay da ton tai.");
                } else {
                    staff.setPhoneNumber(phonenumber);
                }
                break;
            case 3:
                System.out.print("Nhap gioi tinh: (Nam/Nu)");
                Boolean sex = sc.nextLine().equalsIgnoreCase("Nam");
                staff.setSex(sex);
                break;
            case 4:
                System.out.print("Nhap username: ");
                String username = sc.nextLine();
                if (StaffAccount.accounts.containsKey(username)) {
                    System.out.println("Username nay da ton tai.");
                } else {
                    staff.getStaffAccount().setUsername(username);
                }
                break;
            case 5:
                System.out.print("Nhap password: ");
                String password = sc.nextLine();
                staff.getStaffAccount().setPassword(password);
                break;
            default:
                break;
        }
    }

    public void themNhanVienTuFile(File name) {
        if (staffs == null) {
            staffs = new ArrayList<>();
        }

        try (Scanner sc = new Scanner(name)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] arrStrings = line.split("#");

                if (arrStrings.length < 7) {
                    System.out.println("Dòng dữ liệu không hợp lệ: " + line);
                    continue;
                }

                Staff a = null;

                try {
                    if (arrStrings[5].equals("0")) {
                        a = new Admin();
                        ((Admin) a).setSoGioLam(Integer.parseInt(arrStrings[6]));
                    } else if (arrStrings[5].equals("1")) {
                        a = new ServiceTeamMember();
                        ((ServiceTeamMember) a).setSoGioLam(Integer.parseInt(arrStrings[6]));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi định dạng số ở dòng: " + line);
                    continue;
                }

                if (a != null) {
                    a.setName(arrStrings[0]);
                    a.setPhoneNumber(arrStrings[1]);
                    a.setSex(Boolean.parseBoolean(arrStrings[2]));
                    a.setStatus(Boolean.parseBoolean(arrStrings[3]));
                    a.setFloor(Integer.parseInt(arrStrings[4]));

                    staffs.add(a); // Thêm vào danh sách staffs
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Lỗi: File không tồn tại - " + e.getLocalizedMessage());
        }

        for (Staff a : staffs) {
            System.out.println(a.XuatThongTin());
        }
    }

    public void themNhanvien() {
        Scanner sc = new Scanner(System.in);

        System.out.println(yeelow("╔" + RoomManager.border(70) + "╗"));
        System.out.println(yeelow("║") + RoomManager.form_SO("OPTION", 70) + yeelow("║"));
        System.out.println(yeelow("╠" + RoomManager.border(70) + "╣"));
        System.out.println(yeelow("║") + RoomManager.form_option("1. Nhân viên quản lý", 70) + yeelow("║"));
        System.out.println(yeelow("║") + RoomManager.form_option("2. Nhân viên phục vụ ", 70) + yeelow("║"));

        System.out.println(yeelow("╚" + RoomManager.border(70) + "╝"));
        int chon = Integer.parseInt(sc.nextLine());
        Staff nv = null;

        switch (chon) {
            case 1: // Nhân viên quản lý
                ArrayList<Integer> tangChuaCoAdmin = layDanhSachTangChuaCoAdmin(MaxFloor);
                if (tangChuaCoAdmin.isEmpty()) {
                    System.out.println("╔═" + createBorderLine(80) + "═╗");
                    System.out.println("║" + spacing("Tất cả các tầng đã có Admin!", 80) + "║");
                    System.out.println("╚═" + createBorderLine(80) + "═╝");
                    break;
                }
                System.out.println("╔═" + createBorderLine(80) + "═╗");
                System.out.println("║" + spacing("Các tầng chưa có Admin: " + tangChuaCoAdmin, 80) + "║");
                System.out.println("╚═" + createBorderLine(80) + "═╝");

                nv = new Admin();
                while (true) {
                    nv.nhapThongtin();

                    if (tangChuaCoAdmin.contains(nv.getFloor())) {

                        System.out.println("Nhập thông tin tài khoản cho nhân viên:");
                        System.out.print("Username: ");
                        String username = sc.nextLine();
                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        StaffAccount newAccount = new StaffAccount();
                        boolean isSignUp = newAccount.signUp(username, password, nv);
                        if (!isSignUp) {
                            System.out.println("Tạo tài khoản không thành công. Vui lòng thử lại.");
                            continue;
                        }

                        System.out.println("╔═" + createBorderLine(80) + "═╗");
                        System.out.println("║" + spacing("Admin đã được thêm vào tầng " + nv.getFloor(), 80) + "║");
                        System.out.println("╚═" + createBorderLine(80) + "═╝");
                        break;
                    } else {
                        System.out.println("╔═" + createBorderLine(80) + "═╗");
                        System.out.println("║" + spacing("WARNING:Tầng không hợp lệ, Hãy nhập lại.", 80) + "║");
                        System.out.println("╚═" + createBorderLine(80) + "═╝");
                    }
                }
                break;

            case 2: // Nhân viên phục vụ
                ArrayList<Integer> tangChuaCoNhanVienPhucVu = layDanhSachTangChuaCoNhanVienPhucVu(MaxFloor);
                if (tangChuaCoNhanVienPhucVu.isEmpty()) {
                    System.out.println("╔═" + createBorderLine(80) + "═╗");
                    System.out.println("║" + spacing("Tất cả các tầng đã có nhân viên phục vụ!", 80) + "║");
                    System.out.println("╚═" + createBorderLine(80) + "═╝");
                    break;
                }
                System.out.println("╔═" + createBorderLine(80) + "═╗");
                System.out.println(
                        "║" + spacing("Các tầng chưa có ServiceTeamMember: " + tangChuaCoNhanVienPhucVu, 80) + "║");
                System.out.println("╚═" + createBorderLine(80) + "═╝");

                nv = new ServiceTeamMember();
                while (true) {

                    nv.nhapThongtin(); // Nhập thông tin nhân viên
                    if (tangChuaCoNhanVienPhucVu.contains(nv.getFloor())) {

                        System.out.println("Nhập thông tin tài khoản cho nhân viên:");
                        System.out.print("Username: ");
                        String username = sc.nextLine();
                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        StaffAccount newAccount = new StaffAccount();
                        boolean isSignUp = newAccount.signUp(username, password, nv);
                        if (!isSignUp) {
                            System.out.println("Tạo tài khoản không thành công. Vui lòng thử lại.");
                            continue;
                        }

                        System.out.println("╔═" + createBorderLine(80) + "═╗");
                        System.out.println(
                                "║" + spacing("ServiceTeamMember đã được thêm vào tầng " + nv.getFloor(), 80) + "║");
                        System.out.println("╚═" + createBorderLine(80) + "═╝");
                        break;
                    } else {
                        System.out.println("╔═" + createBorderLine(80) + "═╗");
                        System.out.println("║" + spacing("WARNING:Tầng không hợp lệ, Hãy nhập lại.", 80) + "║");
                        System.out.println("╚═" + createBorderLine(80) + "═╝");
                    }
                }
        }

        System.out.println("╚═" + createBorderLine(80) + "═╝");
    }

    public void isQuit(String name) {
        StringBuilder k = new StringBuilder();
        for (Staff nv : staffs) {
            if (nv.getName().equals(name)) {
                if (nv.getStatus()) {
                    nv.setStatus(false); // Đổi trạng thái thành nghỉ việc (false)
                    k.append("╔═" + createBorderLine(80) + "═╗\n");
                    k.append("║" + spacing("Nhân viên " + name + " đã nghỉ việc.", 80) + "║\n");
                    k.append("╚═" + createBorderLine(80) + "═╝\n");
                } else {
                    k.append("╔═" + createBorderLine(80) + "═╗\n");
                    k.append("║" + spacing("Nhân viên " + name + " đã nghỉ việc rồi.", 0) + "║\n");
                    k.append("╚═" + createBorderLine(80) + "═╝\n");
                }
                System.out.println(k);
                return;
            }

        }
        k.append("╔═" + createBorderLine(80) + "═╗\n");
        k.append("║" + spacing("Không tìm thấy nhân viên: " + name, 80) + "║\n");
        k.append("╚═" + createBorderLine(80) + "═╝\n");
        System.out.println(k);
    }

    public Staff timNhanVien(String PhoneNumber) {
        for (Staff staff : staffs) {
            if (staff.getPhoneNumber().equals(PhoneNumber)) {
                return staff; // Trả về staff tìm được
            }
        }
        return null; // Không tìm thấy
    }

    public void switchStaffStatus(String PhoneNumber) {
        Staff nv = timNhanVien(PhoneNumber);
        if (nv == null) {
            System.out.println("Không tìm thấy nhân viên");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("╔═" + createBorderLine(80) + "═╗");
        System.out.println("║" + spacing("Bạn muốn đổi status thành gì?", 80) + "║");
        System.out.println("║" + spacing("1:True", 80) + "║");
        System.out.println("║" + spacing("2:False", 80) + "║");
        System.out.println("╚═" + createBorderLine(80) + "═╝");
        String input;

        while (true) {
            input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Không được để trống.");
                continue;
            }

            if (!input.matches("^[12]$")) { // \\ chỉ cho phép các ký tự từ '1' hoặc '2'
                System.out.println("nhập sai, không chứa ký tự hoặc ký tự đặc biệt.");
                continue;
            }

            break;
        }

        int chon = Integer.parseInt(input);
        System.out.println("hợp lệ: " + chon);

        switch (chon) {
            case 1:
                nv.setStatus(true);
                break;

            case 2:
                nv.setStatus(false);
                break;
        }

    }

    public void xuatInfoTatCaStaff() {
        System.out.println("╔═" + createBorderLine(80) + "═╗");
        System.out.println("║" + spacing("Info tất cả nhân viên là", 80) + "║");
        System.out.println("╚═" + createBorderLine(80) + "═╝");
        System.out.println(border_thuong(80));
        for (Staff nv : staffs) {
            System.out.println(nv.XuatThongTin());
        }
        System.out.println(border_thuong(80));
    }
}
