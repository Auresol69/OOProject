
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public abstract class Room {
    protected String Name;
    protected int Size;
    protected int Status;
    protected float Price;
    protected TreeMap<LocalDate,Booking[]> calendar;

    public Room(String name, int size, int status,float price ){
        this.Name = name;
        this.Size = size;
        this.Status = status;
        this.Price = price;
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

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
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
    
    

    public String form_SO(Object c) {
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
    public static String border(int a){
        StringBuilder b = new StringBuilder();
        for (int i =0 ; i < a; i++){
            b.append("-");
        }
        return b.toString();
    }

    public void show_by_date(LocalDate b, LocalDate e){
        
        for (Map.Entry<LocalDate,Booking[]> c : this.calendar.entrySet()){
            if (c.getKey().isAfter(b) && c.getKey().isBefore(e)){
                
            }
        }
    }
}
