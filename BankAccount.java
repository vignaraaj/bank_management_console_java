abstract class BankAccount {
    private  final int customerId;
    private final int accountNumber;
    private  double balance;
    private  int pin;
    private  Boolean isActive;
    private  double minimumBalance;
    private final int type;
    static Data data = DataDB.getInstance();
    //static Data data = DataList.getInstance();
    public BankAccount(int accountNumber, int pin, int customerId,double balance, double minimumBalance,int type,boolean isActive) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.customerId=customerId;
        this.balance = balance;
        this.minimumBalance = minimumBalance;
        this.type=type;
        this.isActive = isActive;
    }
    public double getBalance() {
        return balance;
    }
    public int getCustomerId() {
        return customerId;
    }
    public int getAccountNumber() {
        return accountNumber;
    }
    public int getType() {
        return type;
    }
    public Boolean getActive() {
        return isActive;
    }
    public int getPin() {
        return pin;
    }
    public double getMinimumBalance() {
        return minimumBalance;
    }
    public void setBalance(double balance) {
        this.balance=balance;
    }
    public void setPin(int pin) {
        this.pin=pin;
    }
    public void setMinimumBalance(double minBalance) {
        this.minimumBalance = minBalance;
    }
    public void setActive(boolean b) {
        this.isActive=b;
    }
    @Override
    public String toString() {
        return String.format("accNo:%8d\tpin:%3d\tcustId:%6d\tBalance:%12f\tminBalance:%12f\ttype:%2d\tactivity:%5b",getAccountNumber(),getPin(),getCustomerId(), getBalance(),getMinimumBalance(),getType(),getActive());
    }
    public abstract double calculateInterest(int months);
    public boolean deposit(double depositAmount) throws InvalidDataException{
            if (depositAmount <= 0) throw new InvalidDataException("Invalid data");
            return data.updateBalance(this,depositAmount);
    }
    public boolean withdraw(double withdrawAmount) throws InvalidDataException,InsufficentBalanceException {
        if (withdrawAmount <= 0) throw new InvalidDataException("Invalid data");
        if (withdrawAmount > getBalance()) throw new InsufficentBalanceException("Insufficient balance");
        return data.updateBalance(this,-withdrawAmount);
    }
    public boolean transfer(int recipientAccountNumber, double amount) throws ClosedAccountException,SameAccountException,InsufficentBalanceException,InvalidDataException {
        BankAccount recipientAccount = data.findAccountByAccountNumber(recipientAccountNumber);
        if(recipientAccount==null) throw new ClosedAccountException("Invalid recipient account");
        if (!recipientAccount.getActive()) throw new ClosedAccountException("Recipient account closed");
        if (recipientAccountNumber == getAccountNumber()) throw new SameAccountException("Can't transfer money to same account");
        if (amount <= 0) throw new InvalidDataException("Invalid data");
        if (amount > getBalance()) throw new InsufficentBalanceException("Insufficient balance");
        return data.transfer(this, recipientAccount,amount,null);
    }
}




