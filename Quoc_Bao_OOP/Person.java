import java.util.Scanner;

public abstract class Person {
    protected String Name;
    protected String PhoneNumber;
    protected boolean Sex;

    public Person() {
    };

    public Person(String Name, String PhoneNumber, boolean Sex) {
        this.Name = Name;
        this.PhoneNumber = PhoneNumber;
        this.Sex = Sex;
    }

    public String getName() {
        return this.Name;
    }

    public String getPhoneNumber() {
        return this.PhoneNumber;
    }

    public boolean getSex() {
        return this.Sex;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public void setSex(boolean Sex) {
        this.Sex = Sex;
    }

    public String XuatThongTin() {
        return "Person{Name=" + Name + ", PhoneNumber=" + PhoneNumber + ", Sex=" + (Sex ? "Male" : "Female") + "}";
    }

    public void NhapThongTin() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hay nhap thong tin:");
        System.out.println("Name:");
        this.setName(sc.nextLine());
        System.out.println("PhoneNumber:");
        this.setPhoneNumber(sc.nextLine());
        System.out.println("Sex:");
        this.setSex(Boolean.parseBoolean(sc.nextLine()));
    }

}
