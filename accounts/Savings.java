package bank.accounts;

import bank.personaldata.Month;
import bank.personaldata.Profile;
import java.text.DecimalFormat;


/**
 * Represents a specific type of Account: Savings
 * @author Dharmik Patel and Krish Patel
 */
public class Savings extends Account{
    private static final double ANNUAL_INTEREST_RATE_LOYAL = 4.25;
    private static final double ANNUAL_INTEREST_RATE_NOT_LOYAL = 4.00;
    private static final double MONTHLY_FEE = 25.00;
    private static final double MONTHLY_FEE_THRESHOLD = 500.00;
    protected boolean isLoyal;

    /**
     * Allocates and instantiates a Savings Account, with the given data.
     * Only used to make an account to match closing functions.
     * @param holder Profile of the account holder
     */
    public Savings(Profile holder){
        this.holder = holder;
    }

    /**
     * Allocates and instantiates a Savings Account, with the given data.
     * Only used to make an account with the same profile, and
     * an amountOfChange that indicates how much deposit or withdraw.
     * @param holder Profile of the account holder
     * @param amountOfChange amount to deposit or withdraw
     */
    public Savings(Profile holder, double amountOfChange){
        this.holder = holder;
        this.balance = amountOfChange;
    }
    /**
     * Allocates and instantiates a Savings Account, with the given data.
     * @param holder Profile of the account holder
     * @param balance The starting balance in the account
     * @param isLoyal The loyalty status of the holder
     */
    public Savings(Profile holder, double balance, boolean isLoyal){
        this.holder = holder;
        this.balance = balance;
        this.isLoyal = isLoyal;
    }

    /**
     * Calculates the monthly interest based on the isLoyal.
     * @return The amount of interest, if applied.
     */
    @Override
    public double monthlyInterest() {
        double monthlyInterest = 0;
        if(isLoyal)
            monthlyInterest = ANNUAL_INTEREST_RATE_LOYAL /
                    (Month.values().length - 1);
        else
            monthlyInterest = ANNUAL_INTEREST_RATE_NOT_LOYAL /
                    (Month.values().length - 1);
        return (balance * monthlyInterest / 100);
    }

    /**
     * Calculates the fee, based on balance < MONTHLY_FEE_THRESHOLD.
     * @return The amount of fee, if charged.
     */
    @Override
    public double monthlyFee() {
        if(balance < MONTHLY_FEE_THRESHOLD)
            return MONTHLY_FEE;
        return 0;
    }
    /**
     * Returns the Account in the format
     * "Savings::HOLDER::Balance AMOUNT,.2f::ISLOYAL"
     * @return The account in the specified format.
     */
    @Override
    public String toString() {
        return String.format(
                "Savings::%s::Balance $%s%s",
                holder,
                new DecimalFormat("#,##0.00").format(balance),
                isLoyal ? "::is loyal" : "");
    }
}
