import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaymentmethodManager {
    protected static ArrayList<PaymentMethod> methods = new ArrayList<>();
    protected static int ID = 0;

    static {
        try (BufferedReader br = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/paymentmethod.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split("#");
                int id = Integer.parseInt(str[0]);
                String methodName = str[1];
                Customer cus = CustomerManager.get_cus(str[2]);
                PaymentMethod pm = new PaymentMethod(methodName, cus);
                methods.add(pm);
                ID = Math.max(ID, id);
            }
        } catch (Exception e) {
            System.out.println("Error loading payment methods: " + e.getMessage());
        }
    }

    public static synchronized int generateID() {
        return ++ID;
    }

    public static void addPaymentMethod(PaymentMethod pm) {
        if (pm != null && !methods.contains(pm)) {
            methods.add(pm);
            writeToFile();
        }
    }

    public static void removePaymentMethod(int id) {
        for (int i = 0; i < methods.size(); i++) {
            if (methods.get(i).getId() == id) {
                methods.remove(i);
                System.out.println("Da xoa thanh cong!");
                writeToFile();
                return;
            }
        }
    }

    public static PaymentMethod getPaymentMethodById(int id) {
        return methods.stream().filter(pm -> pm.id == id).findFirst().orElse(null);
    }

    public static List<PaymentMethod> getPaymentMethodsByName(String methodName) {
        return methods.stream()
                .filter(pm -> pm.getMethodName().equalsIgnoreCase(methodName))
                .toList();
    }

    public static void showAllPaymentMethods() {
        if (methods.isEmpty()) {
            System.out.println("No payment methods found.");
            return;
        }
        methods.forEach(System.out::println);
    }

    public static boolean updatePaymentMethod(int id, String newMethodName) {
        PaymentMethod pm = getPaymentMethodById(id);
        if (pm != null) {
            pm.setCus(newMethodName);
            return true;
        }
        return false;
    }

    public static void writeToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./Quoc_Bao_OOP/data/paymentmethod.txt"))) {
            for (PaymentMethod pm : methods) {
                // Ghi thông tin phương thức thanh toán vào file
                bw.write(pm.getId() + "#" + pm.getMethodName() + "#" + pm.getCus().getPhoneNumber());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
