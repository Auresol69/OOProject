import java.io.BufferedReader;
import java.io.FileReader;

public class BookingIdManager {
    protected static int nextId = 0;

    public static int getNextId() {
        return nextId++;
    }
    
}
