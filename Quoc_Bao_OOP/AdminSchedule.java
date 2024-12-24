import java.time.LocalDate;
public class AdminSchedule {
    private LocalDate date;
    private int shift;
    

    // Constructor
    public AdminSchedule(LocalDate date, int shift) {
        this.date = date;
        this.shift = shift;
      
    }



    // Getter cho date
    public LocalDate getDate() {
        return date;
    }

    // Getter cho shift
    public int getShift() {
        return shift;
    }

    // Hiển thị thông tin lịch
    public void displaySchedule() {
        System.out.println("Ngày: " + date + ", Ca: " + shift);
    }
}





