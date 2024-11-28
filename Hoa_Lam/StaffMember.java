/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OOP_Project;

import java.util.Scanner;

/**
 *
 * @author Acer
 */
public abstract class StaffMember{
    protected int TienLuong;
    protected String Name;
    protected String PhoneNumber;
    protected String Sex;
    public StaffMember(){};

    public StaffMember(String Name, String PhoneNumber, String Sex) {
        this.Name = Name;
        this.PhoneNumber = PhoneNumber;
        this.Sex = Sex;
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
    
    public void setName(String Name){
        this.Name = Name;
    }
    
    public void setPhoneNumber(String PhoneNumber){
        this.PhoneNumber = PhoneNumber;
    }
    
    public void setSex(String Sex){
        this.Sex = Sex;
    }

    public String XuatThongTin() {
        return toString( "Person{" + "Name=" + this.Name + ", PhoneNumber=" + PhoneNumber + ", Sex=" + Sex + '}');
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
    }
    
    abstract double TinhTienLuong(int SoGioLam);

    private String toString(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
