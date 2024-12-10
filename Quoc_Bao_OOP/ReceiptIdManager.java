public class ReceiptIdManager {
    protected static int nextId = 0;

    public static int getNextId() {
        return nextId++;
    }
}
