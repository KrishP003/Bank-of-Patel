package bank.tests;

import bank.AccountDatabase;
import bank.accounts.Account;
import bank.accounts.CollegeChecking;
import bank.accounts.Savings;
import bank.personaldata.Campus;
import bank.personaldata.Date;
import bank.personaldata.Profile;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This JUnit file tests the close functionalities  of the bank
 * @author Dharmik Patel and Krish Patel
 */
public class AccountDatabaseTest {

    /**
     * Test 1: Test if the AccountDatabase.close() method
     * returns false when removing account but the database is empty.
     */
    @Test
    public void testWhenAccountDatabaseIsEmpty() {
        Date dob = new Date("01/10/2002");
        Profile profile = new Profile("Dharmik", "Patel", dob);
        Account account = new Savings(profile, 300, true);
        AccountDatabase accountDatabase = new AccountDatabase();
        assertFalse(accountDatabase.close(account));
    }

    /**
     * Test 2: Test if the AccountDatabase.close() method
     * returns false when removing account but that is not in database.
     */
    @Test
    public void testWhenAccountNotInDatabase() {
        Date dob = new Date("01/10/2002");
        Profile profile = new Profile("Dharmik", "Patel", dob);
        Account account = new Savings(profile, 300, true);
        Date dob2 = new Date("01/11/2002");
        Profile profile2 = new Profile("Mike", "Ross", dob2);
        Account accountToRemove =
                new CollegeChecking(profile2, 6000, Campus.CD);
        AccountDatabase accountDatabase = new AccountDatabase();
        accountDatabase.open(account);
        assertFalse(accountDatabase.close(accountToRemove));
    }

    /**
     * Test 3: Test if the AccountDatabase.close() method
     * returns false when removing account that is already removed
     * from the database. (Double remove).
     */
    @Test
    public void testWhenAccountIsDoubleRemoved() {
        Date dob = new Date("01/10/2002");
        Profile profile = new Profile("Dharmik", "Patel", dob);
        Account account = new Savings(profile, 300, true);
        Date dob2 = new Date("01/11/2002");
        Profile profile2 = new Profile("Mike", "Ross", dob2);
        Account account2 = new CollegeChecking(profile2, 6000, Campus.CD);
        AccountDatabase accountDatabase = new AccountDatabase();
        accountDatabase.open(account);
        accountDatabase.open(account2);
        accountDatabase.close(account);
        assertFalse(accountDatabase.close(account));
    }

    /**
     * Test 4: Test if the AccountDatabase.close() method
     * returns true when it finds and removes a account that
     * is open within the database
     */
    @Test
    public void testWhenAccountInDatabase() {
        Date dob = new Date("01/10/2002");
        Profile profile = new Profile("Dharmik", "Patel", dob);
        Account account = new Savings(profile, 300, true);
        AccountDatabase accountDatabase = new AccountDatabase();
        accountDatabase.open(account);
        assertTrue(accountDatabase.close(account));
    }
}