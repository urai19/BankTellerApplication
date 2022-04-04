/**
 * CollegeChecking class includes operations and data specific to the College checking account type which is provided to Rutgers Students only.
 * @author Udayan Rai, Garvit Gupta
 */
package bankteller.project3;

public class CollegeChecking extends Checking{
    private int campus_code;
    private static final double ANNUAL_RATE= 0.25;
    private static final int FEE=0;

    /**
     * Constructor for the College Checking class.
     * @param holder the profile of the holder.
     * @param balance the initial balance
     * @param campus_code campus code
     */
    public CollegeChecking(Profile holder, double balance, int campus_code){
        super(holder, balance);
        this.campus_code=campus_code;
        annualRate= ANNUAL_RATE;
    }

    /**
     * Method to deposit money into an account.
     * @param amount the amount to be deposited
     */
    @Override
    public void deposit(double amount){
        super.deposit(amount);
    }

    /**
     * Method to withdraw money from an account.
     * @param amount the amount to be withdrawn.
     */
    @Override
    public void withdraw(double amount){
        super.withdraw(amount);
    }

    /**
     * Method to return the fee for the account.
     * @return fee
     */
    @Override
    public double fee(){
        return FEE;
    }

    /**
     * Method to return the type of account.
     * @return Type of account.
     */
    @Override
    public String getType() {return "College Checking";}

    /**
     * Helper method to get the correct campus from the campus code.
     * @return Campus name
     */
    private String getCampus(){
        if(campus_code==0) return "NEW_BRUNSWICK";
        else if(campus_code==1) return "NEWARK";
        else return "CAMDEN";
    }

    /**
     * Method to return the account details in readable format.
     * @return output
     */
    @Override
    public String toString(){
        return super.toString()+"::"+getCampus();
    }

    /**
     * Method to check if an account is equal to another account.
     * @param obj Object that is to be compared
     * @return true if the 2 accounts are equals.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof CollegeChecking){
            CollegeChecking checkAcct= (CollegeChecking) obj;
            return checkAcct.holder.equals(this.holder);
        }
        return false;
    }

}
