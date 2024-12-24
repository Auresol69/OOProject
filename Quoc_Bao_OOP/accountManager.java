
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class accountManager {
    protected static ArrayList<account> accounts;
    static {
        accounts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/account.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split("#");
                String username = str[0];
                String pass = str[1];
                int maquyen = Integer.parseInt(str[2]);
                account acc = new account(username, pass, maquyen);
                accounts.add(acc);

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static account getaccount(String username, String pass) {
        for (account acc : accounts) {
            if (acc.getUsername().equals(username) && acc.getPassword().equals(pass)) {
                return acc;
            }
        }
        return null;
    }

    public static account signIn(String username, String pass) {
        for (account acc : accounts) {
            if (acc.getUsername().equals(username) && acc.getPassword().equals(pass)) {
                return acc;
            }
        }
        return null;
    }

    public static boolean delete_account(String username) {
        for (account ac : accounts) {
            if (ac.getUsername().equals(username)) {
                accounts.remove(ac);
                return true;
            }
        }
        return false;
    }

    public static account terminal_signIn() {

        Scanner sc = new Scanner(System.in);
        int choice = 0;
        System.out.println("username : ");
        String username = sc.nextLine();
        System.out.println("password");
        String password = sc.nextLine();

        return signIn(username, password);
    }
    public static String yeelow(String x) {
        return "\033[33m" + x + "\033[0m";
    }

    public static String red(String x) {
        return "\033[31m" + x + "\033[0m";
    }

    public static String green(String x) {
        return "\033[32m" + x + "\033[0m";
    }

    public static void main(String[] args) {
        System.out.println(accountManager.getaccount("long", "16"));
    }

    public static void terminal_account(account acc) {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        do {
           

            System.out.println(yeelow("╔" + RoomManager.border(70) + "╗"));
                System.out.println(yeelow("║") + RoomManager.form_SO("OPTION", 70) + yeelow("║"));
                System.out.println(yeelow("╠" + RoomManager.border(70) + "╣"));
                System.out.println(yeelow("║") + RoomManager.form_option("0. Quay lai", 70) + yeelow("║"));
                System.out.println(yeelow("║") + RoomManager.form_option("1. Them tai khoan", 70) + yeelow("║"));
                System.out.println(yeelow("║") + RoomManager.form_option("2. Doi password cua ban ", 70) + yeelow("║"));
                System.out.println(yeelow("║") + RoomManager.form_option("3. Xoa (Xoa tk con)", 70) + yeelow("║"));         
                System.out.println(yeelow("╚" + RoomManager.border(70) + "╝"));




            String pass;
            account account;
            do {
                choice = Integer.parseInt(sc.nextLine());
                if (!(choice >= 0 && choice <= 3)) {
                    System.out.println("Nhap lai :");
                }
            } while (!(choice >= 0 && choice <= 3));

        if (choice == 0){
            return ;
        }
        else if( choice == 1){
            System.out.print(yeelow("Nhap username : "));
            String username = sc.nextLine();
            System.out.print(yeelow("Nhap password : "));
             pass = sc.nextLine();
             System.out.println(yeelow("Nhap ma quyen cho tai khaon moi || 0. Admin     1.User    :  "));
             int i ;
            do { 
                 i  = sc.nextInt();sc.nextLine();
                 if(!(i==0 || i==1)){
                    System.out.println("Nhap lai");
                 }
            } while (!(i==0 || i==1));
             account = new account(username, pass, i);
            accountManager.accounts.add(account);
            accountManager.luu_data();
            System.out.println(green("Tao thanh cong tai khoan !! "));
        } else if (choice ==2) {
            System.out.print(yeelow("Nhap mat khau hien tai : "));
            pass = sc.nextLine();
            if (acc.getPassword().equals(pass)){
                System.out.println(red("Mat khau khong dung"));
            } else {
                System.out.print(yeelow("Nhap mat khau moi : "));
                acc.setPassword(pass);
                System.out.println(green("Thay doi thanh cong"));
                accountManager.luu_data();
            }
            
        } else if (choice == 3 ){
            System.out.print(yeelow("Nhap ten tai Khoan ban muon xoa "));
            String username = sc.nextLine();
            if (accountManager.delete_account(username)){
                System.out.println(green("Da xoa thanh cong"));
                accountManager.luu_data();
            } else {
                System.out.println(red("Tai khoan khong ton tai"));
            }
        }
        } while (true);
    }

    public static void luu_data() {
        StringBuilder str = new StringBuilder();
        for (account acc : accountManager.accounts) {
            str.append(acc.getUsername() + "#");
            str.append(acc.getPassword() + "#");
            str.append(acc.getQuyen() + "\n");
        }

        String filePath = "./Quoc_Bao_OOP/data/account.txt"; // Đường dẫn tới file
        String data = str.toString();

        // Sử dụng BufferedWriter để ghi dữ liệu
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(data); // Ghi dữ liệu vào file
        } catch (IOException e) {
            System.err.println("Có lỗi xảy ra khi ghi vào file: " + e.getMessage());
        }
    }
}