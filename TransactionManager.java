package bank;

import bank.accounts.*;
import bank.personaldata.Campus;
import bank.personaldata.Date;
import bank.personaldata.Profile;
import java.util.Scanner;

/**
 * This class interfaces with the user via the command line.
 * An instance of this class can process a single command line
 * or multiple command lines at a time.
 * This class handles all errors and exceptions, and displays
 * the appropriate error messages.
 * @author Dharmik Patel and Krish Patel
 */
public class TransactionManager {
    private static final String CMD_OPEN = "O";
    private static final String CMD_CLOSE = "C";
    private static final String CMD_DEPOSIT = "D";
    private static final String CMD_WITHDRAW = "W";
    private static final String CMD_PRINT = "P";
    private static final String CMD_DISPLAY_INT_FEE = "PI";
    private static final String CMD_APPLY_INT_FEE = "UB";
    private static final String CMD_QUIT = "Q";
    private static final String CMD_EMPTY = "";
    private static final int INDEX_OF_CMD_IN_INPUT = 0;
    private static final int INDEX_OF_ACCOUNT_TYPE_IN_INPUT = 1;
    private static final int INDEX_OF_FNAME_IN_INPUT = 2;
    private static final int INDEX_OF_LNAME_IN_INPUT = 3;
    private static final int INDEX_OF_DOB_IN_INPUT = 4;
    private static final int INDEX_OF_BALANCE_IN_INPUT = 5;
    private static final int INDEX_OF_CAMPUS_IN_INPUT = 6;
    private static final int INDEX_OF_LOYALTY_STATUS_IN_INPUT = 6;
    private static final int CLOSING = 0;
    private static final int OPENING = 1;
    private static final int DEPOSITING = 2;
    private static final int WITHDRAWING = 3;
    private AccountDatabase accountDatabase;
    private int currentTask;

    /**
     * This is the run method to make the CLI work.
     * Will run until CMD_QUIT("Q") is inputted
     * The last line of input must end in a "\n" new line character.
     */
    public void run(){
        accountDatabase = new AccountDatabase();
        System.out.println("Transaction Manager is running.");
        Scanner scanner = new Scanner(System.in);
        String currentFullLine;
        do {
            currentFullLine = scanner.nextLine();
            String[] commands = currentFullLine.split("\\s+");
            switch (commands[INDEX_OF_CMD_IN_INPUT]){
                case CMD_OPEN -> open(commands);
                case CMD_CLOSE -> close(commands);
                case CMD_DEPOSIT -> deposit(commands);
                case CMD_WITHDRAW -> withdraw(commands);
                case CMD_PRINT ->
                        accountDatabase.printSorted();
                case CMD_DISPLAY_INT_FEE ->
                        accountDatabase.printFeesAndInterests();
                case CMD_APPLY_INT_FEE ->
                        accountDatabase.printUpdatedBalances();
                case CMD_QUIT -> System.out.println(
                        "Transaction Manager is terminated.");
                case CMD_EMPTY -> {}
                default -> System.out.println("Invalid command!");
            }
        } while (!(currentFullLine.equals(CMD_QUIT)));
        scanner.close();
    }

    /**
     * Opens an account from data from command line.
     * Checks for restrictions and errors.
     * Only the format bellow is allowed
     * Spacing does not matter, and the character case
     * or the data tokens do not matter.
     * O  C John Doe 2/19/2000 599.99
     * O  CC Jane Doe 10/1/2000 999.99 0
     * O  S april March 1/15/1987 1500 1
     * O  MM Roy Brooks 10/31/1979 2909.10
     * Handles:
     *      InputMismatchException,
     *      NumberFormatException,
     *      NoSuchElementException,
     *      invalid dob,
     *      age restrictions,
     *      type of account restrictions,
     *      invalid campus codes,
     *      invalid amounts.
     * @param commands Data to make an account from.
     */
    private void open(String[] commands) {
        currentTask = OPENING;
        AccountTypeCommand accountType = getAndCheckAccountType(commands);
        if (accountType == null) return;

        Account accountToAdd = makeAccount(commands, accountType);
        if (accountToAdd == null) return;

        if (accountTypeRestrictionCheck(accountType, accountToAdd)) return;
        if(!accountDatabase.open(accountToAdd)) {
            System.out.printf("%s(%s) is already in the database.\n",
                    accountToAdd.getHolder(), accountType.name());
            return;
        }
        System.out.printf("%s(%s) opened.\n",
                accountToAdd.getHolder(),
                accountType.name());
    }

