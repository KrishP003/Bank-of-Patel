package bank.accounts;

import bank.personaldata.Profile;

/**
 * Represents an abstract data type of Account.
 * @author Dharmik Patel and Krish Patel
 */
public abstract class Account implements Comparable<Account>{
    public static final int MIN_AGE = 16;
    protected Profile holder;
    protected double  balance;

    /**
     * All subclasses need to override this method,
     * to calculate the interest accumulated.
     * @return The amount of interest accumulated.
     */
    public abstract double monthlyInterest();

    /**
     * All subclasses need to override this method,
     * to calculate the fee that will be charged.
     * @return The amount of fee to be charged.
     */
    public abstract double monthlyFee();

    /**
     * The bare minimum to withdraw money from an account.
     * Subclasses may override this method, if more functionally is needed
     * for specific account types.
     * @param amount the amount to withdraw
     */
    public boolean withdraw(double amount){
        if(balance >= amount){
            balance-=amount;
            return true;
        }
        return false;
    }

    /**
     * Get the holder of the account
     * @return the profile
     */
    public Profile getHolder() {
        return holder;
    }

    /**
     * The bare minimum to deposit money into an account.
     * Subclasses may override this method, if more functionally is needed
     * for specific account types.
     * @param amount the amount to add
     */
    public void deposit(double amount){
        balance+=amount;
    }

    /**
     * Getter method to get the current balance
     * @return The current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Compares two Accounts of any type. If and only if, Account types are
     * same, then will it compare based on Profiles.
     * @param o the object to be compared.
     * @return Negative Value if this < o. 0 if equal, Positive Value if this
     * > o
     */
    @Override
    public int compareTo(Account o) {
        if(this.getClass().getSimpleName().equals(
                o.getClass().getSimpleName())){
            return holder.compareTo(o.holder);
        }
        return this.getClass().getSimpleName().compareTo(
                o.getClass().getSimpleName());
    }

    /**
     * Checks if two accounts are the same based on account type and profile
     * @param o Object to be compared to.
     * @return True if equal, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Account){
            Account account = (Account) o;
            return this.getClass().getSimpleName().equals(
                    account.getClass().getSimpleName())
                    && holder.equals(account.holder);
        }
        return false;
    }
}
