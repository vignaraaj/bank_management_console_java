import java.util.List;
public interface Data {
    boolean addAccount(BankAccount b) ;
    boolean addCustomer(Customer c) ;
    List<BankAccount> getAccounts() ;
    List<Customer> getCustomerData() ;
    List<Transaction> getRecords() ;
    boolean addRecord(Transaction record) ;
    boolean deleteCustomer(Customer customer) ;
    Customer findCustomerByEmail(String email) ;
    BankAccount findAccountByAccountNumber(int recipientAccountNumber);
    Transaction findTransactionById(int id) ;
    boolean updateBalance(BankAccount account , double amount) ;
    boolean updatePin(BankAccount account,int pin) ;
    boolean updateMinBalance(BankAccount account,double minBalance) ;
    boolean closeAccount(BankAccount account) ;
    boolean revokeTransaction(Transaction transaction) ;
    boolean  transfer(BankAccount account, BankAccount recipientAccount, double amount, Transaction transaction) ;
    List<Customer> getFullCustomerDetails() ;
}
