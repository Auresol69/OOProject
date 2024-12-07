
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public abstract class Room implements Comparable<Room> {
    protected String Name;
    protected int Size;
    protected int Status;
    protected double Price;
    protected int tang;
    protected TreeMap<LocalDate,Booking[]> calendar;

    public Room(String name, int size, int status,double price,int tang ){
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


    public boolean check_calendar(Booking book){
        if(this.calendar.containsKey(book.getDate())){
            if (this.calendar.get(book.getDate())[book.getSession()] != null){
                System.out.println("Lịch đã bị trùng, xin kiểm tra lại");
                return true;
            }
        }
        return false;
    }
    public boolean add_booking(Booking book){
        if (check_calendar(book)){
            return false;
        }
        book.setRoom(this);
        if (!this.calendar.containsKey(book.getDate())){
            Booking books[] = new Booking[3];
            books[book.getSession()] = book;
            this.calendar.put(book.getDate(),books);

        } else {
            this.calendar.get(book.getDate())[book.getSession()] = book;
        }
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
    public double tong_doanh_thu_dichvu(){
        double d = 0;
        for (Map.Entry<LocalDate,Booking[]> c : this.calendar.entrySet()){
            for(int i = 0; i < 3; i++){
                if ( c.getValue()[i] != null){                    
                    for(Service s : c.getValue()[i].getselectedServices()){
                    
                         d += s.getPricepersession();
                    }                    
                }
            }  
        }
        return d;
    }

    public double tong_doanh_thu_by_date(LocalDate begin, LocalDate end){
        double d = 0;
        for (Map.Entry<LocalDate,Booking[]> c : this.calendar.entrySet()){
            if (!c.getKey().isBefore(begin) && !c.getKey().isAfter(end)){
                for(int i = 0; i < 3; i++){
                    if ( c.getValue()[i] != null){
                        d += c.getValue()[i].getPrice();
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
}
