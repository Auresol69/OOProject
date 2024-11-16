package doan_java;


import java.time.LocalDate;
import java.time.LocalDateTime;




public class book {
	private String customer;
	private String phone_number;
	private LocalDate date;
	private int buoi; 
	
		
	public book() {
		this.customer = "";
		this.phone_number = "";
		date = null;
		this.buoi = -1;
	};
	public book(String customer,String phone_number,LocalDate date, int buoi) {
		this.customer = customer;
		this.phone_number = phone_number;
		this.date = date;
		this.buoi = buoi;
	}
	public String getphone_number() {
		return phone_number;
	}
	public void setphone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getcustumer_name() {
		return customer;
	}
	public void setcustomer(String customer) {
		this.customer = customer;
	}
	public LocalDate getDatee() {
		return date;
	}
	public void setDate(LocalDate datee) {
		this.date = datee;
	}
	public int getBuoi() {
		return buoi;
	}
	public void setBuoi(int buoi) {
		this.buoi = buoi;
	}
	
	
	
	


	
	



	
}
