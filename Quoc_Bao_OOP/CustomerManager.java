import java.util.ArrayList;

public class CustomerManager {
    protected static ArrayList<Customer> customers;

    static {
        customers = new ArrayList<>();
    }

    public static void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public static void removeCustomer(int index) {
        customers.remove(index);
    }

    public static void clearCustomer() {
        customers.clear();
    }

    public static ArrayList<Customer> getCustomers() {
        return customers;
    }

    public static void setCustomers(ArrayList<Customer> customers) {
        CustomerManager.customers = customers;
    }
    
}
