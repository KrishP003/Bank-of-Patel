package bank;

import bank.accounts.Account;
import bank.accounts.AccountTypeCommand;
import bank.accounts.MoneyMarket;
import bank.personaldata.Profile;

import java.text.DecimalFormat;

/**
 * This array based implementation make a bank
 * @author Dharmik Patel and Krish Patel
 */
public class AccountDatabase {
    private final static int NOT_FOUND = -1;
    private final static int GROWTH_AMOUNT = 4;
    private final static int NO_ACCOUNTS = 0;
    private Account[] accounts; //the array holding the list of accounts
    //the number of accounts in the list. does not have to equal accounts.length
    private int numAccounts;

    /**
     * Instantiates a AccountDatabase object with an Account[] array
     * with initial capacity of GROWTH_AMOUNT(4) and numEvents to 0;
     */
    public AccountDatabase() {
        accounts = new Account[GROWTH_AMOUNT];
        numAccounts = 0;
    }

    /**
     * Finds an account in the database based on the Profile and Account type.
     * @param account Account to find
     * @return The index of the account or NOT_FOUND(-1);
     */
    private int find(Account account) {
        for (int i = 0; i < numAccounts; i++) {
            if (accounts[i] != null && accounts[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * This helper method, grows the array by GROWTH_AMOUNT(4) everytime, the
     * array is filled up. This performs a manual Array.copy()
     */
    private void grow(){
        Account[] arrTemp = new Account[accounts.length + GROWTH_AMOUNT];
        for (int i = 0; i < accounts.length; i++) {
            arrTemp[i] = accounts[i];
        }
        accounts = arrTemp;
    }

    /**
     * This helper method is used while sorting the array.
     * Shifts the array left from i to the end. The caller needs to
     * save the value of A[i] because it will be overwritten.
     * @param position The index to start to shift from.
     */
    private void leftShiftArray(int position) {
        for (int i = position; i < numAccounts - 1; i++) {
            accounts[i] = accounts[i + 1];
        }
        numAccounts--;
        accounts[numAccounts] = null;
    }

    /**
     * Utility method to check if an account exists in the bank.
     * Depends on Profile and account type.
     * @param account Account to find
     * @return True if account is in bank, false if its not.
     */
    public boolean contains(Account account){
        return find(account) != NOT_FOUND;
    }

    /**
     * Utility method to check if an account exists in the bank, with a
     * certain profile holder, and a certain accountType.
     * @param profile the profile of the person
     * @param accountType the account type
     * @return True if it is, false if its not
     */
    public boolean contains(Profile profile, AccountTypeCommand accountType){
        for (int i = 0; i < numAccounts; i++) {
            if (accounts[i] != null
                    && accounts[i].getHolder().equals(profile)
                    && accounts[i].getClass().getSimpleName().equals(accountType.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Opens an account in the bank if the same account, does
     * not already exist in the bank. Other errors and restrictions
     * must be checked by the caller.
     * Grows the array if needed.
     * @param account Account to open
     * @return True is the given account is opened, or not.
     */
    public boolean open(Account account){
        if (!(contains(account))) {
            try {
                accounts[numAccounts] = account;
                numAccounts++;
            } catch (ArrayIndexOutOfBoundsException e) {
                grow();
                accounts[numAccounts] = account;
                numAccounts++;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Closes the account if the account exists within the bank.
     * @param account Account to close
     * @return True if account is closed, false if not.
     */
    public boolean close(Account account){
        int positionToRemove = find(account);
        if (positionToRemove == NOT_FOUND) {
            return false;
        } else {
            leftShiftArray(positionToRemove);
            return true;
        }
    }

    /**
     * Withdraws money from the account, if they are in bank.
     * Called must check if account
     * is open in bank or not.
     * @param account Same Account type, profile, and the balance is the amount to withdraw
     * @return True if successful transaction, false if not.
     */
    public boolean withdraw(Account account){
        Account accountToWithdrawFrom = accounts[find(account)];
        return accountToWithdrawFrom.withdraw(account.getBalance());
    }
    /**
     * Adds money to the account, if they are in bank.
     * Called must check if account
     * is open in bank or not.
     * @param account Same Account type, profile, and the balance is the amount to deposit
     */
    public void deposit(Account account){
        Account accountToDepositTo = accounts[find(account)];
        accountToDepositTo.deposit(account.getBalance());
    }

    /**
     * Utility method to perform inplace sorting of the accounts.
     */
    private void sort(){
        for (int i = 0; i < numAccounts - 1; i++) {
            int indexOfEarliestEvent = i;
            for (int j = i + 1; j < numAccounts; j++) {
                if (accounts[j].compareTo(
                        accounts[indexOfEarliestEvent]) < 0) {
                    indexOfEarliestEvent = j;
                }
            }
            swap(i, indexOfEarliestEvent);
        }
    }

    /**
     * Utility method used while sorting to swap two accounts in the bank system
     * array.
     * @param i Index of 1st Event to swap
     * @param j Index of 2nd Event to swap
     */
    private void swap(int i, int j) {
        Account temp = accounts[j];
        accounts[j] = accounts[i];
        accounts[i] = temp;
    }

    /**
     * Print sorted array by account type and profile
     * while displaying Fees and Interests.
     */
    public void printFeesAndInterests(){
        if(numAccounts == NO_ACCOUNTS){
            System.out.println("Account Database is empty!");
            return;
        }
        sort();
        System.out.println("\n*list of accounts with fee and monthly interest");
        for (int i = 0; i < numAccounts; i++) {
            System.out.printf("%s::fee $%.2f::monthly interest $%s\n",
                    accounts[i],
                    accounts[i].monthlyFee(),
                    new DecimalFormat("#,##0.00").format(accounts[i].monthlyInterest()));
        }
        System.out.println("*end of list.\n");
    }

    /**
     * Print sorted array by account type and profile
     * with APPLIED Fees and Interests on balance.
     */
    public void printUpdatedBalances(){
        if(numAccounts == NO_ACCOUNTS){
            System.out.println("Account Database is empty!");
            return;
        }
        sort();
        System.out.println("\n*list of accounts with fees and interests applied.");
        for (int i = 0; i < numAccounts; i++) {
            accounts[i].deposit(accounts[i].monthlyInterest());

            accounts[i].withdraw(accounts[i].monthlyFee());

            if(accounts[i] instanceof MoneyMarket)
                ((MoneyMarket) accounts[i]).resetWithdrawal();

            System.out.println(accounts[i]);
        }
        System.out.println("*end of list.\n");
    }
    /**
     * Print sorted array by account type and profile
     */
    public void printSorted() {
        if(numAccounts == NO_ACCOUNTS){
            System.out.println("Account Database is empty!");
            return;
        }
        sort();
        System.out.println("\n*Accounts sorted by account type and profile.");
        for (int i = 0; i < numAccounts; i++) {
            System.out.println(accounts[i]);
        }
        System.out.println("*end of list.\n");
    }
}
