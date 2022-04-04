/**
 * Controller class for the Bank Teller Application. Code for the fxml file.
 * @author Udayan Rai, Garvit Gupta
 */
package bankteller.project3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.NoSuchElementException;

public class bankTellerController {

    AccountDatabase database=new AccountDatabase();
    public static final int MM_INIT_BALANCE=2500;
    public static final int CAMDEN=2;

    @FXML
    private RadioButton rButton_C,rButton_CC,rButton_S,rButton_MM;

    @FXML
    private RadioButton rButton_C_DW,rButton_CC_DW,rButton_S_DW,rButton_MM_DW;

    @FXML
    private RadioButton rButton_NB,rButton_NE,rButton_CA;

    @FXML
    private RadioButton rButton_loyal;

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField dob;

    @FXML
    private ToggleGroup AccType;

    @FXML
    private TextField balance;

    @FXML
    private ToggleGroup Campus;

    @FXML
    private ToggleGroup loyal;

    @FXML
    private TextField fnameDW;

    @FXML
    private TextField amount;

    @FXML
    private TextField lnameDW;

    @FXML
    private TextField dobDW;

    @FXML
    private ToggleGroup type;

    @FXML
    private TextArea output;

    /**
     * Disables the options that are not applicable to the College Checking account type.
     * @param event when the user hits the CollegeChecking radio button
     */
    @FXML
    void disableForCC(ActionEvent event) {
        rButton_NB.setDisable(false);
        rButton_NE.setDisable(false);
        rButton_CA.setDisable(false);
        rButton_loyal.setDisable(true);
    }

    /**
     * Disables the options that are not applicable to the Checking account type.
     * @param event when the user hits the Checking radio button
     */
    @FXML
    void disableForChecking(ActionEvent event) {
        rButton_NB.setDisable(true);
        rButton_NE.setDisable(true);
        rButton_CA.setDisable(true);
        rButton_loyal.setDisable(true);
    }

    /**
     * Disables the options that are not applicable to the MoneyMarket account type.
     * @param event when the user hits the Money Market radio button.
     */
    @FXML
    void disableForMoneyMarket(ActionEvent event) {
        rButton_NB.setDisable(true);
        rButton_NE.setDisable(true);
        rButton_CA.setDisable(true);
        rButton_loyal.setDisable(true);
    }

    /**
     * Disables the options that are not applicable to the Savings account type.
     * @param event when the user hits the Savings radio button.
     */
    @FXML
    void disableForS(ActionEvent event) {
        rButton_NB.setDisable(true);
        rButton_NE.setDisable(true);
        rButton_CA.setDisable(true);
        rButton_loyal.setDisable(false);
    }

    /**
     * Method that closes an account.
     * @param event triggered when the user clicks the close button
     */
    @FXML
    void closeAccount(ActionEvent event) {
        try{
            String fname=this.fname.getText();
            if(fname.equals("")) throw new NoSuchElementException();
            String lname=this.lname.getText();
            if(lname.equals("")) throw new NoSuchElementException();
            String birth= this.dob.getText();
            if (birth.equals("")) throw new NoSuchElementException();
            Profile holder= new Profile(fname,lname,new Date(birth));
            if(AccType.getSelectedToggle()!=null){
                if(rButton_C.isSelected()){
                    Account newAcc=new Checking(holder, 0);
                    closeHelper(newAcc);
                }
                if(rButton_CC.isSelected()){
                    Account newAcc=new CollegeChecking(holder,0,0);
                    closeHelper(newAcc);
                }
                if(rButton_S.isSelected()){
                    Account newAcc=new Savings(holder, 0,true);
                    closeHelper(newAcc);
                }
                if(rButton_MM.isSelected()){
                    Account newAcc=new MoneyMarket(holder, 0);
                    closeHelper(newAcc);
                }
            }else{
                throw new NoSuchElementException();
            }
        }
        catch(NoSuchElementException e){
            output.setText("Missing Data for closing an account.");
        } catch(NumberFormatException e){
            output.setText("Missing Data for closing an account.");
        }
    }

    /**
     * Helper method for closing the account.
     * @param newAcc account to close
     */
    private void closeHelper(Account newAcc) {
        boolean alrClosed = false;
        for (Account acc : database.getAccounts()) {
            if (acc != null && acc.equals(newAcc) && acc.closed == true) {
                alrClosed = true;
            }
        }
        boolean isClose = database.close(newAcc);
        if(isClose==false && alrClosed==false) output.setText(newAcc.toString()+ " is not in database.");
        if (isClose == false && alrClosed) output.setText("Account is closed already.");
        if (isClose) output.setText("Account closed.");
    }

