/**
 * Account class is an abstract class that contains common methods and data for all types of accounts.
 * It is the superclass of all account types.
 * @author Udayan Rai, Garvit Gupta
 */

package bankteller.project3;
import java.text.DecimalFormat;

public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;

    public static final int NUM_MONTHS=12;
    public static final int PERCENT=100;

    /**
     * Constructor for Account class.
     * Takes in basic information of an account that is required for all account types.
     * @param holder Profile of the account holder
     * @param balance Initial balance when opening an account.
     */
    public Account(Profile holder, double balance){
        this.holder=holder;
        this.balance=balance;
        this.closed=false;
    }

    /**
     * Checks if two accounts are exactly equal.
     * @param obj Account being compared
     * @return true if the two account objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Account) {
            Account checkAcc= (Account) obj;
            return checkAcc.holder.equals(this.holder);
        }
        return false;
    }

    /**
     * Converts the information of an Account into a String which is readable by user.
     * @return output
     */
    @Override
    public String toString() {

        DecimalFormat dFormatter = new DecimalFormat("$"+ "##,##0.00");

        if(closed) return getType()+"::"+ holder.toString() + "::Balance "+ dFormatter.format(balance)+ ":: CLOSED";
        else return getType()+"::"+ holder.toString() + "::Balance "+ dFormatter.format(balance);
    }

    /**
     * This method is for withdrawing money from an existing account.
     * @param amount Amount to be withdrawn.
     */
    public void withdraw(double amount) {
        balance-=amount;
    }

    /**
     * This method is to deposit money to an existing account.
     * @param amount Amount to be deposited.
     */
    public void deposit(double amount) {
        balance+=amount;
    }

    /**
     * Abstract method that calculates monthly interest.
     * @return monthly interest
     */
    public abstract double monthlyInterest();

    /**
     * Abstract method that returns the fee on the account.
     * @return fee
     */
    public abstract double fee();

    /**
     * Abstract method that returns the type of account.
     * @return account Type.
     */
    public abstract String getType();


}
