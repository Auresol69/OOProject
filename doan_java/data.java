package doan_java;

import java.security.DomainCombiner;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class data {
	private ArrayList<meet_room> list_room;
	private TreeMap<LocalDate, TreeMap<String,book[]>> calendar;
	private DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public data(){
		this.list_room = new ArrayList<meet_room>();
		this.calendar = new TreeMap<LocalDate, TreeMap<String,book[	]>>();
	}
	
	public void add_list(ArrayList<meet_room> list_room) {
		
		for (meet_room room : list_room) {
			if (room.getCalendar().isEmpty()) { //kiem tra phong do co lich khong 
				
			} else {
				for (Map.Entry<LocalDate, book[]> c_room : room.getCalendar().entrySet()) {
					
					if (!this.calendar.containsKey(c_room.getKey())) {  //truong hop ngay do chua co trong lich  
						TreeMap<String, book[]> c1 = new TreeMap<String, book[]>();
						c1.put(room.getname(), c_room.getValue());
						this.calendar.put(c_room.getKey(), c1);
						
					} else {
						if (!this.calendar.get(c_room.getKey()).containsKey(room.getname())) { //trong lich co ngay do nhung  chua co lich cua phong do
							this.calendar.get(c_room.getKey()).put(room.getname(), c_room.getValue());
						} else { // ngay hom do co lich cua phong do nhung chua co lich cua buoi do 
							for (int i = 0; i< 4; i++) {
								int buoi = c_room.getValue()[i].getBuoi();
								
								if (buoi != -1) {
									this.calendar.get(c_room.getKey()).get(room.getname())[buoi] = c_room.getValue()[i];
								}
							}
						}
							
					}
				}
			}
		}
		
	}
	
	
	
	public void add_book(book book, meet_room room) {
		for (meet_room r : this.list_room) {
			if (r.getname().equals(room.getname())) {
				r.add_book(book);
			}
		}
		if (!this.calendar.containsKey(book.getDatee())) {
			book c = new book();
			book[] books = {c,c,c,c};
			books[book.getBuoi()] = book;
			TreeMap<String , book[]> c1 = new TreeMap<String, book[]>();
			c1.put(room.getname(), books);
			this.calendar.put(book.getDatee(), c1);
			
		} else {
			if (!this.calendar.get(book.getDatee()).containsKey(room.getname())) {
				book c = new book();
				book[] books = {c,c,c,c};
				books[book.getBuoi()] = book;	
				this.calendar.get(book.getDatee()).put(room.getname(), books);
			} else {
				this.calendar.get(book.getDatee()).get(room.getname())[book.getBuoi()] = book;
			}
		}
	}
	
	public void delete(String name_or_pnum,String date , int buoi) {
		
		LocalDate d = LocalDate.parse(date, f);
		for (Map.Entry<String, book[]> c1: this.calendar.get(d).entrySet()) {
			c1.getValue()[buoi] = new book();
		}
	}
	
	
	public void select_calendar_by_name_or_sdt(String name_or_sdt) {
		for (Map.Entry<LocalDate,TreeMap<String, book[]> > c1 : this.calendar.entrySet()) {
			for (Map.Entry<String, book[]> c2 : c1.getValue().entrySet()) {
				for (book book : c2.getValue()) {
					if (book.getcustumer_name().equals(name_or_sdt) || book.getphone_number().equals(name_or_sdt) || book.getcustumer_name().contains(name_or_sdt) || book.getphone_number().contains(name_or_sdt)) {
						System.out.println(form_SO(c1.getKey()) + form_SO(c2.getKey()) + form_SO(book.getBuoi()) );
					}
				}
			}
		}
	}
	
	public void select_calendar_by_date(String date){
		if (!this.calendar.containsKey(LocalDate.parse(date, f))) {
			System.out.println(date);
			System.out.println("    |" + form_SO("tên phòng") + "|" + form_SO("sáng") +"|" + form_SO("trưa") +"|"+ form_SO("tối")+"|");
			
		} else {
			StringBuilder data_t,head_t,border;
			border = new StringBuilder();
			for (int i = 0; i < 150; i++) {
				border.append("-");
			}
			System.out.println(date);
			System.out.println("     "+border);
			System.out.println("    |" + form_SO("tên phòng") + "|" + form_SO("sáng") +"|" + form_SO("trưa") +"|"+ form_SO("chiều")+"|"+ form_SO("tối")+"|");
			System.out.println("     "+border);
			for (Map.Entry<String, book[]> c1 : this.calendar.get(LocalDate.parse(date, f)).entrySet()) {
				 data_t = new StringBuilder();
				data_t.append("    |"+form_SO(c1.getKey())+"|");
				for (book book : c1.getValue()) {
					if (book.getcustumer_name().equals("") && book.getphone_number().equals("")) {
						data_t.append(form_SO("") + "|");
					} else {
						data_t.append(form_SO(book.getcustumer_name()+" : "+book.getphone_number()) + "|");
					}
					
				}
				System.out.println(data_t);
				System.out.println("     "+border);
			}
		}
		
	}
	       
	public void print_list_calendar() {
		StringBuilder border_row = new StringBuilder();
		for (int i = 0;i<150;i++) {
			border_row.append("-");
		}
		for (Map.Entry<LocalDate, TreeMap<String, book[]>> c_day : this.calendar.entrySet()) {
			System.out.println(c_day.getKey());
			System.out.println("     "+ border_row);
			System.out.println("     "+this.form_SO("ten phong")+"|" + this.form_SO("sang") +"|"+ this.form_SO("trua")+"|" +this.form_SO("chieu")+"|" +this.form_SO("toi") +"|");
			System.out.println("     "+ border_row);
			
			
			for (Map.Entry<String, book[]> c_room : c_day.getValue().entrySet()) {
				StringBuilder s = new StringBuilder();
				s.append(this.form_SO(c_room.getKey()));
				s.append("|");
				for (book book : c_room.getValue()) {
					if (book.getBuoi() != -1) {
						s.append(this.form_SO(book.getcustumer_name()+" : "+book.getphone_number()));
						s.append("|");
					} else {
						s.append(this.form_SO(""));
						s.append("|");
					}
				}
				System.out.println("     "+ s);
				System.out.println("     " +border_row);
			}
		}
		
		
		
	}
	
	
	public static String form_SO(Object c) {
		StringBuilder k = new StringBuilder();
		k.append("");
		int space_b = (30-c.toString().length())/2 ;
		int space_a = 30-c.toString().length()-space_b;
		for (int i = 0; i < space_b ; i++) {
			k.append(" ");
		}
		k.append(c.toString());
		for (int i = 0; i< space_a ; i++) {
			k.append(" ");
		}
		
		return k.toString();
	}
	public static void main(String[] args) {
		
		meet_room r1 = new meet_room("phong 1", 0, 0, 0, 0, 0);
		meet_room r2 = new meet_room("phong 2",0, 0, 0, 0, 0);
		meet_room r3 = new meet_room("phong 3",0, 0, 0, 0, 0);
		meet_room r4 = new meet_room("phong 4",0, 0, 0, 0, 0);
		
		
		book book0 = new book("long","0923004055", LocalDate.of(2024, 11, 1), 0);
		book book1 = new book("long1","0923004056", LocalDate.of(2024, 11, 1), 1);
		book book2 = new book("long2","0923004057", LocalDate.of(2024, 11, 1), 2);
		book book3 = new book("long3","0923004058", LocalDate.of(2024, 11, 1), 3);
		
		r1.add_book(book0);
		r2.add_book(book1);
		r3.add_book(book2);
		r4.add_book(book3);
		
		ArrayList<meet_room> list = new ArrayList<meet_room>();
		list.add(r1);
		list.add(r2);
		list.add(r3);
		list.add(r4);
		
		
		data data = new data();
		data.add_list(list);
		
//		data.print_list_calendar();
		
		book book5 = new book("toi","0923004055", LocalDate.of(2024, 11, 1), 3);
		if (r3.check_calendar(book5)) {
			r3.add_book(book5);
		}
		
		data.select_calendar_by_date("01/11/2024");
		
	}
	
}
