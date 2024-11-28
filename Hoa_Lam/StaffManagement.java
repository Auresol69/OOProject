/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OOP_Project;

import java.util.ArrayList;

/**
 *
 * @author Acer
 */
public class StaffManagement {
    private ArrayList<Customer> DsNhanVien;
    public StaffManagement(){
        this.DsNhanVien = new ArrayList<>();
    }
    public void ThemNhanVien(Customer NV){
    DsNhanVien.add(NV);
    }
    
    public static void main(String[] args) {
    StaffManagement TeamList = new StaffManagement();
    
    }
    
}
