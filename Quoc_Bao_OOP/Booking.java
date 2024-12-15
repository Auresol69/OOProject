
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;
public class Booking extends BookingIdManager {
    protected Integer id;
    protected Customer cus;   
    protected double price;
    protected Integer receiptid;
    protected TreeMap<LocalDate, TreeMap<Integer,ArrayList<Room>>> date;
    protected ArrayList<Service> selectedServices;
    DecimalFormat form_tien = new DecimalFormat("#,###.00");   
    public static String yeelow (String x){
        return "\033[33m" + x + "\033[0m";
    } 
    public static String red(String x){
        return "\033[31m" + x + "\033[0m";
    } 
    public static String green (String x){
        return "\033[32m" + x + "\033[0m";
    } 

    public double getPrice() {
        return price;
    }

    
    public void setPrice(double price) {
        this.price = price;
    }

    public TreeMap<LocalDate, TreeMap<Integer, ArrayList<Room>>> getDate() {
        return date;
    }

    public void setDate(TreeMap<LocalDate, TreeMap<Integer, ArrayList<Room>>> date) {
        this.date = date;
    }

    public Booking (int id, double price,int receiptid,Customer cus, ArrayList<Service> selectedServices,TreeMap<LocalDate, TreeMap<Integer,ArrayList<Room>>> date){
        this.id = id;
        this.cus = cus;
        this.price = price;
        this.receiptid = receiptid;
        this.selectedServices = selectedServices;
        this.date = date;

        for (Map.Entry<LocalDate,TreeMap<Integer,ArrayList<Room>>> c : this.date.entrySet()){
            for (Map.Entry<Integer,ArrayList<Room>> c1 : c.getValue().entrySet()){
                for (Room room : c1.getValue()){
                    room.add_booking(c.getKey(), c1.getKey(), this);
                }
            }
        }

    }

