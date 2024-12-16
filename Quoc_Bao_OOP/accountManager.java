
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
       accounts  = new ArrayList<>();
       
        try (BufferedReader br  = new BufferedReader(new FileReader("./Quoc_Bao_OOP/data/account.txt"))){
            String line ;
            while((line = br.readLine())!=null){
            String[] str = line.split("#");
            String username = str[0];
            String pass = str[1];
            int maquyen = Integer.parseInt(str[2]);
            account acc = new account(username, pass, 0);
            accounts.add(acc);
        }
       } catch (Exception e) {
        // TODO: handle exception
       }
    }
    public static account getaccount(String username, String pass ){
        for (account acc : accounts){
            if (acc.getUsername().equals(username) && acc.getPassword().equals(pass)){
                return acc;
            }
        }
        return null;
    }

    public static account signIn (String username , String pass){
        for (account acc : accounts){
            if (acc.getUsername().equals(username) && acc.getPassword().equals(pass)){
                return acc;
            }
        }
        return null;
    }

    public static boolean  delete_account(String username){
        for (account ac : accounts){
            if (ac.getUsername().equals(ac)){
                accounts.remove(ac);
                return true;
            }
        }
        return false;
    }


    public static account terminal_signIn(){
        
       Scanner sc = new Scanner(System.in);
       int choice = 0;
            System.out.println("username : ");
           String username = sc.nextLine();
           System.out.println("password");
           String password = sc.nextLine();
          
            
          return signIn(username, password);
    }
    public static void main(String[] args) {
        System.out.println(accountManager.getaccount("long","16"));
    }

    public static void terminal_account(account acc){
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        do { 
            System.out.println("0. Quay lai ");
        System.out.println("1. Them tai khoan ");
        System.out.println("2. Doi password cua ban ");
        System.out.println("3. Xoa (Xoa cac tk con)");
        String pass;
        account account;
        do { 
            choice = Integer.parseInt(sc.nextLine());
            if(!(choice >= 0 && choice <= 3)){
                System.out.println("Nhap lai :");
            }
        } while (!(choice >= 0 && choice <= 3));

        if (choice == 0){
            return ;
        }
        else if( choice == 1){
            System.out.println("Nhap username");
            String username = sc.nextLine();
            System.out.println("Nhap password ");
             pass = sc.nextLine();
             account = new account(username, pass, choice);
            accountManager.accounts.add(acc);
            accountManager.luu_data();
        } else if (choice ==2) {
            System.out.println("Nhap mat khau hien tai ");
            pass = sc.nextLine();
            if (acc.getPassword().equals(pass)){
                System.out.println("Mat khau khong dung");
            } else {
                System.out.println("Nhap mat khau moi");
                acc.setPassword(pass);
                System.out.println("Thay doi thanh cong");
                accountManager.luu_data();
            }
            
        } else if (choice == 3 ){
            System.out.println("Nhap ten tai Khoan ban muon xoa ");
            String username = sc.nextLine();
            if (accountManager.delete_account(username)){
                System.out.println("Da xoa thanh cong");
                accountManager.luu_data();
            } else {
                System.out.println("Tai khoan khong ton tai");
            }
        }
        } while (true);
    }
   public static void luu_data(){
    StringBuilder str = new StringBuilder();
    for (account acc : accountManager.accounts){
        str.append(acc.getUsername()+"#");
        str.append(acc.getPassword()+"#");
        str.append(acc.getQuyen()+"\n");
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
