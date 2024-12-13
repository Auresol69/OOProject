import java.util.Scanner;

public class ServiceTeamMember extends Staff {
    protected int SoGioLam;

    public ServiceTeamMember() {
        super();
        this.Role = "Phục vụ";
    }

    @Override
    public double TinhTienLuong(int SoGioLam) {
        if (SoGioLam >= 4) {
            return 1000;
        } else {
            return 500;
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
