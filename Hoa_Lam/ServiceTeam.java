/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OOP_Project;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Acer
 */
public class ServiceTeam extends StaffMember{
    private ArrayList<StaffMember> dsServiceTeam;

    public ServiceTeam(){
        super();
        dsServiceTeam = new ArrayList<>();
    }
    
    
    
    
    
    public void ThemMotNhanVienManually(StaffMember nv){
        nv.NhapThongTin_Manual();
        this.dsServiceTeam.add(nv);
      
    }
    
    
    public void XuatThongTinServiceTeam(){
        for(StaffMember nv : dsServiceTeam){
            System.out.println(nv.XuatThongTin());
        }
    }

    @Override
    double TinhTienLuong(int SoGioLam) {
    if(SoGioLam>=4){
            return 1000;
        }
        else{
            return 500;
        }
    }

    
}
