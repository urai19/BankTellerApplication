/**
 * Savings class includes operations and data specific to the Savings account type.
 * Extends the Account class
 * @author Udayan Rai, Garvit Gupta
 */
package bankteller.project3;

public class Savings extends Account{
    protected double annualRate;
    protected boolean loyal;
    private static final double ANNUAL_RATE_LOYAL= 0.45;
    private static final double ANNUAL_RATE_NOT_LOYAL= 0.3;

    private static final int WAIVE_THRESHOLD= 300;
    private static final int FEE= 6;

    /**
     Constructor for the Savings Account Type Class.
     @param holder The profile of the Account holder
     @param balance The current balance of the holder
     @param loyal boolean which represents the loyalty of holder
     */
    public Savings(Profile holder, double balance, boolean loyal){
        super(holder,balance);
        this.loyal = loyal;
        if(loyal==true)this.annualRate=ANNUAL_RATE_LOYAL;
        else this.annualRate=ANNUAL_RATE_NOT_LOYAL;
    }
    /**
     * This method calculates the monthly interest that is to be applied to the balance based on the account type.
     * @return interest
     */
    public double monthlyInterest(){
        double monthlyRate= annualRate/NUM_MONTHS;
        double decimalRate= monthlyRate/PERCENT;
        double interest= balance*decimalRate;
        return interest;
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
     * Method to withdraw from an account.
     * @param amount Amount to be withdrawn.
     */
    @Override
    public void withdraw(double amount){
        super.withdraw(amount);
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
     * Method to get the type of account-Savings.
     * @return account type.
     */
    public String getType() {
        return "Savings";
    }

    /**
     * Method that returns the account details in readable format.
     * Includes Profile details, balance and loyalty
     * @return output
     */
    @Override
    public String toString(){
        if(loyal) return super.toString()+":: Loyal";
        else return super.toString();
    }

    /**
     * Method that compares 2 accounts to check if they are equal.
     * @param obj Account being compared
     * @return true if the two account objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof MoneyMarket) return false;
        if(obj instanceof Savings){
            Savings checkAcct= (Savings) obj;
            return checkAcct.holder.equals(this.holder);
        }
        return false;
    }
}
