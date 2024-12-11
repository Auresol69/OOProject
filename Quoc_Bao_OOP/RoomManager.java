
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
    private ArrayList<Room> list_room;
    
    public TreeMap<LocalDate,ArrayList<Room>> calendar;
    public RoomManager (){

        ArrayList<Room> list = new ArrayList<>();

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
            list.add(room);
    
        }
       } catch (Exception e) {
        // TODO: handle exception
       }

       Collections.sort(list, (a,b) -> a.getName().compareTo(b.getName()));
       
       this.list_room = list;
       
       
       this.calendar = new TreeMap<>();
    }



    public static boolean check_room(String name){
        try (BufferedReader br  = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/Roommanager.txt"))){
            String line ;
            while((line = br.readLine())!=null){
                String[] str = line.split("#");
                if (str[0].equalsIgnoreCase(name)){
                    return true;
                }
            
            }
        }catch (Exception e) {
            // TODO: handle exception
           }
        return false;
    }
    
    public ArrayList<Room> getList_room() {
        return list_room;
    }
    public void setList_room(ArrayList<Room> list_room) {
        this.list_room = list_room;
    }

    public void update_calendar(){
        for (Room room : this.list_room){
            for (Map.Entry<LocalDate,Booking[]> c: room.getCalendar().entrySet()){
                if (!this.calendar.containsKey(c.getKey())){
                    this.calendar.put(c.getKey(), new ArrayList<>());
                }
                if (!this.calendar.get(c.getKey()).contains(room)){
                    this.calendar.get(c.getKey()).add(room);
                }
            }
        }
    }

    public void add_room(Room room){
        this.list_room.add(room);
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

    public void show_calendar(LocalDate begin, LocalDate end,int size){
        boolean kt = false;
        TreeMap<LocalDate,ArrayList<Room> > bansao = new TreeMap<>();
        for (LocalDate date = begin; !date.isAfter(end);date= date.plusDays(1)){
                bansao.put(date,list_room); 
        }

        
        for (Map.Entry<LocalDate,ArrayList<Room> > lich : bansao.entrySet()){
        int dem = 0;
        String mau ;
        if (lich.getKey().getDayOfMonth()%2==0){ mau ="\u001B[33m";} else { mau ="\u001B[35m"; }
            System.out.println(mau+"╔"+border(143)+"╗"+"\u001B[0m");
          System.out.println(mau+"║"+"\u001B[0m"+"\u001B[1m"+form_SO(lich.getKey().format(f_out),143)+"\u001B[0m"+mau+"║"+"\u001B[0m");
          System.out.println(mau+"╠"+border(143)+"╣"+"\u001B[0m");
          System.out.println(mau+"║"+"\u001B[0m"+ form_SO("ten phong") + mau+"║"+"\u001B[0m" + form_SO("sang") +mau+"║"+"\u001B[0m" + form_SO("trua") + mau+"║"+"\u001B[0m" + form_SO("chieu") + mau+"║"+"\u001B[0m");
          for (Room room : lich.getValue()){
            kt = false;
            if (room.getSize() == size+1 || room.getSize() == size){
                kt = true;
            }
            String mota = room.getSize() == 0 ? "  (10-15)  " : room.getSize() == 1 ? "  (15-25)  " : "  (25-40) ";
            StringBuilder str = new StringBuilder();
            str.append(mau+"║"+"\u001B[0m" + form_SO(room.getName()+mota) + mau+"║"+"\u001B[0m");
            if (!room.getCalendar().containsKey(lich.getKey())){
                str.append(form_SO("") + mau+"║"+"\u001B[0m" +form_SO("") + mau+"║"+"\u001B[0m" +form_SO("") +mau+ "║"+"\u001B[0m");
            } else {
                for (int i = 0; i< 3; i++){
                    if(room.getCalendar().get(lich.getKey())[i] == null){
                        str.append(form_SO("")+ mau+"║"+"\u001B[0m");
                    } else {
                        str.append(form_SO(room.getCalendar().get(lich.getKey())[i].getCus().getName() +  room.getCalendar().get(lich.getKey())[i].getCus().getPhoneNumber())+mau+"║"+"\u001B[0m");
                    }
                }
            } 
            if (dem == 0){
                System.out.println(mau+"╠" + border(143) +"╣"+"\u001B[0m");
                System.out.println(str);
                dem++;
            } else if(kt) {
                System.out.println(mau+"║" +border_thuong(143)+"║"+"\u001B[0m");
                System.out.println(str);
                dem++;
            }
           
            
          }
          System.out.println(mau+"╚" + border(143) + "╝"+"\u001B[0m");
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
  

public static ArrayList<Room> ds_test(){
    ArrayList<Room> ds = new ArrayList<>();
    for (int i = 0; i< 3 ;i++){
        Room st = new Standard_room("" +(101+i), i+1, 1, i+2, i+1);
        Room v = new Vip_room("Vip" + (101+i), i+1, 1, i+10, i+1);
        ds.add(st);
        ds.add(v);
    }
    return ds;
}
 

    public Room get_room(String room_name){
        for (Room room : this.list_room){
            if (room.getName().equals(room_name.trim())){
                return room;
            }
        }
        return null ;
    }

    

}
