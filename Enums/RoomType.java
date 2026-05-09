package enums;

public enum RoomType {

    SINGLE ("Single Room", 1),
    DOUBLE ("Double Room", 2),
    SUITE  ("Suite",       6);

    private final String label;
    private final int defaultCapacity;

    RoomType(String label, int defaultCapacity) {
        this.label           = label;
        this.defaultCapacity = defaultCapacity;
    }

    public String getLabel() {return this.label;}
    public int getDefaultCapacity() {return this.defaultCapacity;}

    @Override
    public String toString() {
        return this.label;
    }
}
