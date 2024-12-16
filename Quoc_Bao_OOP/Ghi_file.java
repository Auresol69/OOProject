import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Ghi_file {
    public static void main(String[] args) {

        StringBuilder dataa = new StringBuilder();

        StringBuilder str = new StringBuilder();
        str.append("asdkyg ausyd" + "*");
        String filePath = "./Quoc_Bao_OOP/data/output.txt"; // Đường dẫn tới file

        // Sử dụng BufferedWriter để ghi dữ liệu
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(str.toString()); // Ghi dữ liệu vào file

        } catch (IOException e) {
            System.err.println("Có lỗi xảy ra khi ghi vào file: " + e.getMessage());
        }
    }
}
