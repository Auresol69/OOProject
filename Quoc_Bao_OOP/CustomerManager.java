import java.util.ArrayList;

public class CustomerManager {
    protected ArrayList<Customer> customers;

    public CustomerManager() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
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
}
