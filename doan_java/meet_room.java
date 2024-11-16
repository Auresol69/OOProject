package doan_java;

import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.text.DateFormatter;


public class meet_room {
	private String name;
	private int loai;
	private int size;
	private float gia;
	private int trangthai;
	private int tang;
	private TreeMap<LocalDate,book[]>  calendar;
	
	
	
	
	public meet_room(String name, int loai, int size, float gia,int trangthai, int tang) {
		this.name = name;
		this.loai = loai;
		this.size = size;
		this.gia = gia;
		this.trangthai=0;
		book c = new book();
		calendar = new TreeMap<LocalDate, book[]>();
	}

	public String getname() {
		return name;
	}

	public void setTen_phong(String name) {
		this.name =name;
	}

	public int getLoai() {
		return loai;
	}

	public void setLoai(int loai) {
		this.loai = loai;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public float getGia() {
		return gia;
	}

	public void setGia(float gia) {
		this.gia = gia;
	}

	public int getTrangthai() {
		return trangthai;
	}




	public void setTrangthai(int trangthai) {
		this.trangthai = trangthai;
	}




	public TreeMap<LocalDate, book[]> getCalendar() {
		return calendar;
	}




	public void setCalendar(TreeMap<LocalDate, book[]> calendar) {
		this.calendar = calendar;
	}
	
	
	public void add_book(book book) {
		if (!this.calendar.containsKey(book.getDatee())) {
			book c = new book();
			book[] books = {c,c,c,c};
			books[book.getBuoi()] = book;
			this.calendar.put(book.getDatee(), books);
 		} else {
 			this.calendar.get(book.getDatee())[book.getBuoi()] = book;
 		}
	}
	
	public void delete_book(book book) {
		this.calendar.get(book.getDatee())[book.getBuoi()] = new book();
	}
	
	public void print_calendar() {
		StringBuilder border_row = new StringBuilder(100);
		for (int i = 0;i<100;i++) {
			border_row.append("-");
		}
		System.out.println(this.getname());
		System.out.println("	"+border_row);
		System.out.println("	"+form_SO("ngày")+form_SO("sáng")+form_SO("trưa") +form_SO("chiều")+form_SO("tối"));
		System.out.println("	"+border_row);
		for (Map.Entry<LocalDate, book[]> k : this.calendar.entrySet()) {
			StringBuilder data = new StringBuilder();
			
			data.append(form_SO(k.getKey()));
			for (book book : k.getValue() ) {
				if (book.getcustumer_name() != null) {
					data.append(form_SO(book.getcustumer_name()));
				} else {
					data.append(form_SO(""));
				}
			} System.out.println("	"+data);	System.out.println("	"+border_row);
		}
	}
	
	public static String form_SO(Object c) {
		StringBuilder k = new StringBuilder();
		k.append("|");
		int space_b = (20-c.toString().length())/2 ;
		int space_a = 20-c.toString().length()-space_b;
		for (int i = 0; i < space_b ; i++) {
			k.append(" ");
		}
		k.append(c.toString());
		for (int i = 0; i< space_a ; i++) {
			k.append(" ");
		}
		
		return k.toString();
	}
	
	public boolean check_calendar(book book) {
		if (!this.calendar.containsKey(book.getDatee())) return false;
		else {
			if ( this.calendar.get(book.getDatee())[book.getBuoi()].getBuoi() == -1 ) return false ;
		}
		return true;
}
	
	public static void main(String[] args) {
		
		
		
	}
	
	
	
	

	
	
}
	
	
