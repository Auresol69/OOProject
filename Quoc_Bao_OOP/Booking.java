
import java.lang.classfile.instruction.ThrowInstruction;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.random.RandomGenerator;

public class Booking extends BookingIdManager {
    protected Integer id;
    protected Customer cus;
    protected ArrayList<Service> selectedServices;
    protected TreeMap<LocalDate, TreeMap<Integer, ArrayList<Room>>> date;
    protected double price;
    protected Integer receiptid;

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

    public Booking(int id,  Customer cus, ArrayList<Service> selectedServices ) {
        this.id = id;
        this.cus = cus;
        this.selectedServices = selectedServices;
        date = new TreeMap<>();
        price = 0;

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

    public void setInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap ngay : ");
        String date = sc.nextLine();

        System.out.println("nhap buoi : ");
        int session = Integer.parseInt(sc.nextLine());

        // nhap khach hang
        // nhap thoi gian

        // chon dich vu

        // Tìm thông tin khách hàng trong danh sách
        while (true) {
            this.cus.setInfo();
            Customer foundCustomer = findCustomerByPhone(cus.getPhoneNumber(), CustomerManager.getCustomers());
            if (foundCustomer != null) {
                cus = foundCustomer;
                System.out.println("Customer found: " + foundCustomer.getName());
                break;
            } else {
                System.out.println("Customer not found.");
                System.out.println("1. Nhap lai");
                System.out.println("0. Them moi khach hang");
                System.out.print("Nhap lua chon cua ban: ");

                int option;
                option = Integer.parseInt(sc.nextLine());
                if (option == 0) {
                    System.out.println("Dang them khach hang moi...");
                    CustomerManager.addCustomer(cus); // Them khach hang moi
                    break; // Thoat vong lap sau khi them khach hang moi
                } else if (option == 1) {
                    System.out.println("Vui long nhap lai thong tin khach hang.");
                    // Tiep tuc vong lap de nhap lai thong tin khach hang
                } else {
                    System.out.println("Lua chon khong hop le. Vui long chon lai.");
                    // Dua ra thong bao va tiep tuc vong lap neu lua chon khong hop le
                }
            }
        }

        // Danh sách dịch vụ
        ArrayList<Service> services = ServiceManager.availableServices;

        boolean continueChoosing = true;
        do {
            // Hiển thị menu dịch vụ
            System.out.println(" ╔═════════════════════════════════╗");
            System.out.println(" ║   What do you want to do?       ║");
            System.out.println(" ╠═════════════════════════════════╣");
            System.out.println(" ║ 0. Add Tea & Coffee             ║");
            System.out.println(" ║ 1. Add Technical Support        ║");
            System.out.println(" ║ 2. Add Wifi                     ║");
            System.out.println(" ║ 3. Remove a service             ║");
            System.out.println(" ║ 4. Exit                         ║");
            System.out.println(" ╚═════════════════════════════════╝");

            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 0:
                case 1:
                case 2:
                    // Thêm dịch vụ vào danh sách
                    selectedServices.add(services.get(choice));
                    System.out.println("Service added: " + services.get(choice).getName());
                    double tmp = 0;
                    for (Service sv : selectedServices) {
                        // tmp += sv.getPricepersession() * getSession();
                    }
                    System.out.println("Total provisional service fee: " + tmp + "$");
                    break;

                case 3:
                    // Hiển thị danh sách dịch vụ đã chọn
                    if (selectedServices.isEmpty()) {
                        System.out.println("No services to remove.");
                        break;
                    }

                    System.out.println("Selected services:");
                    for (int i = 0; i < selectedServices.size(); i++) {
                        System.out.println(i + ". " + selectedServices.get(i).getName());
                    }

                    System.out.print("Enter the index of the service to remove: ");
                    int removeIndex = Integer.parseInt(sc.nextLine());
                    if (removeIndex >= 0 && removeIndex < selectedServices.size()) {
                        Service removedService = selectedServices.remove(removeIndex);
                        System.out.println("Service removed: " + removedService.getName());
                    } else {
                        System.out.println("Invalid index. No service removed.");
                    }
                    break;

                case 4:
                    // Thoát vòng lặp
                    continueChoosing = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        } while (continueChoosing);
        BookingManager.addBooking(this);
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

    public LocalDate getfirtdate(){
        return this.date.isEmpty()? null : this.date.lastKey();
    }
   
    
    public static void set_calendar(RoomManager rmng){
        TreeMap<LocalDate,TreeMap<Integer,ArrayList<Room>>> c = new TreeMap<>();
        String luachon = null; 
        DateTimeFormatter form_time = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Scanner sc = new Scanner(System.in);
        LocalDate date = null;

        // date = nhapngay();
    
        // if(c.containsKey(date)){
        //     c.put(date, new TreeMap<>());
        // }

        // int session  = -1;
        // session = nhap_buoi();

        // if(c.get(date).containsKey(session)){
        //     c.get(date).put(session,new ArrayList<>());
        // }
        Room room = null;
        String roomname = null;
        boolean kt = true;
        date = LocalDate.now();
        int session = 1;
        c.put(date, new TreeMap<>());
        c.get(date).put(session, new ArrayList<>());
       do { 
           
           System.out.println("nhap ten phong ");
           roomname = sc.nextLine();
           if (!RoomManager.check_room(roomname)){
               System.out.println("phong '" + roomname + "' khong ton tai");
           } else {
            room = rmng.get_room(roomname);
            if(room.check_calendar(date,session)){
                System.out.println("bi trung lich chon phong khac");
            } else {
                if (c.get(date).get(session).contains(room)){
                    System.out.println("Phong " + roomname + " da co trong lich, nhap lai ");
                } else {
                    c.get(date).get(session).add(room);
                    System.out.println("Da them phong vao lich");
                }
            }
           }
        

        } while (kt);



       
     
        
        
        
    }

    public LocalDate nhapngay(){
        DateTimeFormatter form_time = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Scanner sc = new Scanner(System.in);
        LocalDate date = null;
        do { 
            System.out.print("Nhap ngay (dd/MM/yyyy) : ");
            String date_b = sc.nextLine();
            try {
                date = LocalDate.parse(date_b, form_time);
            } catch (Exception e) {
                System.out.println("Loi dinh dang, Vui long nhap ngay theo form dd/MM/yyyy");
            }
        } while (date == null);
        sc.close();
        return date;
    }
    public int nhap_buoi(){
        int i = -1;
        Scanner sc = new Scanner(System.in);
        do {  
            System.out.print("0:Sang     1:trua     2:chieu :     ");
            i = sc.nextInt();
            String str = (i <0 || i >2) ? "Loi" :"";
            System.out.println(str);
        } while (i<0 || i >2);
        sc.close();
        return i;
    }

    

    public static void main(String[] args) {
        RoomManager rmng = new RoomManager();
        set_calendar(rmng);
    //     Room vip = new Vip_room("longlonglong", 0, nextId, 500, nextId);
    //    rmng.add_room(vip);
    //    rmng.show_calendar(LocalDate.now(), LocalDate.now(), 0);
    //    Customer cus = new Customer("ksdliahd", "123", false);
    //    Booking b = new Booking(10, cus, null);
    //    b.add_room(LocalDate.now(), 0, vip);
    //    rmng.show_calendar(LocalDate.now(), LocalDate.now(), 0);
       

    }
}
