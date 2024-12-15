import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CustomerManager {
    protected static ArrayList<Customer> customers;

    static {
        customers = new ArrayList<>();
        ArrayList<Customer> list = new ArrayList<>();
        try (BufferedReader br  = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/customer.txt"))){
            String line ;
            while((line = br.readLine())!=null){
            String[] str = line.split("#");
            String name = str[0];
            String phonenumber = str[1];
            boolean sex = false;
            if (str[2] == "true"){
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
        try (BufferedReader br  = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/customer.txt"))){
            String line ;
            while((line = br.readLine())!=null){
            String[] str = line.split("#");
            String name = str[0];
            String phonenumber = str[1];
            boolean sex = false;
            if (str[2] == "true"){
                sex = true;
            }
            Customer cus = new Customer(name, phonenumber, sex);                                   
            list.add(cus);
    
        }
       } catch (Exception e) {
        // TODO: handle exception
       }
       this.customers = list;

        
    }

    public static void addCustomer(Customer customer) {
        CustomerManager.customers.add(customer);
    }

    public void removeCustomer(int index) {
        customers.remove(index);
    }

    public void clearCustomer() {
        customers.clear();
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public void xuatInfoTatCaCustomer() {
        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }
    }
    public static boolean find(String sdt){
        for (Customer cus : CustomerManager.customers){
            if (cus.getPhoneNumber().equals(sdt)){
                return true;
            }
        }
        return false;
    }
    public static Customer get_cus(String sdt){
        for (Customer cus : CustomerManager.customers){
            if (cus.getPhoneNumber().equals(sdt)){
                return cus;
            }
        }
        return null;
    }    
    public boolean  check(String sdt ){
        for (Customer cus : this.customers){
            if (cus.getPhoneNumber().equals(sdt.trim())){
                return true;
            }
        }
        return false;
    }

}
