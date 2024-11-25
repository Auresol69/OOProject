public class Customer extends Person {
    protected int DiemTichLuy = 0;

    public Customer() {
        super();
    }

    public Customer(String Name, String PhoneNumber, boolean Sex) {
        super(Name, PhoneNumber, Sex);
    }

    public void setInfo() {
        super.NhapThongTin();
    }

    public int CongDiemTichLuy() {
        return ++DiemTichLuy;
    }

    public int getDiemTichLuy() {
        return DiemTichLuy;
    }
}
