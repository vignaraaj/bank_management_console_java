import java.util.ArrayList;
import java.util.List;
public class DataList implements Data {
    private  static Data data = DataList.getInstance();
    private  static final List<BankAccount> accounts = new ArrayList<>();
    private static final List<Customer> customerData = new ArrayList<>();
    private static final List<Transaction> transactionRecord = new ArrayList<>();
    private DataList(){
    }
    public static Data getInstance(){
       if(data ==null) data =   new DataList();
      return  data;
    }
    public boolean addAccount(BankAccount b) {
        accounts.add(b);
        return true;
    }
    public boolean addCustomer(Customer c) {
        customerData.add(c);
        return true;
    }
    public  List<BankAccount> getAccounts() {
        return accounts;
    }
    public List<Customer> getCustomerData() {
        return customerData;
    }
    public List<Transaction> getRecords() {
        return transactionRecord;
    }
    public boolean addRecord(Transaction record) {
        transactionRecord.add(record);
        return true;
    }
    public  boolean deleteCustomer(Customer customer) {
        customerData.remove(customer);
        return true;
    }
    public Customer findCustomerByEmail(String email){
        for (Customer c : data.getCustomerData()) {
            if (c.getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }
    public BankAccount findAccountByAccountNumber(int recipientAccountNumber)  {
        for (BankAccount account : data.getAccounts()) {
            if (account.getAccountNumber() == recipientAccountNumber) {
                return account;
            }
        }
        return null;
    }
    public Transaction findTransactionById(int id)  {
        for (Transaction transaction:data.getRecords()){
            if(transaction.getTransactionId()==id){
                return transaction;
            }
        }return null;
    }
    public boolean updateBalance(BankAccount account, double amount) {
        account.setBalance(account.getBalance()+amount);
        return true;
    }
    public boolean updatePin(BankAccount account, int pin) {
        account.setPin(pin);
        return true;
    }
    public boolean updateMinBalance(BankAccount account, double minBalance) {
        account.setMinimumBalance(minBalance);
        return true;
    }
    public boolean closeAccount(BankAccount account) {
        account.setBalance(0);
        account.setMinimumBalance(0);
        account.setActive(false);
        return true;
    }
    public boolean revokeTransaction(Transaction transaction) {
        transaction.setRevoked(true);
        return true;
    }
    public boolean transfer(BankAccount bankAccount, BankAccount recipientAccount, double amount, Transaction transaction) {
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            recipientAccount.setBalance(recipientAccount.getBalance() + amount);
            if(transaction==null) {
                transaction = new Transaction(AccountsUtil.generateId(), bankAccount.getAccountNumber(), recipientAccount.getAccountNumber(), amount, false);
                data.addRecord(transaction);
            }
            else revokeTransaction(transaction);
            return true;
    }
    public List<Customer> getFullCustomerDetails() {
        List<Customer> temp = new ArrayList<>();
        for(Customer c:getCustomerData()){
            List<BankAccount> account = new ArrayList<>();
            for(BankAccount acc : getAccounts()){
                if(c.getCustomerId()==acc.getCustomerId()){
                    account.add(acc);
                }
            }c.setCustomerAccounts(account);
            temp.add(c);
        }
        return temp;
    }

}
