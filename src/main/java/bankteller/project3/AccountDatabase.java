/**
 * AccountDatabase class encompasses an array container to open, close, deposit, withdraw and print.
 * @author Udayan Rai, Garvit Gupta
 */
package bankteller.project3;

import java.text.DecimalFormat;

public class AccountDatabase {
    public static final int NOT_FOUND = -1;
    public static final int INITAL_LENGTH = 4;

    private Account[] accounts;
    private int numAcct;

    /**
     * Constructor for the AccountDatabase class.
     * Initializes a new Account[] array with length 4.
     */
    public AccountDatabase() {
        this.accounts = new Account[INITAL_LENGTH];
        this.numAcct = 0;
    }

    /**
     * Getter method for the accounts array
     * @return The accounts array.
     */
    public Account[] getAccounts() {
        return accounts;
    }

    /**
     * Getter method for numAcct.
     * @return numAcct the number of accounts.
     */
    public int getNumAcct(){ return numAcct;}

    /**
     * Method to find an Account in the accounts array.
     * @param account Account to be found
     * @return the index of the account. -1 if not found.
     */
    private int find(Account account) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Increases the length of the array by 4.
     */
    private void grow() {

        Account[] newAccountArray = new Account[accounts.length + 4];
        for (int i = 0; i < accounts.length; i++) {
            newAccountArray[i] = accounts[i];
        }
        accounts = newAccountArray;
    }

    /**
     * Method to open an account.
     * If the account exists but is closed, it reopens it.
     * @param account The account to be opened
     * @return true if successfully opens a new account or reopen an existing one. False otherwise.
     */
    public boolean open(Account account) {
        boolean returner = false;
        int index = find(account);
        if(account.getType().equals("Checking")){
            for(Account acc:accounts){
                if(acc!=null && acc.getType().equals("College Checking") && acc.holder.equals(account.holder)) return false;
            }
        }
        if(account.getType().equals("College Checking")){
            for(Account acc:accounts){
                if(acc!=null && acc.getType().equals("Checking") && acc.holder.equals(account.holder)) return false;
            }
        }
        if ((find(account) != NOT_FOUND) && accounts[index].closed == true){

            account.closed = false;
            accounts[index].deposit(account.balance);
            numAcct++;
            return true;
        }
        else if ((find(account) != NOT_FOUND) && accounts[index].closed == false) {
            return false;
        }else {
            for (int i = 0; i < accounts.length; i++) {
                if (accounts[i] == null) {
                    accounts[i] = account;
                    account.closed = false;
                    numAcct++;
                    returner = true;
                    break;
                } else {
                    grow();
                }
            }
        }
        return returner;
    }

    /**
     * Method to close an account.
     * @param account The account to be closed.
     * @return true is closed successfully. False if account not found or existing account already closed.
     */
    public boolean close(Account account) {
        boolean returner;
        int index = find(account);
        if (index == NOT_FOUND) {
            returner = false;
        } else if (index != NOT_FOUND && accounts[index].closed == true) {
            returner = false;
        } else {
            accounts[index].balance = 0;
            accounts[index].closed = true;
            if (accounts[index] instanceof Savings) {
                ((Savings) accounts[index]).loyal = false;
            }
            if (accounts[index] instanceof MoneyMarket) {
                ((MoneyMarket) accounts[index]).setWithdrawalCounterto0();
            }
            returner = true;
            numAcct--;

        }
        return returner;
    }

    /**
     * Method to deposit money into an account.
     * @param account The account object initialized with the amount to be deposited.
     */
    public void deposit(Account account) {
        int index = find(account);
        if(index!=-1) {
            Account depositAccount = accounts[index];
            depositAccount.deposit(account.balance);
        }
    }

    /**
     * Method to withdraw money from an account.
     * @param account The account object initialized with the amount to be withdrawn
     * @return true if successfully withdrawn. False if insufficient balance.
     */
    public boolean withdraw(Account account) {
        int index = find(account);
        Account withdrawAccount = accounts[index];
        if (account.balance > withdrawAccount.balance) {
            return false;
        } else {
            withdrawAccount.withdraw(account.balance);
            return true;
        }

    }

