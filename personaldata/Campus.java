package bank.personaldata;

/**
 * Enum Class: Represents all possible campus.
 * @author Dharmik Patel and Krish Patel
 */
public enum Campus {
    NB("NEW_BRUNSWICK"),
    NW("NEWARK"),
    CD("CAMDEN");
    private final String campusFullName;

    /**
     * Makes a ENUM of type Campus.
     * @param campusFullName The full name of the campus.
     */
    Campus(String campusFullName){
        this.campusFullName = campusFullName;
    }

    /**
     * Gets the full campus name.
     * @return A string representation of full campus name.
     */
    @Override
    public String toString() {
        return campusFullName;
    }
}