    /**
     * Closes an account from bank, if it exists.
     * Only works with the format bellow.
     * Spacing does not matter, and the character case
     * or the data tokens do not matter.
     * C ACCOUNT_TYPE FNAME LNAME DOB
     * Handles errors as well.
     * @param commands Data to identify what account to close
     */
    private void close(String[] commands){
        currentTask = CLOSING;
        AccountTypeCommand accountType = getAndCheckAccountType(commands);
        if (accountType == null) return;

        Account accountToClose = makeAccount(commands, accountType);
        if (accountToClose == null) return;

        if(!accountDatabase.close(accountToClose)){
            System.out.printf("%s(%s) is not in the database.\n",
                    accountToClose.getHolder(), accountType.name());
            return;
        }
        System.out.printf("%s(%s) has been closed.\n",
                accountToClose.getHolder(),
                accountType.name());
    }

    /**
     * This method deposits money into the given account.
     * @param commands CLI Args
     */
    private void deposit(String[] commands){
        currentTask = DEPOSITING;
        AccountTypeCommand accountType = getAndCheckAccountType(commands);
        if (accountType == null) return;

        Account accountWithAmountOfMoneyToDeposit =
                makeAccount(commands, accountType);
        if (accountWithAmountOfMoneyToDeposit == null) return;

        boolean isAccountOpenBank = accountDatabase.contains(
                accountWithAmountOfMoneyToDeposit);
        if(isAccountOpenBank){
            accountDatabase.deposit(accountWithAmountOfMoneyToDeposit);
            System.out.printf("%s(%s) Deposit - balance updated.\n",
                    accountWithAmountOfMoneyToDeposit.getHolder(),
                    accountType.name());
        }
        else {
            System.out.printf("%s(%s) is not in the database.\n",
                    accountWithAmountOfMoneyToDeposit.getHolder(),
                    accountType.name());
        }
    }

    /**
     * This method withdraws money from the account.
     * @param commands CLI Args
     */
    private void withdraw(String[] commands){
        currentTask = WITHDRAWING;
        AccountTypeCommand accountType = getAndCheckAccountType(commands);
        if (accountType == null) return;

        Account accountWithAmountOfMoneyToWithdraw =
                makeAccount(commands, accountType);
        if (accountWithAmountOfMoneyToWithdraw == null) return;
        boolean isAccountOpenBank = accountDatabase.contains(
                accountWithAmountOfMoneyToWithdraw);
        if(isAccountOpenBank)
            if(accountDatabase.withdraw(accountWithAmountOfMoneyToWithdraw))
                System.out.printf("%s(%s) Withdraw - balance updated.\n",
                        accountWithAmountOfMoneyToWithdraw.getHolder(),
                        accountType.name());
            else
                System.out.printf("%s(%s) Withdraw - insufficient fund.\n",
                        accountWithAmountOfMoneyToWithdraw.getHolder(),
                        accountType.name());
        else
            System.out.printf("%s(%s) is not in the database.\n",
                    accountWithAmountOfMoneyToWithdraw.getHolder(),
                    accountType.name());
    }

    /**
     * The method makes sure a person that a person can not hold a
     * checking and college checking account at the same time.
     * @param accountType C or CC
     * @param accountToAdd The account to check on
     * @return True if good, false if not
     */
    private boolean accountTypeRestrictionCheck(
            AccountTypeCommand accountType, Account accountToAdd) {
        if(accountType.equals(AccountTypeCommand.CC)
                && accountDatabase.contains(
                accountToAdd.getHolder(),
                AccountTypeCommand.C)){
            System.out.printf("%s(%s) is already in the database.\n",
                    accountToAdd.getHolder(), AccountTypeCommand.CC.name());
            return true;
        }
        if(accountType.equals(AccountTypeCommand.C)
                && accountDatabase.contains(
                accountToAdd.getHolder(),
                AccountTypeCommand.CC)){
            System.out.printf("%s(%s) is already in the database.\n",
                    accountToAdd.getHolder(), AccountTypeCommand.C.name());
            return true;
        }
        return false;
    }