    /**
     * Method to deposit amount in a bank account.
     * @param event triggered when the user clicks the deposit button.
     */
    @FXML
    void depositToAccount(ActionEvent event) {
        try{
            String fname = this.fnameDW.getText();
            String lname = this.lnameDW.getText();
            String dob = this.dobDW.getText();
            double amount = Double.parseDouble(this.amount.getText());
            Date birth = new Date(dob);
            Date today = new Date();
            if (!birth.isValid() || birth.compareTo(today) <= 0) {
                throw new NoSuchElementException();
            }
            deposit(fname,lname,dob,amount);
        } catch(IllegalArgumentException e){
            output.setText("Not a valid amount");
        } catch(NoSuchElementException e){
            output.setText("Date of Birth invalid");
        }
    }

    /**
     * Helper method to deposit amount to an account.
     * @param fname first name
     * @param lname last name
     * @param dob date of birth
     * @param amount amount to be deposited
     */
    private void deposit(String fname, String lname, String dob, double amount) {
        Profile holder = new Profile(fname, lname, new Date(dob));
        try {
            boolean found = false;
            if (amount <= 0) {
                throw new IllegalArgumentException();
            }
            if(type.getSelectedToggle()!=null) {
                if (rButton_C_DW.isSelected()) {
                    Account newAcc = new Checking(holder, amount);
                    for (Account acc : database.getAccounts()) {
                        if (acc != null && acc.equals(newAcc)) found = true;
                    }
                    if (found) database.deposit(newAcc);

                }
                if (rButton_CC_DW.isSelected()) {
                    Account newAcc = new CollegeChecking(holder, amount, 0);
                    for (Account acc : database.getAccounts()) {
                        if (acc != null && acc.equals(newAcc)) found = true;
                    }
                    if (found) database.deposit(newAcc);

                }
                if (rButton_S_DW.isSelected()) {
                    Account newAcc = new Savings(holder, amount, true);
                    for (Account acc : database.getAccounts()) {
                        if (acc != null && acc.equals(newAcc)) found = true;
                    }
                    if (found) database.deposit(newAcc);

                }
                if (rButton_MM_DW.isSelected()) {
                    Account newAcc = new MoneyMarket(holder, amount);
                    for (Account acc : database.getAccounts()) {
                        if (acc != null && acc.equals(newAcc)) found = true;
                    }
                    if (found) database.deposit(newAcc);
                }
            } else throw new NoSuchElementException();
            if (found) output.setText("Deposit- balance updated.");
            else output.setText(holder.toString() + " is not in the database.");
        } catch (IllegalArgumentException e) {
            output.setText("Deposit- amount cannot be 0 or negative.");
        } catch (NoSuchElementException e){
            output.setText("Missing Data to deposit to an account.");
        }
    }

    /**
     * Method to open an account.
     * @param event triggered when the open button is clicked.
     */
    @FXML
    void openAccount(ActionEvent event) {
        try {
            String fname = this.fname.getText();
            if(fname.equals("")) throw new RuntimeException();
            String lname = this.lname.getText();
            if(lname.equals("")) throw new RuntimeException();
            String dob = this.dob.getText();
            if(dob.equals("")) throw new RuntimeException();
            if(this.balance.getText().equals("")) throw new RuntimeException();
            double balance = Double.parseDouble(this.balance.getText());
            Date birth = new Date(dob);
            Date today = new Date();
            if (!birth.isValid() || birth.compareTo(today) <= 0) {
                throw new NoSuchElementException();
            }
            if (AccType.getSelectedToggle() != null) {
                if (rButton_C.isSelected()) {
                    openC(fname, lname, dob, balance);
                }
                if (rButton_CC.isSelected()) {
                    if (Campus.getSelectedToggle() != null) {
                        if (rButton_NB.isSelected()) openCC(fname, lname, dob, balance, 0);
                        if (rButton_NE.isSelected()) openCC(fname, lname, dob, balance, 1);
                        if (rButton_CA.isSelected()) openCC(fname, lname, dob, balance, CAMDEN);
                    }else throw new RuntimeException();
                }
                if (rButton_S.isSelected()) {
                    if (rButton_loyal.isSelected()) openS(fname, lname, dob, balance, true);
                    else openS(fname, lname, dob, balance, false);
                }
                if (rButton_MM.isSelected()) openMM(fname, lname, dob, balance);
            }
            else throw new RuntimeException();
        } catch(NoSuchElementException e){
            output.setText("Invalid Date of Birth");
        } catch(IllegalArgumentException e){
            output.setText("Not a valid amount.");
        } catch(RuntimeException e){
            output.setText("Missing Data for opening an account");
        }
    }

