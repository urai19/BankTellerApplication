/**
 * Checking class includes operations and data specific to the checking account type.
 * Extends the Account class
 * @author Udayan Rai, Garvit Gupta
 */
package bankteller.project3;

public class Checking extends Account{
    protected double annualRate;
    private static final double ANNUAL_RATE= 0.1;
    public static final int NUM_MONTHS=12;
    public static final int PERCENT=100;
    private static final int WAIVE_THRESHOLD= 1000;
    private static final int FEE= 25;

    /**
     * Constructor for the Checking account type.
     * @param holder Profile of the holder
     * @param balance initial balance
     */
    public Checking(Profile holder, double balance){
        super(holder,balance);
        annualRate=ANNUAL_RATE;
    }

    /**
     * Method to deposit money into an account.
     * @param amount Amount to be deposited.
     */
    @Override
    public void deposit(double amount){
        super.deposit(amount);
    }

    /**
     * Method to withdraw money from an account.
     * @param amount Amount to be withdrawn.
     */
    @Override
    public void withdraw(double amount){
        super.withdraw(amount);
    }

    /**
     * This method calculates the monthly interest that is to be applied to the balance based on the account type.
     * @return interest
     */
    public double monthlyInterest() {
        double monthlyRate= annualRate/NUM_MONTHS;
        double decimalRate= monthlyRate/PERCENT;
        double interest= balance*decimalRate;
        return interest;
    }

    /**
     * This method returns the monthly fee that is to be applied to the balance based on the account type.
     * @return fee
     */
    public double fee() {
        if(balance>=WAIVE_THRESHOLD) return 0;
        else return FEE;
    }

    /**
     * This method returns the account type.
     * @return type of account.
     */
    public String getType() {
        return "Checking";
    }

    /**
     * Method that returns the account details in readable format.
     * @return output
     */
    @Override
    public String toString(){
        return super.toString();
    }

    /**
     * Method that compares 2 accounts to check if they are equal.
     * @param obj Account being compared
     * @return true if the 2 accounts are equal
     */
    @Override
    public boolean equals(Object obj){

        if (obj instanceof CollegeChecking) return false;

        if(obj instanceof Checking){
            Checking checkAcct= (Checking)obj;
            return checkAcct.holder.equals(this.holder);
        }
        return false;
    }


}
