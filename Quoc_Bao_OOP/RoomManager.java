
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class RoomManager {
    protected static ArrayList<Room> list_room;
    protected static TreeMap<LocalDate,ArrayList<Room>> calendar;

    static {
        list_room = new ArrayList<>();
        calendar = new TreeMap<>();
        try (BufferedReader br  = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/Roommanager.txt"))){
            String line ;
            while((line = br.readLine())!=null){
            String[] str = line.split("#");
            String name = str[0];
            int size = Integer.parseInt(str[1]) ;
            int status = Integer.parseInt(str[2]) ;
            double price = Double.parseDouble(str[3]) ;
            int tang = Integer.parseInt(str[4]) ;
            Room room;
            if (name.contains("Vip")){
                room = new Vip_room(name, size, status, price, tang);
            } else {
                room = new Standard_room(name, size, status, price, tang);
            }
            list_room.add(room);
           
    
        }
       } catch (Exception e) {
        // TODO: handle exception
       }
       Collections.sort(list_room, (a,b) -> a.getName().compareTo(b.getName()));
       Collections.sort(list_room, (a,b) -> a.getTang() - b.getTang());
       
       
    }
    
    
    



    
    public static boolean check_room(String roomname){
        for (Room room : list_room){
            if (room.getName().equalsIgnoreCase(roomname)){
                return true;
            }
        }
        return false;
    }
    public ArrayList<Room> getList_room() {
        return list_room;
    }
    public void setList_room(ArrayList<Room> list_room) {
        this.list_room = list_room;
    }

    public static void update_calendar(){
        for (Room room : RoomManager.list_room){
            for (Map.Entry<LocalDate,Booking[] > c : room.getCalendar().entrySet()){
                if(!RoomManager.calendar.containsKey(c.getKey())){
                    RoomManager.calendar.put(c.getKey(), new ArrayList<>());
                }
                if (!RoomManager.calendar.get(c.getKey()).contains(room)){
                    RoomManager.calendar.get(c.getKey()).add(room);
                }
                }
        }
    }

    public void add_room(Room room){
        this.list_room.add(room);
    }
    
    DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static DateTimeFormatter f_out = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    static DecimalFormat form_tien = new DecimalFormat("#,###.00");
    public static String form_SO(Object c) {
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
    public static String form_SO(Object c,int n) {
		StringBuilder k = new StringBuilder();
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
    public static String form_option(Object b , int m){
        StringBuilder str  = new StringBuilder();
        for (int i = 0 ; i< 5 ; i++){
            str.append(" ");
        } 
        str.append(b);
        int j = m - b.toString().length() - 5;
        for (int i =0 ; i < j;i++){
            str.append(" ");
        }
        return str.toString();
    }

    public static void show_calendar(LocalDate begin, LocalDate end,int size){
        boolean kt = false;
       
        RoomManager.update_calendar();
        
        for (Map.Entry<LocalDate,ArrayList<Room> > lich : RoomManager.calendar.entrySet()){
        int dem = 0;
        String mau ;
        if (lich.getKey().getDayOfMonth()%2==0){ mau ="\u001B[33m";} else { mau ="\u001B[35m"; }
            System.out.println(mau+"╔"+border(143)+"╗"+"\u001B[0m");
          System.out.println(mau+"║"+"\u001B[0m"+"\u001B[1m"+form_SO(lich.getKey().format(f_out),143)+"\u001B[0m"+mau+"║"+"\u001B[0m");
          System.out.println(mau+"╠"+border(35)+"╦"+border(35)+"╦"+border(35)+"╦"+border(35)+"╣"+"\u001B[0m");
          System.out.println(mau+"║"+"\u001B[0m"+ form_SO("ten phong") + mau+"║"+"\u001B[0m" + form_SO("sang") +mau+"║"+"\u001B[0m" + form_SO("trua") + mau+"║"+"\u001B[0m" + form_SO("chieu") + mau+"║"+"\u001B[0m");
          for (Room room : lich.getValue()){
            kt = false;
            if (room.getSize() == size+1 || room.getSize() == size){
                kt = true;
            }
            String mota = room.getSize() == 0 ? "  (10-15)  " : room.getSize() == 1 ? "  (15-25)  " : "  (25-40) ";
            StringBuilder str = new StringBuilder();
            str.append(mau+"║"+"\u001B[0m" + form_SO(room.getName()+mota) + mau+"|"+"\u001B[0m");
            if (!room.getCalendar().containsKey(lich.getKey())){
                str.append(form_SO("") + mau+"|"+"\u001B[0m" +form_SO("") + mau+"|"+"\u001B[0m" +form_SO("") +mau+ "║"+"\u001B[0m");
            } else {
                for (int i = 0; i< 3; i++){
                    if(room.getCalendar().get(lich.getKey())[i] == null){
                        str.append(form_SO("")+ mau+"|"+"\u001B[0m");
                    } else {
                        str.append(form_SO(room.getCalendar().get(lich.getKey())[i].getCus().getName() +  room.getCalendar().get(lich.getKey())[i].getCus().getPhoneNumber())+mau+"|"+"\u001B[0m");
                    }
                }
            } 
            if (dem == 0){
                System.out.println(mau+"╠"+border(35)+"╩"+border(35)+"╩"+border(35)+"╩"+border(35)+"╣"+"\u001B[0m");
                System.out.println(str);
                dem++;
            } else if(kt) {
                System.out.println(mau+"║" +border_thuong(35)+"+"+border_thuong(35)+"+"+border_thuong(35)+"+"+border_thuong(35)+"║"+"\u001B[0m");
                System.out.println(str);
                dem++;
            }
           
            
          }
          System.out.println(mau+"╚" + border(143) + "╝"+"\u001B[0m");
        }
    }


  
    public LocalDate setDate(LocalDate begin , LocalDate end){
        Scanner sc = new Scanner(System.in);
        show_calendar(begin, end,1);
        
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
        System.out.println("║"+form_SO("TONG THONG KE", 143)+"║");
        System.out.println("╠" + border(10)+"╦"+ border(10)+"╦"+ border(10)+"╦"+ border(10)+"╦"+ border(15)+"╦"+ border(20)+"╦"+ border(20)+"╦"+ border(20)+"╦"+ border(20) + "╣");
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
            row.append("|"+form_SO(room.getSize(),10));
            row.append("|"+form_SO(room.getStatus(),10));
            row.append("|"+form_SO(room.getPrice(),10));
            row.append("|"+form_SO(soluong,15));
            row.append("|"+form_SO(form_tien.format(soluong*price),20));
            row.append("|"+form_SO(form_tien.format(tien_dich_vu),20));
            row.append("|"+form_SO(form_tien.format(tien_khuyen_mai),20));
            row.append("|"+form_SO(form_tien.format(doanh_thu_tt),20)+"║");

            String s ;
            if (bor == 0){
                s = "╠" + border(10)+"╩"+ border(10)+"╩"+ border(10)+"╩"+ border(10)+"╩"+ border(15)+"╩"+ border(20)+"╩"+ border(20)+"╩"+ border(20)+"╩"+ border(20) + "╣";
            } else {
                s= "║" + border_thuong(10)+"+"+ border_thuong(10)+"+"+ border_thuong(10)+"+"+ border_thuong(10)+"+"+ border_thuong(15)+"+"+ border_thuong(20)+"+"+ border_thuong(20)+"+"+ border_thuong(20)+"+"+ border_thuong(20)+ "║";
            }
            bor++;
            System.out.println(s);
            System.out.println(row);
        }
        System.out.println("╚" + border(143) + "╝");
        System.out.println("Tong doanh thu : " + total );

  }
  public static void thong_ke_bydate(LocalDate begin, LocalDate end){
    double total =0;
    int bor = 0;
    StringBuilder row = new StringBuilder();
    ArrayList<Room> bansao = RoomManager.list_room;
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
    
    System.out.println("╠" + border(10)+"╦"+ border(10)+"╦"+ border(10)+"╦"+ border(10)+"╦"+ border(15)+"╦"+ border(20)+"╦"+ border(20)+"╦"+ border(20)+"╦"+ border(20) + "╣");
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
        row.append("|"+form_SO(room.getSize(),10));
        row.append("|"+form_SO(room.getStatus(),10));
        row.append("|"+form_SO(room.getPrice(),10));
        row.append("|"+form_SO(soluong,15));
        row.append("|"+form_SO(form_tien.format(soluong*price),20));
        row.append("|"+form_SO(form_tien.format(tien_dich_vu),20));
        row.append("|"+form_SO(form_tien.format(tien_khuyen_mai),20));
        row.append("|"+form_SO(form_tien.format(doanh_thu_tt),20)+"║");

        String s ;
        if (bor == 0){
            s = "╠" + border(10)+"╩"+ border(10)+"╩"+ border(10)+"╩"+ border(10)+"╩"+ border(15)+"╩"+ border(20)+"╩"+ border(20)+"╩"+ border(20)+"╩"+ border(20) + "╣";
        } else {
            s= "║" + border_thuong(10)+"+"+ border_thuong(10)+"+"+ border_thuong(10)+"+"+ border_thuong(10)+"+"+ border_thuong(15)+"+"+ border_thuong(20)+"+"+ border_thuong(20)+"+"+ border_thuong(20)+"+"+ border_thuong(20)+ "║";
        }
        bor++;
        System.out.println(s);
        System.out.println(row);
    }
   
    System.out.println("╠" + border(143)+ "╣");
    System.out.println("║"+form_SO("Tong doanh thu : " + total,143) +"║");
    System.out.println("╚" + border(143) + "╝");

}

public void thong_ke_theo_quy(int nam){
    System.out.println(" DOANH THU THEO QUY ");
    System.out.println("QUY 1 ");
    System.out.println("╔" + border(143) + "╗");
    System.out.println("║" + form_SO("QUY 1", 143) + "║");
    thong_ke_bydate(LocalDate.of(nam, 1, 1), LocalDate.of(nam, 4, 30));
    System.out.println("\n\n\n");
    System.out.println("╔" + border(143) + "╗");
    System.out.println("║" + form_SO("QUY 2", 143) + "║");
    thong_ke_bydate(LocalDate.of(nam, 1, 4), LocalDate.of(nam, 7, 30));
    System.out.println("\n\n\n");
    System.out.println("╔" + border(143) + "╗");
    System.out.println("║" + form_SO("QUY 3", 143) + "║");
    thong_ke_bydate(LocalDate.of(nam, 1, 7), LocalDate.of(nam, 10, 30));
    System.out.println("\n\n\n");
    System.out.println("╔" + border(143) + "╗");
    System.out.println("║" + form_SO("QUY 4", 143) + "║");
    thong_ke_bydate(LocalDate.of(nam, 1, 10), LocalDate.of(nam, 12, 31));
}

public static double tong_doanh_thu_theo_nam(int nam){
    double d = 0;
   for (Room room : RoomManager.list_room){
    d+= room.tong_doanh_thu_bydate(LocalDate.of(nam, 1, 1), LocalDate.of(nam, 12, 31));
   }
    return d;
}



public static void sosanh_cac_nam(){
    RoomManager.update_calendar();
    Map.Entry<LocalDate,ArrayList<Room>> firttime = RoomManager.calendar.firstEntry();
    Map.Entry<LocalDate,ArrayList<Room>>   lasttime= RoomManager.calendar.lastEntry();
    System.out.println(firttime.getKey());
    System.out.println(lasttime.getKey());
    int bor = 0;
    StringBuilder row = new StringBuilder();
    ArrayList<Room> bansao = RoomManager.list_room;

    System.out.println("╔"+border(94)+'╗');
    System.out.println("║"+form_SO("SO SANH CAC NAM ", 94)+"║");
    System.out.println("╠" + border(10)+"╦"+ border(20)+"╦"+ border(20)+"╦"+ border(20)+"╦"+ border(20)+"╣");
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
        for (Room room : RoomManager.list_room){
            LocalDate b = LocalDate.of(date.getYear(), 1, 1);
            LocalDate e = LocalDate.of(date.getYear(), 12, 31);
            thue += room.dem_sl_by_date(b,e)+room.getPrice();
            dichvu += room.tong_doanh_thu_dichvu_bydate(b, e);
            total += room.tong_doanh_thu_bydate(b, e);
        }
        khuyenmai += dichvu + thue - total;
        row = new StringBuilder();
        row.append("║"+form_SO(date.getYear(),10));
        row.append("|"+form_SO(thue,20));
        row.append("|"+form_SO(dichvu,20));
        row.append("|"+form_SO(khuyenmai,20));
        row.append("|"+form_SO(total,20)+"║");

        String s ;
        if(bor == 0){
            s = "╠" + border(10)+"╩"+ border(20)+"╩"+ border(20)+"╩"+ border(20)+"╩"+ border(20)+"╣";
            bor++;
        }  else {
            s = "║" + border_thuong(10)+"+"+ border_thuong(20)+"+"+ border_thuong(20)+"+"+ border_thuong(20)+"+"+ border_thuong(20)+"║";
        }
           
            System.out.println(s);
        System.out.println(row);
    }
    System.out.println("╚" + border(94) + "╝");
}