    /**
     * This method calls the Open() method in AccountDatabase to open accounts
     * @param holder The Profile of the opening account
     * @param acc The account object to be opened
     */
    private void openHelper(Profile holder, Account acc) {
        boolean exists = false;

        boolean isOpen = database.open(acc);

        for (Account account : database.getAccounts()) {
            if (account != null && account.equals(acc) && account.closed==true) {
                exists = true;
                account.closed=false;
                break;
            }
        }

        if (!isOpen) output.setText(holder.toString() + " same account(type) is in the database.");
        if (isOpen && exists) {
            output.setText("Account reopened.");
        }
        else if (isOpen) output.setText("Account opened");
    }

    /**
     * This method processes the open command for Checking accounts.
     * @param fname First name of the holder
     * @param lname Last name of the holder
     * @param dob Date of Birth of the holder
     * @param balance Initial balance
     */
    private void openC(String fname, String lname, String dob, double balance) {
        try {
            Profile holder = new Profile(fname, lname, new Date(dob));
            if (balance <= 0) {
                throw new IllegalArgumentException();
            }
            Account newAcc = new Checking(holder, balance);
            openHelper(holder, newAcc);
        } catch (IllegalArgumentException e) {
            output.setText("Initial Deposit cannot be 0 or negative.");
        }
    }

    /**
     * This method processes the open command for College Checking accounts.
     * @param fname First name of the holder
     * @param lname Last name of the holder
     * @param dob Date of Birth of the holder
     * @param balance Initial Balance
     * @param campus campus code
     */
    private void openCC(String fname, String lname, String dob, double balance, int campus) {
        try {
            Profile holder = new Profile(fname, lname, new Date(dob));
            if (balance <= 0) {
                throw new IllegalArgumentException();
            }
            Account newAcc = new CollegeChecking(holder, balance, campus);
            openHelper(holder, newAcc);
        } catch (IllegalArgumentException e1) {
            output.setText("Initial Deposit cannot be 0 or negative.");
        }
    }

    /**
     * This method processes the open command for Savings accounts.
     * @param fname First name of the holder
     * @param lname Last name of the holder
     * @param dob Date of Birth of the holder
     * @param balance Initial Balance
     * @param loyal Loyalty status
     */
    private void openS(String fname, String lname, String dob, double balance, boolean loyal) {
        try {
            Profile holder = new Profile(fname, lname, new Date(dob));
            if (balance <= 0) {
                throw new IllegalArgumentException();
            }
            Account newAcc = new Savings(holder, balance, loyal);
            openHelper(holder, newAcc);
        } catch (IllegalArgumentException e) {
            output.setText("Initial Deposit cannot be 0 or negative.");
        }
    }

    /**
     * This method processes the open command for the Money Market account.
     * @param fname first name of the holder
     * @param lname last name of the holder
     * @param dob date of birth of the holder
     * @param balance initial balance
     */
    private void openMM(String fname, String lname, String dob, double balance) {
        try {
            if (balance < MM_INIT_BALANCE && balance >0) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            output.setText("Minimum of $2500 to open a MoneyMarket account.");
            return;
        }
        try {
            Profile holder = new Profile(fname, lname, new Date(dob));
            if (balance <= 0) {
                throw new IllegalArgumentException();
            }
            Account newAcc = new MoneyMarket(holder, balance);
            openHelper(holder, newAcc);
        } catch (IllegalArgumentException e) {
            output.setText("Initial Deposit cannot be 0 or negative.");
        }
    }

    /**
     * Method to print all accounts.
     * @param event triggered when user clicks the print all accounts button.
     */
    @FXML
    void printAllAcc(ActionEvent event) {
        String allAcc= database.print();
        output.setText(allAcc);
    }

    /**
     * Method to print all accounts by account type.
     * @param event triggered when user clicks the print all accounts by type button.
     */
    @FXML
    void printAllByType(ActionEvent event) {
        String allAccType= database.printByAccountType();
        output.setText(allAccType);
    }

