final class Savings extends BankAccount {
    public Savings(int accountNumber, int pin,int customerId,double balance,double minimumBalance,int type,boolean isActive) {
        super(accountNumber, pin,customerId,balance,minimumBalance,type,isActive);
    }
    public double calculateInterest(int months) {
        double interestRate = 0.05;
        return getBalance() * interestRate * months;
    }
}

