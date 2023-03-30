enum Choice {
        CREATE_ACCOUNT(1),USE_ACCOUNT(2),ADMIN_ONLY(3),EXIT(4);
        final int choice;
        Choice(int ch){
                this.choice=ch;
        }
}
enum Select {
        VIEW_BALANCE(1), WITHDRAW(2), DEPOSIT(3), TRANSFER(4), CHANGE_MINIMUM_BALANCE(5),CHANGE_PIN(6), CALCULATE_INTEREST(7),CLOSE_ACCOUNT(8),EXIT(9);
        final int choice;
        Select(int ch) {
                this.choice = ch;
        }
}
enum Option {
        VIEW_ACCOUNTS(1), VIEW_CUSTOMER_DETAILS(2), VIEW_CUSTOMERS(3),VIEW_TRANSACTIONS(4),DELETE_INACTIVE_CUSTOMERS(5),REVOKE_TRANSACTION(6),EXIT(7);
        final int choice;
        Option(int ch) {
                this.choice = ch;
        }
}

