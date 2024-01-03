package bank.personaldata;
import java.util.Calendar;
/**
 * Done, need to include JUNIT testing
 * Represents a specific instant of a given date.
 * Imported from Project 1
 * @author Dharmik Patel and Krish Patel
 */
public class Date implements Comparable<Date> {
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final Date PRESENT_DATE = Date.getThePresentDay();
    private final int year;
    private final Month month;
    private final int day;

    /**
     * Allocates a Date object and initializes it so that it represents
     * the day specified by the given String.
     * @param date Takes in a String date: MONTH/DAY/YEAR;Example: 09/06/2023
     */
    public Date(String date) {
        String[] tokens = date.split("/");
        this.year = Integer.parseInt(tokens[2]);
        int monthNumber = Integer.parseInt(tokens[0]);
        if (monthNumber <= Month.MAX_NUM_OF_MONTH && monthNumber >=
                Month.MIN_NUM_OF_MONTH) {
            this.month = Month.values()[monthNumber - 1];
        } else {
            this.month = Month.NON_A_MONTH;
        }
        this.day = Integer.parseInt(tokens[1]);
    }

    /**
     * Getter method to get year field.
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Getter method to get month field.
     * @return the month
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Getter method to get day field.
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Template code to format and print the test results to the command line.
     * @param expectedOutput The correct output of this test.
     * @param testDate The date that is being tested right now.
     * @param outputMessage The description of the test.
     */
    private static void testResult(boolean expectedOutput, Date testDate,
                                   String outputMessage) {
        boolean actualOutput = testDate.isValid();
        System.out.println("\t" + outputMessage + " " + testDate);
        if (expectedOutput == actualOutput)
            System.out.printf("\t\t%s | Expected: %s, Got: %s\n",
                    "Test Passed!", expectedOutput, actualOutput);
        else
            System.out.printf("\t\t%s | Expected: %s, Got: %s\n",
                    "Test Failed!", expectedOutput, actualOutput);
    }

    /**
     * Test case 1: checks if days that are in february & are leap years have
     * a day that is:
     * <p>
     * {@code Month.MIN_NUM_OF_DAYS <= day <= Month.DAYS_IN_FEB_LEAP};
     */
    private static void testDaysInFebLeap() {
        Date testDate1 = new Date("2/30/2024");
        Date testDate2 = new Date("2/-1/2024");
        Date testDate3 = new Date("2/29/2024");
        Date testDate4 = new Date("2/1/2024");
        Date testDate5 = new Date("2/15/2024");
        Date testDate6 = new Date("2/0/2024");
        boolean expectedFalse = false;
        boolean expectedTrue = true;
        System.out.println("**Testing Days in Feb with Leap Year");
        testResult(expectedFalse, testDate1, "Days is more than 29");
        testResult(expectedFalse, testDate2, "Day is a negative number");
        testResult(expectedTrue, testDate3, "Day is in valid range");
        testResult(expectedTrue, testDate4, "Day is in valid range");
        testResult(expectedTrue, testDate5, "Day is in valid range");
        testResult(expectedFalse, testDate6, "Day is 0");
    }

    /**
     * Test case 2: checks if days that are in february & are not leap years
     * have a day that is:
     * <p>
     * {@code Month.MIN_NUM_OF_DAYS <= day <= Month.DAYS_IN_FEB_NON_LEAP};
     */
    private static void testDaysInFebNonLeap() {
        Date testDate1 = new Date("2/29/2023");
        Date testDate3 = new Date("2/28/2023");
        Date testDate4 = new Date("2/9/2023");
        Date testDate5 = new Date("2/1/2023");
        Date testDate6 = new Date("2/0/2023");
        Date testDate7 = new Date("2/-190/2023");
        boolean expectedFalse = false;
        boolean expectedTrue = true;
        System.out.println("**Testing Days in Feb with out Leap Year");
        testResult(expectedFalse, testDate1, "Day is more than 28");
        testResult(expectedTrue, testDate3, "Day is in valid range");
        testResult(expectedTrue, testDate4, "Day is in valid range");
        testResult(expectedTrue, testDate5, "Day is in valid range");
        testResult(expectedFalse, testDate6, "Day is 0");
        testResult(expectedFalse, testDate7, "Day is negative");
    }