    /**
     * Method to print all accounts with interest and fees.
     * @param event triggered when user clicks the calculate interest and fees button.
     */
    @FXML
    void printWithInterestFees(ActionEvent event) {
        String allAccInt= database.printFeeAndInterest();
        output.setText(allAccInt);
    }

    /**
     * Method to update all balances
     * @param event triggered when user clicks the apply interest and fees button.
     */
    @FXML
    void updateBalances(ActionEvent event) {
        if(database.getNumAcct()>0) {
            for (Account acc : database.getAccounts()) {
                if (acc != null) {
                    double interest = acc.monthlyInterest();
                    double fee = acc.fee();
                    acc.deposit(interest);
                    acc.balance=acc.balance-fee;
                }
            }
        }
        String updated= database.print();
        output.setText(updated);
    }

    /**
     * Method to withdraw from an account.
     * @param event triggered when user clicks the withdraw button.
     */
    @FXML
    void withdrawFromAcc(ActionEvent event) {
        try{
            String fname = this.fnameDW.getText();
            String lname = this.lnameDW.getText();
            String dob = this.dobDW.getText();
            double amount = Double.parseDouble(this.amount.getText());
            Date birth = new Date(dob);
            Date today = new Date();
            if (!birth.isValid() || birth.compareTo(today) <= 0) {
                throw new NoSuchElementException();
            }
            withdraw(fname,lname,dob,amount);
        } catch(NoSuchElementException e){
            output.setText("Date of Birth invalid");
        } catch(IllegalArgumentException e){
            output.setText("Not a valid amount.");
        }
    }

    /**
     * Helper method to withdraw from an account.
     * @param fname first name
     * @param lname last name
     * @param dob date of birth
     * @param amount amount to be withdrawn
     */
    private void withdraw(String fname, String lname, String dob, double amount) {
        Profile holder = new Profile(fname, lname, new Date(dob));
        try {
            boolean found = false;
            if (amount <= 0) {
                throw new IllegalArgumentException();
            }
            if(type.getSelectedToggle()!=null) {
                if (rButton_C_DW.isSelected()) {
                    Account newAcc = new Checking(holder, amount);
                    for (Account acc : database.getAccounts()) {
                        if (acc != null && acc.equals(newAcc)) found = true;
                    }
                    if (found) {
                        boolean valid = database.withdraw(newAcc);
                        if (!valid) output.setText("Insufficient fund.");
                        else output.setText("Withdraw- balance updated.");
                    } else output.setText(holder.toString() + " " + newAcc.getType() + " is not in the database.");
                }
                if (rButton_CC_DW.isSelected()) {
                    Account newAcc = new CollegeChecking(holder, amount, 0);
                    for (Account acc : database.getAccounts()) {
                        if (acc != null && acc.equals(newAcc)) found = true;
                    }
                    if (found) {
                        boolean valid = database.withdraw(newAcc);
                        if (!valid) output.setText("Insufficient fund.");
                        else output.setText("Withdraw- balance updated.");
                    } else output.setText(holder.toString() + " " + newAcc.getType() + " is not in the database.");
                }
                if (rButton_S_DW.isSelected()) {
                    Account newAcc = new Savings(holder, amount, true);
                    for (Account acc : database.getAccounts()) {
                        if (acc != null && acc.equals(newAcc)) found = true;
                    }
                    if (found) {
                        boolean valid = database.withdraw(newAcc);
                        if (!valid) output.setText("Insufficient fund.");
                        else output.setText("Withdraw- balance updated.");
                    } else output.setText(holder.toString() + " " + newAcc.getType() + " is not in the database.");
                }
                if (rButton_MM_DW.isSelected()) {
                    Account newAcc = new MoneyMarket(holder, amount);
                    for (Account acc : database.getAccounts()) {
                        if (acc != null && acc.equals(newAcc)) found = true;
                    }
                    if (found) {
                        boolean valid = database.withdraw(newAcc);
                        if (!valid) output.setText("Insufficient fund.");
                        else output.setText("Withdraw- balance updated.");
                    } else output.setText(holder.toString() + " " + newAcc.getType() + " is not in the database.");

                }
            } else throw new NoSuchElementException();
        } catch (IllegalArgumentException e) {
            output.setText("Withdraw- amount cannot be 0 or negative.");
        } catch (NoSuchElementException e){
            output.setText("Missing data to withdraw from an account.");
        }
    }

}
