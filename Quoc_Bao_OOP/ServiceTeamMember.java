import java.util.ArrayList;
import java.util.Scanner;

public class ServiceTeamMember extends Staff {
    protected int SoGioLam;
    protected ArrayList<StaffSchedule> PersonalSchedule;

    public ServiceTeamMember() {
        super();
        this.Role = "Phục vụ";
        this.PersonalSchedule = new ArrayList<>();
    }

    @Override
    public double TinhTienLuong(int SoGioLam) {
        if (SoGioLam >= 4) {
            return 1000;
        } else {
            return 500;
        }
    }

    public void addPersonalSchedule(StaffSchedule schedule) {
        PersonalSchedule.add(schedule);
    }

    public void viewPersonalSchedules() {
        System.out.println("Lịch riêng của nhân viên: " + super.getName() + " (Phone: " + super.getPhoneNumber() + ")");
        for (StaffSchedule schedule : PersonalSchedule) {
            schedule.displaySchedule();
        }
    }

    public void setSoGioLam(int SoGioLam) {
        this.SoGioLam = SoGioLam;
    }

    public int getSoGioLam() {
        return this.SoGioLam;
    }

    public ServiceTeamMember(int SoGioLam) {
        this.SoGioLam = SoGioLam;
    }

    public ArrayList<StaffSchedule> getPersonalSchedules() {
        return PersonalSchedule;
    }

    @Override
    public String toString() {
        return "ServiceTeamMember:" + "SoGioLam=" + SoGioLam;
    }

    @Override
    public void nhapThongtin() {
        super.NhapThongTin_Manual();
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap so gio lam: ");
        setSoGioLam(Integer.parseInt(sc.nextLine()));
    }
}
