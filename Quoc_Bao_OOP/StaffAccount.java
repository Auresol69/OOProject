import java.util.HashMap;

public class StaffAccount {

    protected String username;
    protected String password;
    protected Staff staff;

    protected static HashMap<String, StaffAccount> accounts = new HashMap<>();

    public StaffAccount(String username, String password, Staff staff) {
        this.username = username;
        this.password = password;
        this.staff = staff;
    }

    public StaffAccount() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String toString() {
        return "StaffAccount{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", staff='" + staff + '\'' +
                '}';
    }

    public boolean signUp(String username, String password, Staff staff) {
        if (accounts.containsKey(username)) {
            System.out.println("Tài khoản " + username + " đã tồn tại!");
            return false;
        }

        // Kiểm tra tính hợp lệ của nhân viên
        if (staff == null) {
            System.out.println("Nhân viên không hợp lệ!");
            return false;
        }

        if (staff.getName() == null || staff.getName().isEmpty()) {
            System.out.println("Tên nhân viên không được để trống!");
            return false;
        }

        if (staff.getPhoneNumber() == null || staff.getPhoneNumber().isEmpty()) {
            System.out.println("Số điện thoại của nhân viên không hợp lệ!");
            return false;
        }

        if (!staff.getStatus()) {
            System.out.println("Nhân viên này đã nghỉ việc, không thể đăng ký tài khoản!");
            return false;
        }

        // Nếu không có lỗi, tiếp tục thêm nhân viên vào hệ thống
        StaffManager sm = new StaffManager();
        if (!sm.getStaffs().contains(staff)) {
            sm.getStaffs().add(staff);
            System.out.println("Nhan vien chua co trong he thong, da them vao danh sach.");
        } else {
            System.out.println("Nhân viên đã có trong danh sách.");
        }

        accounts.put(username, this);

        // Liên kết tài khoản với nhân viên
        staff.setStaffAccount(this);
        this.setStaff(staff);
        System.out.println("Đăng ký tài khoản thành công!");

        return true;
    }

    public static StaffAccount signIn(String username, String password) {
        StaffAccount account = accounts.get(username);
        if (account == null) {
            System.out.println("Đăng nhập thất bại! Tài khoản không tồn tại.");
            return null; // tài khoản không tồn tại.
        }

        if (!account.getPassword().equals(password)) {
            System.out.println("Đăng nhập thất bại! Sai mật khẩu.");
            return null; // mật khẩu sai.
        }

        System.out.println("Đăng nhập thành công!");
        return account; // tài khoản khi đăng nhập thành công.
    }
}