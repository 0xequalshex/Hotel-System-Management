package enums;

public enum RoomType {

    SINGLE_ROOM("Single Room", 1),
    DOUBLE_ROOM("Double Room", 2),
    FAMILY_SUITE("Family Suite", 5),
    ROYAL_SUITE("Royal Suite", 8);

    private String title;
    private int capacity;

    RoomType(String title, int capacity) {

        this.title = title;
        this.capacity = capacity;
    }

    public String getTitle() {
        return title;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return title;
    }
}