/**
 Profile class creates an object that holds the first name, last name, and date of birth(DOB) of an account holder.
 @author Udayan Rai, Garvit Gupta
 */
package bankteller.project3;

public class Profile {
    private String fname;
    private String lname;
    private Date dob;

    /**
     Constructor for the Profile class.
     @param fname The first name of the account holder
     @param lname The last name of the account holder
     @param dob The date of birth of the account holder.
     */
    public Profile(String fname, String lname, Date dob){
        this.fname=fname;
        this.lname=lname;
        this.dob=dob;
    }

    /**
     Converts the information of the Account holder's profile into a String which is readable by BankTeller user.
     @return output
     */
    @Override
    public String toString() {
        String output= fname+ " " + lname+ ", DOB: "+ dob;
        return output;
    }

    /**
     * Checks if two Profiles are exactly equals.
     * @param obj The profile being compared to
     * @return true if the profiles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Profile) {
            Profile checkProfile= (Profile)obj;
            return checkProfile.fname.equalsIgnoreCase(fname) &&
                    checkProfile.lname.equalsIgnoreCase(lname) &&
                    checkProfile.dob.compareTo(dob)==0;
        }
        return false;
    }


}
