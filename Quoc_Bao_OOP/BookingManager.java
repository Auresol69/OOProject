import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BookingManager {
    protected static ArrayList<Booking> bookings;
    
    static {
        BookingManager.bookings = new ArrayList<>();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try (BufferedReader br = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/booking.txt"))) {
           
            String line;
            while ((line = br.readLine()) != null) {
                String[] linee = line.split("\\*");
                for (int ss = 0; ss < linee.length; ss++){
                    
                
                    String[] str = linee[ss].split("#");
                    int id = Integer.parseInt(str[0]);
                    double price = Double.parseDouble(str[1]);
                    int idreciep = Integer.parseInt(str[2]);                    
                    
                    
                    Customer cus = CustomerManager.get_cus(str[3]);
                    if (cus == null){
                        cus = new Customer("####", "####", false);   
                    } 

                    ArrayList<Service> list_dv = new ArrayList<>();
                    String[] dichvu = str[4].split("\\$"); 
                    for (String dv : dichvu){
                        Service sv = ServiceManager.getdichvu(dv); 
                        list_dv.add(sv);
                        if(sv == null){
                            sv = new Service("####", 0.0);                    
                        }
                    }                    
                    TreeMap<LocalDate,TreeMap<Integer,ArrayList<Room>>> calendar = new TreeMap<>();
                    String[] lich = str[5].split("\\?");
                    for (int i = 0; i< lich.length; i++){
                        String[] ngay = lich[i].split("\\$"); 
                        calendar.put(LocalDate.parse(ngay[0], f), new TreeMap<>());
                        String[] buoi = ngay[1].split("!");
                        for (int j =0 ;j < buoi.length ; j++){
                            String[] phong = buoi[j].split("\\^"); 
                            for (int k = 0; k < phong.length ; k++){
                               
                                if (k == 0){
                                    calendar.get(LocalDate.parse(ngay[0], f)).put(Integer.parseInt(phong[0]),new ArrayList<>());
                                } else {                                                                        
                                    Room r = RoomManager.get_room(phong[k]);
                                    calendar.get(LocalDate.parse(ngay[0], f)).get(Integer.parseInt(phong[0])).add(r);
                                }
                            }
                        }                                            
                    }                    
                    Booking booking = new Booking(id, price, idreciep, cus, list_dv, calendar);     
                    System.out.println(booking);              
                    bookings.add(booking);
                    

                    }
            }
                
        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
       
    }

    public static void addBooking(Booking booking) {
        bookings.add(booking);
    }

    



    public  boolean removeBooking(int index) {
        if (index < 0 || index >= bookings.size())
            return false;
        bookings.remove(index);
        return true;
    }

    public  void clearBooking() {
        bookings.clear();
    }

    public  ArrayList<Booking> getBookings() {
        return bookings;
    }

    public static void setBookings(ArrayList<Booking> bookings) {
        BookingManager.bookings = bookings;
    }

    public static void printList() {
        for (Booking b : bookings) {
            System.out.println(b.getCus().XuatThongTin() + " " + "id: " + b.getId());
        }
    }

    // THAO TÁC

    public void terminal() {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to Booking Terminal:");
            System.out.println("1. Add new booking");
            System.out.println("2. Remove booking");
            System.out.println("3. Clear all bookings");
            System.out.println("4. List all bookings");
            System.out.println("5. Find booking by phone number");
            System.out.println("0. Exit");
            System.out.println("Nhập một số nguyên (0 để thoát): ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Bạn phải nhập một số nguyên!");
                continue;
            }
            switch (choice) {
                case 1:
                    Booking tmp = new Booking();
                    // tmp.setInfo();
                    break;
                case 2:
                    System.out.println("Nhập ID của đặt chỗ cần xóa:");
                    int id_remove;
                    try {
                        id_remove = Integer.parseInt(sc.nextLine());
                        if (this.removeBooking(id_remove)) {
                            System.out.println("Đã xóa đặt chỗ thành công!");
                        } else {
                            System.out.println("Không tìm thấy đặt chỗ với ID này.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: ID phải là số nguyên!");
                    }
                    break;
                case 3:
                    this.clearBooking();
                    break;
                case 4:
                    this.printList();
                    break;
                case 5:
                    String num;
                    num = sc.nextLine();
                    this.findBookingByPhoneNumber(num);
                    break;
                case 0:
                    break;
                default:
                    break;
            }
            if (choice == 0) {
                System.out.println("Exiting...");
                break;
            }
        }
    }

    public  void findBookingByPhoneNumber(String phonenumber) {
        boolean found = false;

        for (Booking booking : bookings) {
            if (booking != null && booking.getCus() != null &&
                    phonenumber.equals(booking.getCus().getPhoneNumber())) {
                System.out.println(booking.getCus().XuatThongTin() + " " + "id: " + booking.getId());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No booking found with the phone number: " + phonenumber);
        }
    }

    public  void thanhToan() {
        CustomerManager cm = new CustomerManager();
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap so dien thoai nguoi dung de thanh toan: ");
        String phone_cus = sc.nextLine();
        // Loc booking theo sdtn
        List<Booking> filteredBookings = bookings.stream()
                .filter(booking -> booking.getCus().getPhoneNumber().equals(phone_cus)).collect(Collectors.toList());
        if (filteredBookings == null) {
            System.out.println("Khong tim thay Booking nao cho so dien thoai nay.");
            return;
        }
        // Hien thi Bookings
        filteredBookings
                .forEach(booking -> System.out.println("Id: " + booking.getId() + ", total: " + booking.getPrice()
                        + ", ReceiptID: " + booking.getReceiptid()));
        ArrayList<Booking> bookingList = new ArrayList<>();
        while (true) {
            try {
                System.out.print("Chon ID booking de thanh toan: ");
                int id_thanhtoan = Integer.parseInt(sc.nextLine());
                Booking bookingToPay = filteredBookings.stream().filter(booking -> booking.getId() == id_thanhtoan)
                        .findFirst().orElse(null);
                if (bookingToPay == null) {
                    System.out.println("Khong tim thay Booking voi ID nay.");
                    continue;
                }
                if (bookingList.contains(bookingToPay)) {
                    System.out.println("Booking nay da duoc them vao danh sach thanh toan.");
                } else {
                    bookingList.add(bookingToPay);
                    System.out.println("Da them Booking voi ID " + id_thanhtoan + " vao danh sach thanh toan.");
                }
                System.out.print("Ban co muon them ID booking khac khong? (1 = co, 0 = khong): ");
                int option = Integer.parseInt(sc.nextLine());
                if (option == 0) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so nguyen hop le.");
            }
        }
        if (!bookingList.isEmpty()) {
            Customer cus = cm.getCustomers().stream()
                    .filter(customer -> customer.getPhoneNumber().equals(phone_cus))
                    .findFirst().orElse(null);

            if (cus != null) {
                PaymentMethod paymentmethod = null;
                while (paymentmethod == null) {
                    System.out.println(" ╔═══════════════════════════════════╗");
                    System.out.println(" ║    CHON PHUONG THUC THANH TOAN    ║");
                    System.out.println(" ╠═══════════════════════════════════╣");
                    System.out.println(" ║  1. Cash                          ║");
                    System.out.println(" ║  2. Momo                          ║");
                    System.out.println(" ║  3. ATM                           ║");
                    System.out.println(" ╚═══════════════════════════════════╝");
                    System.out.print(" Nhập lựa chọn của bạn: ");
                    System.out.print("Lua chon: ");
                    int option = Integer.parseInt(sc.nextLine());
                    switch (option) {
                        case 1:
                            paymentmethod = new Cash();
                            break;
                        case 2:
                            System.out.print("Nhap Momo PIN: ");
                            String momoPin = sc.nextLine();
                            System.out.print("Nhap Momo phone: ");
                            String momoPhone = sc.nextLine();
                            paymentmethod = new Momo(momoPin, momoPhone);
                            break;
                        case 3:
                            System.out.print("Nhap so the ATM: ");
                            String cardNumber = sc.nextLine();
                            System.out.print("Nhap ATM PIN: ");
                            String atmPin = sc.nextLine();
                            paymentmethod = new ATM(cardNumber, atmPin);
                            break;
                        default:
                            System.out.println("Lua chon khong hop le!");
                            return;
                    }
                    paymentmethod.ProcessPayment(0); // de tam la 0 vi chua lay dc price
                }

                Receipt rc = new Receipt(cus, paymentmethod, bookingList);
                ReceiptManager.receipts.add(rc);
                for (Booking booking : bookingList) {
                    booking.setReceiptid(rc.getId());
                }
                System.out.println("Thanh toan thanh cong. Receipt ID: " + rc.getId());
                System.out.println("So hoa don cua ban la: " + rc.getId());
            } else {
                System.out.println("Khong tim thay khach hang voi so dien thoai: " + phone_cus);
            }
        } else {
            System.out.println("Khong co Booking nao duoc thanh toan.");
        }
        ReceiptManager.showReceipt1Cus();
    }

    public void ket_thuc(){

        StringBuilder str = new StringBuilder();
        for (Booking book : this.bookings){
            str.append(book.getId()+"#");
            str.append(book.getPrice()+"#");
            str.append(book.getReceiptid()+"#");
            str.append(book.getCus().getPhoneNumber()+"#");
            for (Service sv : book.getSelectedServices()){
                int i =0;
                if (i==0){
                    str.append(sv.getName());
                    i++;
                } else {
                    str.append("\\$"+sv.getName());
                } 
            }
            str.append("#");
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (Map.Entry<LocalDate,TreeMap<Integer,ArrayList<Room>>> c : book.getDate().entrySet()){
                str.append(c.getKey().format(f)+"$");
                for (Map.Entry<Integer,ArrayList<Room>> c1 : c.getValue().entrySet()){
                    str.append(c1.getKey()+"^");
                    for (Room room : c1.getValue()){
                        str.append(room.getName());
                    }
                    str.append("!");
                }
            }
        }
        String filePath = "./Quoc_Bao_OOP/data/output.txt"; // Đường dẫn tới file
        String data = "Đây là một ví d";

        // Sử dụng BufferedWriter để ghi dữ liệu
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(data); // Ghi dữ liệu vào file
            writer.write("Thêm một dòng khác.");
        } catch (IOException e) {
            System.err.println("Có lỗi xảy ra khi ghi vào file: " + e.getMessage());
        }
    }
   public void add_booking(Booking b){
    this.bookings.add(b);
   }

   public static void hisroty(){
        int dembuoi=0;
        int temp =0;
        int idtam =-1;
        System.out.println("╔"+RoomManager.border(160)+"╗");
        System.out.println("║"+RoomManager.form_SO("HISTORY", 160)+"║");
        System.out.println("╠"+RoomManager.border(10)+"╦"+RoomManager.border(15)+"╦"+RoomManager.border(10)+"╦"+RoomManager.border(50)+"╦"+RoomManager.border(50)+"╦"+RoomManager.border(20)+"╣");
        StringBuilder str = new StringBuilder();
       str.append("║"+RoomManager.form_SO("ID", 10));
       str.append("║"+RoomManager.form_SO("Date", 15));
       str.append("║"+RoomManager.form_SO("buoi", 10));
       str.append("║"+RoomManager.form_SO("phong", 50));
       str.append("║"+RoomManager.form_SO("dich vu", 50));
       str.append("║"+RoomManager.form_SO("tong tien", 20)+"║");
       System.out.println(str);
        for (Booking booking : bookings){
            LocalDate datetam = LocalDate.of(1, 1, 1);
            System.out.println("║"+RoomManager.border(10)+"╬"+RoomManager.border(15)+"╬"+RoomManager.border(10)+"╬"+RoomManager.border(50)+"╬"+RoomManager.border(50)+"╬"+RoomManager.border(20)+"╣");
            temp = 0;
            for (Map.Entry<LocalDate,TreeMap<Integer,ArrayList<Room>>> c : booking.getDate().entrySet()){
                if(temp ==0){
                    temp++;
                } else {
                    System.out.println("║"+RoomManager.form_SO("",10)+"╠"+RoomManager.border(15)+"╬"+RoomManager.border(10)+"╬"+RoomManager.border(50)+"╣"+RoomManager.form_SO("", 50)+"║"+RoomManager.form_SO("",20)+"║");
                }
                dembuoi =0;
                    for (Map.Entry<Integer,ArrayList<Room>> c1 : c.getValue().entrySet()){    
                        if(dembuoi ==0){
                            dembuoi++;
                        } else {
                            System.out.println("║"+RoomManager.form_SO("",10)+"║"+RoomManager.form_SO("",15)+"╠"+RoomManager.border(10)+"╬"+RoomManager.border(50)+"╣"+RoomManager.form_SO("" ,50)+"║"+RoomManager.form_SO("",20)+"║");
                        }
                        StringBuilder phong = new StringBuilder();
                        int k = 0;
                        for (Room room : c1.getValue()){                                                   
                            
                            if(k==0){
                                k++;
                                phong.append(room.getName());
                            } else {
                                phong.append(", "+room.getName());
                            }
                        }
                        StringBuilder dichvu = new StringBuilder();
                        k=0;
                        for (Service sv : booking.getSelectedServices()){
                            if (k==0){
                                k++;
                                dichvu.append(sv.getName());
                            } else {
                                dichvu.append(", "+sv.getName());
                            }
                            
                        }
                        if (idtam != booking.getId()){
                            idtam = booking.getId();
                            str = new StringBuilder();
                            String session = (c1.getKey()==0) ? "sang" : (c1.getKey() == 1) ? "trua" : "toi";
                            str.append("║"+RoomManager.form_SO(booking.getId(), 10));
                            str.append("║"+RoomManager.form_SO(c.getKey(), 15));
                            str.append("║"+RoomManager.form_SO(session, 10));//buoi
                            str.append("║"+RoomManager.form_SO(phong, 50));//danh sahcphong 
                            str.append("║"+RoomManager.form_SO(dichvu, 50));//danhsach dv
                            str.append("║"+RoomManager.form_SO(RoomManager.form_tien.format(booking.getPrice()), 20)+"║");
                            datetam = c.getKey();
                        } else {
                            if (!datetam.isEqual(c.getKey())) {
                                datetam = c.getKey();
                                str = new StringBuilder();
                                String session = (c1.getKey()==0) ? "sang" : (c1.getKey() == 1) ? "trua" : "toi";
                                str.append("║"+RoomManager.form_SO("", 10));
                                str.append("║"+RoomManager.form_SO(c.getKey(), 15));
                                str.append("║"+RoomManager.form_SO(session, 10));//buoi
                                str.append("║"+RoomManager.form_SO(phong, 50));//danh sahcphong 
                                str.append("║"+RoomManager.form_SO("", 50));//danhsach dv
                                str.append("║"+RoomManager.form_SO("", 20)+"║");
                            } else {
                                str = new StringBuilder();
                                String session = (c1.getKey()==0) ? "sang" : (c1.getKey() == 1) ? "trua" : "toi";
                                str.append("║"+RoomManager.form_SO("", 10));
                                str.append("║"+RoomManager.form_SO("", 15));
                                str.append("║"+RoomManager.form_SO(session, 10));//buoi
                                str.append("║"+RoomManager.form_SO(phong, 50));//danh sahcphong 
                                str.append("║"+RoomManager.form_SO("", 50));//danhsach dv
                                str.append("║"+RoomManager.form_SO("", 20)+"║");
                            }
                            
                            
                        }
                            
                              
                        
                        System.out.println(str);   
                    }
                }
        }
        
        System.out.println("╚"+RoomManager.border(10)+"╩"+RoomManager.border(15)+"╩"+RoomManager.border(10)+"╩"+RoomManager.border(50)+"╩"+RoomManager.border(50)+"╩"+RoomManager.border(20)+"╝");
    }



    public static void main(String[] args) {
       
        // RoomManager.show_calendar(LocalDate.of(2004, 9, 1),LocalDate.of(2004, 9, 3), 0);
        // Booking b = new Booking();
        // bookings.add(b);
        // // b.set_calendar_and_service();
        // RoomManager.show_calendar(LocalDate.of(2004, 9, 1),LocalDate.of(2004, 9, 3), 0);
        hisroty();
        RoomManager.sosanh_cac_nam();
        RoomManager.thong_ke_theo_nam();
        
       
       
        
    }

    
}
