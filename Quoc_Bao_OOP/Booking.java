import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Booking extends IdManager {
    protected int id = 0;
    protected Customer cus;
    protected ArrayList<Service> selectedServices;
    protected int session;
    protected LocalDate date;
    protected Room room;

    public Booking(int id, LocalDate date, int session, Customer cus, ArrayList<Service> selectedServices) {
        this.id = id;
        this.cus = cus;
        this.selectedServices = selectedServices;
        this.date = date;
        this.session = session;
    }

    public Booking() {
        this.id = IdManager.getNextId();
        this.cus = new Customer();
        this.selectedServices = new ArrayList<>();
        this.price = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Service> getSelectedServices() {
        return selectedServices;
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

    public ArrayList<Service> getselectedServices() {
        return selectedServices;
    }

    public void setselectedServices(ArrayList<Service> selectedServices) {
        this.selectedServices = selectedServices;
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

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

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
        this.setDate(date); // -> lcoaldate

        System.out.println("nhap buoi : ");
        int session = sc.nextInt();
        this.setSession(session);

        // nhap khach hang
        this.setCus(cus);
        // nhap thoi gian
        this.setDate(date);
        this.setSession(session);
        // chon phong
        this.setRoom(room);

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
        Scanner scanner = new Scanner(System.in);
        System.out.println(" ╔═════════════════════════════════╗");
        System.out.println(" ║   Which service do you want?    ║");
        System.out.println(" ╠═════════════════════════════════╣");
        System.out.println(" ║ 0. Tea & Coffee                 ║");
        System.out.println(" ║ 1. Technical Support            ║");
        System.out.println(" ║ 2. Wifi                         ║");
        System.out.println(" ╚═════════════════════════════════╝");

        System.out.print("Enter your choice: ");
        ArrayList<Service> services = ServiceManager.availableServices;
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice < 0 || choice > 2) {
            System.out.println("Invalid choice!");
            return;
        }

        // Thêm dịch vụ đã chọn vào selectedServices
        boolean continueChoosing = true;

        do {
            // Thêm dịch vụ đã chọn vào selectedServices
            selectedServices.add(services.get(choice));

            // Hỏi người dùng xem có muốn chọn thêm dịch vụ không
            System.out.println("Service added: " + services.get(choice).getName());
            System.out.print("Do you want to add another service? (yes/no): ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("no")) {
                continueChoosing = false; // Nếu người dùng chọn không thì dừng lại
            } else {
                // Nếu muốn chọn dịch vụ khác, yêu cầu nhập lại
                System.out.println(" ╔═════════════════════════════════╗");
                System.out.println(" ║   Which service do you want?    ║");
                System.out.println(" ╠═════════════════════════════════╣");
                System.out.println(" ║ 0. Tea & Coffee                 ║");
                System.out.println(" ║ 1. Technical Support            ║");
                System.out.println(" ║ 2. Wifi                         ║");
                System.out.println(" ╚═════════════════════════════════╝");
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 0 || choice > 2) {
                    System.out.println("Invalid choice! Try again.");
                }
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}
