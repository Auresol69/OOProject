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
                Customer cus = new Customer(name, phonenumber, sex);
                list.add(cus);

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        customers = list;
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
                Customer cus = new Customer(name, phonenumber, sex);
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
            str.append(cus.getSex());
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
}
