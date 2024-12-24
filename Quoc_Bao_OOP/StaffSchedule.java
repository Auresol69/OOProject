import java.time.LocalDate;

public class StaffSchedule {
    private LocalDate date;
    private int shift;
    private Room room; // Thêm thông tin phòng

    // Constructor
    public StaffSchedule(LocalDate date, int shift, Room room) {
        this.date = date;
        this.shift = shift;
        this.room = room;
    }

    // Getter cho room
    public Room getRoom() {
        return room;
    }

    // Setter cho room
    public void setRoom(Room room) {
        this.room = room;
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
        System.out.println("Ngày: " + date + ", Ca: " + shift + ", Phòng: " + room);
    }
}

