import java.time.LocalDate;
import java.time.LocalTime;

public class StaffSchedule {
        private LocalDate date;
        private int Shift;

    // Constructor
    public StaffSchedule(LocalDate date, int  Shift) {
        this.date = date;
        this.Shift = Shift;
    }

    // Phương thức hiển thị thông tin lịch làm việc
    public void displaySchedule() {
        System.out.println("Ngày: " + date +
                           ", Buổi: " + Shift
        );
    }

    // Getter (có thể cần nếu muốn xử lý dữ liệu lịch ngoài lớp)
    public LocalDate getDate() {
        return date;
    }

    public int getShift() {
        return Shift;
    }
}