    public Booking() {
        this.id = BookingIdManager.getNextId();
        this.cus = new Customer();
        this.selectedServices = new ArrayList<>();
        this.price = 0;
        this.date = new TreeMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Service> getSelectedServices() {
        return this.selectedServices;
    }

    public Integer getReceiptid() {
        return receiptid;
    }

    public void setReceiptid(Integer receiptid) {
        this.receiptid = receiptid;
    }

    public void setSelectedServices(ArrayList<Service> selectedServices) {
        this.selectedServices = selectedServices;
    }

    public Customer getCus() {
        return cus;
    }

    public void setCus(Customer cus) {
        this.cus = cus;
    }

    public void clearselectedServices() {
        selectedServices.clear();

    }

    public void removeService(int index) {
        selectedServices.remove(index);
    }

    public void addService(Service service) {
        selectedServices.add(service);
    }

    DateTimeFormatter form_time = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Customer findCustomerByPhone(String phoneNumber, ArrayList<Customer> customers) {
        if (customers == null || phoneNumber == null) {
            return null;
        }

        for (Customer customer : customers) {
            if (customer != null && phoneNumber.equals(customer.getPhoneNumber())) {
                return customer;
            }
        }

        return null;
    }

    DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyy");

    public void infoBook() {
        Scanner sc = new Scanner(System.in);
        LocalDate date = null;
        String date_in;
        while (date == null) {
            try {
                date_in = sc.nextLine();
                date = LocalDate.parse(date_in, f);
            } catch (Exception e) {
                System.out.println("lỗi, Nhập lại ngày ");
            }
        }
    }

    public void tinhPrice() {
        double phidichvu = 0, sum = 0;
        for (Service sv : selectedServices) {
            phidichvu += sv.getPricepersession();
        }

        for (Map.Entry<LocalDate, TreeMap<Integer, ArrayList<Room>>> entry : date.entrySet()) {
            for (Map.Entry<Integer, ArrayList<Room>> entry2 : entry.getValue().entrySet()) {
                for (Room room : entry2.getValue()) {
                    if (room instanceof Vip_room) {
                        sum += phidichvu * 1.5 + room.getPrice();
                    } else {
                        sum += phidichvu + room.getPrice();
                    }
                }
            }
        }
        this.setPrice(sum);
    }

    public void xuatFileBooking() {
        try {
            FileWriter fw = new FileWriter("./Quoc_Bao_OOP/data/Bookingmanager.txt");
            for (Booking booking : BookingManager.bookings) {
                String services = booking.getSelectedServices().stream().map(Service::toString)
                        .collect(Collectors.joining("|"));
                String dates = booking.getDate().entrySet().stream()
                        .map(entry -> entry.getKey() + ":" + entry.getValue()).collect(Collectors.joining(";"));
                fw.append(
                        booking.getId() + "#" + booking.getCus().getPhoneNumber() + "#" + services + "#" + dates + "#"
                                + booking.getPrice() + "#" + booking.getReceiptid() + "\n");
            }
            fw.close();
            System.out.println("Ghi dữ liệu thành công!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInfo() {
        Scanner sc = new Scanner(System.in);
        int choice = -1 ;
       do { 
        System.out.print(yeelow("Enter Phone number customer  : "));
        String sdt = sc.nextLine();
        if (!CustomerManager.find(sdt)){
            System.out.println(red("╔"+RoomManager.border(70)+"╗"));
            System.out.println(red("║") +RoomManager.form_SO("Khach hang khong ton tai", 70)+red("║"));
            System.out.println(red("╠"+RoomManager.border(70)+"╣"));
            System.out.println(red("║")+RoomManager.form_option("0. Enter Phone_number customer", 70)+red("║"));
            System.out.println(red("║")+RoomManager.form_option("1. Created Customer", 70)+red("║"));
            System.out.println(red("║")+RoomManager.form_option("2. Exit ", 70)+yeelow("║"));
            System.out.println(red("╚" + RoomManager.border(70) + "╝"));                               
            do { 
                choice = sc.nextInt();
                sc.nextLine();
                if (!(choice >=0 && choice <=1)){
                    System.out.println("Nhap lai");
                }
            } while (!(choice >=0 && choice <=1));

            if(choice == 1){
                System.out.print("Nhap ten khach hang : ");
                String name = sc.nextLine();
                System.out.print("Nhap gioi tinh khach hang ||   0. Nam   1 . Nu   : ");
                
                do { 
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (!(choice >=0 && choice <=1)){
                        System.out.println("Nhap lai");
                    }
                } while (!(choice >=0 && choice <=1));


                
                if (choice == 0){
                    if (choice == 0){
                        Customer cus = new Customer(name, name, true);
                    } else {
                        Customer cus = new Customer(name, name, false);
                    }
                    CustomerManager.addCustomer(cus);
                    this.cus = cus;
                    System.out.println(green(cus.getName() + " " + cus.getPhoneNumber()));
                    choice = -1;
                } 
               
            }         
        }else if (choice == 2) {
            return ;
        } else {
            this.cus = CustomerManager.get_cus(sdt);
            System.out.println(green(cus.getName() + " " + cus.getPhoneNumber()));
        }
       } while (choice == 0);
        
      if (set_calendar_and_service()){
        System.out.println("Huy bo dat lich ");
        return ;
      }
      
    }

    public void printServices() {
        for (Service service : selectedServices) {
            System.out.println(service.getName());
        }
    }

    public double calculateTotalCost() {
        return 0.0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer: ").append(cus.getName()).append("\n");
        sb.append("Phone: ").append(cus.getPhoneNumber()).append("\n");
        sb.append("selectedServices:\n");
        for (Service service : selectedServices) {
            sb.append("- ").append(service.getName()).append(": ").append(service.getPricepersession())
                    .append("/hour\n");
        }
        return sb.toString();
    }

    public boolean add_room(LocalDate date, int session, Room room) {
        String str = session == 0 ? "sang" : session == 1 ? "trua" : "chieu";
        if (room.check_calendar(date, session)) {

            System.out.println("!!!!THEM KHONG THANH CONG !!!! : Phong " + room.getName() + " da co lich vao buoi "
                    + str + " ngay " + date);
            return false;
        }
        if (!this.date.containsKey(date)) {
            this.date.put(date, new TreeMap<>());
        }

        if (!this.date.get(date).containsKey(session)) {
            this.date.get(date).put(session, new ArrayList<>());
        }

        if (this.date.get(date).get(session).contains(room)) {
            System.out.println("!!!!THEM KHONG THANH CONG !!!! : Ban da dat lich nay cho phong " + room.getName()
                    + " vao buoi " + str + " ngay " + date);
            return false;
        }
        room.add_booking(date, session, this);
        this.date.get(date).get(session).add(room);
        System.out.println("Them thanh cong lich vao buoi " + str + " ngay " + date);
        return true;
    }

    public boolean delete_session(LocalDate date, int session) {
        String str = session == 0 ? "sang" : session == 1 ? "trua" : "chieu";
        if (!this.date.containsKey(date)) {
            System.out.println("Ban khong dat ngay " + date.format(form_time) + " cho lich");
            return false;
        }
        if (!this.date.get(date).containsKey(session)) {
            System.out.println("Ban khong dat buoi " + str + " ngay " + date.format(form_time) + " cho lich ");
            return false;
        }

        for (Room r : this.date.get(date).get(session)) {
            r.delete_booking(date, session);
        }

        this.date.get(date).put(session, new ArrayList<>());
        System.out.println("Lich nay vao buoi " + str + " ngay " + date.format(form_time) + " da duoc xoa tat ca");
        return true;
    }

    public boolean delete_room(LocalDate date, int session, Room room) {
        String str = session == 0 ? "sang" : session == 1 ? "trua" : "chieu";
        room.delete_booking(date, session);
        this.date.get(date).put(session, new ArrayList<>());
        System.out.println("lich nay vao buoi " + str + " ngay " + date.format(form_time) + " da duoc xoa");
        return true;
    }

    public LocalDate getfirtdate() {
        return this.date.isEmpty() ? null : this.date.lastKey();
    }


    public boolean set_calendar_and_service(){
        double price = 0;
        int sophongthuong = 0;
        int sophongvip = 0;
        double price_dv = 0;
        TreeMap<LocalDate,TreeMap<Integer,ArrayList<Room>>> c = new TreeMap<>();
        int luachon =0 ; 
        DateTimeFormatter form_time = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Scanner sc = new Scanner(System.in);
        LocalDate date = null;
        Room room = null;
        int session = 0;   
        boolean continueChoosing = true;
        TreeMap<Integer,TreeMap<Service,Boolean>> service_list = new TreeMap<>();  
        for (Service sv : ServiceManager.availableServices){
            TreeMap<Service,Boolean> sos = new TreeMap<>();
            sos.put(sv, false);
            service_list.put(session, sos);
            session++;            
        }
        session = -1;              
            do { 
                do { 
                    System.out.print(yeelow("Nhap ngay (dd/MM/yyyy) : "));
                    String date_b = sc.nextLine();
                    try {
                        date = LocalDate.parse(date_b, form_time);
                        session = 0;
                    } catch (Exception e) {
                        System.out.println(red("Loi dinh dang, Nhap lai !!"));
                    }
                } while (session != 0);  
                session = -1;
                if (!c.containsKey(date)){
                    c.put(date, new TreeMap<>());
                }    
                boolean kt = true;
                do { 
                    do { 
                        System.out.print(yeelow("Chon buoi cho ngay " + date.format(form_time) +  " || 0. sang       1.trua      2.chieu     : "));
                        
                        session = sc.nextInt();
                        sc.nextLine();
                        if(session >=0 && session <=2){
                            kt = false;
                        } else {
                            System.out.println(red("Loi!! Nhp lai buoi "));
                        }
                    } while (kt);                                       
                    if (!c.get(date).containsKey(session)){
                        c.get(date).put(session, new ArrayList<>());
                    }        
                    kt = true;
                    do { 
                        do { 
                            System.out.print(yeelow("nhap phong : "));
                            String roomname = sc.nextLine();
                            if (!RoomManager.check_room(roomname)){
                                System.out.println(red("phong '" + roomname + "' khong ton tai, Nhap lai !!"));
                            } else {
                                room = RoomManager.get_room(roomname);
                                kt = false;
                            }
                        } while (kt);
                        kt = true;

                        String str = session == 0? "sang" : session ==1 ?"trua" :"toi";
                        if (room.check_calendar(date, session)){
                            System.out.println(red("day la session : " +session));
                            System.out.println(red("Phong "+room.getName() + " da co lich vao buoi " + str + " ngay " + date.format(form_time)));
                        } else {
                            if (c.get(date).get(session).contains(room)){
                                System.out.println(red(str));
                            } else {
                                c.get(date).get(session).add(room);
                                System.out.println(green("them thanh cong ngay " + date.format(form_time) + " buoi " +str+ " phong " + room.getName()));
                                price += room.getPrice();
                                if (room instanceof Vip_room){
                                    sophongvip++;
                                } else {
                                    sophongthuong++;
                                }
                               
                            }
                        }                                        
                    do { 
                        System.out.println(yeelow("╔"+RoomManager.border(70)+"╗"));
                        System.out.println(yeelow("║") +RoomManager.form_SO("OPTION", 70)+yeelow("║"));
                        System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                        System.out.println(yeelow("║")+RoomManager.form_option("0. show lich", 70)+yeelow("║"));                        
                        System.out.println(yeelow("║")+RoomManager.form_option("1. Dat them ngay ", 70)+yeelow("║"));
                        if (date != null){
                            System.out.println(yeelow("║")+RoomManager.form_option("2. Dat them buoi trong ngay " +date.format(form_time), 70)+yeelow("║"));
                            if(session != -1) {
                                System.out.println(yeelow("║")+RoomManager.form_option("3. Dat them phong cho buoi "+ str + " ngay "+ date.format(form_time), 70)+yeelow("║"));
                            }                            
                        }                                                
                        System.out.println(yeelow("║")+RoomManager.form_option("4. Xoa ", 70)+yeelow("║"));
                        System.out.println(yeelow("║")+RoomManager.form_option("5. Xac nhan ", 70)+yeelow("║"));
                        System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                        System.out.println(yeelow("║")+RoomManager.form_SO("Tong tien tam tinh : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+yeelow("║"));                                    
                        System.out.println(yeelow("╚" + RoomManager.border(70) + "╝")); 
                        boolean xd = true;
                        do {
                            System.out.print(yeelow("Nhap lua chon : "));
                            luachon = sc.nextInt();
                            sc.nextLine();
                            if (date == null){
                                if (!(luachon == 0 || luachon == 1 || luachon == 4 || luachon == 5)){
                                    System.out.println(red("Nhap lai lua chon"));
                                    xd = true;
                                } else {
                                    xd = false;
                                }
                            } else {
                                if(luachon < 0 || luachon > 5){
                                    System.out.print(red("Nhap lai lua chon : "));
                                    xd = true;
                                    }  else {
                                        xd = false;
                                    }
                            }                            
                        } while (xd);

                        if (luachon == 4){
                            int luachontrong = -1;
                            boolean ktt = true;
                            do { 
                                System.out.println(yeelow("╔"+RoomManager.border(70)+"╗"));
                                System.out.println(yeelow("║") +RoomManager.form_SO("OPTION", 70)+yeelow("║"));
                                System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                                System.out.println(yeelow("║")+RoomManager.form_option("0.Xoa ngay", 70)+yeelow("║"));
                                System.out.println(yeelow("║")+RoomManager.form_option("1.Xoa buoi", 70)+yeelow("║"));
                                System.out.println(yeelow("║")+RoomManager.form_option("2.Xoa phong ", 70)+yeelow("║"));
                                System.out.println(yeelow("║")+RoomManager.form_option("3.Tro lai ", 70)+yeelow("║"));
                                System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                                System.out.println(yeelow("║")+RoomManager.form_SO("Tong tien tam tinh : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+yeelow("║"));                                    
                                System.out.println(yeelow("╚" + RoomManager.border(70) + "╝"));                                                                                          
                            do {
                                System.out.print(yeelow("Nhap lua chon : "));
                                luachontrong = sc.nextInt();
                                sc.nextLine();
                                if(luachontrong < 0 || luachontrong > 3){
                                System.out.print(red("Nhap lai lua chon : "));
                                }
                            } while (!(luachontrong >=0 && luachontrong<=5));
                            if (luachontrong == 0){
                                // xóa ngày 
                                int luachontrongtrong = -1;
                                do { 
                                    date = null;
                                System.out.print(yeelow("Nhap ngay muon xoa (dd/MM/yyyy) : "));
                                do { 
                                    try {
                                        String date_in = sc.nextLine();
                                        date = LocalDate.parse(date_in,form_time);
                                    } catch (Exception e) {
                                        System.out.println(red("Loi dinh dang, Nhap lai"));
                                    }
                                } while (date == null);
                                
                                if(c.containsKey(date)){
                                    for (Map.Entry<Integer,ArrayList<Room>> c1 : c.get(date).entrySet()){
                                        for (Room r : c1.getValue()){
                                            price -= r.getPrice();
                                            if(r instanceof Vip_room){
                                                sophongvip --;
                                            } else {
                                                sophongthuong--;
                                            }
                                            
                                        }
                                    }                                                                      
                                    c.remove(date);                                    
                                    System.out.println(green("Da xoa ngay " + date.format(form_time)+ " trong lich"));
                                    date = null;
                                    session = -1;
                                    luachontrongtrong = 1;
                                    show_booking_calendar(c);
                                } else {                                                                  
                                    System.out.println(red("╔"+RoomManager.border(70)+"╗"));
                                    System.out.println(red("║") +RoomManager.form_SO("Ngay " + date.format(form_time) + " khong co trong lich ", 70)+red("║"));
                                    System.out.println(red("╠"+RoomManager.border(70)+"╣"));
                                    System.out.println(red("║")+RoomManager.form_option("0. Nhap lai phong", 70)+red("║"));                        
                                    System.out.println(red("║")+RoomManager.form_option("1. Tro lai ", 70)+red("║"));
                                    System.out.println(red("╠"+RoomManager.border(70)+"╣"));
                                    System.out.println(red("║")+RoomManager.form_SO("Tong tien tam tinh : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+red("║"));                                    
                                    System.out.println(red("╚" + RoomManager.border(70) + "╝"));
                                    System.out.print(yeelow("Nhap lua chon : "));
                                    luachontrongtrong = sc.nextInt();
                                    sc.nextLine();                                    
                                }
                                } while (luachontrongtrong == 0);
                            } else if (luachontrong == 1){
                                // xoa buoi
                                int luachontrongtrong = -1;
                                do { 
                                    date = null;
                                System.out.print(yeelow("Nhap ngay muon xoa (dd/MM/yyyy) : "));
                                do { 
                                    try {
                                        String date_in = sc.nextLine();
                                        date = LocalDate.parse(date_in,form_time);
                                    } catch (Exception e) {
                                        System.out.println(red("Loi dinh dang, Nhap lai"));
                                    }
                                } while (date == null);
                                if(!c.containsKey(date)){                                                                      
                                    System.out.println(red("╔"+RoomManager.border(70)+"╗"));
                                    System.out.println(red("║") +RoomManager.form_SO("Ngay " + date.format(form_time) + " khong co trong lich ", 70)+red("║"));
                                    System.out.println(red("╠"+RoomManager.border(70)+"╣"));
                                    System.out.println(red("║")+RoomManager.form_option("0. Nhap lai phong", 70)+red("║"));                        
                                    System.out.println(red("║")+RoomManager.form_option("1. Tro lai ", 70)+red("║"));
                                    System.out.println(red("╠"+RoomManager.border(70)+"╣"));
                                    System.out.println(red("║")+RoomManager.form_SO("Tong tien tam tinh : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+red("║"));                                    
                                    System.out.println(red("╚" + RoomManager.border(70) + "╝"));
                                    System.out.print(yeelow("Nhap lua chon : "));
                                    luachontrongtrong = sc.nextInt();
                                    sc.nextLine();                                    
                                } else {
                                    luachontrongtrong = -1;
                                }
                                } while (luachontrongtrong == 0);
                                if (luachontrongtrong != 1 ){
                                    do { 
                                        System.out.print(yeelow("Nhap buoi || 0. sang     1.trua     2.chieu : "));
                                        session = sc.nextInt();                                        
                                        if(c.get(date).containsKey(session)){

                                            for (Room r : c.get(date).get(session)){
                                                price -= r.getPrice();
                                                if (r instanceof Vip_room){
                                                    sophongvip-- ;
                                                } else {
                                                    sophongthuong-- ;
                                                }
                                                
                                            }

                                            c.get(date).remove(session);
                                            if(c.get(date).isEmpty()){
                                                c.remove(date);
                                            }                                            
                                            String strr = session == 0 ? "sang" : session ==1 ? "trua" : "chieu";
                                            System.out.println(green("Da xoa lich trong buoi " + strr+ " ngay " +date.format(form_time)));                                            
                                            date =null;
                                            session = -1;
                                            luachontrongtrong = -1;
                                        } else {                                                                                   
                                            String strr = session == 0 ? "sang" : session ==1 ? "trua" : "chieu";
                                            System.out.println(red("╔"+RoomManager.border(70)+"╗"));
                                            System.out.println(red("║") +RoomManager.form_SO("Lich khong co trong " + " buoi " + strr + " ngay " + date.format(form_time) , 70)+red("║"));
                                            System.out.println(red("╠"+RoomManager.border(70)+"╣"));
                                            System.out.println(red("║")+RoomManager.form_option("0. Nhap lai buoi ", 70)+red("║"));                        
                                            System.out.println(red("║")+RoomManager.form_option("1. Tro lai ", 70)+red("║"));
                                            System.out.println(red("╠"+RoomManager.border(70)+"╣"));
                                            System.out.println(red("║")+RoomManager.form_SO("Tong tien tam tinh : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+red("║"));                                     
                                            System.out.println(red("╚" + RoomManager.border(70) + "╝"));
                                            System.out.print(yeelow("Nhap lua chon : "));
                                            luachontrongtrong = sc.nextInt();
                                            sc.nextLine();                                        
                                        }
                                    } while (luachontrongtrong == 0);
    
                                }                                                        
                            } else if(luachontrong == 2){
                                //  xoa phong

                                int luachontrongtrong = -1;
                                do { 
                                    date = null;
                                System.out.print(yeelow("Nhap ngay muon xoa (dd/MM/yyyy) : "));
                                do { 
                                    try {
                                        String date_in = sc.nextLine();
                                        date = LocalDate.parse(date_in,form_time);
                                    } catch (Exception e) {
                                        System.out.println(red("Loi dinh dang, Nhap lai"));
                                    }
                                } while (date == null);
                                if(!c.containsKey(date)){                                                                      
                                    System.out.println(red("╔"+RoomManager.border(70)+"╗"));
                                    System.out.println(red("║") +RoomManager.form_SO("Ngay " + date.format(form_time) + " khong co trong lich ", 70)+red("║"));
                                    System.out.println(red("╠"+RoomManager.border(70)+"╣"));
                                    System.out.println(red("║")+RoomManager.form_option("0. Nhap lai phong", 70)+red("║"));                        
                                    System.out.println(red("║")+RoomManager.form_option("1. Tro lai ", 70)+red("║"));
                                    System.out.println(red("╠"+RoomManager.border(70)+"╣"));
                                    System.out.println(red("║")+RoomManager.form_SO("Tong tien tam tinh : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+red("║"));                                    
                                    System.out.println(red("╚" + RoomManager.border(70) + "╝"));
                                    System.out.print(yeelow("Nhap lua chon : "));
                                    luachontrongtrong = sc.nextInt();
                                    sc.nextLine();                                    
                                } else {
                                    luachontrongtrong = -1;
                                }
                                } while (luachontrongtrong == 0);
                                if (luachontrongtrong != 1 ){
                                    do { 
                                        System.out.print(yeelow("Nhap buoi || 0. sang     1.trua     2.chieu : "));
                                        session = sc.nextInt();
                                        
                                        if(!c.get(date).containsKey(session)){
                                            c.get(date).put(session, new ArrayList<>());
                                            String strr = session == 0 ? "sang" : session ==1 ? "trua" : "chieu";                                                                                                                               
                                            luachontrongtrong = -1;                                                                             
                                            System.out.println(red("╔"+RoomManager.border(70)+"╗"));
                                            System.out.println(red("║") +RoomManager.form_SO("Lich khong co trong " + " buoi " + str + " ngay " + date.format(form_time) , 70)+red("║"));
                                            System.out.println(red("╠"+RoomManager.border(70)+"╣"));
                                            System.out.println(red("║")+RoomManager.form_option("0. Nhap lai buoi ", 70)+red("║"));                        
                                            System.out.println(red("║")+RoomManager.form_option("1. Tro lai ", 70)+red("║"));
                                            System.out.println(red("╠"+RoomManager.border(70)+"╣"));
                                            System.out.println(red("║")+RoomManager.form_SO("Tong tien tam tinh : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+red("║"));                                    
                                            System.out.println(red("╚" + RoomManager.border(70) + "╝"));

                                            System.out.print(yeelow("Nhap lua chon : "));
                                            luachontrongtrong = sc.nextInt();
                                            sc.nextLine();                                        
                                        }
                                    } while (luachontrongtrong == 0);
                                    boolean xdd = true;
                                    if (luachontrongtrong != 1){
                                        System.out.print(yeelow("Nhap ten phong : "));
                                        do { 
                                            String rname = sc.nextLine();
                                            if (RoomManager.check_room(rname)){
                                                room = RoomManager.get_room(rname);
                                                if (!c.get(date).get(session).contains(room)){
                                                    String strr = session == 0 ? "sang" : session == 1 ? "sang" : "chieu";                                                    
                                                    System.out.println(red("╔"+RoomManager.border(70)+"╗"));
                                                    System.out.println(red("║") +RoomManager.form_SO("Khong co phong " + room.getName() + " vao buoi " + strr + " ngay " + date.format(form_time) , 70)+red("║"));
                                                    System.out.println(red("╠"+RoomManager.border(70)+"╣"));
                                                    System.out.println(red("║")+RoomManager.form_option("0.nhap lai ten phong ", 70)+red("║"));                        
                                                    System.out.println(red("║")+RoomManager.form_option("1.quay lai", 70)+red("║"));
                                                    System.out.println(red("╠"+RoomManager.border(70)+"╣"));
                                                    System.out.println(red("║")+RoomManager.form_SO("Tong tien tam tinh : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+red("║"));                                   
                                                    System.out.println(red("╚" + RoomManager.border(70) + "╝"));

                                                    System.out.print(yeelow("Nhap lua chon : "));
                                                    luachontrongtrong = sc.nextInt();
                                                    sc.nextLine();
                                                } else {
                                                    c.get(date).get(session).remove(room);
                                                    if (c.get(date).get(session).isEmpty()){
                                                        price -= room.getPrice();
                                                        if (room instanceof Vip_room){
                                                            sophongvip-- ;
                                                        } else {
                                                            sophongthuong-- ;
                                                        }
                                                        

                                                        c.get(date).remove(session);
                                                        if(c.get(date).isEmpty()){
                                                            c.remove(date);
                                                        }
                                                    }
                                                    String strr = session == 0 ? "sang" : session == 1 ? "trua" : "chieu";
                                                    System.out.println(green("Da xoa phong " + room.getName() + " trong buoi " + strr));
                                                    luachontrongtrong = -1;
                                                    room = null;
                                                    date = null;
                                                    session = -1;
                                                }
                                            } else {
                                               System.out.println(red("Phong '" + rname + "' khong ton tai"));

                                               System.out.println(yeelow("╔"+RoomManager.border(70)+"╗"));
                                               System.out.println(yeelow("║") +RoomManager.form_SO("OPTION", 70)+yeelow("║"));
                                               System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                                               System.out.println(yeelow("║")+RoomManager.form_option("0. Nhap lai ten phong", 70)+yeelow("║"));
                                               System.out.println(yeelow("║")+RoomManager.form_option("1. Quay lai", 70)+yeelow("║"));
                                               System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                                               System.out.println(yeelow("║")+RoomManager.form_SO("Tong tien tam tinh : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+yeelow("║"));                                    
                                               System.out.println(yeelow("╚" + RoomManager.border(70) + "╝"));                                                   

                                               do { 
                                                System.out.print(yeelow("Nhap lua chon : "));
                                                luachontrongtrong = sc.nextInt();
                                                sc.nextLine();
                                                if (!(luachontrongtrong == 1 || luachontrongtrong==0)){
                                                    System.out.println(red("Nhap lai lua chon"));
                                                }
                                               } while (!(luachontrongtrong == 1 || luachontrongtrong==0));
                                                                                                                                                                                       
                                            }
                                           
                                        } while (luachontrongtrong == 0);
                                    }
                                }
                            }else if(luachontrong == 3){
                                
                                luachon = 0;
                                ktt= false;
                            }
                            
                            } while (ktt);                                                        
                        }
                        else if(luachon == 5){
                            System.out.println(yeelow("╔"+RoomManager.border(70)+"╗"));
                           System.out.println(yeelow("║") +RoomManager.form_SO("OPTION", 70)+yeelow("║"));
                           System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                           System.out.println(yeelow("║")+RoomManager.form_option("0. Xac nhan", 70)+yeelow("║"));
                           System.out.println(yeelow("║")+RoomManager.form_option("1. Tro lai ", 70)+yeelow("║"));
                           System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                           System.out.println(yeelow("║")+RoomManager.form_SO("Tong tien tam tinh : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+yeelow("║"));                                    
                           System.out.println(yeelow("╚" + RoomManager.border(70) + "╝"));
                            System.out.println(yeelow("0. Xac nhan "));
                            System.out.println(yeelow("1. Tro lai "));
                            do { 
                                System.out.print(yeelow("Nhap lua chon : "));
                                luachon = sc.nextInt();
                                sc.nextLine();
                                if(luachon < 0 || luachon > 1){
                                System.out.print(red("Nhap lai lua chon : "));
                                }
                                } while (!(luachon >=0 && luachon<=1));
                                if(luachon == 1){
                                    luachon = 0;
                                } else {
                                    
                                    
                                    do {
                                        // Hiển thị menu dịch vụ
                                        
                                        System.out.println(yeelow("╔"+RoomManager.border(70)+"╗"));
                                        System.out.println(yeelow("║") +RoomManager.form_SO(" Ban muon dich vu gi?", 70)+yeelow("║"));
                                        System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                                                                             
                                        int select = 0;
                                        for (Map.Entry<Integer,TreeMap<Service,Boolean>> bansao : service_list.entrySet()){
                                            for (Map.Entry<Service,Boolean> bansao1 : bansao.getValue().entrySet()){
                                                select ++;
                                                if (bansao1.getValue()){
                                                    System.out.println(yeelow("║")+RoomManager.form_option(bansao.getKey() + ". "+bansao1.getKey().getName() + "  " +form_tien.format(bansao1.getKey().getPricepersession()) + "  "+green("V"), 70)+yeelow("         ║"));
                                                }else {
                                                    System.out.println(yeelow("║")+RoomManager.form_option(bansao.getKey() + ". "+bansao1.getKey().getName() + "  " +form_tien.format(bansao1.getKey().getPricepersession()), 70)+yeelow("║"));
                                                }
                                                
                                            }
                                        }
                                        int bien1 = select++;
                                        System.out.println(yeelow("║") +RoomManager.form_option(bien1 + ". Hoan thanh", 70)+yeelow("║"));                                                                                                                                    
                                        System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                                        System.out.println(yeelow("║")+RoomManager.form_SO("Tong tien tam tinh : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+yeelow("║"));                                   
                                         System.out.println(yeelow("╚" + RoomManager.border(70) + "╝")); 
                                        int choice;
                                        do { 
                                            System.out.print(yeelow("Nhap lua chon : "));
                                         choice = Integer.parseInt(sc.nextLine());
                                        } while(!(choice >= 0 && choice <= bien1));                                                                                                                                                              
                                                 
                                                 if(choice == bien1){
                                                    this.show_booking_calendar(c);
                                                    this.show_service(service_list);
                                                    
                                                    
                                                    do { 
                                                        System.out.println( yeelow("╔"+RoomManager.border(70)+"╗") );
                                                        System.out.println(yeelow("║") +RoomManager.form_SO("Lua chon", 70)+yeelow("║"));
                                                        System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                                                        System.out.println(yeelow("║")+RoomManager.form_option("0. Dong y ", 70)+yeelow("║"));                        
                                                        System.out.println(yeelow("║")+RoomManager.form_option("1. Chinh sua", 70)+yeelow("║"));
                                                        System.out.println(yeelow("║")+RoomManager.form_option("2. Thoat", 70)+yeelow("║"));
                                                        System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                                                        System.out.println(yeelow("║")+RoomManager.form_SO("Tong tien tam tinh : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+yeelow("║"));                                     
                                                        System.out.println(yeelow("╚" + RoomManager.border(70) + "╝"));
                                                    
                                                     
                                                        do { 
                                                            System.out.print(yeelow("Nhap lua chon : "));
                                                            luachon = sc.nextInt();
                                                            sc.nextLine();
                                                            if(luachon < 0 || luachon > 2){
                                                            System.out.print(red("Nhap lai lua chon : "));
                                                            }
                                                        } while (!(luachon >=0 && luachon<=2));
    
                                                        if(luachon == 0 ){
                                                            luachon = -1;
                                                            continueChoosing = false;
    
                                                        }
                                                        else if (luachon == 1) {
                                                            System.out.println(yeelow("╔"+RoomManager.border(70)+"╗"));
                                                            System.out.println(yeelow("║") +RoomManager.form_SO("Lua chon", 70)+"║");
                                                            System.out.println("╠"+RoomManager.border(70)+"╣");
                                                            System.out.println(yeelow("║")+RoomManager.form_option("0. Chinh sua lich", 70)+yeelow("║"));                        
                                                            System.out.println(yeelow("║")+RoomManager.form_option("1. Chinh sua dich vu", 70)+yeelow("║"));      
                                                            System.out.println(yeelow("║")+RoomManager.form_option("2. Quay lai", 70)+yeelow("║"));                                                      
                                                            System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                                                            System.out.println(yeelow("║")+RoomManager.form_SO("Tong tien tam tinh : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+yeelow("║"));                                   
                                                            System.out.println(yeelow("╚" + RoomManager.border(70) + "╝"));
                                                            
                                                            do { System.out.print(yeelow("Nhap lua chon : "));
                                                                luachon = sc.nextInt();
                                                                sc.nextLine();
                                                                if(luachon < 0 || luachon > 2){
                                                                System.out.print(red("Nhap lai lua chon : "));
                                                                }
                                                                } while (!(luachon >=0 && luachon<=2));
                                                                if(luachon == 0){   
                                                                    continueChoosing = false;
                                                                }
                                                                if (luachon == 1){
                                                                    continueChoosing = true;
                                                                } else if (luachon == 2);
                                                        }

                                                        else if(luachon == 2){
                                                            System.out.println(yeelow("╔"+RoomManager.border(70)+"╗"));
                                                                    System.out.println(yeelow("║" )+RoomManager.form_SO("Ban co chac chan muon thoat", 70)+yeelow("║" ));
                                                                    System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                                                                    System.out.println(yeelow("║" )+RoomManager.form_option("0. Quay lai", 70)+yeelow("║" ));                        
                                                                    System.out.println(yeelow("║" )+RoomManager.form_option("1. Chac chan! ", 70)+yeelow("║" ));      
                                                                    System.out.println(yeelow("║" )+RoomManager.form_option("", 70)+yeelow("║" ));                                                      
                                                                    System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                                                                    System.out.println(yeelow("║")+RoomManager.form_SO("Tong tien tam tinh : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+yeelow("║"));                                    
                                                                    System.out.println(yeelow("╚" + RoomManager.border(70) + "╝"));
                                                                    do { 
                                                                        luachon = sc.nextInt();
                                                                        sc.nextLine();
                                                                        if(luachon < 0 || luachon > 2){
                                                                        System.out.print(red("Nhap lai lua chon : "));
                                                                        }
                                                                        } while (!(luachon >=0 && luachon<=1));
                                                                    
                                                                    if(luachon == 1){
                                                                        System.out.println(green("Lich da bi huy"));
                                                                        return false;
                                                                    } else {
                                                                        continueChoosing = true;
                                                                    }
                                                        }
                                                    } while (luachon == 2);
                                                }else {
                                                    for (Map.Entry<Service,Boolean> bansao : service_list.get(choice).entrySet()){
                                                        if (bansao.getValue()){
                                                            service_list.get(choice).put(bansao.getKey(), false);
                                                            price_dv -= bansao.getKey().getPricepersession();
                                                        } else {
                                                            service_list.get(choice).put(bansao.getKey(), true);
                                                            price_dv += bansao.getKey().getPricepersession();
                                                        }
                                                    }
                                                }   
                                                                                                                                                                                                                                                                          
                                    } while (continueChoosing);
                                }
                        }
                        if (luachon == 0){
                            show_booking_calendar(c);
                        }
                    } while (luachon == 0);
                } while (luachon == 3);
            } while (luachon == 2);
        } while (luachon == 1);        
       show_booking_calendar(c);
        show_service(service_list);
        System.out.println("╔" +RoomManager.border(70) +"╗" );
        System.out.println(yeelow("║")+RoomManager.form_SO("Tong tien : " + form_tien.format((price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5), 70)+yeelow("║"));
        System.out.println("╚" + RoomManager.border(70) + "╝");


        
        // for (Map.Entry<LocalDate, TreeMap<Integer, ArrayList<Room>>> l : c.entrySet()){
        //     for (Map.Entry<Integer,ArrayList<Room>> l1 : l.getValue().entrySet()){
        //         for (Room r : l1.getValue()){
        //             this.add_room(l.getKey(), l1.getKey(), room);
        //         }
        //     }
        // }
       
        for (Map.Entry<LocalDate,TreeMap<Integer,ArrayList<Room>>> lich : c.entrySet()){
           for (Map.Entry<Integer,ArrayList<Room>> lich11 : lich.getValue().entrySet()){
                for (Room r : lich11.getValue() ){
                    if (this.add_room(lich.getKey(), lich11.getKey(), room)){
                        System.out.println("them khogn thanh cong");
                    }
                }
           }
        }
        for (Map.Entry<Integer,TreeMap<Service,Boolean>> bansao :service_list.entrySet() ){
            for (Map.Entry<Service,Boolean> bansao1 : bansao.getValue().entrySet()){
                if (bansao1.getValue()){
                    this.selectedServices.add(bansao1.getKey());
                }
            }
        }
        this.price =(price)+(sophongthuong*price_dv) + (sophongvip*price_dv)*1.5;
        this.date = c;
    return true;
}

    public void show_booking_calendar(TreeMap<LocalDate, TreeMap<Integer, ArrayList<Room>>> d) {
        System.out.println(
                yeelow("╔" + RoomManager.border(20) + "╦" + RoomManager.border(30) + "╦" + RoomManager.border(30) + "╗"));
        System.out.println(yeelow("║") + RoomManager.form_SO("ngay", +20) + yeelow("║") + RoomManager.form_SO("buoi", 30) + yeelow("║")
                + RoomManager.form_SO("phong", 30) + yeelow("║"));
        System.out.println(
           yeelow( "║" + RoomManager.border(20) + "║" + RoomManager.border(30) + "║" + RoomManager.border(30) + "║"));
        LocalDate date_tam = null;
        int session_tam = -1;

        for (Map.Entry<LocalDate, TreeMap<Integer, ArrayList<Room>>> l : d.entrySet()) {
            Map.Entry<LocalDate, TreeMap<Integer, ArrayList<Room>>> last_day = d.lastEntry();

            for (Map.Entry<Integer, ArrayList<Room>> lo : l.getValue().entrySet()) {
                Map.Entry<Integer, ArrayList<Room>> last_session = l.getValue().lastEntry();

                for (Room loo : lo.getValue()) {
                    StringBuilder ghi = new StringBuilder();

                    if (date_tam == null) {
                        date_tam = l.getKey();
                        ghi.append(RoomManager.form_SO(l.getKey(), 20) + yeelow("║"));
                    } else if (date_tam.isEqual(l.getKey())) {
                        ghi.append(RoomManager.form_SO("", 20) + yeelow("║"));
                    } else {
                        ghi.append(RoomManager.form_SO(l.getKey(), 20) + yeelow("║"));
                        date_tam = l.getKey();
                    }

                    if (session_tam == -1) {
                        session_tam = lo.getKey();
                        String buoi = session_tam == 0 ? "sang" : session_tam == 1 ? "trua" : "toi";
                        ghi.append(RoomManager.form_SO(buoi, 30) + yeelow("║"));
                    } else if (lo.getKey() != session_tam) {
                        session_tam = lo.getKey();
                        String buoi = session_tam == 0 ? "sang" : session_tam == 1 ? "trua" : "toi";
                        ghi.append(RoomManager.form_SO(buoi, 30) + yeelow("║"));
                    } else {
                        String buoi = session_tam == 0 ? "sang" : session_tam == 1 ? "trua" : "toi";
                        ghi.append(RoomManager.form_SO("", 30) + yeelow("║"));
                    }

                    ghi.append(RoomManager.form_SO(loo.getName(), 30) + yeelow("║"));
                    System.out.println(yeelow("║") + ghi);

                }
                if (lo.getKey() != last_session.getKey()) {
                    System.out.println(yeelow("║") + RoomManager.form_SO(" ", 20) + yeelow("╠") + yeelow(RoomManager.border(30)) + yeelow("╬")
                            + yeelow(RoomManager.border(30)) + yeelow("╣"));
                }
            }

            if (l.getKey().isEqual(last_day.getKey())) {
                System.out.println(yeelow("╚" + RoomManager.border(20) +"╩" + RoomManager.border(30) +"╩"
                        + RoomManager.border(30) + "╝"));
            } else {
                System.out.println(yeelow("╠") + yeelow(RoomManager.border(20)) + yeelow("╬") + yeelow(RoomManager.border(30)) + yeelow("╬")
                        + yeelow(RoomManager.border(30)) + yeelow("╣"));
            }
        }
    }

    public void show_service(Map<Integer,TreeMap< Service,Boolean>> c){
        int i = 0;
        System.out.println(yeelow("╔"+RoomManager.border(70)+"╗"));
        System.out.println(yeelow("║" +RoomManager.form_SO("Service",70) + "║"));
        System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
        for (Map.Entry<Integer,TreeMap<Service,Boolean>> bansao : c.entrySet()){
            for (Map.Entry<Service,Boolean> bansao1 : bansao.getValue().entrySet()){            
                if (bansao1.getValue()){
                    i++;
                    System.out.println(yeelow("║")+RoomManager.form_option(bansao.getKey() + ". "+bansao1.getKey().getName() + "  " +form_tien.format(bansao1.getKey().getPricepersession()), 70)+yeelow("║"));
                }                
            }
        }
        if(i==0){
            System.out.println(yeelow("║")+RoomManager.form_option("KHONG CHON DICH VU NAO ", 70)+yeelow("║"));
        }
        System.out.println(yeelow("╚" + RoomManager.border(70) + "╝"));
        
    }


    public static void main(String[] args) {
       

        RoomManager.show_calendar(LocalDate.of(2004, 9, 1),LocalDate.of(2004, 9, 3), 0);
        

 
       


    }
}
