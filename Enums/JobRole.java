package enums;

public enum JobRole {
	MANAGER      ("Manager",      ""),
    RECEPTIONIST ("Receptionist", ""),
    CLEANER      ("Cleaner",      ""),
    SECURITY     ("Security",     "");

    private final String label;
    private final String duty;

    JobRole(String label, String duty) {
        this.label = label;
        this.duty  = duty;
    }

    public String getLabel() {
    	return this.label;
    }
    public String getDuty() {
    	return this.duty;
    }

    @Override
    public String toString() {
        return this.label;
    }
}

