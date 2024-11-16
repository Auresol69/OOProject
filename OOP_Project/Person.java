/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OOP_Project;

/**
 *
 * @author Acer
 */


import java.util.Scanner;
public abstract class Person {
    protected String Name;
    protected String PhoneNumber;
    protected String Sex;
    public Person(){};

    public Person(String Name, String PhoneNumber, String Sex) {
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
        return "Person{" + "Name=" + Name + ", PhoneNumber=" + PhoneNumber + ", Sex=" + Sex + '}';
    }
    
    public void NhapThongTin(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Hay nhap thong tin:");
        System.out.println("Name:");
        this.Name = getName();
        System.out.println("PhoneNumber:");
        this.PhoneNumber = getPhoneNumber();
        System.out.println("Sex:");
        this.Sex = getSex();
    }
    
}
