
import java.util.ArrayList;

public class vd {
    public static void main(String[] args) {
        Customer cus1 = new Customer("long1", "0923", false);
        Customer cus2 = new Customer("long2", "0924", false);
        Customer cus3 = new Customer("long3", "0925", false);
        Customer cus4 = new Customer("long4", "0926", false);

        ArrayList<Customer> list = new ArrayList<>();
        list.add(cus1);
        list.add(cus2);
        list.add(cus3);
        list.add(cus4);

        for (Customer cus : list){
            System.out.println(cus.getName());
        }

        cus1.setName("bao");
        System.out.println("sau thay doi ");

        for (Customer cus : list){
            System.out.println(cus.getName());
        }
    }
}
