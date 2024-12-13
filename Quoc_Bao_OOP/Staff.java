import java.util.Scanner;

public abstract class Staff extends Person {
    protected int TienLuong;
    protected boolean Status = true;
    protected String Role;
    protected int Floor;
    protected StaffAccount staffAccount;

    public Staff() {
        super();
        this.Status = true;
        this.Role = "Staff";
    };

    public Staff(String Name, String PhoneNumber, boolean Sex, int Floor) {
        super(Name, PhoneNumber, Sex);
        this.Status = true;
        this.Role = "Staff";
        this.Floor = Floor;
    }

    public Staff(String Name, String PhoneNumber, boolean Sex) {
        super(Name, PhoneNumber, Sex);
        this.Status = true;
        this.Role = "Staff";
    }

    public StaffAccount getStaffAccount() {
        return staffAccount;
    }

    public void setStaffAccount(StaffAccount staffAccount) {
        this.staffAccount = staffAccount;
    }

    public String getName() {
        return super.getName();
    }

    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    public boolean getSex() {
        return super.getSex();
    }

    public boolean getStatus() {
        return this.Status;
    }

    public String getRole() {
        return this.Role;
    }

    public int getFloor() {
        return this.Floor;
    }

    public void setName(String Name) {
        super.setName(Name);
    }

    public void setPhoneNumber(String PhoneNumber) {
        super.setPhoneNumber(PhoneNumber);
    }

    public void setSex(boolean Sex) {
        super.setSex(Sex);
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    public void setFloor(int floorNumber) {
        this.Floor = floorNumber;
    }

    public String XuatThongTin() {
        return "Thông tin nhân viên: \n" +
                super.XuatThongTin() +
                "Trạng thái: " + (this.Status ? "Đang làm việc" : "Đã nghỉ việc") + "\n" +
                "Vai trò: " + this.Role +
                "Tầng:" + this.Floor;
    }

    public void NhapThongTin_Manual() {
        Scanner sc = new Scanner(System.in);
        super.NhapThongTin();
        System.out.print("tầng?: ");
        setFloor(Integer.parseInt(sc.nextLine()));
    }

    public boolean isAdmin() {
        return "Quản lý".equals(this.Role);
    }

    public void setAdmin(boolean isAdmin) {
        this.Role = "Quản lý";
    }

    abstract double TinhTienLuong(int SoGioLam);

    abstract void nhapThongtin();
}
