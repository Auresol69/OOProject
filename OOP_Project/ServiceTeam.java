/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OOP_Project;

import Dahinh.Nhanvien;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Acer
 */
public class ServiceTeam extends Person implements StaffMember{
    public ArrayList<Person> DsTeam;
    
    public ServiceTeam() {
        DsTeam = new ArrayList<>();
    }
        public double TienLuongChoMotNhanVien(){
        return 1000;
    }
    public void ThemNhanVienServiceTeam(){
        Scanner sc = new Scanner(System.in);
        Person nv = null;
        nv.NhapThongTin();
        this.DsTeam.add(nv);
    }
    
    public void XoaNhanVienServiceTeamTheoPhoneNumber(String targetPhoneNumber){
       for(Iterator<Person> it = DsTeam.iterator(); it.hasNext();){
           Person nv = it.next();
           if(nv.getPhoneNumber().equals(targetPhoneNumber)){
               DsTeam.remove(nv);
           }
       }
    }
    
    public double TinhTongTienLuongCuaTeam(){
        double TongLuong=0;
        for(Person nv : DsTeam){
            TongLuong=TongLuong+TienLuongChoMotNhanVien();
        }
        return TongLuong;
    }
       public void sapXepTheoTen() {
        for (int i = 0; i < this.DsTeam.size() - 1; i++) {
            for (int j = i + 1; j < this.DsTeam.size(); j++) {
                if (DsTeam.get(i).getName().compareToIgnoreCase(DsTeam.get(j).getName()) == 1) {
                    Person tmp = DsTeam.get(i);
                    DsTeam.set(i, DsTeam.get(j));
                    DsTeam.set(j, tmp);
                }
            }
        }
        
    } 
    
    @Override
    public double TinhTienLuong() {
        return 1;
    }
    
}
