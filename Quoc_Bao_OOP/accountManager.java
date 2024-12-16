
import java.io.BufferedReader;
import java.io.FileReader;
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
   
}
