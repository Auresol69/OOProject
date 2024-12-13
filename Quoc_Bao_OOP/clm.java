
import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

public class clm {

    public static void main(String[] args) {
        DecimalFormat form_tien = new DecimalFormat("#,###.00");
        TreeMap<Integer,TreeMap<Service,Boolean>> service_list = new TreeMap<>();  
        int session = 0;
        for (Service sv : ServiceManager.availableServices){
            TreeMap<Service,Boolean> sos = new TreeMap<>();
            sos.put(sv, true);
            service_list.put(session, sos);
            session++;            
        }
       
        for (Map.Entry<Integer,TreeMap<Service,Boolean>> bansao : service_list.entrySet()){
            for (Map.Entry<Service,Boolean> bansao1 : bansao.getValue().entrySet()){
                if (bansao1.getValue()){
                    System.out.println("║"+RoomManager.form_option(bansao.getKey() + ". "+bansao1.getKey().getName() + "  " +form_tien.format(bansao1.getKey().getPricepersession()) + "  "+"V", 70)+"║");
                }else {
                    System.out.println("║"+RoomManager.form_option(bansao.getKey() + ". "+bansao1.getKey().getName() + "  " +form_tien.format(bansao1.getKey().getPricepersession()), 70)+"║");
                }
                
            }
        }
        
    }
    
}