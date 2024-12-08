import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Booking extends IdManager {
    protected int id = 0;
    protected Customer cus;
    protected ArrayList<Service> selectedServices;
    protected TreeMap<LocalDate,TreeMap<Integer,ArrayList<Room>>> date;
    protected double price;

    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }

    public Booking(int id,  Customer cus, ArrayList<Service> selectedServices ) {
        this.id = id;
        this.cus = cus;
        this.selectedServices = selectedServices;
        date = new TreeMap<>();
        price = 0;
        
    }

    public Booking() {
        this.id = IdManager.getNextId();
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
        int session = sc.nextInt();

        // nhap khach hang
        this.setCus(cus);
        // nhap thoi gian
      


        // chon dich vu

        this.cus.setInfo();

        // Tìm thông tin khách hàng trong danh sách
        Customer foundCustomer = findCustomerByPhone(cus.getPhoneNumber(), CustomerManager.getCustomers());
        if (foundCustomer != null) {
            cus = foundCustomer;
            // this.cus.CongDiemTichLuy();
            System.out.println("Customer found: " + foundCustomer.getName());
        } else {
            System.out.println("Customer not found. Using new customer info.");
            CustomerManager.addCustomer(cus);
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

    

    public boolean add_room(LocalDate date,int session,Room room) {
        String str = session == 0 ? "sang" : session == 1? "trua" :"chieu";
        if (room.check_calendar(date, session)){
            
            System.out.println("!!!!THEM KHONG THANH CONG !!!! : Phong " + room.getName() + " da co lich vao buoi " +str + " ngay " +date );
            return false ;
        }
        if(!this.date.containsKey(date)){
            this.date.put(date, new TreeMap<>());
        }

        if (!this.date.get(date).containsKey(session)){
            this.date.get(date).put(session,new ArrayList<>());
        }

        if (this.date.get(date).get(session).contains(room)){
            System.out.println("!!!!THEM KHONG THANH CONG !!!! : Ban da dat lich nay cho phong " + room.getName() + " vao buoi " +str + " ngay " +date );
            return false; 
        }
        room.add_booking(date, session, this);
        this.date.get(date).get(session).add(room);
        System.out.println("Them thanh cong lich vao buoi " +str + " ngay " +date );
        return true;
    }

    public boolean delete_session(LocalDate date , int session){
        String str = session == 0 ? "sang" : session == 1? "trua" :"chieu";
        if (!this.date.containsKey(date)){
            System.out.println("Ban khong dat ngay " + date.format(form_time) + " cho lich");
            return false ;
        }
        if (!this.date.get(date).containsKey(session)){
            System.out.println("Ban khong dat buoi "+ str +" ngay " + date.format(form_time) + " cho lich ");
            return false;
        }   
        
        for (Room r : this.date.get(date).get(session)){
            r.delete_booking(date, session);
        }
        
        this.date.get(date).put(session, new ArrayList<>());
        System.out.println("Lich nay vao buoi " + str + " ngay " + date.format(form_time) + " da duoc xoa tat ca");
        return true;
    }

    public boolean delete_room(LocalDate date, int session, Room room){
        String str = session == 0 ? "sang" : session == 1? "trua" :"chieu";
        room.delete_booking(date, session);
        this.date.get(date).put(session, new ArrayList<>());
        System.out.println("lich nay vao buoi " + str + " ngay " + date.format(form_time) + " da duoc xoa");
        return true;
    }


    public static void main(String[] args) {

        Service wf = new wifiService("wifi", 100) ;
        Service staff = new technicalSupportService("staff",15);
        ArrayList<Service> list_sv = new ArrayList<>();
        list_sv.add(wf);
        list_sv.add(staff);
        Customer cus = new Customer("long", "0123", true);
        Room room = new Vip_room("vip1", 1, 1, 10, 1);
        
       

        LocalDate date1 = LocalDate.of(2024, 1, 1);
        LocalDate date2 = LocalDate.of(2024, 1, 2);
        LocalDate date3 = LocalDate.of(2024, 1, 3);
        
        Booking b1 = new Booking();b1.setId(1);
        

        b1.add_room(date1, 0, room);
        b1.add_room(date1, 0, room);

        b1.setSelectedServices(list_sv);

        for (Map.Entry<LocalDate,Booking[]> c : room.getCalendar().entrySet()){
            System.out.println(c.getKey() + " : ");
            for (int i =0; i < 3 ; i++){
                if (c.getValue()[i]!= null){
                    for (Service sv :c.getValue()[i].getSelectedServices()){
                        System.out.println(sv.getName());
                    }
                }
            }
        }


        

        
    }
}
