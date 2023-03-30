import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DataDB  implements Data {
    private static Data data = DataDB.getInstance();
    private static final String url = "jdbc:sqlite:/Users/vigna-pt6743/Documents/databases/Banking.db";
    private static final Connection conn;
    static {
        try {
            conn = DriverManager.getConnection(url);
            //System.out.println("Connection established...");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private DataDB() {
    }
    public static Data getInstance() {
        if (data == null) data = new DataDB();
        return data;
    }
    public boolean addAccount(BankAccount b) {
        try{
            int accountNumber = b.getAccountNumber();
            int pin = b.getPin();
            int id = b.getCustomerId();
            double balance = b.getBalance();
            double minimumBalance = b.getMinimumBalance();
            int type = b.getType();
            boolean isActive = b.getActive();
            String insertSQL = "insert into accounts(accountNumber,pin,id,balance,minimumBalance,type,isActive) values(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertSQL);
            preparedStatement.setInt(1, accountNumber);
            preparedStatement.setInt(2, pin);
            preparedStatement.setInt(3, id);
            preparedStatement.setDouble(4, balance);
            preparedStatement.setDouble(5, minimumBalance);
            preparedStatement.setInt(6, type);
            preparedStatement.setBoolean(7, isActive);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }
        catch(SQLException e){
            return false;
        }

    }
    public boolean addCustomer(Customer c)  {
        try {
            int id = c.getCustomerId();
            String name = c.getName();
            String email = c.getEmail();
            String address = c.getAddress();
            String insertSQL = "insert into customer(id,name,email,address) values(?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertSQL);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, address);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch(SQLException e){
            return false;
        }
    }
    public List<BankAccount> getAccounts()  {
        try{
        List<BankAccount> temp = new ArrayList<>();
        String selectSQL = "select * from accounts";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(selectSQL);
        while (rs.next()) {
            BankAccount account;
            int acc = rs.getInt("accountNumber");
            int pin = rs.getInt("pin");
            int id = rs.getInt("id");
            double balance = rs.getDouble("balance");
            double minBalance = rs.getDouble("minimumBalance");
            int type = rs.getInt("type");
            boolean isActive = rs.getBoolean("isActive");
            if (type == 1) account = new Savings(acc, pin, id, balance, minBalance, type, isActive);
            else account = new Current(acc, pin, id, balance, minBalance, type, isActive);
            temp.add(account);
        }
        statement.close();
        return temp;
        }catch(SQLException e){return null;}
    }
    public List<Customer> getCustomerData() {
        try {
            List<Customer> temp = new ArrayList<>();
            String selectSQL = "select * from customer";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(selectSQL);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String address = rs.getString("address");
                Customer c = new Customer(id, name, email, address);
                temp.add(c);
            }
            statement.close();
            return temp;
        }catch(SQLException e){return null;}
    }
    public List<Transaction> getRecords() {
        try{
        List<Transaction> temp = new ArrayList<>();
        String selectSQL = "select * from transactionRecord";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(selectSQL);
        while (rs.next()) {
            int id = rs.getInt("transactionId");
            int accNumber = rs.getInt("accountNumber");
            int recipientNumber = rs.getInt("recipientAccountNumber");
            double amount = rs.getDouble("amountTransferred");
            boolean isRevoked = rs.getBoolean("isRevoked");
            Transaction trans = new Transaction(id, accNumber, recipientNumber, amount, isRevoked);
            temp.add(trans);
        }
        statement.close();
        return temp;
        }catch(SQLException e){return null;}
    }
    public boolean addRecord(Transaction record) {
        try {
            String insertSQL = "insert into transactionRecord values(?,?,?,?,?)";
            int transactionId = record.getTransactionId();
            int accountNumber = record.getAccountNumber();
            int recipientAccountNumber = record.getRecipientAccountNumber();
            double amount = record.getAmountTransferred();
            boolean isRevoked = record.isRevoked();
            PreparedStatement preparedStatement = conn.prepareStatement(insertSQL);
            preparedStatement.setInt(1, transactionId);
            preparedStatement.setInt(2, accountNumber);
            preparedStatement.setInt(3, recipientAccountNumber);
            preparedStatement.setDouble(4, amount);
            preparedStatement.setBoolean(5, isRevoked);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch(SQLException e){
            return false;
        }
    }
    public Customer findCustomerByEmail(String email)  {
        try {
            String sql = "select * from customer where email=? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email1 = resultSet.getString("email");
            String address = resultSet.getString("address");
            return new Customer(id, name, email1, address);
        }catch(SQLException e){
            return null;
        }
    }
    public BankAccount findAccountByAccountNumber(int recipientAccountNumber)  {
        try {
            String sql = "select * from accounts where accountNumber=? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, recipientAccountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            BankAccount account;
            int accNumber = resultSet.getInt("accountNumber");
            int pin = resultSet.getInt("pin");
            int id = resultSet.getInt("id");
            double balance = resultSet.getDouble("balance");
            double minBalance = resultSet.getDouble("minimumBalance");
            int type = resultSet.getInt("type");
            boolean isActive = resultSet.getBoolean("isActive");
            if (type == 1) account = new Savings(accNumber, pin, id, balance, minBalance, type, isActive);
            else account = new Current(accNumber, pin, id, balance, minBalance, type, isActive);
            return account;
        }catch(SQLException e){
            return null;
        }
    }
    public Transaction findTransactionById(int id)  {
        try{
            String sql = "select * from transactionRecord where transactionId=? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            int transactionId = resultSet.getInt("transactionId");
            int accNumber = resultSet.getInt("accountNumber");
            int recipientNumber = resultSet.getInt("recipientAccountNumber");
            double amount = resultSet.getDouble("amountTransferred");
            boolean isRevoked = resultSet.getBoolean("isRevoked");
            return new Transaction(transactionId, accNumber, recipientNumber, amount, isRevoked);
        }catch(SQLException e){
            return null;
        }
    }
    public boolean updateBalance(BankAccount account,double amount)  {
        try {
            String updateSQL = "UPDATE accounts SET balance=balance+? WHERE accountNumber=?";
            PreparedStatement preparedStatement = conn.prepareStatement(updateSQL);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, account.getAccountNumber());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch(SQLException e){
            return false;
        }
    }
    public boolean updateMinBalance(BankAccount account,double minBalance)  {
        try {
            String updateSQL = "UPDATE accounts SET minimumBalance=? WHERE accountNumber=?";
            PreparedStatement preparedStatement = conn.prepareStatement(updateSQL);
            preparedStatement.setDouble(1, minBalance);
            preparedStatement.setInt(2, account.getAccountNumber());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch(SQLException e){
            return false;
        }
    }
    public boolean updatePin(BankAccount account,int pin)  {
        try {
            String updateSQL = "UPDATE accounts SET pin=? WHERE accountNumber=?";
            PreparedStatement preparedStatement = conn.prepareStatement(updateSQL);
            preparedStatement.setInt(1, pin);
            preparedStatement.setInt(2, account.getAccountNumber());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch(SQLException e){
            return false;
        }
    }
    public boolean closeAccount(BankAccount account)  {
        try {
            String updateSQL = "UPDATE accounts SET minimumBalance=?,balance=?,isActive=? WHERE accountNumber=?";
            int accNumber = account.getAccountNumber();
            PreparedStatement preparedStatement = conn.prepareStatement(updateSQL);
            preparedStatement.setDouble(1, 0);
            preparedStatement.setDouble(2, 0);
            preparedStatement.setBoolean(3, false);
            preparedStatement.setInt(4, accNumber);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch(SQLException e){
            return false;
        }
    }
    public boolean revokeTransaction(Transaction transaction) {
        try{
        String updateSQL = "UPDATE transactionRecord set isRevoked=? WHERE  transactionId=?";
        PreparedStatement preparedStatement = conn.prepareStatement(updateSQL);
        preparedStatement.setBoolean(1,true);
        preparedStatement.setInt(2,transaction.getTransactionId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return true;
        }catch(SQLException e){
            return false;
        }
    }
    public boolean deleteCustomer(Customer customer) {
        try{
        String deleteSQL = "delete from customer where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL);
        preparedStatement.setInt(1,customer.getCustomerId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return true;
        }catch(SQLException e){
            return false;
        }
    }
    public boolean transfer(BankAccount account, BankAccount recipientAccount, double amount, Transaction transaction)  {
        try  {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement_1 = conn.prepareStatement("UPDATE accounts SET balance = balance-? WHERE accountNumber = ?");
            PreparedStatement prepareStatement_2 = conn.prepareStatement("UPDATE accounts SET balance = balance+? WHERE accountNumber = ?");
            preparedStatement_1.setDouble(1, amount);
            preparedStatement_1.setInt(2, account.getAccountNumber());
            preparedStatement_1.executeUpdate();
            prepareStatement_2.setDouble(1, amount);
            prepareStatement_2.setInt(2, recipientAccount.getAccountNumber());
            prepareStatement_2.executeUpdate();
            if(transaction==null) {
                if(!data.addRecord(new Transaction(AccountsUtil.generateId(), account.getAccountNumber(), recipientAccount.getAccountNumber(), amount, false))) throw new SQLException();
            }else{
                if(!data.revokeTransaction(transaction)) throw new SQLException();
            }
            conn.commit();
            preparedStatement_1.close();
            prepareStatement_2.close();
            return true;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }return false;
        } finally{
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        }
    public List<Customer> getFullCustomerDetails() {
        try {
            List<Customer> temp = new ArrayList<>();
            String selectSQL = "select a.accountNumber,a.pin,a.id,c.name,c.email,c.address,a.balance,a.minimumBalance,a.type,a.isActive from accounts as a left join customer as c on a.id= c.id;";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(selectSQL);
            while (rs.next()) {
                boolean flag=true;
                int accountNumber = rs.getInt("accountNumber");
                int pin = rs.getInt("pin");
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String address = rs.getString("address");
                double balance = rs.getDouble("balance");
                double minBalance = rs.getDouble("minimumBalance");
                int type = rs.getInt("type");
                boolean isActive = rs.getBoolean("isActive");
                for (Customer customer : temp) {
                    if (customer.getCustomerId() == id) {
                        if (type == 1) {
                            customer.addCustomerAccounts(new Savings(accountNumber, pin, id, balance, minBalance, type, isActive));
                        } else {
                            customer.addCustomerAccounts(new Current(accountNumber, pin, id, balance, minBalance, type, isActive));
                        }
                        flag = false;
                    }
                }
                if (flag) {
                    if (type == 1)
                        temp.add(new Customer(id, name, email, address, new Savings(accountNumber, pin, id, balance, minBalance, type, isActive)));
                    else
                        temp.add(new Customer(id, name, email, address, new Current(accountNumber, pin, id, balance, minBalance, type, isActive)));
                }
            }
            statement.close();
            return temp;
        }catch(SQLException e){return null;}
    }
}