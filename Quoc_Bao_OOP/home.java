
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Home {
    public static void main(String[] args) {
        System.out.println("test");
        ArrayList<Room> list = new ArrayList<>();
        String file = "./Quoc_Bao_OOP/data/Roommanager.txt";
       
       try (BufferedReader br  = new BufferedReader(new FileReader(file))){
        String line ;
        while((line = br.readLine())!=null){
            
            String[] str = line.split("#");
            String name = str[0];
            int size = Integer.parseInt(str[1]) ;
            int status = Integer.parseInt(str[2]) ;
            double price = Double.parseDouble(str[3]) ;
            int tang = Integer.parseInt(str[4]) ;
            Room room;
            if (name.contains("Vip")){
                room = new Vip_room(name, size, status, price, tang);
            } else {
                room = new Standard_room(name, size, status, price, tang);
            }
            list.add(room);

        }
       } catch (Exception e) {
        // TODO: handle exception
       }
       for (Room room : list){
        System.out.println(room.toString());
       }

    }
}
