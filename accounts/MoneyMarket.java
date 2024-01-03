package bank.accounts;

import bank.personaldata.Month;
import bank.personaldata.Profile;
import java.text.DecimalFormat;

/**
 * Represents a specific type of Saving Account: Money Market
 * @author Dharmik Patel and Krish Patel
 */
public class MoneyMarket extends Savings{
    private static final double ANNUAL_INTEREST_RATE_LOYAL = 4.75;
    private static final double ANNUAL_INTEREST_RATE_NOT_LOYAL = 4.50;
    private static final double MONTHLY_FEE = 25.0;
    public static final double ACCOUNT_THRESHOLD = 2000;
    private static final double WITHDRAW_THRESHOLD = 3;
    private static final double FEE_TOO_MANY_WITHDRAWALS = 10;
    public static final int MAX_AGE = 24;
    private int numOfWithdrawals;
    /**
     * Allocates and instantiates a MoneyMarket Account, with the given data.
     * Only used to make an account to match closing functions.
     * @param holder Profile of the account holder
     */
    public MoneyMarket(Profile holder){
        super(holder);
    }

    /**
     * Allocates and instantiates a Money Market Account, with the given data.
     * Loyalty is True by default bc balance >= 2000;
     * Sets the number of withdrawals to 0.
     * @param holder Profile of the account holder
     * @param balance The starting balance in the account
     */
    public MoneyMarket(Profile holder, double balance){
        super(holder, balance, true);
        numOfWithdrawals = 0;
    }

    /**
     * Used to reset the amount of withdrawals to zero
     * when fees and interests are applied.
     */
    public void resetWithdrawal() {
        this.numOfWithdrawals = 0;
    }

    /**
     * Calculates the monthly interest based on the isLoyal.
     * @return The amount of interest, if applied.
     */
    @Override
    public double monthlyInterest() {
        double monthlyInterest;
        if(isLoyal)
            monthlyInterest = ANNUAL_INTEREST_RATE_LOYAL /
                    (Month.values().length - 1);
        else
            monthlyInterest = ANNUAL_INTEREST_RATE_NOT_LOYAL /
                    (Month.values().length - 1);
        return (balance * monthlyInterest / 100);
    }

    /**
     * Calculates the fee, based on balance < ACCOUNT_THRESHOLD,
     * and number of withdrawals > WITHDRAW_THRESHOLD.
     * @return The amount of fee, if charged.
     */
    @Override
    public double monthlyFee() {
        double totalFee = 0;
        if(balance < ACCOUNT_THRESHOLD)
            totalFee += MONTHLY_FEE;
        if(numOfWithdrawals > WITHDRAW_THRESHOLD)
            totalFee += FEE_TOO_MANY_WITHDRAWALS;
        return totalFee;
    }

    /**
     * Called by the AccountDatabase.withdraw() only. Updates withdrawal
     * counter and loyalty status based on account balance.
     * @param amount amount to deduct
     * @return True if able to withdraw, false if not.
     */
    @Override
    public boolean withdraw(double amount) {
        if(balance >= amount){
            balance-=amount;
            numOfWithdrawals++;
            isLoyal = balance >= ACCOUNT_THRESHOLD;
            return true;
        }
        return false;
    }

    /**
     * Called by the AccountDatabase.deposit() only.
     * Updates loyalty status based on account balance.
     * @param amount amount to add
     */
    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        isLoyal = balance >= ACCOUNT_THRESHOLD;
    }

    /**
     * Returns the Account in the format
     * "Money Market::HOLDER::Balance AMOUNT,.2f::ISLOYAL::withdrawal:
     * WITHDRAWALS"
     * @return The account in the specified format.
     */
    @Override
    public String toString() {
        return String.format(
                "Money Market::Savings::%s::Balance $%s::%swithdrawal: %d",
                holder,
                new DecimalFormat("#,##0.00").format(balance),
                isLoyal? "is loyal::" : "",
                numOfWithdrawals);
    }
}
