import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class AccountsUtil {
    private static final String userId = "admin";
    private static final int password = 51001;
    private final static double minimumBalance = 1000;
    static Data data = DataDB.getInstance();
    //static Data data = DataList.getInstance();
    private static int getPassword() {
        return password;
    }
    private static String getUserId() {
        return userId;
    }
    static void createAccount()  {
        boolean newCustomer = true;
        String name;
        do {
            System.out.print("Enter name : ");
            name = Main.sc.nextLine();
        } while (name.isEmpty() || name.isBlank());
        System.out.print("Enter email : ");
        String email;
        while (true) {
            email = Main.sc.nextLine();
            String regex = "^[a-z]+@[a-z]+.[a-z]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) break;
            else System.out.print("Enter valid email : ");
        }
        String address;
        do {
            System.out.print("Enter Address : ");
            address = Main.sc.nextLine();
        } while (address.isEmpty() || address.isBlank());
        Customer customer = data.findCustomerByEmail(email);
        if(customer!=null ) {
            if (customer.getName().equals(name) && customer.getAddress().equals(address)) {
                System.out.println("Already an account has been linked to the email continue to open an additional account");
                newCustomer = false;
            } else {
                System.out.println("Email is already linked to another person's account (Try again with another valid email)");
                return;
            }
        }
        BankAccount account;
        int customerId;
        if (newCustomer) {
            customerId = Main.random.nextInt(99999);
            Customer customer_1 = new Customer(customerId,name, email, address);
            data.addCustomer(customer_1);
        } else {
            customerId =customer.getCustomerId();
        }
        int accountNumber = Main.random.nextInt(999999);
        int pin = Main.random.nextInt(999);
        while (true) {
            System.out.print("1.Savings 2.Current \nChoose banking type : ");
            int choice = checkType();
            boolean isActive=true;
            switch (choice) {
                case 1: {
                    account = new Savings (accountNumber,pin,customerId,minimumBalance,minimumBalance,choice,isActive);
                    break;
                }
                case 2: {
                    account = new Current (accountNumber,pin,customerId,minimumBalance, minimumBalance,choice,isActive);
                    break;
                }
                default:
                    continue;
            }if(data.addAccount(account)) System.out.println("Account created successfully");
            break;
        }

        System.out.println("Your account number is " + accountNumber);
        System.out.println("Your pin is " + pin);
        System.out.println("Your minimum balance will be " + minimumBalance);
    }
    static void useAccount(BankAccount account)  {
        int input;
        while (true) {
            System.out.println("1 VIEW_BALANCE 2 WITHDRAW 3 DEPOSIT 4 TRANSFER 5 CHANGE_MINIMUM_BALANCE  6 CHANGE_PIN 7 CALCULATE_INTEREST 8 CLOSE_ACCOUNT 9 EXIT");
            input = checkType();
            Select ch3 = null;
            for (Select c3 : Select.values()) {
                if (c3.choice == input) {
                    ch3 = c3;
                    break;
                }
            }
            if (ch3 != null && account!=null) {
                account = data.findAccountByAccountNumber(account.getAccountNumber());
                switch (ch3) {
                    case VIEW_BALANCE:
                        System.out.println(account.getBalance());
                        continue;
                    case WITHDRAW:
                        System.out.print("Enter amount to be withdrew : ");
                        int withdrawAmount = checkType();
                        try {
                            if(account.withdraw(withdrawAmount))
                                System.out.println("Amount withdrew...");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        continue;
                    case DEPOSIT:
                        System.out.print("Enter amount to be deposited : ");
                        int depositAmount = checkType();
                        try{
                            if(account.deposit(depositAmount))
                                System.out.println("Amount deposited...");
                        }catch( InvalidDataException e){
                            System.out.println(e.getMessage());
                        }
                        continue;
                    case TRANSFER:
                        System.out.print("Enter account number of recipient : ");
                        int recipientAccountNumber = checkType();
                        System.out.print("Enter amount to be transferred : ");
                        double amount = checkType();
                        try {
                            if(account.transfer(recipientAccountNumber, amount))
                                System.out.println("Transaction Success...");
                            else System.out.println("Transaction failed");
                        }
                        catch(Exception e){
                            System.out.println(e.getMessage());
                        }
                        continue;
                    case CHANGE_MINIMUM_BALANCE:
                        System.out.print("Enter minimum balance : ");
                        int newMinBalance = checkType();
                        if (AccountsUtil.changeMinBalance(account, newMinBalance)) {
                            System.out.println("Minimum balance has been changed ");
                        } else System.out.println("Invalid minimum balance");
                        continue;
                    case CALCULATE_INTEREST:
                        System.out.print("Enter number of months : ");
                        int month = checkType();
                        System.out.println(account.calculateInterest(month));
                        continue;
                    case CHANGE_PIN:
                        if (AccountsUtil.changePin(account)) {
                            account = data.findAccountByAccountNumber(account.getAccountNumber());
                            System.out.println("Your new pin will be " +account.getPin() );
                        } else System.out.println("pin reset failed");
                        continue;
                    case CLOSE_ACCOUNT:
                        System.out.println("Enter your pin to confirm : ");
                        int pin1 = checkType();
                        if (pin1 == account.getPin()) {
                            double balance =account.getBalance();
                            if(AccountsUtil.closeAccount(account)) {
                                System.out.println("Your balance amount " + balance + " is returned ");
                                System.out.println("And your account is closed");
                                break;
                            }
                        }else System.out.println("wrong pin");
                        continue;
                    case EXIT:
                        break;
                }break;
            }
        }
    }
    static boolean changePin(BankAccount account){
        int pin = Main.random.nextInt(999);
        return data.updatePin(account, pin);
    }
    static boolean closeAccount(BankAccount account) {
        return (data.closeAccount(account));
    }
    static BankAccount login(int accountNumber, int pin)  {
        for (BankAccount account : data.getAccounts())
            if (account.getAccountNumber() == accountNumber && account.getPin() == pin) return account;
        return null;
    }
    static Boolean loginAdmin(String id, int password){
        return id.equals(getUserId()) && password == AccountsUtil.getPassword();
    }
    static void displayAccounts()  {
        System.out.println("List of accounts created ");
        for (BankAccount a : data.getAccounts()) {
            System.out.printf("accNo:%8d\tpin:%3d\tcustId:%6d\tBalance:%12f\tminBalance:%12f\ttype:%2d\tactivity:%5b%n",a.getAccountNumber(),a.getPin(),a.getCustomerId(),a.getBalance(),a.getMinimumBalance(),a.getType(),a.getActive());
        }
    }
    static boolean changeMinBalance(BankAccount account, int newMinBalance)  {
        if (newMinBalance >= 0) return data.updateMinBalance(account, newMinBalance);
        return false;
    }
    static int generateId()  {
        int transactionId = data.getRecords().size();
        return ++transactionId;}
    static void displayCustomer()  {
        System.out.println("List of customers ");
        for (Customer customer : data.getCustomerData()) {
            System.out.printf("custId:%7d\t\tname:%10s\t\temail:%15s\t\taddress:%15s%n",customer.getCustomerId(),customer.getName(),customer.getEmail(),customer.getAddress());
        }
    }
    static boolean revokeTransaction(Transaction transaction) throws ExistTransactionException,ClosedAccountException,InsufficentBalanceException,InvalidTransactionException{
        if(transaction==null) throw new InvalidTransactionException("Invalid transaction id...");
        double amount = transaction.getAmountTransferred();
        int accountNumber = transaction.getAccountNumber();
        int recipientAccountNumber = transaction.getRecipientAccountNumber();
        BankAccount account = data.findAccountByAccountNumber(accountNumber);
        BankAccount recipientAccount = data.findAccountByAccountNumber(recipientAccountNumber);
        if(transaction.isRevoked()) throw new ExistTransactionException("Transaction already revoked");
        if(!account.getActive() ) throw new ClosedAccountException("User account has been closed");
        if(!recipientAccount.getActive())throw new ClosedAccountException("Recipient account has been closed");
        if(recipientAccount.getBalance()<amount) throw new InsufficentBalanceException("Insufficient balance revoking failed");
        return data.transfer(recipientAccount, account, amount, transaction);
    }
    static void deleteCustomerRecord()  {
        List<Customer> details = data.getCustomerData();
        List<BankAccount> allAccounts = data.getAccounts();
        int totalCustomer = details.size();
        for (int i=0;i<totalCustomer;i++) {
            List<BankAccount> personalAccounts = new ArrayList<>();
            int customerId = details.get(i).getCustomerId();
            for (BankAccount account : allAccounts) {
                if (account.getCustomerId() == customerId) personalAccounts.add(account);
            }
            int size = 0;
            for (BankAccount account : personalAccounts) if (!account.getActive()) size += 1;
            if (size == personalAccounts.size()) data.deleteCustomer(details.get(i));
        }
    }
    static void manage() {
        while (true) {
            System.out.println("1 VIEW_ACCOUNTS 2 VIEW_CUSTOMER_DETAILS 3 VIEW_CUSTOMERS 4 VIEW_TRANSACTIONS 5 DELETE_INACTIVE_CUSTOMERS 6 REVOKE_TRANSACTION 7 EXIT");
            int input = checkType();
            Option ch2 = null;
            for (Option c2 : Option.values()) {
                if (c2.choice == input) {
                    ch2 = c2;
                    break;
                }
            }
            if (ch2 != null) {
                switch (ch2) {
                    case VIEW_ACCOUNTS:
                        displayAccounts();
                        if (data.getAccounts().isEmpty()) System.out.println(0);
                        continue;
                    case VIEW_CUSTOMER_DETAILS:
                        for(Customer c : data.getFullCustomerDetails()){
                            System.out.println(c);
                        }
                        if (data.getFullCustomerDetails().isEmpty()) System.out.println(0);
                        continue;
                    case VIEW_CUSTOMERS:
                        displayCustomer();
                        if(data.getCustomerData().isEmpty()) System.out.println(0);
                        continue;
                    case VIEW_TRANSACTIONS:
                        for (Transaction transaction : data.getRecords()) System.out.println(transaction);
                        if (data.getRecords().isEmpty()) System.out.println(0);
                        continue;
                    case DELETE_INACTIVE_CUSTOMERS:
                        deleteCustomerRecord();
                        System.out.println("Inactive customer records are deleted...");
                        continue;
                    case REVOKE_TRANSACTION:
                        try {System.out.print("Enter transaction_id : ");
                            int transactionId = checkType();
                            Transaction transaction = data.findTransactionById(transactionId);
                            if(revokeTransaction(transaction)) System.out.println("Transaction revoked successfully...");
                            else System.out.println("Transaction not revoked...");
                        }catch(Exception e){
                            System.out.println(e.getMessage());
                        }
                        continue;
                    case EXIT:
                        break;
                    default:
                        continue;
                }break;
            }
        }
    }
    static int checkType(){
        int num = 0;
        boolean flag = true;
        while (flag) {
            String str = Main.sc.nextLine();
            try {
                num = Integer.parseInt(str);
                flag = false;
            } catch (NumberFormatException e) {
                System.out.print("Enter an integer : ");
            }
        }
        return num;
    }
}



