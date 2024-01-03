package bank.personaldata;

/**
 * Enum Class: Represents all possible months.
 * The order of declaration (in time order) determines the functionality of
 * the Month.compareTo method.
 * Imported from Project 1
 * @author Dharmik Patel and Krish Patel
 */
public enum Month {

    JANUARY(Month.LONG_MONTH_DAYS),
    FEBRUARY(Month.DAYS_IN_FEB_NON_LEAP),
    MARCH(Month.LONG_MONTH_DAYS),
    APRIL(Month.SHORT_MONTH_DAYS),
    MAY(Month.LONG_MONTH_DAYS),
    JUNE(Month.SHORT_MONTH_DAYS),
    JULY(Month.LONG_MONTH_DAYS),
    AUGUST(Month.LONG_MONTH_DAYS),
    SEPTEMBER(Month.SHORT_MONTH_DAYS),
    OCTOBER(Month.LONG_MONTH_DAYS),
    NOVEMBER(Month.SHORT_MONTH_DAYS),
    DECEMBER(Month.LONG_MONTH_DAYS),
    NON_A_MONTH(0);
    private final static int LONG_MONTH_DAYS = 31;
    private final static int SHORT_MONTH_DAYS = 30;
    public final static int DAYS_IN_FEB_NON_LEAP = 28;
    public final static int DAYS_IN_FEB_LEAP = 29;
    public final static int MIN_NUM_OF_DAYS = 1;
    public final static int MIN_NUM_OF_MONTH = 1;
    public final static int MAX_NUM_OF_MONTH = 12;
    private final int maxAmountOfDays;

    /**
     * This constructor is used by JVM, makes all the Month enums.
     * @param amountOfDays - max amount of days in a specific month.
     */
    Month(int amountOfDays) {
        this.maxAmountOfDays = amountOfDays;
    }

    /**
     * Getter method to get the maximum amount of days in a month.
     * <p>
     * For Month.FEBRUARY, the default max amount of days is according to
     * a non leap year: DAYS_IN_FEB_NON_LEAP(28).
     * @return the max amount of days.
     */
    public int getMaxAmountOfDays() {
        return maxAmountOfDays;
    }
}
