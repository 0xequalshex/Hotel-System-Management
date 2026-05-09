package enums;

public enum ReservationStatus {
	PENDING   ("Pending Payment"), 
    PAID      ("Paid"),            
    CANCELLED ("Cancelled");      

    private final String label;

    ReservationStatus(String label) {this.label = label;}

    public String getLabel() {return this.label;}

    @Override
    public String toString() {return this.label;}
}

