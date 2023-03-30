final class Current extends BankAccount {
    public Current(int accountNumber, int pin,int customerId,double balance,double minimumBalance,int type,boolean isActive) {
        super(accountNumber, pin,customerId,balance,minimumBalance,type,isActive);
    }
    public double calculateInterest(int months) {
        double interestRate = 0.01;
        return getBalance() * interestRate * months;
    }
}
