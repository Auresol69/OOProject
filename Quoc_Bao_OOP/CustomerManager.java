import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerManager {
    protected static ArrayList<Customer> customers;

    static {
        customers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/customer.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                
                String[] str = line.split("#");
                
                String name = str[0];
                String phonenumber = str[1];
                
                boolean sex = false;
                if (str[2] == "true") {
                    sex = true;
                }
               
                double diem = Double.parseDouble(str[3]);
                
                Customer cus = new Customer(name, phonenumber, sex,diem);
                customers.add(cus);
                

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
   
    }
    public static String yeelow(String x) {
        return "\033[33m" + x + "\033[0m";
    }

    public static String red(String x) {
        return "\033[31m" + x + "\033[0m";
    }

    public static String green(String x) {
        return "\033[32m" + x + "\033[0m";
    }

    

    public CustomerManager() {
        ArrayList<Customer> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/customer.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split("#");
                String name = str[0];
                String phonenumber = str[1];
                boolean sex = false;
                if (str[2] == "true") {
                    sex = true;
                }
                Customer cus = new Customer(name, phonenumber, sex,0);
                list.add(cus);

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        CustomerManager.customers = list;

    }

    public static void addCustomer(Customer customer) {
        CustomerManager.customers.add(customer);
    }

    public void removeCustomer(Customer cus) {
        customers.remove(cus);
    }

    public void editCustomer(Customer cus) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ban muon chinh sua cai gi?");
        System.out.println("1. Name");
        System.out.println("2. Phonenumber");
        System.out.println("3. Sex");
        int option = -1;
        while (true) {
            try {
                System.out.print("Nhap lua chon: ");
                option = Integer.parseInt(sc.nextLine());
                if (option >= 1 && option <= 3) {
                    break;
                }
                System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 3.");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
            }
        }

        switch (option) {
            case 1:
                System.out.print("Nhap ten: ");
                String name = sc.nextLine();
                cus.setName(name);
                break;
            case 2:
                System.out.print("Nhap so dien thoai: ");
                String phonenumber = sc.nextLine();
                if (CustomerManager.find(phonenumber)) {
                    System.out.println("So dien thoai nay da ton tai.");
                } else {
                    cus.setPhoneNumber(phonenumber);
                }
                break;
            case 3:
                System.out.print("Nhap gioi tinh: (Nam/Nu)");
                Boolean sex = sc.nextLine().equalsIgnoreCase("Nam");
                cus.setSex(sex);
                break;
            default:
                break;
        }
    }

    public void clearCustomer() {
        customers.clear();
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        CustomerManager.customers = customers;
    }

    public void xuatInfoTatCaCustomer() {
        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }
    }

    public static boolean find(String sdt) {
        for (Customer cus : CustomerManager.customers) {
            if (cus.getPhoneNumber().equals(sdt)) {
                return true;
            }
        }
        return false;
    }

    public static Customer get_cus(String sdt) {
        for (Customer cus : CustomerManager.customers) {
            if (cus.getPhoneNumber().equals(sdt)) {
                return cus;
            }
        }
        return null;
    }

    public boolean check(String sdt) {
        for (Customer cus : CustomerManager.customers) {
            if (cus.getPhoneNumber().equals(sdt.trim())) {
                return true;
            }
        }
        return false;
    }

    public static void luu_data() {
        StringBuilder str = new StringBuilder();

        for (Customer cus : CustomerManager.customers) {
            str.append(cus.getName() + "#");
            str.append(cus.getPhoneNumber() + "#");
            str.append(cus.getSex()+"#");
            str.append(cus.getDiemTichLuy());
            str.append("\n");
        }

        String filePath = "./Quoc_Bao_OOP/data/customer.txt"; // Đường dẫn tới file
        String data = str.toString();

        // Sử dụng BufferedWriter để ghi dữ liệu
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(data); // Ghi dữ liệu vào file
        } catch (IOException e) {
            System.err.println("Có lỗi xảy ra khi ghi vào file: " + e.getMessage());
        }
    }
    public static void cham(){
        
    }
    
    public static void show(){
        int i = 0;
        StringBuilder str= new StringBuilder();

        str.append("╠"+RoomManager.border( 20));
        str.append("╦"+RoomManager.border( 20));
        str.append("╦"+RoomManager.border( 20));
        str.append("╦"+RoomManager.border( 20)+"╣");
        System.out.println(str);
        str= new StringBuilder();
        str.append("║"+RoomManager.form_SO("Ten KH", 20));
        str.append("║"+RoomManager.form_SO("SDT", 20));
        str.append("║"+RoomManager.form_SO("DTL", 20));
        str.append("║"+RoomManager.form_SO("GIOI TINH", 20)+"║");
        System.out.println(str);
        str = new StringBuilder();
        str.append("╠"+RoomManager.border( 20));
        str.append("╩"+RoomManager.border( 20));
        str.append("╩"+RoomManager.border( 20));
        str.append("╩"+RoomManager.border( 20)+"╣");
        System.out.println(str);
        for (Customer cus : customers){
            if (i == 0){
                i++;
            } else {
                str= new StringBuilder();
                str.append("║"+RoomManager.border_thuong( 20));
                str.append("+"+RoomManager.border_thuong( 20));
                str.append("+"+RoomManager.border_thuong( 20));
                str.append("+"+RoomManager.border_thuong( 20)+"║");
                System.out.println(str);
            }
            str= new StringBuilder();
            str.append("║"+RoomManager.form_SO(cus.getName(), 20));
            str.append("|"+RoomManager.form_SO(cus.getPhoneNumber(), 20));
            str.append("|"+RoomManager.form_SO(cus.getDiemTichLuy(), 20));
            str.append("|"+RoomManager.form_SO(cus.getSex(), 20)+"║");
            System.out.println(str);
            
        }
        System.out.println("╚"+ RoomManager.border(83) + "╝");
    }

    public static void terminal(){
        show();
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        do { 
        System.out.println(yeelow("╔" + RoomManager.border(70) + "╗"));
        System.out.println(yeelow("╠" + RoomManager.border(70) + "╣"));
        System.out.println(yeelow("║") + RoomManager.form_SO("OPTION", 70) + yeelow("║"));
        System.out.println(yeelow("║") + RoomManager.form_option("0. Them khach hang", 70) + yeelow("║"));
        System.out.println(yeelow("║") + RoomManager.form_option("1. Sua thong tin khach hang ", 70) + yeelow("║"));
        System.out.println(yeelow("║") + RoomManager.form_option("2. Xoa khach hang", 70) + yeelow("║"));
        System.out.println(yeelow("║") + RoomManager.form_option("3. Quay lai ", 70) + yeelow("║"));
        System.out.println(yeelow("╚" + RoomManager.border(70) + "╝"));


        System.out.print("Nhap lua chon"); 

            do { 
                choice = sc.nextInt();sc.nextLine();
                if (!(choice >= 0 && choice <= 3)){
                    System.out.println(red("SAI"));
                }
            } while (!(choice >= 0 && choice <= 3));
           
            if (choice == 0){
                System.out.println("Nhap so dien thoai khach hang ");
                String sdt = sc.nextLine();
                
                 Customer cus = CustomerManager.get_cus(sdt);
                 if (cus == null){
                    System.out.println("Nhap ten khach hang");
                    String ten = sc.nextLine();
                    System.out.print("Nhap gioi tinh || 0. nam    1. nu   : ");
                    do { 
                        
                    } while (!(choice == 0 || choice == 1));

                    if (choice == 0){
                        cus = new Customer(sdt, ten, true, 0);
                    } else {
                        cus = new Customer(sdt, ten, false, 0);
                    }
                    customers.add(cus);
                    CustomerManager.addCustomer(cus);
                    CustomerManager.luu_data();
                    System.out.println(green("Them thanh cong khach hang"));
                   
                 } else {
                    System.out.println(red("So dien thoai da duoc su dung"));
                 }
            } else if ( choice == 1){

                System.out.println("Nhap so dien thoai khach hang ban muon sua thong tin ");
                String sdt = sc.nextLine();
                
                Customer cus = CustomerManager.get_cus(sdt);
                if (cus == null){
                    System.out.println(red("so dien thoai khong ton tai"));
                } else {
                    do { 
                       
                    
                    System.out.println(yeelow("╔" + RoomManager.border(70) + "╗"));
                    System.out.println(yeelow("╠" + RoomManager.border(70) + "╣"));
                    System.out.println(yeelow("║") + RoomManager.form_SO("OPTION", 70) + yeelow("║"));
                    System.out.println(yeelow("║") + RoomManager.form_option("0. Sua ten ", 70) + yeelow("║"));
                    System.out.println(yeelow("║") + RoomManager.form_option("2. Sua gioi tinh ", 70) + yeelow("║"));
                    System.out.println(yeelow("║") + RoomManager.form_option("3. Sua diem ", 70) + yeelow("║"));
                    System.out.println(yeelow("║") + RoomManager.form_option("4. Quay lai", 70) + yeelow("║"));
                    System.out.println(yeelow("╚" + RoomManager.border(70) + "╝"));
                    
                    System.out.print(yeelow("Nhap lua chon : "));

                    do { 
                        choice = sc.nextInt();sc.nextLine();
                        if (!(choice >= 0 && choice <= 3)){
                            System.out.println(red("SAI"));
                        }
                    } while (!(choice >= 0 && choice <= 4));

                    if (choice == 0){
                        System.out.print(yeelow("Nhap ten moi : "));
                        String ten  =sc.nextLine();
                        cus.setName(ten);
                        CustomerManager.luu_data();
                    } else if (choice == 1) {
                        System.out.println("Nhap so dien thoai moi ");
                        cus.setPhoneNumber(sdt);
                        CustomerManager.luu_data();
                    } else if (choice == 2 ){
                        boolean b = cus.getSex()? false :true;
                        cus.setSex(b);
                        System.out.println(green("Sua thanh cong gioi tinh "));
                    } else if ( choice == 3){
                        System.out.print("Nhap diem moi cho khach hang  :  ");
                        double diem = Double.parseDouble(sc.nextLine());
                        cus.setDiem(diem);
                        System.out.println(green("Sua diem thanh cong"));
                    }
                    } while (choice != 4);

                }
               

                
                
            } else if (choice == 2){
                System.out.print(yeelow("Nhap sdt khach hang ban muon xoa "));

                String sdt = sc.nextLine();
                Customer cus = CustomerManager.get_cus(sdt);
                if (cus==null){
                    System.out.println(red("SDT khong ton tai"));
                } else {
                    customers.remove(cus);
                    CustomerManager.luu_data();
                    System.out.println(green("Xoa thanh cong khach hang"));
                }

            }
            else if (choice == 3){
                return;
            }
        } while (true);

    }
    public static void main(String[] args) {
        show();
       
    }
}
