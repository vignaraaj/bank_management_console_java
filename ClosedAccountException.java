public class ClosedAccountException extends Exception{
    public ClosedAccountException(String msg) {
        super(msg);
    }
}
class SameAccountException extends Exception{
    public SameAccountException(String msg) {
        super(msg);
    }
}
class InsufficentBalanceException extends Exception{
    public InsufficentBalanceException(String msg) {
        super(msg);
    }
}
class InvalidDataException extends Exception{
    public  InvalidDataException(String msg) {
        super(msg);
    }
}
class ExistTransactionException extends Exception{
    public ExistTransactionException(String msg) {
        super(msg);
    }
}
class InvalidTransactionException extends Exception{
    public InvalidTransactionException(String msg) {
        super(msg);
    }
}