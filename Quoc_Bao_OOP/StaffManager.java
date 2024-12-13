import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import java.util.Scanner;

public class StaffManager {
    protected static ArrayList<Staff> staffs;
    protected int MaxFloor = 3;

    static {
        staffs = new ArrayList<>();
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

    public void themNhanvien() {
        Scanner sc = new Scanner(System.in);
        StringBuilder k = new StringBuilder();
        k.append("╔═" + createBorderLine(80) + "═╗\n");
        k.append("║" + spacing("Bạn muốn thêm nhân viên quản lý hay phục vụ?", 80) + "║\n");
        k.append("╠═" + createBorderLine(80) + "═╣\n");
        k.append("║" + spacing("1. Nhân viên quản lý", 80) + "║\n");
        k.append("║" + spacing("2. Nhân viên phục vụ", 80) + "║\n");
        k.append("╠═" + createBorderLine(80) + "═╣\n");
        System.out.println(k);
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
