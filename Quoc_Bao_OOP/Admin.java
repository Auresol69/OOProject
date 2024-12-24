import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends Staff {
    protected int SoGioLam;
    protected ArrayList<AdminSchedule> PersonalSchedule;

    public Admin() {
        super();
        this.Role = "Quản lý";
        this.PersonalSchedule = new ArrayList<>();
    }

    public Admin(String Name, String PhoneNumber, boolean Sex) {
        super(Name, PhoneNumber, Sex);
        this.Role = "Quản lý";
    }

    @Override
    public double TinhTienLuong(int SoGioLam) {
        if (SoGioLam >= 4) {
            return 2000;
        } else {
            return 1000;
        }
    }

    public void addPersonalSchedule(AdminSchedule schedule) {
        PersonalSchedule.add(schedule);
    }

    public ArrayList<AdminSchedule> getPersonalSchedules() {
        return PersonalSchedule;
    }

    public void viewPersonalSchedules() {
        System.out.println("Lịch riêng của nhân viên: " + super.getName() + " (Phone: " + super.getPhoneNumber() + ")");
        for (AdminSchedule schedule : PersonalSchedule) {
            schedule.displaySchedule();
        }
    }

    public void setSoGioLam(int SoGioLam) {
        this.SoGioLam = SoGioLam;
    }

    public int getSoGioLam() {
        return this.SoGioLam;
    }

    public Admin(int SoGioLam) {
        this.SoGioLam = SoGioLam;
    }

    @Override
    public String toString() {
        return "NhanVienQuanLy " + "SoGioLam=" + SoGioLam + " StaffAccount=";
    }

    @Override
    public void nhapThongtin() {
        super.NhapThongTin_Manual();
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap so gio lam: ");
        setSoGioLam(Integer.parseInt(sc.nextLine()));
    }

}
