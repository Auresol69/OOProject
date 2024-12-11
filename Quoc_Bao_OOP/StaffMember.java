

import java.util.Scanner;

public abstract class StaffMember{
    protected int TienLuong;
    protected String Name;
    protected String PhoneNumber;
    protected String Sex;
    protected boolean Status;
    protected String Role;
    public StaffMember(){};

    public StaffMember(String Name, String PhoneNumber, String Sex) {
        this.Name = Name;
        this.PhoneNumber = PhoneNumber;
        this.Sex = Sex;
        this.Status = true;
        this.Role = "Staff";
    }
    
    public String getName(){
        return this.Name;
    }
    
    public String getPhoneNumber(){
        return this.PhoneNumber;
    }
    
    public String getSex(){
        return this.Sex;
    }
    

    public boolean getStatus(){
        return this.Status;
    }

    public String getRole(){
        return this.Role;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    
    public void setPhoneNumber(String PhoneNumber){
        this.PhoneNumber = PhoneNumber;
    }
    
    public void setSex(String Sex){
        this.Sex = Sex;
    }

    public void setStatus(boolean Status){
        this.Status=Status;
    }

    public String XuatThongTin() {
        return "Thông tin nhân viên: \n" + 
               "Tên: " + this.Name + "\n" + 
               "Số điện thoại: " + this.PhoneNumber + "\n" + 
               "Giới tính: " + this.Sex + "\n" + 
               "Trạng thái: " + (this.Status ? "Đang làm việc" : "Đã nghỉ việc") + "\n" + 
               "Vai trò: " + this.Role;
    }
    
    public void NhapThongTin_Manual(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Hay nhap thong tin:");
        System.out.println("Name:");
        setName(sc.nextLine());
        System.out.println("PhoneNumber:");
        setPhoneNumber(sc.nextLine());
        System.out.println("Sex:");
        setSex(sc.nextLine());
        sc.close();
    }
    
    abstract double TinhTienLuong(int SoGioLam);
}
