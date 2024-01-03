package bank.accounts;
import bank.personaldata.Campus;
import bank.personaldata.Profile;
import java.text.DecimalFormat;

/**
 * Represents a specific type of Checking Account: College Checking
 * @author Dharmik Patel and Krish Patel
 */
public class CollegeChecking extends Checking{
    private static final double MONTHLY_FEE = 0;
    private final Campus campus;

    /**
     * Allocates and instantiates a College Checking Account, with the
     * given data. Only used to make an account to match closing functions.
     * @param holder Profile of the account holder
     */
    public CollegeChecking(Profile holder){
        super(holder);
        campus = null;
    }

    /**
     * Allocates and instantiates a College Checking Account, with the
     * given data. Only used to make an account with the same profile, and
     * an amountOfChange that indicates how much deposit or withdraw.
     * @param holder Profile of the account holder
     * @param amountOfChange amount to deposit or withdraw
     */
    public CollegeChecking(Profile holder, double amountOfChange){
        super(holder, amountOfChange);
        campus = null;
    }

    /**
     * Allocates and instantiates a College Checking Account, with the
     * given data.
     * @param holder Profile of the account holder
     * @param balance The starting balance in the account
     * @param campus The location of the holder
     */
    public CollegeChecking(Profile holder, double balance, Campus campus){
        super(holder, balance);
        this.campus = campus;
    }

    /**
     * Overrides the monthlyFee() method from super, because a
     * College Checking Account has no monthly fee. MONTHLY_FEE = 0
     * @return returns the monthly fee
     */
    @Override
    public double monthlyFee() {
        return MONTHLY_FEE;
    }

    /**
     * Returns the Account in the format:
     * "College Checking::HOLDER::Balance AMOUNT,.2f::CAMPUS"
     * @return The account in the specified format.
     */
    @Override
    public String toString() {
        return String.format("College Checking::%s::Balance $%s::%s",
                holder,
                new DecimalFormat("#,##0.00").format(balance),
                campus);
    }
}