    /**
     * Test case 3: Checks if months are in range.
     * <p>
     * {@code Month.MIN_NUM_OF_MONTH <= month <= MAX_NUM_OF_MONTH}
     */
    private static void testMonthOutOfRange() {
        Date testDate1 = new Date("-1/15/2023");
        Date testDate2 = new Date("0/15/2023");
        Date testDate3 = new Date("1/15/2024");
        Date testDate4 = new Date("2/15/2024");
        Date testDate5 = new Date("15/15/2024");
        boolean expectedFalse = false;
        boolean expectedTrue = true;
        System.out.println("**Testing Months are in range");
        testResult(expectedFalse, testDate1, "Month is Negative");
        testResult(expectedFalse, testDate2, "Month is zero");
        testResult(expectedTrue, testDate3, "Month is in valid range");
        testResult(expectedTrue, testDate4, "Month is in valid range");
        testResult(expectedFalse, testDate5, "Month is greater than 12");
    }

    /**
     * Used to test if a given date is valid. Handles leap years as well.
     * See test case methods for more specifications.
     * @return True if the date is a valid calendar date. False if it is not.
     */
    public boolean isValid() {
        if (month.equals(Month.FEBRUARY)) {
            if (isLeapYear()) {
                return day >= Month.MIN_NUM_OF_DAYS &&
                        day <= Month.DAYS_IN_FEB_LEAP;
            } else {
                return day >= Month.MIN_NUM_OF_DAYS &&
                        day <= Month.DAYS_IN_FEB_NON_LEAP;
            }
        } else if (month.equals(Month.NON_A_MONTH)) {
            return false;
        } else {
            return day >= Month.MIN_NUM_OF_DAYS &&
                    day <= month.getMaxAmountOfDays();
        }
    }

    /**
     * Used to test if given date occurs before the PRESENT_DATE.
     * @return True if given date occurs before PRESENT_DATE.
     * False if given date occurs on or after PRESENT_DATE.
     */
    public boolean isLessThanPresentDate() {
        return this.compareTo(PRESENT_DATE) <= 0;
    }

    /**
     * Utility method to set the PRESENT_DATE
     * @return Today's Date
     */
    private static Date getThePresentDay(){
        Calendar today = Calendar.getInstance();
        int todayDay = today.get(Calendar.DATE);
        int todayMonth = today.get(Calendar.MONTH)+1;
        int todayYear = today.get(Calendar.YEAR);
        return new Date(String.format("%d/%d/%d", todayMonth, todayDay,
                todayYear));
    }

    /**
     * Checks if the date occurs in a leap year.
     * @return True if year is a leap year. False if year is not a leap year.
     */
    private boolean isLeapYear() {
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                if (year % QUATERCENTENNIAL == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Checks if two dates are equal.
     * @return Returns true if the day, month, and year are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date d2 = (Date) obj;
            return this.day == d2.day && this.month == d2.month &&
                    this.year == d2.year;
        }
        return false;
    }

    /**
     * Overrides the default toString method.
     * @return Returns a string in the format "MONTH/DAY/YEAR"
     */
    @Override
    public String toString() {
        return String.format("%d/%d/%d",
                month.ordinal() + 1, day, year);
    }

    /**
     * Compares 2 dates. Let this instance be "date1".
     * @param date2 the object to be compared.
     * @return Returns -1 if date1 occurs before date2.
     * Returns 0 if date1 is same as date2.
     * Returns +1 if date 1 occurs after date2.
     */
    @Override
    public int compareTo(Date date2) {
        if (this.year > date2.year) {
            return 1;
        } else if (this.year < date2.year) {
            return -1;
        } else {
            if (!(this.month.equals(date2.month))) {
                return this.month.compareTo(date2.month);
            } else {
                return Integer.compare(this.day, date2.day);
            }
        }
    }

    /**
     * testbed main, to test the isValid() method
     * @param args command line args
     */
    public static void main(String[] args) {
        testDaysInFebLeap();
        testDaysInFebNonLeap();
        testMonthOutOfRange();
    }

}
