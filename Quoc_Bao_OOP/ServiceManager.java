
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ServiceManager {
    protected static ArrayList<Service> availableServices;

    static {
        availableServices = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/Service.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split("#");
                String name = str[0];
                double price = Double.parseDouble(str[1]);
                Service sv = new Service(name, price);
                availableServices.add(sv);
            }
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi đọc tệp: " + e.getMessage());
        }
        // Khởi tạo danh sách dịch vụ sẵn có khi lớp ServiceManager được tải lên
        
        
       
        
    }
    public static String yeelow (String x){
        return "\033[33m" + x + "\033[0m";
    } 
    public static String red(String x){
        return "\033[31m" + x + "\033[0m";
    } 
    public static String green (String x){
        return "\033[32m" + x + "\033[0m";
    } 
    public static String tim (String x){
        return "\u001B[35m" + x + "\033[0m";
    } 

    public ArrayList<Service> getavailableServices() {
        return availableServices;
    }

    public void setavailableServices(ArrayList<Service> availableServices) {
        ServiceManager.availableServices = availableServices;
    }

    public void addService(Service sv) {
        availableServices.add(sv);
    }

    public void removeService(int index) {
        availableServices.remove(index);
    }

    public void clearService() {
        availableServices.clear();
    }

    public static Service getdichvu(String name){
        
        for (Service sv : availableServices){
            if (sv.getName().equals(name)){
                return sv;
            }
        }
        return new Service("#####", 00);
    }

    public static void show_list_service(){

        System.out.println(tim("╔"+RoomManager.border(70)+"╗"));
        System.out.println(tim("║") +RoomManager.form_SO("Service", 70)+tim("║"));
        System.out.println(tim("╠"+RoomManager.border(35)+"╦"+RoomManager.border(34)+"╣"));
        System.out.println(tim("║")+RoomManager.form_SO("Ten dich vu",35)+"║"+RoomManager.form_SO("Gia ",34)+tim("║"));
        System.out.println(tim("╠"+RoomManager.border(35)+"╩"+RoomManager.border(34)+"╣"));
       
    
        for (Service sv : ServiceManager.availableServices){
            System.out.println(tim("║")+RoomManager.form_option(sv.getName() + "   " + sv.getPricepersession(), 70)+tim("║"));
        }
        System.out.println(tim("╚" + RoomManager.border(70) + "╝")); 
    }

    public  static  Service get_service(String name){
        for (Service sv : ServiceManager.availableServices){
            if (sv.getName().equalsIgnoreCase(name)){
                return sv;
            }
        } 
        return null;
    }

    public static void termianl_service(){
        show_list_service();
        int choice ;
        Scanner sc= new Scanner(System.in);
        do { 
            

            System.out.println(yeelow("╔"+RoomManager.border(70)+"╗"));
                System.out.println(yeelow("║") +RoomManager.form_SO("OPTION", 70)+yeelow("║"));
                System.out.println(yeelow("╠"+RoomManager.border(70)+"╣"));
                System.out.println(yeelow("║")+RoomManager.form_option("0. Them dich vu moi  ", 70)+yeelow("║"));
                System.out.println(yeelow("║")+RoomManager.form_option("1. Sua dich vu", 70)+yeelow("║"));
                System.out.println(yeelow("║")+RoomManager.form_option("2. Xoa dich vu ", 70)+yeelow("║"));
                System.out.println(yeelow("║")+RoomManager.form_option("3. Quay lai", 70)+yeelow("║"));
                System.out.println(yeelow("╚" + RoomManager.border(70) + "╝")); 

            do { 
                System.out.println("Nhap lua chon : ");
                choice = Integer.parseInt(sc.nextLine());
                if (!(choice >= 0 && choice <= 3 )){
                    System.out.println("Nhap lai !!");
                }
            } while (!(choice >= 0 && choice <= 3 ));

            String sv_name;
            double gia;
            Service service = null;
            switch (choice) {
                case 0:
                    System.out.println("Nhap ten dich vu moi ");
                    sv_name = sc.nextLine();
                    System.out.println("Nhap gia cua dich vu ");
                     gia = Double.parseDouble(sc.nextLine());
                    Service sv = new Service(sv_name, gia);
                    ServiceManager.availableServices.add(sv);
                    ServiceManager.luu_data();
                    break;
                case 1:
                    System.out.println("Nhap ten dich vu ban muon chinh sua");
                    sv_name = sc.nextLine();
                     service = ServiceManager.get_service(sv_name);
                    if (service == null){
                        System.out.println("Dich vu '" + sv_name + "' khong ton tai");
                    } else {
                        System.out.println("0. Sua ten dich vu ");                    
                        System.out.println("1. Sua gia dich vu ");

                        do { 
                            choice = Integer.parseInt(sc.nextLine());
                            if (!(choice == 0 || choice == 1)){
                                System.out.println("Nhap lai ");
                            }
                        } while (!(choice == 0 || choice == 1));


                        if (choice == 0 ){                           
                            System.out.println("Nhap ten moi cho dich vu '" + sv_name +"' ");
                            sv_name = sc.nextLine();
                            service.setName(sv_name);
                        } else {
                            System.out.println("Nhap gia moi cho dich vu '" + sv_name+"' ");
                            gia = Double.parseDouble(sc.nextLine());
                        }
                    }
                    ServiceManager.luu_data();
                       
                    break;
                case 2:
                    service = null;
                    System.out.println("Nhap ten dich vu ban muon xao ");
                    sv_name = sc.nextLine();
                    service = ServiceManager.get_service(sv_name);
                    if (service == null){
                        System.out.println("Khong co dich vu '" + sv_name + "'  ");

                    } else {
                        ServiceManager.availableServices.remove(service);
                        System.out.println("Da xoa dich vu '" + sv_name+ "' ");
                    }

                    ServiceManager.luu_data();
                    
                    
                    break;
                case 3:
                return ;
                    
                    
                default:
                    throw new AssertionError();
            }
        } while (true);
    }

    public static void luu_data(){
        StringBuilder str = new StringBuilder();
        for (Service sv : ServiceManager.availableServices){
            str.append(sv.getName()+"#");
            str.append(sv.getPricepersession()+"\n");
        }
        String filePath = "./Quoc_Bao_OOP/data/Service.txt"; // Đường dẫn tới file
        String data = str.toString();

        // Sử dụng BufferedWriter để ghi dữ liệu
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(data); // Ghi dữ liệu vào file
        } catch (IOException e) {
            System.err.println("Có lỗi xảy ra khi ghi vào file: " + e.getMessage());
        }
    }
}
