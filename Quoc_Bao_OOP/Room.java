
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

public abstract class Room implements Comparable<Room> {
    protected String Name;
    protected int Size;
    protected int Status;
    protected double  Price;
    protected int tang;
    protected TreeMap<LocalDate,Booking[]> calendar;

    public Room(String name, int size, int status,double  price,int tang ){
        this.Name = name;
        this.Size = size;
        this.Status = status;
        this.Price = price;
        this.tang = tang;
        this.calendar = new TreeMap<>();
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public TreeMap<LocalDate, Booking[]> getCalendar() {
        return this.calendar;
    }

    public void setCalendar(TreeMap<LocalDate, Booking[]> calendar) {
        this.calendar = calendar;
    }

    DateTimeFormatter form_time = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public boolean check_calendar(LocalDate date,int session){
        try {
            if(this.calendar.get(date)[session] != null);
            return true ;
        } catch (Exception e) {
        }
        return false;
            
    }

    public boolean add_booking(LocalDate date , int session,Booking book){
        String str = session == 0? "sang" : session == 1 ? "trua" : "toi";
        if (this.check_calendar(date, session)){
            System.out.println("Phong " + this.getName() +" da co lich vao buoi "+str);
            return false; 
        }
        if (!this.calendar.containsKey(date)){
            this.calendar.put(date, new Booking[3]);
        }
        this.calendar.get(date)[session] = book;
        System.out.println("Phong " + this.getName() + " da them thanh cong lich vao buoi " + str);
        return true;
    }

    public boolean delete_booking(LocalDate date, int session){
        String str = session == 0 ? "sang" : session == 1? "trua" :"chieu";
        if (!check_calendar(date, session)){
            System.out.println("Phong " + this.Name + " vao buoi " + str + " ngay " + date.format(form_time) + " khong co lich");
            return false ;
        }

        this.calendar.get(date)[session] = null;
        // System.out.println("lich vao buoi " + str + " ngay " + date.format(form_time) + " cua phong "+ this.getName() + " da duoc xoa thanh cong ");
        return true;
    }
      
    public int dem_sl(){
        int d = 0;
        for (Map.Entry<LocalDate,Booking[]> c : this.calendar.entrySet()){
            for(int i = 0; i < 3; i++){
                if ( c.getValue()[i] != null){
                    d++;
                }
            }  
        }
        return d;
    }

    public int dem_sl_bydate(LocalDate begin, LocalDate end){
        int d = 0;
        for (Map.Entry<LocalDate,Booking[]> c : this.calendar.entrySet()){
            if(!c.getKey().isBefore(begin) && !c.getKey().isAfter(end)){
                for(int i = 0; i < 3; i++){
                    if ( c.getValue()[i] != null){
                        d++;
                    }
                }  
            }
            
        }
        return d;
    }
   
    public int dem_sl_by_date(LocalDate begin, LocalDate end){
        int d = 0;
        for (Map.Entry<LocalDate,Booking[]> c : this.calendar.entrySet()){
            if(!c.getKey().isBefore(begin) && !c.getKey().isAfter(end)){
                for(int i = 0; i < 3; i++){
                    if ( c.getValue()[i] != null){
                        d++;
                    }
                }  
            }
           
        }
        return d;
    }

    public double tong_doanh_thu(){
        double d = 0;
        for (Map.Entry<LocalDate,Booking[]> c : this.calendar.entrySet()){
            for(int i = 0; i < 3; i++){
                if ( c.getValue()[i] != null){
                    d += c.getValue()[i].getPrice();
                }
            }  
        }
        return d;
    }

    public double tong_doanh_thu_bydate(LocalDate begin, LocalDate end){
        double d = 0;
        for (Map.Entry<LocalDate,Booking[]> c : this.calendar.entrySet()){
            if(!c.getKey().isBefore(begin) && !c.getKey().isAfter(end)){
                for(int i = 0; i < 3; i++){
                    if ( c.getValue()[i] != null){
                        d += c.getValue()[i].getPrice();
                    }
                } 
            }
             
        }
        return d;
    }


    public double tong_doanh_thu_dichvu(){
        double d = 0;
        for (Map.Entry<LocalDate,Booking[]> c : this.calendar.entrySet()){
            for(int i = 0; i < 3; i++){
                if ( c.getValue()[i] != null){                   
                    for (Service sc :  c.getValue()[i].getSelectedServices()){
                            d += sc.getPricepersession();
                    }           
                }
            }  
        }
        return d;
    }
    public double tong_doanh_thu_dichvu_bydate(LocalDate begin, LocalDate end){
        double d = 0;
        for (Map.Entry<LocalDate,Booking[]> c : this.calendar.entrySet()){
            if(!c.getKey().isBefore(begin) && !c.getKey().isAfter(end)){
                for(int i = 0; i < 3; i++){
                    if ( c.getValue()[i] != null){                   
                        for (Service sc :  c.getValue()[i].getSelectedServices()){
                                d += sc.getPricepersession();
                        }           
                    }
                } 
            }
             
        }
        return d;
    }

    

    public int getTang() {
        return tang;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }

    @Override
    public int compareTo(Room other) {
        return this.getName().compareTo(other.getName());
    }   

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Room{");
        sb.append("Name=").append(Name);
        sb.append(", Size=").append(Size);
        sb.append(", Status=").append(Status);
        sb.append(", Price=").append(Price);
        sb.append(", tang=").append(tang);
        sb.append('}');
        return sb.toString();
    }
}
