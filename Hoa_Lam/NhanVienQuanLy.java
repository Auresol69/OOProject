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
public class NhanVienQuanLy extends StaffMember {
    protected int SoGioLam;
    public NhanVienQuanLy(){
        super();
    }

 
    @Override
    public double TinhTienLuong(int SoGioLam) {
        if(SoGioLam>=4){
            return 2000;
        }
        else{
            return 1000;
        }
    }

    public void setSoGioLam(int SoGioLam) {
        this.SoGioLam = SoGioLam;
    }

    public int getSoGioLam() {
        return this.SoGioLam;
    }

    public NhanVienQuanLy(int SoGioLam) {
        this.SoGioLam = SoGioLam;
    }

    @Override
    public String toString() {
        return "NhanVienQuanLy{" + "SoGioLam=" + SoGioLam + '}';
    }

    public void nhapThongtin() {
        super.NhapThongTin_Manual(); 
        Scanner sc =new Scanner(System.in);
        this.SoGioLam = Integer.parseInt(sc.nextLine());
        double TienLuong = TinhTienLuong(SoGioLam);
        System.out.println("Tien Luong cua thg ngu nay la:"+TienLuong);
    }
    
}
