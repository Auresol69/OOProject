
import java.util.ArrayList;

public class clm {
    protected static ArrayList<Integer> so ;
    static {
        so = new ArrayList<>();
        so.add(10);
        so.add(20);
        so.add(30);
        so.add(40);
        so.add(50);
        so.add(60);
    }
    public static void main(String[] args) {
        System.out.println("hello word");
        System.out.println(clm.so);
        int i = 50;
        so.remove(1);
        System.out.println(so); 
       
        
        
    }
    
}