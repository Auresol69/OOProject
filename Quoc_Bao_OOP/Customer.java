public class Customer extends Person {
    protected double  DiemTichLuy = 0;
    protected String Tier = "Đồng";

    public Customer() {
        super();
    }

    public Customer(String Name, String PhoneNumber, boolean Sex,double diem) {
        super(Name, PhoneNumber, Sex);
        this.DiemTichLuy = diem;
    }   

    public void setInfo() {
        super.NhapThongTin();
    }

    public double CongDiemTichLuy(double receiptValue) {
        double Points = receiptValue * 0.1;
        updateTier();
        return DiemTichLuy +=  Points;
    }

    public double  getDiemTichLuy() {
        return DiemTichLuy;
    }

    public String updateTier() {
        if (DiemTichLuy >= 1000) {
            Tier = "Vàng";
        } else if (DiemTichLuy >= 500) {
            Tier = "Bạc";
        } else {
            Tier = "Đồng";
        }
        return Tier;
    }

    public String getTier() {
        return Tier;
    }
    public void setDiem(double diem){
        this.DiemTichLuy = diem;
    }

    public String toString() {
        return "Customer{" +
                "DiemTichLuy=" + DiemTichLuy +
                ", Name='" + Name + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", Sex=" + Sex + '\'' +
                ", Tier=" + Tier + '\'' +
                '}';
    }

    public double tierDiscount(String Tier) {
        double Discount;
        if (this.Tier == "Vàng") {
            Discount = 0.1;
        } else if (this.Tier == "Bạc") {
            Discount = 0.05;
        } else
            Discount = 0;
        return Discount;
    }
}
