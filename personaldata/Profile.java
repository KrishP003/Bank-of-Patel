package bank.personaldata;

/**
 * Represents a specific person who has an account with the bank.
 * @author Dharmik Patel and Krish Patel
 */
public class Profile implements Comparable<Profile>{
    private final String fname;
    private final String lname;
    private final Date dob;

    /**
     * Instantiates a profile, with the given first name, last name, and dob.
     * @param fname First name
     * @param lname Last name
     * @param dob Date of Birth
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Gets the DOB of the person
     * @return DOB
     */
    public Date getDOB() {
        return dob;
    }

    /**
     * Calculates the age of the person
     * based on Date.PRESENT_DATE.
     * @return The age of the person
     */
    public int getAge(){
        int age = Date.PRESENT_DATE.getYear() - dob.getYear();
        Date newDate = new Date(String.format("%s/%s/%s",
                dob.getMonth().ordinal()+1, dob.getDay(),
                Date.PRESENT_DATE.getYear()));
        if(!(newDate.isLessThanPresentDate()))
            age--;
        return age;
    }

    /**
     * Checks if two profiles are the same, based on name and date of birth
     * @param obj Object to compare to.
     * @return True if equal, false if not.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Profile){
            Profile profile = (Profile) obj;
            return fname.equalsIgnoreCase(profile.fname) &&
                    lname.equalsIgnoreCase(profile.lname) &&
                    dob.equals(profile.dob);
        }
        return false;
    }

    /**
     * Compares two Profiles, in the order: last name, first name,
     * and date of birth.
     * @param o the object to be compared.
     * @return Negative Value if this < o, 0 if equal, Positive value if
     * this > o
     */
    @Override
    public int compareTo(Profile o) {
        if(lname.compareTo(o.lname) == 0){
            if(fname.compareTo(o.fname) == 0){
                return dob.compareTo(o.dob);
            }
            return fname.compareTo(o.fname);
        }
        return lname.compareTo(o.lname);
    }

    /**
     * Returns the Profile as a string in the format: "fname lname dob"
     * @return The profile in the specified format.
     */
    @Override
    public String toString() {
        return String.format("%s %s %s", fname, lname, dob);
    }
}
