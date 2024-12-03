
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class RoomManager {
    private ArrayList<Room> list_room;
    public TreeMap<LocalDate, TreeMap<String,Booking[]>> total_calendar; 
    public RoomManager (){
        this.list_room = new ArrayList<>();
        this.total_calendar = new TreeMap<>();
    }
    public RoomManager(ArrayList<Room> list) {
        this.list_room = list;
        this.total_calendar = new TreeMap<>();
        for (Room r : list){
            for (Map.Entry<LocalDate,Booking[]> a : r.getCalendar().entrySet()){
                if (!this.total_calendar.containsKey(a.getKey())){ // chưa có ngày
                    TreeMap<String,Booking[]> c = new TreeMap<>();
                    c.put(r.getName(), a.getValue());
                    this.total_calendar.put(a.getKey(), c );
                } else { // có ngày 
                   if (!this.total_calendar.get(a.getKey()).containsKey(r.getName()) ){ // chưa có phòng
                        this.total_calendar.get(a.getKey()).put(r.getName(), a.getValue());
                   } 
                }
            }
        }
    }
    public ArrayList<Room> getList_room() {
        return list_room;
    }
    public void setList_room(ArrayList<Room> list_room) {
        this.list_room = list_room;
    }


    public String form_SO(Object c) {
		StringBuilder k = new StringBuilder();
		k.append("");
		int space_b = (35-c.toString().length())/2 ;
		int space_a = 35-c.toString().length()-space_b;
		for (int i = 0; i < space_b ; i++) {
			k.append(" ");
		}
		k.append(c.toString());
		for (int i = 0; i< space_a ; i++) {
			k.append(" ");
		}
		
		return k.toString();
	}

    public String form_SO_red(Object c) {
		StringBuilder k = new StringBuilder();
		k.append("");
		int space_b = (35-c.toString().length())/2 ;
		int space_a = 35-c.toString().length()-space_b;
		for (int i = 0; i < space_b ; i++) {
			k.append(" ");
		}
		k.append(c.toString());
		for (int i = 0; i< space_a ; i++) {
			k.append(" ");
		}
		
		return "\u001B[1;31m" +k.toString()+"\u001B[0m";
	}
    public static String border(int a){
        StringBuilder b = new StringBuilder();
        for (int i =0 ; i < a; i++){
            b.append("-");
        }
        return b.toString();
    }




    public void add(Room r) {
        this.list_room.add(r);
        if(!r.getCalendar().isEmpty()){ // kiểm tra phòng add vào có lịch không 
           for (Map.Entry<LocalDate,Booking[]> a : r.getCalendar().entrySet()){
                if(!this.total_calendar.containsKey(a.getKey())){ 
                    TreeMap<String,Booking[]> b = new TreeMap<>();
                    b.put(r.getName(), a.getValue());
                    this.total_calendar.put(a.getKey(), b);
                }
           } 
        }
    }



    public void add_Booking_to_calendar(Booking book, String room_name){
        

        for (Room r : this.list_room){
            if (r.getName().equals(room_name)){
                if(r.check_calendar(book)){
                    return;
                }
                r.add_booking(book);
            }
        }
        if (!this.total_calendar.containsKey(book.getDate())){ // trong lịch chưa có ngày đó 
            TreeMap<String,Booking[]> b = new TreeMap<>();
            Booking[] books = new Booking[3];
            books[book.getSession()] = book;
            b.put(room_name, books);
            this.total_calendar.put(book.getDate(), b);
        } else { // trong lịch đã có ngày đó 
            if (!this.total_calendar.get(book.getDate()).containsKey(room_name)){ // có ngày chưa có tên phòng 
                TreeMap<String,Booking[]> b = new TreeMap<>();
                Booking[] books = new Booking[3];
                books[book.getSession()] = book;
                this.total_calendar.get(book.getDate()).put(room_name, books);
            } else { // có ngày có tên phòng 
                this.total_calendar.get(book.getDate()).get(room_name)[book.getSession()] = book;
            }
        }
    }



    public void add_Booking(Booking book,String Room_name){
        for (Room r : this.list_room){
            if(r.getName().equals(Room_name)){
                r.add_booking(book);
            }
        }
        if (!this.total_calendar.containsKey(book.getDate())){
            
        }

    }

    public void show_total_calendar(LocalDate begin, LocalDate end){
        for (Map.Entry<LocalDate,TreeMap<String,Booking[]>> a : this.total_calendar.entrySet()){
            if (a.getKey().isAfter(begin) && a.getKey().isBefore(end)){
                System.out.println(" - " + a.getKey());
                System.out.println(border(145));
                System.out.println("|" + form_SO("Phòng") + "|" + form_SO("Sáng") + "|" + form_SO("Trưa") + "|" + form_SO("Tối") + "|");
                for (Map.Entry<String,Booking[]> b : a.getValue().entrySet()){
                    StringBuilder c = new StringBuilder();
                    c.append("|" + form_SO(b.getKey()) + "|");
                    for (int i = 0; i < 3; i++){
                        if (b.getValue()[i] != null){
                            c.append(form_SO(b.getValue()[i].getCus().getName() + " " +b.getValue()[i].getCus().getPhoneNumber()) + "|");
                            
                        } else {
                            c.append(form_SO("") + "|");
                        }
                    }
                }
            }
        }
    }

    public void show_total_calendar_by( ){
        for (Map.Entry<LocalDate,TreeMap<String,Booking[]>> a : this.total_calendar.entrySet()){
                System.out.println(" - " + a.getKey());
                System.out.println(border(145));
                System.out.println("|" + form_SO("Phong") + "|" + form_SO("Sang") + "|" + form_SO("Trua") + "|" + form_SO("Toi") + "|");
                System.out.println(border(145));
                for (Map.Entry<String,Booking[]> b : a.getValue().entrySet()){
                    StringBuilder c = new StringBuilder();
                    c.append("|" + form_SO(b.getKey()) + "|");
                    for (int i = 0; i < 3; i++){
                        if (b.getValue()[i] != null){
                            c.append(form_SO_red(b.getValue()[i].getCus().getName() + " " + b.getValue()[i].getCus().getPhoneNumber()) + "|");
                            
                        } else {
                            c.append(form_SO("rong") + "|");
                        }
                        
                        
                    }
                    System.out.println(c);
                    System.out.println(border(145));
                }
            
        }
    }

  public static void main(String[] args) {
    Room r1 = new Vip_room("vip1", 1, 1, 1000000);
    Room r2 = new Standard_room("101", 1, 1, 100);

    Customer cus1 = new Customer("trinh tran phuong tuan", "0344700023", false);
    
    LocalDate d1 = LocalDate.of(2024, 12, 12);
    LocalDate d2 = LocalDate.of(2024, 01, 01);

    Booking b1 = new Booking(1, d1, 0, cus1, null);
    Booking b2 = new Booking(2, d1, 1, cus1, null);
    Booking b3 = new Booking(3, d2, 2, cus1, null);
    
    r1.add_booking(b1);
    r2.add_booking(b2);
    r1.add_booking(b3);

    ArrayList<Room> list_room = new ArrayList<>();
    list_room.add(r1);
    list_room.add(r2);
    

    RoomManager mng = new RoomManager(list_room);

   mng.show_total_calendar_by();
    

  }
}
