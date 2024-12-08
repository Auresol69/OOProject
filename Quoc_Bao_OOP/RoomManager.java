
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
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
        this.calendar = new TreeMap<>(Comparator.reverseOrder());

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
    DateTimeFormatter f_out = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DecimalFormat form_tien = new DecimalFormat("#,###.00");
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
                    str.append("║" +form_SO(c.getKey().format(f_out),15));
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
           dem++;
           System.out.println(s);
            System.out.println(str);
            
        }
    }
    System.out.println("╚" + border(154) + "╝");
   }


   public void history_by_date(LocalDate begin, LocalDate end){
    int dem = 0;
        System.out.println("╔"+border(154) +'╗');
        System.out.println("║"+form_SO("BK ID",10) + "║" + form_SO("PHONG",10)+"║"  + form_SO("NGAY",15) + "║" +form_SO("buoi",10)+"║" +form_SO("SDT KH",15) +"║" +form_SO("TEN Khach Hang",25) +"║" + form_SO("ID MNG",10) +"║" +form_SO("SV_TEAM",10) +"║" +form_SO("DICH VU",20)+"║" + form_SO("Total price",20) +"║");

    for (Map.Entry<LocalDate,ArrayList<Room>> c: this.calendar.entrySet()){
        if (!c.getKey().isAfter(end) && !c.getKey().isBefore(begin)){
            for (Room room : c.getValue()){
                StringBuilder str = new StringBuilder();
                for (int i = 0; i< 3; i++){
                    if(room.getCalendar().get(c.getKey())[i] != null ){
                        Booking booking = room.getCalendar().get(c.getKey())[i];
                        //  15 10 10 10 15 20 10 10 20 20 
                        str.append("║" +form_SO(booking.getId(),10));
                        str.append("║" +form_SO(room.getName(),10));
                        str.append("║" +form_SO(c.getKey().format(f_out),15));
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
            dem++;
            System.out.println(s);
                System.out.println(str);
                
            }
        }
        System.out.println("╚" + border(154) + "╝");
    }
   }
    
    public LocalDate setDate(LocalDate begin , LocalDate end){
        Scanner sc = new Scanner(System.in);
        show_carlendar(begin, end);
        
        LocalDate date = null;
        
        do{
            System.out.print("Nhap ngay (dd/MM/yyyy) : ");
            System.out.println();
            String date_in = sc.nextLine();
            try {
                date = LocalDate.parse(date_in, f);
            } catch (Exception e) {
                System.out.println("loi dinh dang ngay vui long nhap lai!!");
            }

        } while(date == null);
        sc.close();
        return date;
    }
    public String setName_room(){
        Scanner sc = new Scanner(System.in);
        boolean kt = true ;
        String tenphong;
        for (Room r : this.list_room){
            System.out.println(r.getName());
        }
        do{
            
        
            System.out.print("Nhap ma phong : ");
            tenphong = sc.nextLine();
            System.out.println();
            for (Room room : this.list_room){
                if (room.getName().equals(tenphong.trim())){
                    kt = false;
                }
            }
            if (kt) System.out.println("Phong nay khong ton tai !!");
        } while(kt);
            
        return tenphong.trim();
    }

    public int set_sesssoon(){
        Scanner sc = new Scanner(System.in);
        int i = -1;
        do{
        System.out.println("Chon thoi gian trong ngay : ");
        System.out.println("0. Sang");
        System.out.println("1. trua");
        System.out.println("2. chieu");
        i = sc.nextInt();
        
        if (i<0 || i> 2){
            System.out.println("Loi!!!  Vui long nhap lai!");
        }
        } while (i<0 || i>2);
        return i;
    }

    public void show (){
        for (Map.Entry<LocalDate,ArrayList<Room> > c : this.calendar.entrySet()){
            System.out.print(c.getKey());
            for (Room room : c.getValue()){
                System.out.print( " : " +room.getName() +",");
            }
            System.out.println();
        }
    }

      public void thong_ke(){
        double total =0;
        int bor = 0;
        StringBuilder row = new StringBuilder();
        ArrayList<Room> bansao = this.list_room;
        Collections.sort(bansao, (a, b) -> Double.compare(b.tong_doanh_thu(), a.tong_doanh_thu()));

        
        row.append("║"+form_SO("TEN PHG",10));
        row.append("║"+form_SO("SIZE",10));
        row.append("║"+form_SO("STATUS",10));
        row.append("║"+form_SO("PRICE",10));
        row.append("║"+form_SO("SO LAN THUE",15));
        row.append("║"+form_SO("TIEN THUE PHONG",20));
        row.append("║"+form_SO("TIEN DICH VU",20));
        row.append("║"+form_SO("TIEN KHUYEN MAI",20));
        row.append("║"+form_SO("DOANH THU",20)+"║");
        
        System.out.println("╔"+border(143)+'╗');
        System.out.println(row);

        for (Room room : bansao){
            
            row = new StringBuilder();
            int soluong = room.dem_sl();
            double price = room.getPrice();
            double tien_dich_vu = room.tong_doanh_thu_dichvu();
            double doanh_thu_tt = room.tong_doanh_thu();
            total += doanh_thu_tt;
            double tien_khuyen_mai = soluong*price + tien_dich_vu - doanh_thu_tt ;
            
            row.append("║"+form_SO(room.getName(),10));
            row.append("║"+form_SO(room.getSize(),10));
            row.append("║"+form_SO(room.getStatus(),10));
            row.append("║"+form_SO(room.getPrice(),10));
            row.append("║"+form_SO(soluong,15));
            row.append("║"+form_SO(form_tien.format(soluong*price),20));
            row.append("║"+form_SO(form_tien.format(tien_dich_vu),20));
            row.append("║"+form_SO(form_tien.format(tien_khuyen_mai),20));
            row.append("║"+form_SO(form_tien.format(doanh_thu_tt),20)+"║");

            String s = bor == 0 ? ("╠" + border(143) +"╣") : ("║" + border_thuong(143) + "║");
            bor++;
            System.out.println(s);
            System.out.println(row);
        }
        System.out.println("╚" + border(143) + "╝");
        System.out.println("Tong doanh thu : " + total );

  }
  public void thong_ke_bydate(LocalDate begin, LocalDate end){
    double total =0;
    int bor = 0;
    StringBuilder row = new StringBuilder();
    ArrayList<Room> bansao = this.list_room;
    Collections.sort(bansao, (a, b) -> Double.compare(b.tong_doanh_thu_bydate(begin,end), a.tong_doanh_thu_bydate(begin,end)));

    
    row.append("║"+form_SO("TEN PHG",10));
    row.append("║"+form_SO("SIZE",10));
    row.append("║"+form_SO("STATUS",10));
    row.append("║"+form_SO("PRICE",10));
    row.append("║"+form_SO("SO LAN THUE",15));
    row.append("║"+form_SO("TIEN THUE PHONG",20));
    row.append("║"+form_SO("TIEN DICH VU",20));
    row.append("║"+form_SO("TIEN KHUYEN MAI",20));
    row.append("║"+form_SO("DOANH THU",20)+"║");
    
    System.out.println("╔"+border(143)+'╗');
    System.out.println(row);

    for (Room room : bansao){
        
        row = new StringBuilder();
        int soluong = room.dem_sl_bydate(begin,end);
        double price = room.getPrice();
        double tien_dich_vu = room.tong_doanh_thu_dichvu_bydate(begin,end);
        double doanh_thu_tt = room.tong_doanh_thu_bydate(begin,end);
        total += doanh_thu_tt;
        double tien_khuyen_mai = soluong*price + tien_dich_vu - doanh_thu_tt ;
        
        row.append("║"+form_SO(room.getName(),10));
        row.append("║"+form_SO(room.getSize(),10));
        row.append("║"+form_SO(room.getStatus(),10));
        row.append("║"+form_SO(room.getPrice(),10));
        row.append("║"+form_SO(soluong,15));
        row.append("║"+form_SO(form_tien.format(soluong*price),20));
        row.append("║"+form_SO(form_tien.format(tien_dich_vu),20));
        row.append("║"+form_SO(form_tien.format(tien_khuyen_mai),20));
        row.append("║"+form_SO(form_tien.format(doanh_thu_tt),20)+"║");

        String s = bor == 0 ? ("╠" + border(143) +"╣") : ("║" + border_thuong(143) + "║");
        bor++;
        System.out.println(s);
        System.out.println(row);
    }
    System.out.println("╚" + border(143) + "╝");
    System.out.println("Tong doanh thu : " + total );

}

public void thong_ke_theo_quy(int nam){
    System.out.println(" DOANH THU THEO QUY ");
    System.out.println("QUY 1 ");
    thong_ke_bydate(LocalDate.of(nam, 1, 1), LocalDate.of(nam, 4, 30));
    System.out.println("QUY 2 ");
    thong_ke_bydate(LocalDate.of(nam, 1, 4), LocalDate.of(nam, 7, 30));
    System.out.println("QUY 3 ");
    thong_ke_bydate(LocalDate.of(nam, 1, 7), LocalDate.of(nam, 10, 30));
    System.out.println("QUY 4 ");
    thong_ke_bydate(LocalDate.of(nam, 1, 10), LocalDate.of(nam, 12, 31));
}

public double tong_doanh_thu_theo_nam(int nam){
    double d = 0;
   for (Room room : this.list_room){
    d+= room.tong_doanh_thu_bydate(LocalDate.of(nam, 1, 1), LocalDate.of(nam, 12, 31));
   }
    return d;
}



public void sosanh_cac_nam(){
    Map.Entry<LocalDate,ArrayList<Room>> lasttime = this.calendar.firstEntry();
    Map.Entry<LocalDate,ArrayList<Room>> firttime = this.calendar.lastEntry();
    
    int bor = 0;
    StringBuilder row = new StringBuilder();
    ArrayList<Room> bansao = this.list_room;

    System.out.println("╔"+border(94)+'╗');
    row.append("║"+form_SO("NAM",10));
    row.append("║"+form_SO("TIEN THUE PHONG",20));
    row.append("║"+form_SO("PHI DICH VU",20));
    row.append("║"+form_SO("KHUYEN MAI",20));
    row.append("║"+form_SO("DOANH THU ",20)+"║");
    
    System.out.println(row);
    
    for (LocalDate date = LocalDate.of(firttime.getKey().getYear(), 1, 1); !date.isAfter(lasttime.getKey()); date=date.plusYears(1)){
        double total =0;
        double thue =0;
         double dichvu =0;
        double khuyenmai =0;
        for (Room room : this.list_room){
            LocalDate b = LocalDate.of(date.getYear(), 1, 1);
            LocalDate e = LocalDate.of(date.getYear(), 12, 31);
            thue += room.dem_sl_by_date(b,e)+room.getPrice();
            dichvu += room.tong_doanh_thu_dichvu_bydate(b, e);
            total += room.tong_doanh_thu_bydate(b, e);
        }
        khuyenmai += dichvu + thue - total;
        row = new StringBuilder();
        row.append("║"+form_SO(date.getYear(),10));
        row.append("║"+form_SO(thue,20));
        row.append("║"+form_SO(dichvu,20));
        row.append("║"+form_SO(khuyenmai,20));
        row.append("║"+form_SO(total,20)+"║");

        String s = bor == 0 ? ("╠" + border(94) +"╣") : ("║" + border_thuong(94) + "║");
            bor++;
            System.out.println(s);
        System.out.println(row);
    }
    System.out.println("╚" + border(94) + "╝");

    

}




public void thong_ke_theo_nam(){
    TreeMap<Integer,Double> doanh_thu_byyear = new TreeMap<>();
    Map.Entry<LocalDate,ArrayList<Room>> lasttime = this.calendar.firstEntry();

    Map.Entry<LocalDate,ArrayList<Room>> firttime = this.calendar.lastEntry();
    System.out.println(firttime.getKey() + " va " + lasttime.getKey());

  for (LocalDate date = firttime.getKey() ; !date.isAfter(lasttime.getKey()) ; date = date.plusYears(1)){
       System.out.println("DOANH THU CUA NAM " + date.getYear());
       thong_ke_bydate(LocalDate.of(date.getYear(), 1, 1), LocalDate.of(date.getYear(), 12, 31));
       doanh_thu_byyear.put(date.getYear(), this.tong_doanh_thu_theo_nam(date.getYear()));
  }

  for (Map.Entry<Integer,Double> c : doanh_thu_byyear.entrySet()){
    System.out.println(c.getKey() + " : " + c.getValue()) ;
  }
}
  

 
  public static void main(String[] args) {
    Room r1 = new Vip_room("vip1", 1, 1, 1000000,1);
    Room r2 = new Standard_room("101", 1, 1, 100,1);

    Customer cus1 = new Customer("trinh tran phuong tuan", "0344700023", false);
    
    LocalDate d1 = LocalDate.of(2024, 12, 12);
    LocalDate d2 = LocalDate.of(2024, 01, 01);

    Service wifi = new wifiService("wifi", 1);
    Service tn = new technicalSupportService("tn", 150);
    ArrayList<Service> c = new ArrayList<>();

    c.add(tn);
    c.add(wifi);

    LocalDate b = LocalDate.of(2024, 12, 11);
    LocalDate e = LocalDate.of(2024, 12, 17);
    

    ArrayList<Room> list_room = new ArrayList<>();
    list_room.add(r1);
    list_room.add(r2);
    

    RoomManager mng = new RoomManager(list_room);



    
    
    
    // mng.show_carlendar(b,e);
    
    // mng.setName_room();

    LocalDate d5 = LocalDate.of(2025, 01, 01);
    //  mng.history();

    
    // mng.history();
    mng.thong_ke_theo_nam();
    mng.sosanh_cac_nam();
    
  }
}
