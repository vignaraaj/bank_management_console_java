final  class Transaction {
    private final int transactionId;
    private final int accountNumber;
    private final int recipientAccountNumber;
    private final double amountTransferred;
    private  boolean isRevoked;
    public Transaction(int transactionId,int accountNumber, int recipientAccountNumber, double amountTransferred,boolean isRevoked) {
        this.transactionId =transactionId;
        this.accountNumber = accountNumber;
        this.recipientAccountNumber = recipientAccountNumber;
        this.amountTransferred = amountTransferred;
        this.isRevoked=isRevoked;
    }
    public int getAccountNumber() {
        return accountNumber;
    }
    public int getTransactionId() {
        return transactionId;
    }
    public int getRecipientAccountNumber() {
        return recipientAccountNumber;
    }
    public double getAmountTransferred() {
        return amountTransferred;
    }
    public boolean isRevoked() {
        return isRevoked;
    }
    public void setRevoked(boolean b) {
        this.isRevoked=b;
    }
    public String toString() {
        return "transactionId=" + getTransactionId() +
                ", account_number=" + getAccountNumber() +
                ", recipient_account_number=" + getRecipientAccountNumber() +
                ", amount_transferred=" + getAmountTransferred() +
                ", isRevoked=" + isRevoked() ;
    }
}
