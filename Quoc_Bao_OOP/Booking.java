
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

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

    public Booking(int id, Customer cus, ArrayList<Service> selectedServices) {
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
            // this.cus.setInfo();
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

    public LocalDate getfirtdate() {
        return this.date.isEmpty() ? null : this.date.lastKey();
    }

    public void set_calendar(RoomManager rmng) {
        TreeMap<LocalDate, TreeMap<Integer, ArrayList<Room>>> c = new TreeMap<>();
        int luachon = 0;
        DateTimeFormatter form_time = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Scanner sc = new Scanner(System.in);
        LocalDate date = null;
        Room room = null;
        int session = -1;
        do {
            do {
                System.out.print("Nhap ngay (dd/MM/yyyy) : ");
                String date_b = sc.nextLine();
                try {
                    date = LocalDate.parse(date_b, form_time);
                } catch (Exception e) {
                    System.out.println("Loi dinh dang, Nhap lai !!");
                }
            } while (date == null);
            System.out.println("nhap lua chon");
            if (!c.containsKey(date)) {
                c.put(date, new TreeMap<>());
            }
            boolean kt = true;
            do {
                do {
                    System.out.println("Chon buoi ");
                    System.out.print("0. sang       1.trua      2.chieu     : ");
                    session = sc.nextInt();
                    sc.nextLine();
                    if (session >= 0 && session <= 2) {
                        kt = false;
                    } else {
                        System.out.println("SAi");
                    }
                } while (kt);
                if (!c.get(date).containsKey(session)) {
                    c.get(date).put(session, new ArrayList<>());
                }
                kt = true;
                do {
                    do {
                        System.out.print("nhap phong : ");
                        String roomname = sc.nextLine();
                        if (!rmng.check_room(roomname)) {
                            System.out.println("phong '" + roomname + "' khong ton tai, Nhap lai !!");
                        } else {
                            room = rmng.get_room(roomname);
                            kt = false;
                        }
                    } while (kt);
                    kt = true;

                    String str = session == 0 ? "sang" : session == 1 ? "trua" : "toi";
                    if (room.check_calendar(date, session)) {
                        System.out.println("Phong nay da co lich!!");
                    } else {
                        if (c.get(date).get(session).contains(room)) {
                            System.out.println("phong da them vao truoc!!");
                        } else {
                            c.get(date).get(session).add(room);
                            System.out.println("them thanh cong ngay " + date.format(form_time) + " buoi " + str
                                    + " phong " + room.getName());
                            room = null;
                        }
                    }
                    do {
                        System.out.println("╔" + RoomManager.border(70) + "╗");
                        System.out.println("║" + RoomManager.form_SO("OPTION", 70) + "║");
                        System.out.println("╠" + RoomManager.border(70) + "╣");
                        System.out.println("║" + RoomManager.form_option("0. show lich", 70) + "║");
                        System.out.println("║" + RoomManager.form_option("1. Dat them ngay ", 70) + "║");
                        if (date != null) {
                            System.out.println("║" + RoomManager
                                    .form_option("2. Dat them buoi trong ngay " + date.format(form_time), 70) + "║");
                            if (session != -1) {
                                System.out.println("║" + RoomManager.form_option(
                                        "3. Dat them phong cho buoi " + str + " ngay " + date.format(form_time), 70)
                                        + "║");
                            }
                        }
                        System.out.println("║" + RoomManager.form_option("4. Xoa ", 70) + "║");
                        System.out.println("║" + RoomManager.form_option("5. Xac nhan ", 70) + "║");
                        System.out.println("╚" + RoomManager.border(70) + "╝");
                        boolean xd = true;
                        do {
                            luachon = sc.nextInt();
                            sc.nextLine();
                            if (date == null) {
                                if (!(luachon == 0 || luachon == 1 || luachon == 4 || luachon == 5)) {
                                    System.out.println("Nhap lai lua chon");
                                    xd = true;
                                } else {
                                    xd = false;
                                }
                            } else {
                                if (luachon < 0 || luachon > 5) {
                                    System.out.print("Nhap lai lua chon : ");
                                    xd = true;
                                } else {
                                    xd = false;
                                }
                            }
                        } while (xd);
                        System.out.println(luachon + " asdhg aisuy bfgasiudygf ");

                        if (luachon == 4) {
                            int luachontrong = -1;
                            boolean ktt = true;
                            do {
                                System.out.println("╔" + RoomManager.border(70) + "╗");
                                System.out.println("║" + RoomManager.form_SO("OPTION", 70) + "║");
                                System.out.println("╠" + RoomManager.border(70) + "╣");
                                System.out.println("║" + RoomManager.form_option("0.Xoa ngay", 70) + "║");
                                System.out.println("║" + RoomManager.form_option("1.Xoa buoi", 70) + "║");
                                System.out.println("║" + RoomManager.form_option("2.Xoa phong ", 70) + "║");
                                System.out.println("║" + RoomManager.form_option("3.Tro lai ", 70) + "║");
                                System.out.println("╚" + RoomManager.border(70) + "╝");
                                do {
                                    luachontrong = sc.nextInt();
                                    sc.nextLine();
                                    if (luachontrong < 0 || luachontrong > 3) {
                                        System.out.print("Nhap lai lua chon : ");
                                    }
                                } while (!(luachontrong >= 0 && luachontrong <= 5));
                                if (luachontrong == 0) {
                                    // xóa ngày
                                    int luachontrongtrong = -1;
                                    do {
                                        date = null;
                                        System.out.print("Nhap ngay muon xoa (dd/MM/yyyy) : ");
                                        do {
                                            try {
                                                String date_in = sc.nextLine();
                                                date = LocalDate.parse(date_in, form_time);
                                            } catch (Exception e) {
                                                System.out.println("Loi dinh dang, Nhap lai");
                                            }
                                        } while (date == null);
                                        if (c.containsKey(date)) {
                                            c.put(date, new TreeMap<>());
                                            System.out.println("Da xoa ngay " + date.format(form_time) + " trong lich");
                                            date = null;
                                            session = -1;
                                            luachontrongtrong = 1;
                                            show_booking_calendar(c);
                                        } else {
                                            System.out.println(
                                                    "Ngay " + date.format(form_time) + " khong co trong lich ");
                                            System.out.println("0. Nhap lai phong");
                                            System.out.println("1. Tro lai");
                                            luachontrongtrong = sc.nextInt();
                                            sc.nextLine();
                                        }
                                    } while (luachontrongtrong == 0);
                                } else if (luachontrong == 1) {
                                    // xoa buoi
                                    int luachontrongtrong = -1;
                                    do {
                                        date = null;
                                        System.out.print("Nhap ngay muon xoa (dd/MM/yyyy) : ");
                                        do {
                                            try {
                                                String date_in = sc.nextLine();
                                                date = LocalDate.parse(date_in, form_time);
                                            } catch (Exception e) {
                                                System.out.println("Loi dinh dang, Nhap lai");
                                            }
                                        } while (date == null);
                                        if (!c.containsKey(date)) {
                                            System.out.println(
                                                    "Ngay " + date.format(form_time) + " khong co trong lich ");
                                            System.out.println("0. Nhap lai phong");
                                            System.out.println("1. Tro lai");
                                            luachontrongtrong = sc.nextInt();
                                            sc.nextLine();
                                        } else {
                                            luachontrongtrong = -1;
                                        }
                                    } while (luachontrongtrong == 0);
                                    if (luachontrongtrong != 1) {
                                        do {
                                            System.out.print("Nhap buoi || 0. sang     1.trua     2.chieu : ");
                                            session = sc.nextInt();
                                            if (c.get(date).containsKey(session)) {
                                                c.get(date).put(session, new ArrayList<>());
                                                String strr = session == 0 ? "sang" : session == 1 ? "trua" : "chieu";
                                                System.out.println("Da xoa lich trong buoi " + str + " ngay "
                                                        + date.format(form_time));
                                                date = null;
                                                session = -1;
                                                luachontrongtrong = -1;
                                            } else {
                                                String strr = session == 0 ? "sang" : session == 1 ? "trua" : "chieu";
                                                System.out.println("Lich khong co trong " + " buoi " + str + " ngay "
                                                        + date.format(form_time));
                                                System.out.println("0. Nhap lai buoi ");
                                                System.out.println("1. Tro lai");
                                                luachontrongtrong = sc.nextInt();
                                                sc.nextLine();
                                            }
                                        } while (luachontrongtrong == 0);

                                    }
                                } else if (luachontrong == 2) {
                                    // xoa phong

                                    int luachontrongtrong = -1;
                                    do {
                                        date = null;
                                        System.out.print("Nhap ngay muon xoa (dd/MM/yyyy) : ");
                                        do {
                                            try {
                                                String date_in = sc.nextLine();
                                                date = LocalDate.parse(date_in, form_time);
                                            } catch (Exception e) {
                                                System.out.println("Loi dinh dang, Nhap lai");
                                            }
                                        } while (date == null);
                                        if (!c.containsKey(date)) {
                                            System.out.println(
                                                    "Ngay " + date.format(form_time) + " khong co trong lich ");
                                            System.out.println("0. Nhap lai phong");
                                            System.out.println("1. Tro lai");
                                            luachontrongtrong = sc.nextInt();
                                            sc.nextLine();
                                        } else {
                                            luachontrongtrong = -1;
                                        }
                                    } while (luachontrongtrong == 0);
                                    if (luachontrongtrong != 1) {
                                        do {
                                            System.out.print("Nhap buoi || 0. sang     1.trua     2.chieu : ");
                                            session = sc.nextInt();

                                            if (!c.get(date).containsKey(session)) {
                                                c.get(date).put(session, new ArrayList<>());
                                                String strr = session == 0 ? "sang" : session == 1 ? "trua" : "chieu";
                                                luachontrongtrong = -1;
                                                System.out.println("Lich khong co trong " + " buoi " + str + " ngay "
                                                        + date.format(form_time));
                                                System.out.println("0. Nhap lai buoi ");
                                                System.out.println("1. Tro lai");
                                                luachontrongtrong = sc.nextInt();
                                                sc.nextLine();
                                            }
                                        } while (luachontrongtrong == 0);
                                        boolean xdd = true;
                                        if (luachontrongtrong != 1) {
                                            System.out.print("Nhap ten phong : ");
                                            do {
                                                String rname = sc.nextLine();
                                                if (rmng.check_room(rname)) {
                                                    room = rmng.get_room(rname);
                                                    if (!c.get(date).get(session).contains(room)) {
                                                        String strr = session == 0 ? "sang"
                                                                : session == 1 ? "sang" : "chieu";
                                                        System.out.println(
                                                                "Khong co phong " + room.getName() + " vao buoi " + strr
                                                                        + " ngay " + date.format(form_time));
                                                        System.out.println("0.nhap lai ten phong");
                                                        System.out.println("1.quay lai");
                                                        luachontrongtrong = sc.nextInt();
                                                        sc.nextLine();
                                                    } else {
                                                        c.get(date).get(session).remove(room);
                                                        String strr = session == 0 ? "sang"
                                                                : session == 1 ? "trua" : "chieu";
                                                        System.out.println("Da xoa phong " + room.getName()
                                                                + " trong buoi " + strr);
                                                        luachontrongtrong = -1;
                                                        room = null;
                                                        date = null;
                                                        session = -1;
                                                    }
                                                } else {
                                                    System.out.println("Phong '" + rname + "' khong ton tai");
                                                    System.out.println("0. Nhap lai ten phong");
                                                    System.out.println("1. Quay lai");
                                                    luachontrongtrong = sc.nextInt();
                                                    sc.nextLine();
                                                }

                                            } while (luachontrongtrong == 0);
                                        }
                                    }
                                } else if (luachontrong == 3) {

                                    luachon = 0;
                                    ktt = false;
                                }

                            } while (ktt);

                            System.out.println("da thoat");
                        } else if (luachon == 5) {
                            System.out.println("0. Xac nhan ");
                            System.out.println("1. Tro lai ");
                            do {
                                luachon = sc.nextInt();
                                sc.nextLine();
                                if (luachon < 0 || luachon > 1) {
                                    System.out.print("Nhap lai lua chon : ");
                                }
                            } while (!(luachon >= 0 && luachon <= 1));
                            if (luachon == 1) {
                                luachon = 0;
                            } else {
                                luachon = -1;
                            }
                        }
                        if (luachon == 0) {
                            show_booking_calendar(c);
                        }
                    } while (luachon == 0);
                } while (luachon == 3);
            } while (luachon == 2);
        } while (luachon == 1);
        show_booking_calendar(c);
    }

    public void show_booking_calendar(TreeMap<LocalDate, TreeMap<Integer, ArrayList<Room>>> d) {
        System.out.println(
                "╔" + RoomManager.border(20) + "╦" + RoomManager.border(30) + "╦" + RoomManager.border(30) + "╗");
        System.out.println("║" + RoomManager.form_SO("ngay", +20) + "║" + RoomManager.form_SO("buoi", 30) + "║"
                + RoomManager.form_SO("phong", 30) + "║");
        System.out.println(
                "╠" + RoomManager.border(20) + "╬" + RoomManager.border(30) + "╬" + RoomManager.border(30) + "╣");
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
                        ghi.append(RoomManager.form_SO(l.getKey(), 20) + "║");
                    } else if (date_tam.isEqual(l.getKey())) {
                        ghi.append(RoomManager.form_SO("", 20) + "║");
                    } else {
                        ghi.append(RoomManager.form_SO(l.getKey(), 20) + "║");
                        date_tam = l.getKey();
                    }

                    if (session_tam == -1) {
                        session_tam = lo.getKey();
                        String buoi = session_tam == 0 ? "sang" : session_tam == 1 ? "trua" : "toi";
                        ghi.append(RoomManager.form_SO(buoi, 30) + "║");
                    } else if (lo.getKey() != session_tam) {
                        session_tam = lo.getKey();
                        String buoi = session_tam == 0 ? "sang" : session_tam == 1 ? "trua" : "toi";
                        ghi.append(RoomManager.form_SO(buoi, 30) + "║");
                    } else {
                        String buoi = session_tam == 0 ? "sang" : session_tam == 1 ? "trua" : "toi";
                        ghi.append(RoomManager.form_SO("", 30) + "║");
                    }

                    ghi.append(RoomManager.form_SO(loo.getName(), 30) + "║");
                    System.out.println("║" + ghi);

                }
                if (lo.getKey() != last_session.getKey()) {
                    System.out.println("║" + RoomManager.form_SO(" ", 20) + "╠" + RoomManager.border(30) + "╬"
                            + RoomManager.border(30) + "╣");
                }
            }

            if (l.getKey().isEqual(last_day.getKey())) {
                System.out.println("╚" + RoomManager.border(20) + "╩" + RoomManager.border(30) + "╩"
                        + RoomManager.border(30) + "╝");
            } else {
                System.out.println("╠" + RoomManager.border(20) + "╬" + RoomManager.border(30) + "╬"
                        + RoomManager.border(30) + "╣");
            }
        }
    }

    public static void main(String[] args) {
        RoomManager rmng = new RoomManager();
        Customer cus = new Customer("long", "0923", false);
        Booking book = new Booking(nextId, cus, null);

        LocalDate a = LocalDate.of(2024, 12, 12);
        LocalDate b = LocalDate.of(2024, 12, 11);
        LocalDate c = LocalDate.of(2024, 12, 10);

        TreeMap<LocalDate, TreeMap<Integer, ArrayList<String>>> d = new TreeMap<>();

        book.set_calendar(rmng);

    }
}
