
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ServiceManager {
    protected  ArrayList<Service> availableServices;

    public ServiceManager() {
        ArrayList<Service> list = new ArrayList<>();
        availableServices = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/Service.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split("#");
                String name = str[0];
                double price = Double.parseDouble(str[1]);
                Service sv = new Service(name, price);
                list.add(sv);
            }
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi đọc tệp: " + e.getMessage());
        }
        // Khởi tạo danh sách dịch vụ sẵn có khi lớp ServiceManager được tải lên
        
        this.availableServices = list;
       
        
    }
    public Service getdichvu(String name ){
        for (Service sv : this.getavailableServices()){
            if (sv.getName().equals(name)){
                return sv;
            }
        }
        return null;
    }

    public ArrayList<Service> getavailableServices() {
        return availableServices;
    }

    public void setavailableServices(ArrayList<Service> availableServices) {
        this.availableServices = availableServices;
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

   

    
}
