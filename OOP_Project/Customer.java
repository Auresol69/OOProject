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
public class Customer extends Person {
    protected int DiemTichLuy;
    public Customer(){
    super();
    this.DiemTichLuy = 0;
    }
    public int CongDiemTichLuy(){
        return DiemTichLuy++;
    }
    public int getDiemTichLuy(){
        return DiemTichLuy;
    }
}
