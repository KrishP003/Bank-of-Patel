package bank.accounts;

/**
 * This utility enum class represents all possible account types
 * @author Dharmik Patel and Krish Patel
 */
public enum AccountTypeCommand {
    C(Checking.class.getSimpleName()),
    CC(CollegeChecking.class.getSimpleName()),
    S(Savings.class.getSimpleName()),
    MM(MoneyMarket.class.getSimpleName());
    private final String fullName;

    /**
     * This constructor is used by JVM, makes all the AccountTypeCommand
     * enums.
     * @param fullName the full name of the account type
     */
    AccountTypeCommand(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Overrides toString method to return an account type's full name.
     * @return the account type's full name
     */
    @Override
    public String toString() {
        return fullName;
    }
}
