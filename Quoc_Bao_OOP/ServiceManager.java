
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
}