    /**
     * THis method will make an valid account
     * @param commands CLI Args
     * @param accountType The type of account
     * @return returns a valid account, null if not valid
     */
    private Account makeAccount(
            String[] commands, AccountTypeCommand accountType) {
        Account account = null;
        switch (accountType){
            case C -> account = makeCheckingAccount(commands);
            case CC -> account = makeCollegeCheckingAccount(commands);
            case S -> account = makeSavingsAccount(commands);
            case MM -> account = makeMoneyMarketAccount(commands);
        }
        return account;
    }

    /**
     * This method makes a checking account.
     * @param commands CLI Args
     * @return A valid checking account or null
     */
    private Checking makeCheckingAccount(String[] commands){
        Profile profile = makeAndCheckProfile(commands);
        if(profile == null) return null;

        if(currentTask == CLOSING){
            return new Checking(profile);
        }
        else{
            double balanceAmount = getAndCheckAmount(commands);
            if(balanceAmount == -1) return null;
            return new Checking(profile, balanceAmount);
        }
    }

    /**
     * This method makes a college checking account
     * @param commands CLI Args
     * @return a valid college checking account or null
     */
    private CollegeChecking makeCollegeCheckingAccount(String[] commands){
        Profile profile = makeAndCheckProfile(commands);
        if(profile == null) return null;
        if(currentTask == CLOSING){
            return new CollegeChecking(profile);
        } else {
            double balanceAmount = getAndCheckAmount(commands);
            if (balanceAmount == -1) return null;
            if(currentTask == OPENING){
                if(profile.getAge() >= MoneyMarket.MAX_AGE){
                    System.out.printf("DOB invalid: %s over 24.\n",
                            profile.getDOB());
                    return null;
                }
                Campus campus = getAndCheckCampus(commands);
                if (campus == null) return null;
                return new CollegeChecking(profile, balanceAmount, campus);
            }
            return new CollegeChecking(profile, balanceAmount);
        }
    }

    /**
     * This method makes a valid savings account
     * @param commands CLI Args
     * @return a savings account or null
     */
    private Savings makeSavingsAccount(String[] commands){
        Profile profile = makeAndCheckProfile(commands);
        if(profile == null) return null;

        if(currentTask == CLOSING){
            return new Savings(profile);
        } else {
            double balanceAmount = getAndCheckAmount(commands);
            if (balanceAmount == -1) return null;
            if(currentTask == OPENING){
                int isLoyal = getAndCheckLoyalty(commands);
                if (isLoyal == -1) return null;
                return new Savings(profile, balanceAmount, isLoyal == 1);
            }
            return new Savings(profile, balanceAmount);
        }
    }

    /**
     * This method makes a valid money market account
     * @param commands CLI Args
     * @return a money market account or null
     */
    private MoneyMarket makeMoneyMarketAccount(String[] commands){
        Profile profile = makeAndCheckProfile(commands);
        if(profile == null) return null;

        if(currentTask == CLOSING){
            return new MoneyMarket(profile);
        }else {
            double balanceAmount = getAndCheckAmount(commands);
            if (balanceAmount == -1) return null;
            if(currentTask == OPENING){
                if (balanceAmount < MoneyMarket.ACCOUNT_THRESHOLD) {
                    System.out.printf("Minimum of $%.0f to open a Money " +
                                    "Market account.\n",
                            (MoneyMarket.ACCOUNT_THRESHOLD));
                    return null;
                }
            }
            return new MoneyMarket(profile, balanceAmount);
        }
    }