public static void thong_ke_theo_nam(){
    
    TreeMap<Integer,Double> doanh_thu_byyear = new TreeMap<>();
    RoomManager.update_calendar();
    Map.Entry<LocalDate,ArrayList<Room>> lasttime = RoomManager.calendar.firstEntry();
    Map.Entry<LocalDate,ArrayList<Room>> firttime = RoomManager.calendar.lastEntry();
    System.out.println(firttime.getKey() + " va " + lasttime.getKey());

  for (LocalDate date = firttime.getKey() ; !date.isAfter(lasttime.getKey()) ; date = date.plusYears(1)){
       System.out.println("DOANH THU CUA NAM " + date.getYear());
       thong_ke_bydate(LocalDate.of(date.getYear(), 1, 1), LocalDate.of(date.getYear(), 12, 31));
       doanh_thu_byyear.put(date.getYear(), RoomManager.tong_doanh_thu_theo_nam(date.getYear()));
  }

  for (Map.Entry<Integer,Double> c : doanh_thu_byyear.entrySet()){
    System.out.println(c.getKey() + " : " + c.getValue()) ;
  }
}
  




    public static Room get_room(String room_name){
        if(RoomManager.list_room.isEmpty()){
            System.out.println("rong");
            return null;
        }
        for (Room room : RoomManager.list_room){
            if (room.getName().equalsIgnoreCase(room_name.trim())){
                return room;
            }
        }
        return null ;
    }

    public static void show_list_room(){
        int bor = 0;
        System.out.println("╔" + border(20) +"╦"+ border(10) +"╦"+ border(10) +"╦"+ border(20) +"╦"+ border(20) +"╦"+ border(20)  + "╗");      
        System.out.print("║" + form_SO("Room name", 20));
        System.out.print("║" + form_SO("Size",10 ));
        System.out.print("║" + form_SO("Tang", 10));           
        System.out.print("║" + form_SO("Status ",20 ));
        System.out.print("║" + form_SO("Price",20 ));
        System.out.println("║" + form_SO("Last update",20 )+"║");
        
        for (Room room : RoomManager.list_room){
            if (bor == 0){
                System.out.println("╠" + border(20) +"╩"+ border(10) +"╩"+ border(10) +"╩"+ border(20) +"╩"+ border(20) +"╩"+ border(20)  + "╣");
                bor++;
            } 
            else {
                System.out.println("║" + border_thuong(20) +"+"+ border_thuong(10) +"+"+ border_thuong(10) +"+"+ border_thuong(20) +"+"+ border_thuong(20) +"+"+ border_thuong(20)  + "║");
            }
            StringBuilder str = new StringBuilder();  
            str.append("║" + form_SO(room.getName(), 20));
            str.append("|" + form_SO(room.getSize(),10 ));
            str.append("|" + form_SO(room.getTang(), 10));
            String trangthai = room.getStatus() == 0 ? "Tam dung" : "Dang hoat dong";
            str.append("|" + form_SO(trangthai,20 ));
            str.append("|" + form_SO(form_tien.format(room.getPrice())+" vnd ",20 ));
            String date = room.getNgaycapnhatgannhat() == null ? "######" : room.getNgaycapnhatgannhat().format(f_out);
            str.append("|" + form_SO(date,20 )+"║");
               System.out.println(str);
        }
       System.out.println("╚"+border(105)+"╝");
    }

    public void chinh_sua_phong(){
        Scanner sc = new Scanner(System.in);
        int luachon = 0;
        do { 
            this.show_list_room();
            do { 
            System.out.println("╔"+RoomManager.border(70)+"╗");
            System.out.println("║" +RoomManager.form_SO("OPTION", 70)+"║");
            System.out.println("╠"+RoomManager.border(70)+"╣");
            System.out.println("║"+RoomManager.form_option("0.Show list room", 70)+"║");
            System.out.println("║"+RoomManager.form_option("0.Sua ten phong", 70)+"║");
            System.out.println("║"+RoomManager.form_option("1.Chinh sua kich thuoc", 70)+"║");
            System.out.println("║"+RoomManager.form_option("2.Thay doi trang thai", 70)+"║");
            System.out.println("║"+RoomManager.form_option("3.hoang thanh", 70)+"║");            
            System.out.println("╚" + RoomManager.border(70) + "╝");   
            System.out.println("Nhap lua chon : ");
            
            do { 
                
            } while (true);
            
            } while (true); 
            
        } while (luachon == 0);  
    }


public static void main(String[] args) {
    update_calendar();
}
 
}
