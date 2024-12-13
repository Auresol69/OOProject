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

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public void xuatInfoTatCaCustomer() {
        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }
    }
}