    /**
     * This method makes a valid Profile
     * @param commands CLI Args
     * @return a valid profile or null
     */
    private Profile makeAndCheckProfile(String[] commands){
        String fname, lname, dob;
        try{
            fname = commands[INDEX_OF_FNAME_IN_INPUT];
            lname = commands[INDEX_OF_LNAME_IN_INPUT];
            dob = commands[INDEX_OF_DOB_IN_INPUT];
        }catch (ArrayIndexOutOfBoundsException err){
            missingDataOutput();
            return null;
        }
        Date date = getAndCheckDOB(dob);
        if(date == null) return null;

        Profile profileToReturn = new Profile(fname, lname, date);
        if(profileToReturn.getAge() < Account.MIN_AGE){
            System.out.printf("DOB invalid: %s under 16.\n",
                    profileToReturn.getDOB());
            return null;
        }
        return profileToReturn;
    }

    /**
     * Utility method to make and validate the DOB.
     * @param date date start time in format "MONTH/DAY/YEAR"
     * @return returns a valid Date. Null if not valid
     */
    private Date getAndCheckDOB(String date) {
        Date dob = new Date(date);
        if(!(dob.isValid())){
            System.out.printf("DOB invalid: %s not a valid calendar date!\n",
                    date);
            return null;
        } else if (!(dob.isLessThanPresentDate())) {
            System.out.printf("DOB invalid: %s cannot be today or a " +
                            "future day.\n", date);
            return null;
        }
        return dob;
    }

    /**
     * Method to make account type
     * @param commands CLI Args
     * @return A valid AccountType or null
     */
    private AccountTypeCommand getAndCheckAccountType(String[] commands) {
        AccountTypeCommand accountType;
        try {
            accountType = AccountTypeCommand.valueOf(
                    commands[INDEX_OF_ACCOUNT_TYPE_IN_INPUT]);
        }catch (IndexOutOfBoundsException | IllegalArgumentException err) {
            missingDataOutput();
            return null;
        }
        return accountType;
    }

    /**
     * Method to make Double amount from string
     * @param commands CLI Args
     * @return A valid amount or null
     */
    private double getAndCheckAmount(String[] commands){
        double balanceAmount;
        try {
            balanceAmount = Double.parseDouble(
                    commands[TransactionManager.INDEX_OF_BALANCE_IN_INPUT]);
        } catch (ArrayIndexOutOfBoundsException err){
            missingDataOutput();
            return -1;
        } catch (NumberFormatException err){
            System.out.println("Not a valid amount.");
            return -1;
        }
        if(balanceAmount <= 0) {
            if(currentTask == OPENING)
                System.out.println(
                        "Initial deposit cannot be 0 or negative.");
            else if(currentTask == DEPOSITING)
                System.out.println(
                        "Deposit - amount cannot be 0 or negative.");
            else if(currentTask == WITHDRAWING)
                System.out.println(
                        "Withdraw - amount cannot be 0 or negative.");
            return -1;
        }
        return balanceAmount;
    }

    /**
     * Method to make a valid Campus
     * @param commands CLI Args
     * @return A valid Campus or null
     */
    private Campus getAndCheckCampus(String[] commands){
        Campus campus;
        try{
            int campusIndex = Integer.parseInt(
                    commands[INDEX_OF_CAMPUS_IN_INPUT]);
            if(campusIndex >= Campus.values().length){
                System.out.println("Invalid campus code.");
                return null;
            }
            campus = Campus.values()[campusIndex];
        } catch (IndexOutOfBoundsException | NumberFormatException err){
            missingDataOutput();
            return null;
        }
        return campus;
    }

    /**
     * Method to parse the CLI to get a valid loyalty status
     * @param commands CLI Args
     * @return A valid amloyalty status(0 or 1) ount or -1
     */
    private int getAndCheckLoyalty(String[] commands){
        int isLoyal = -1;
        try{
            isLoyal = Integer.parseInt(
                    commands[INDEX_OF_LOYALTY_STATUS_IN_INPUT]);
        } catch (IndexOutOfBoundsException | NumberFormatException err){
            missingDataOutput();
        }
        if(!(isLoyal == 0 || isLoyal == 1)){
            missingDataOutput();
            return -1;
        }
        return isLoyal;
    }

    /**
     * A utility method to just print an error.
     */
    private void missingDataOutput(){
        if(currentTask == OPENING)
            System.out.println("Missing data for opening an account.");
        else if(currentTask == CLOSING)
            System.out.println("Missing data for closing an account.");
        else
            System.out.println("Missing data for making an account.");
    }
}
