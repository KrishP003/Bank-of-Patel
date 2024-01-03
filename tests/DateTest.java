package bank.tests;

import bank.personaldata.Date;

import static org.junit.Assert.*;

/**
 * This JUnit file tests the isValid() date method
 * @author Dharmik Patel and Krish Patel
 */
public class DateTest {
    /**
     * Test 1: Tests if the Date.isValid() method returns false
     * when the day value is negative
     */
    @org.junit.Test
    public void testDaysNegative() {
        Date date = new Date("01/-10/2021");
        assertFalse(date.isValid());
    }

    /**
     * Test 2: Tests if the isValid method returns false
     * when the day value is zero
     */
    @org.junit.Test
    public void testDaysZero() {
        Date date = new Date("01/0/2021");
        assertFalse(date.isValid());
    }

    /**
     * Test 3: Tests if the isValid method returns false
     * when the day value is greater than 31, for
     * months that have 31 days
     */
    @org.junit.Test
    public void testDaysWhenMonthsHave31Days() {
        Date date = new Date("01/32/2021");
        assertFalse(date.isValid());
    }

    /**
     * Test 4: Tests if the isValid method returns false
     * when the day value is greater than 30, for
     * months that have 30 days
     */
    @org.junit.Test
    public void testDaysWhenMonthsHave30Days() {
        Date date = new Date("04/31/2021");
        assertFalse(date.isValid());
    }

    /**
     * Test 5: Tests if the isValid method returns false
     * when the day value is greater than 29, for
     * month value is February and year value is a leap year
     */
    @org.junit.Test
    public void testDaysInFebLeapYear() {
        Date date = new Date("02/30/2024");
        assertFalse(date.isValid());
    }

    /**
     * Test 6: Tests if the isValid method returns false
     * when the day value is greater than 28, for
     * month value is February and year value is NOT a leap year
     */
    @org.junit.Test
    public void testDaysInFebNonLeapYear() {
        Date date = new Date("02/29/2021");
        assertFalse(date.isValid());
    }

    /**
     * Test 7: Tests if the isValid method returns true
     * when the day value is in valid range for
     * months with 31 days
     */
    @org.junit.Test
    public void testDaysInValidRangeFor31DayMonths() {
        Date date = new Date("12/31/1999");
        assertTrue(date.isValid());
    }

    /**
     * Test 8: Tests if the isValid method returns true
     * when the day value is in valid range for
     * months with 30 days
     */
    @org.junit.Test
    public void testDaysInValidRangeFor30DayMonths() {
        Date date = new Date("11/30/1999");
        assertTrue(date.isValid());
    }

    /**
     * Test 9: Tests if the isValid method returns true
     * when the day value is in valid range for
     * month value is February and year value is a leap year
     */
    @org.junit.Test
    public void testDaysInValidRangeForFebLeapYear() {
        Date date = new Date("2/29/2024");
        assertTrue(date.isValid());
    }

    /**
     * Test 10: Tests if the isValid method returns true
     * when the day value is in valid range for
     * month value is February and year value is NOT a leap year
     */
    @org.junit.Test
    public void testDaysInValidRangeForFebNonLeapYear() {
        Date date = new Date("2/28/2021");
        assertTrue(date.isValid());
    }

    /**
     * Test 11: Tests if the isValid method returns false
     * when the month value is 0
     */
    @org.junit.Test
    public void testMonthIsZero() {
        Date date = new Date("0/5/2020");
        assertFalse(date.isValid());
    }

    /**
     * Test 12: Tests if the isValid method returns false
     * when the month value is negative
     */
    @org.junit.Test
    public void testMonthIsNegative() {
        Date date = new Date("-10/5/2020");
        assertFalse(date.isValid());
    }

    /**
     * Test 13: Tests if the isValid method returns false
     * when the month value is greater than 12
     */
    @org.junit.Test
    public void testMonthIsGreaterThan12() {
        Date date = new Date("13/5/2020");
        assertFalse(date.isValid());
    }

    /**
     * Test 14: Tests if the isValid method returns false
     * when the month value is 1 <= month <= 12
     */
    @org.junit.Test
    public void testMonthInValidRange() {
        Date date = new Date("5/5/2020");
        assertTrue(date.isValid());
    }
}