package Enums;

public enum JobRole {
	MANAGER      ("Manager",      "Managing and overseeing all hotel operations"),
    RECEPTIONIST ("Receptionist", "Handling guest reservations and check-ins"),
    CLEANER      ("Cleaner",      "Cleaning and maintaining rooms and facilities"),
    SECURITY     ("Security",     "Monitoring hotel premises and ensuring safety");

    private final String label;
    private final String duty;

    JobRole(String label, String duty) {
        this.label = label;
        this.duty  = duty;
    }

    public String getLabel() {return this.label;}
    public String getDuty() {return this.duty;}

    @Override
    public String toString() {return this.label;}
}

