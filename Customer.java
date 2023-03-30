import java.util.ArrayList;
import java.util.List;
final class Customer {
    private  final int customerId;
    private  final String name;
    private final String email;
    private  final String address;
    private  List<BankAccount> customerAccounts = new ArrayList<>();
    public Customer(int customerId, String name, String email, String address, BankAccount account) {
        this.customerId=customerId;
        this.name = name;
        this.email = email;
        this.address = address;
        addCustomerAccounts(account);
    }
    public Customer(int customerId, String name, String email, String address) {
        this.customerId=customerId;
        this.name = name;
        this.email = email;
        this.address = address;
    }
    public int getCustomerId() {
        return customerId;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    public List<BankAccount> getCustomerAccounts() {
        return customerAccounts;
    }
    public void addCustomerAccounts(BankAccount account) {
        customerAccounts.add(account);
    }
    public void setCustomerAccounts(List<BankAccount> customerAccounts) {
        this.customerAccounts = customerAccounts;
    }
    public String toString() {
        return String.format("custId:%7d\t\tname:%10s\t\temail:%15s\t\taddress:%15s\t\tAccounts:%100s",getCustomerId(),getName(),getEmail(),getAddress(),getCustomerAccounts());
    }

}