    /**
     * Method to print all accounts in array order
     * @return output
     */
    public String print() {
        String output="";
        if (numAcct == 0) {
            output="Account Database is empty!";
            return output;
        }
        for (Account acc : accounts) {
            if (acc != null) {
                output=output+acc.toString()+"\n";
            }
        }

        return output;
    }

    /**
     * Helper method that goes through the array and prints all accounts
     * @param accounts the accounts array to be printed.
     * @return output
     */
    private String helperPrint(Account[] accounts) {
        String output="";
        for (Account acc : accounts) {
            if (acc != null) {
                output= output+acc.toString()+"\n";
            }
        }
        return output;
    }

    /**
     * Method to print all accounts in the array sorted by account type.
     * @return output
     */
    public String printByAccountType() {
        String output="";
        if (numAcct == 0) {
            output="Account Database is empty!";
            return output;
        }
        Account[] printAccounts = new Account[accounts.length];
        int counter = 0;
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].getType().equals("Checking")) {
                printAccounts[counter] = accounts[i];
                counter++;
            }
        }
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].getType().equals("College Checking")) {
                printAccounts[counter] = accounts[i];
                counter++;
            }
        }
        for (int i = 0; i < accounts.length; i++) {
            if ( accounts[i] != null && accounts[i].getType().equals("Money Market Savings")) {
                printAccounts[counter] = accounts[i];
                counter++;
            }
        }
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].getType().equals("Savings") ) {
                printAccounts[counter] = accounts[i];
                counter++;
            }
        }

        output=helperPrint(printAccounts);

        return output;
    }

    /**
     * Method to print all accounts in the array with fees and monthly interests.
     * @return output
     */
    public String printFeeAndInterest() {
        String output="";
        DecimalFormat dFormatter = new DecimalFormat("$"+ "##,##0.00");

        if (numAcct == 0) {
            output="Account Database is empty!";
            return output;
        }

        for (Account acc : accounts) {
            if (acc !=null) {
                output=output+ (acc.toString() + "::fee " + dFormatter.format(acc.fee())
                        + "::monthly interest " + dFormatter.format(acc.monthlyInterest())+"\n");
            }
        }

        return output;
    }


    public static void main(String[] args) {
        AccountDatabase database1_1 = new AccountDatabase();
        Account acc1_1 = new MoneyMarket(new Profile("Garvit","Gupta",new Date("09/19/2002")), 2501);
        Account acc1_2 = new Checking(new Profile("Garvit","Gupta",new Date("09/19/2002")), 100);
        Account acc1_3 = new Savings(new Profile("Udayan","Gupta",new Date("09/19/2002")), 100, true);
        Account acc1_4 = new CollegeChecking(new Profile("Udayan","Gupta",new Date("09/19/2002")), 100, 1);
        Account acc1_5 = new MoneyMarket(new Profile("Dhruv","Gupta",new Date("09/19/2002")), 100);
        Account acc1_6= new Checking(new Profile("Udayan","Gupta",new Date("9/19/2002")),1666);

        database1_1.open(acc1_1);
        database1_1.open(acc1_2);
        database1_1.open(acc1_3);
        database1_1.open(acc1_4);
        database1_1.open(acc1_5);
        database1_1.open(acc1_6);

        database1_1.print();

        Account add= new MoneyMarket(new Profile("Garvit","Gupta",new Date("09/19/2002")),100);
        database1_1.deposit(add);
        database1_1.print();

//        System.out.println("---WithDrawfrom_acc1_1---");
//        acc1_1.withdraw(2);
//        database1_1.printFeeAndInterest();

//        database1_1.print();
//        System.out.println("-----------Print by Type:----------");
//        database1_1.printByAccountType();
//        System.out.println("-----------Print by Type:----------");
//        database1_1.close(acc1_3);
//        database1_1.printByAccountType();
//        database1_1.open(acc1_3);
//        System.out.println("-----------Print by Type x3:----------");
//        database1_1.printByAccountType();

    }
}
