/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OOP_Project;

import java.io.File;
import java.io.FileNotFoundException;
/**
 *
 * @author Amire
 */
import java.util.Scanner;

public abstract class Customer {
    protected String Name;
    protected String PhoneNumber;
    protected String Sex;
    protected double Points;
    protected String Tier;
    public Customer(){};

    public Customer(String Name, String PhoneNumber, String Sex, double Points, String Tier) {
        this.Name = Name;
        this.PhoneNumber = PhoneNumber;
        this.Sex = Sex;
        this.Points = Points;
        this.Tier = Tier;
    }
    
    //getter
    public String getName(){
        return this.Name;
    }
    
    public String getPhoneNumber(){
        return this.PhoneNumber;
    }
    
    public String getSex(){
        return this.Sex;
    }
    
    public double getPoints(){
        return this.Points;
    }
    
    public String getTier(){
        return this.Tier;
    }
    //setter
    public void setName(String Name){
        this.Name = Name;
    }
    
    public void setPhoneNumber(String PhoneNumber){
        this.PhoneNumber = PhoneNumber;
    }
    
    public void setSex(String Sex){
        this.Sex = Sex;
    }

    public void setPoints(double Points){
        this.Points += Points;
        updateTier();
    }

    public void setTier(String Tier){
        this.Tier=Tier;
    }

    public String xuatThongTin() {
        return "Person{" + "Name=" + Name + ", PhoneNumber=" + PhoneNumber + ", Sex=" + Sex + ", Point=" + Points + ", Tier=" + Tier +"}";
    }
    
    public void nhapThongTin_Manual(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Hay nhap thong tin:");
        System.out.println("Name:");
        setName(sc.nextLine());
        System.out.println("PhoneNumber:");
        String PhoneNumberInput = sc.nextLine();
        while (!isValidPhoneNumber(PhoneNumberInput)){
            System.out.println("Invalid phonenumber, please re-enter:");
            PhoneNumberInput= sc.nextLine();
        }
        setPhoneNumber(PhoneNumberInput);
        setSex(getGenderFromUser(sc));
    }

    public void nhapThongTin_automatic(String filename){
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();  // Đọc một dòng
                String[] data = line.split(",");   // Tách các phần tử bằng dấu phẩy

                if (data.length == 5) {  // Đảm bảo dòng có đủ thông tin
                    setName(data[0]);
                    setPhoneNumber(data[1]);
                    setSex(data[2]);
                    setPoints(0);
                    setTier("Đồng");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Tệp không tồn tại: " + e.getMessage());
        }
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        // Biểu thức chính quy để kiểm tra số điện thoại
        String regex = "^0\\d{9}$"; // Bắt đầu bằng 0, theo sau là 9 chữ số
        return phoneNumber.matches(regex);
    }

    public String getGenderFromUser(Scanner scanner) {
        int choice;
        String gender = "";
        
        while (true) {
            System.out.println("Chọn giới tính:");
            System.out.println("1 - Male");
            System.out.println("2 - Female");
            System.out.print("Nhập lựa chọn của bạn (1 hoặc 2): ");
            
            // Nhập giá trị lựa chọn từ người dùng
            choice = scanner.nextInt();
            
            // Kiểm tra lựa chọn
            if (choice == 1) {
                gender = "Male";  // Nếu chọn 1 thì giới tính là Male
                break;
            } else if (choice == 2) {
                gender = "Female";  // Nếu chọn 2 thì giới tính là Female
                break;
            } else {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại.");
            }
        }
        return gender;
    }

    public double stackPoints(double receiptValue){
        double Points = receiptValue * 0.1;
        return Points;
    }

    public String updateTier(){
        if (Points>=1000){
            Tier = "Vàng";
        }
        else if (Points >=500){
            Tier = "Bạc";
        }
        else{
            Tier = "Đồng";
        }
        return Tier;
    }

    public double tierDiscount(String Tier){
        double Discount;
        if (this.Tier == "Vàng"){
            Discount = 0.1;
        }
        else if (this.Tier == "Bạc"){
            Discount = 0.05;
        }
        else 
            Discount = 0;
        return Discount;
    }
}
