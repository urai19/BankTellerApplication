/**
 * MoneyMarket class includes operations and data specific to the MoneyMarket account type.
 * Extends the Savings class
 * @author Udayan Rai, Garvit Gupta
 */
package bankteller.project3;

public class MoneyMarket extends Savings {

    private static final double ANNUAL_RATE_LOYAL= 0.95;
    private static final double ANNUAL_RATE_NOT_LOYAL= 0.80;
    private static final int WAIVE_THRESHOLD= 2500;
    private static final int FEE = 10;
    private static final int WITHDRAWAL_THRESHOLD = 2;
    private int withdrawalCounter;

    /**
     Constructor for the MoneyMarket Account Type Class.
     @param holder The profile of the Account holder
     @param balance The current balance of the holder
     */
    public MoneyMarket(Profile holder, double balance){
        super(holder,balance, true);
        this.withdrawalCounter = 0;
        if (this.balance < WAIVE_THRESHOLD) {
            this.loyal = false;
            this.annualRate = ANNUAL_RATE_NOT_LOYAL;
        }else{
            this.annualRate= ANNUAL_RATE_LOYAL;
        }
    }

    /**
     * Helper method to set the withdrawal counter to 0.
     */
    public void setWithdrawalCounterto0(){withdrawalCounter=0;}

    /**
     * This method is for withdrawing money from an existing account.
     * @param amount Amount to be withdrawn.
     */
    @Override
    public void withdraw(double amount) {
        balance-=amount;
        this.withdrawalCounter++;
        if (balance <= WAIVE_THRESHOLD){
            this.loyal = false;
            this.annualRate = ANNUAL_RATE_NOT_LOYAL;
        }
    }

    /**
     * This method is to deposit money to an existing account.
     * @param amount Amount to be deposited.
     */
    @Override
    public void deposit(double amount) {
        balance+=amount;
        if (amount >= WAIVE_THRESHOLD){
            this.loyal = true;
            this.annualRate = ANNUAL_RATE_LOYAL;
        }
    }
    /**
     * This method calculates the monthly interest that is to be applied to the balance based on the account type.
     * @return interest
     */
    public double monthlyInterest(){
        return super.monthlyInterest();
    }

    /**
     * This method returns the monthly fee that is to be applied to the balance based on the account type.
     * @return fee
     */
    @Override
    public double fee() {
        if(withdrawalCounter > WITHDRAWAL_THRESHOLD || balance<= WAIVE_THRESHOLD) return FEE;
        else return 0;
    }

    /**
     *Method to get the type of account- Money Market Savings
     * @return account type.
     */
    @Override
    public String getType() {
        return "Money Market Savings";
    }

    /**
     * Method that returns the account details in readable format.
     * Includes Profile details, balance,withdrawal counter.
     * @return output
     */
    @Override
    public String toString(){
        return super.toString()+":: withdrawal: "+ withdrawalCounter;
    }

    /**
     * Method that compares 2 accounts to check if they are equal.
     * @param obj Account being compared
     * @return true if the two account objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof MoneyMarket){
            MoneyMarket checkAcct= (MoneyMarket) obj;
            return checkAcct.holder.equals(this.holder);
        }
        return false;
    }

}
