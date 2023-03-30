import java.util.*;
public class Main {
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();
    public static  void main(String[] args) {
        System.out.println("BANKING SYSTEM ... ");
        while (true) {
            int input;
            System.out.println("1 CREATE_ACCOUNT 2 USE_ACCOUNT 3 ADMIN_ONLY 4 EXIT");
            input= AccountsUtil.checkType();
            Choice choice = null;
            for (Choice c1 : Choice.values()) {
                if (c1.choice == input) {
                    choice = c1;
                    break;
                }
            }
            if (choice != null) {
                switch (choice) {
                    case CREATE_ACCOUNT:
                        AccountsUtil.createAccount();
                        continue;
                    case USE_ACCOUNT:
                        System.out.print("Enter account number : ");
                        int accountNumber = AccountsUtil.checkType();
                        System.out.print("Enter pin :");
                        int pin = AccountsUtil.checkType();
                        BankAccount account = AccountsUtil.login(accountNumber, pin);
                        if (account == null || !account.getActive()) System.out.println("Invalid login");
                        else AccountsUtil.useAccount(account);
                        continue;
                    case ADMIN_ONLY:
                        System.out.print("Enter userID : ");
                        String id = sc.nextLine();
                        System.out.print("Enter password :");
                        int pwd = AccountsUtil.checkType();
                        Boolean access = AccountsUtil.loginAdmin(id, pwd);
                        if (access) AccountsUtil.manage();
                        else System.out.println("INVALID LOGIN...");
                        continue;
                    case EXIT:
                        break;
                }break;
            }
        }
    }
}

