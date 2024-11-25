
import java.util.ArrayList;

public class ServiceManager {
    protected static ArrayList<Service> availableServices;

    static {
        // Khởi tạo danh sách dịch vụ sẵn có khi lớp ServiceManager được tải lên
        availableServices = new ArrayList<>();
        availableServices.add(new coffeenteaService("CoffeenTea", 9.99));
        availableServices.add(new technicalSupportService("SoundnProjector", 20.6));
        availableServices.add(new wifiService("Wifi 5G", 4.99));
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

}
