package bank.accounts;

import bank.personaldata.Month;
import bank.personaldata.Profile;
import java.text.DecimalFormat;

/**
 * Represents a specific type of Account: Checking
 * @author Dharmik Patel and Krish Patel
 */
public class Checking extends Account{
    private static final double ANNUAL_INTEREST_RATE = 1.0;
    private static final double MONTHLY_FEE = 12.00;
    private static final double MONTHLY_FEE_THRESHOLD = 1000.00;

    /**
     * Allocates and instantiates a Checking Account, with the given data.
     * Only used to make an account to match closing functions.
     * @param holder Profile of the account holder
     */
    public Checking(Profile holder){
        this.holder = holder;
    }

    /**
     * Allocates and instantiates a Checking Account, with the given data.
     * @param holder Profile of the account holder
     * @param balance The starting balance in the account
     */
    public Checking(Profile holder, double balance){
        this.holder = holder;
        this.balance = balance;
    }

    /**
     * Calculates the monthly interest.
     * @return The amount of interest, if applied.
     */
    @Override
    public double monthlyInterest() {
        double monthlyInterest =
                ANNUAL_INTEREST_RATE / (Month.values().length - 1);
        return balance * (monthlyInterest / 100);
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
     * Returns the Account in the format:
     * "Checking::HOLDER::Balance AMOUNT,.2f"
     * @return The account in the specified format.
     */
    @Override
    public String toString() {
        return String.format("Checking::%s::Balance $%s", holder,
                new DecimalFormat("#,##0.00").format(balance));
    }
}
