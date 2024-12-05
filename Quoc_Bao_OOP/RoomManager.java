
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class RoomManager {
    private ArrayList<Room> list_room;
    
    public TreeMap<LocalDate,ArrayList<Room>> calendar;
    public RoomManager (){
        this.list_room = new ArrayList<>();
       this.calendar = new TreeMap<>();
    }
    public RoomManager(ArrayList<Room> list) {
        this.list_room = list;
        this.calendar = new TreeMap<>();

        for(Room r : list ){
            if (!r.getCalendar().isEmpty()){
                for(Map.Entry<LocalDate,Booking[]> c : r.getCalendar().entrySet()){
                    if(!this.calendar.containsKey(c.getKey())){
                        this.calendar.put(c.getKey(), new ArrayList<>());
                    }
                    this.calendar.get(c.getKey()).add(r);
                    Collections.sort(this.calendar.get(c.getKey()) , (a,b) -> a.getName().compareTo(b.getName()) );
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

    DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
    public String form_SO(Object c,int n) {
		StringBuilder k = new StringBuilder();
		k.append("");
		int space_b = (n-c.toString().length())/2 ;
		int space_a = n-c.toString().length()-space_b;
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
            b.append("═");
        }
        return b.toString();
    }

    public static String border_thuong(int a){
        StringBuilder b = new StringBuilder();
        for (int i =0 ; i < a; i++){
            b.append("-");
        }
        return b.toString();
    }

    public void show_carlendar(LocalDate begin, LocalDate end){
        TreeMap<LocalDate,ArrayList<Room> > bansao = new TreeMap<>();
        for (LocalDate date = begin; !date.isAfter(end);date= date.plusDays(1)){
                
                bansao.put(date,list_room); 
        }



        for (Map.Entry<LocalDate,ArrayList<Room> > lich : bansao.entrySet()){
        int dem = 0;
          System.out.println(lich.getKey());
          System.out.println("╔"+border(143)+"╗");
          System.out.println("║"+ form_SO("ten phong") + "║" + form_SO("sang") + "║" + form_SO("trua") + "║" + form_SO("chieu") + "║");
          for (Room room : lich.getValue()){
            if (dem == 0){
                System.out.println("╠" + border(143) +"╣");
                dem++;
            } else {
                System.out.println("║" +border_thuong(143)+"║");
            }
            StringBuilder str = new StringBuilder();
            str.append("║" + form_SO(room.getName()) + "║");
            if (!room.getCalendar().containsKey(lich.getKey())){
                str.append(form_SO("") + "║" +form_SO("") + "║" +form_SO("") + "║");
            } else {
                for (int i = 0; i< 3; i++){
                    if(room.getCalendar().get(lich.getKey())[i] == null){
                        str.append(form_SO("")+ "║");
                    } else {
                        str.append(form_SO(room.getCalendar().get(lich.getKey())[i].getCus().getName() +  room.getCalendar().get(lich.getKey())[i].getCus().getPhoneNumber())+"║");
                    }
                }
            }           
            System.out.println(str);
          }
          System.out.println("╚" + border(143) + "╝");
        }
    }

   public void history(){
    int dem = 0;
        System.out.println("╔"+border(154) +'╗');
        System.out.println("║"+form_SO("BK ID",10) + "║" + form_SO("PHONG",10)+"║"  + form_SO("NGAY",15) + "║" +form_SO("buoi",10)+"║" +form_SO("SDT KH",15) +"║" +form_SO("TEN Khach Hang",25) +"║" + form_SO("ID MNG",10) +"║" +form_SO("SV_TEAM",10) +"║" +form_SO("DICH VU",20)+"║" + form_SO("Total price",20) +"║");

    for (Map.Entry<LocalDate,ArrayList<Room>> c: this.calendar.entrySet()){
        for (Room room : c.getValue()){
            StringBuilder str = new StringBuilder();
            for (int i = 0; i< 3; i++){
                if(room.getCalendar().get(c.getKey())[i] != null ){
                    Booking booking = room.getCalendar().get(c.getKey())[i];
                    //  15 10 10 10 15 20 10 10 20 20 
                    str.append("║" +form_SO(booking.getId(),10));
                    str.append("║" +form_SO(room.getName(),10));
                    str.append("║" +form_SO(c.getKey(),15));
                    str.append("║"+ ( i== 0 ? form_SO("sang",10) : i == 1 ? form_SO("trua",10) : form_SO("chieu",10)));
                    str.append("║" +form_SO(booking.getCus().getPhoneNumber(),15));
                    str.append("║" +form_SO(booking.getCus().getName(),25));
                    str.append("║" +form_SO("booking",10));// dòng này thêm ID service manager 
                    str.append("║" +form_SO("booking",10));// dòng này thêm ID team service 
                    str.append("║" +form_SO("booking",20));// dòng này thêm các dịch vụ 
                    str.append("║" +form_SO("booking",20)+"║");// dòng này là giá 
                }
            }
           String s = dem == 0 ? ("╠" + border(154) +"╣") : ("║" + border_thuong(154) + "║");
           System.out.println(s);
            System.out.println(str);
            
        }
    }
    System.out.println("╚" + border(154) + "╝");
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

//    mng.show_total_calendar();
    // LocalDate[] date = mng.Get_time_Booking();
    // System.out.println(date[0] + " den " + date[1]);

    LocalDate b = LocalDate.of(2024, 12, 11);
    LocalDate e = LocalDate.of(2024, 12, 17);
    
    // mng.show_carlendar(b,e);
    mng.history();
  }
}
