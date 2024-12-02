public class IdManager {
    protected static int nextId = 1;

    public static int getNextId() {
        return nextId++;
    }
}
