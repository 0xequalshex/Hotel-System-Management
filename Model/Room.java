package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Enums.RoomType;

public class Room {

    private int id;
    private int floor;
    private int capacity;
    private RoomType type;
    private String description;
    private double price;

    private List<String> reservedDates;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Room() {
        reservedDates = new ArrayList<>();
    }

    public Room(int id, int floor, int capacity, RoomType type, String description, double price) {
        this.id = id;
        this.floor = floor;
        this.capacity = capacity;
        this.type = type;
        this.description = description;
        this.price = price;
        this.reservedDates = new ArrayList<>();
    }
    
    public void setFloor(int floor) {this.floor = floor;}
    public void setCapacity(int capacity) {this.capacity = capacity;}
    public void setType(RoomType type) {this.type = type;}
    public void setDescription(String description) {this.description = description;}
    public void setPrice(double price) {this.price = price;}

    public int getId() {return id;}
    public int getFloor() {return floor;}
    public int getCapacity() {return capacity;}
    public RoomType getType() {return type;}
    public String getDescription() {return description;}
    public double getPrice() {return price;}
    public List<String> getReservedDates() {return reservedDates;}


    public void reserve(LocalDate checkIn, LocalDate checkOut) {
        LocalDate current = checkIn;
        while (current.isBefore(checkOut)) {
            reservedDates.add(current.format(DATE_FORMAT));
            current = current.plusDays(1);
        }
    }

    public void unreserve(LocalDate checkIn, LocalDate checkOut) {
        LocalDate current = checkIn;
        while (current.isBefore(checkOut)) {
            reservedDates.remove(current.format(DATE_FORMAT));
            current = current.plusDays(1);
        }
    }

    public boolean isReserved(LocalDate checkIn, LocalDate checkOut) {
        LocalDate current = checkIn;
        while (current.isBefore(checkOut)) {
            if (reservedDates.contains(current.format(DATE_FORMAT))) {
                return true;
            }
            current = current.plusDays(1);
        }
        return false;
    }

    public void print() {
        System.out.println("Room ID      : " + id);
        System.out.println("Floor Number : " + floor);
        System.out.println("Capacity     : " + capacity);
        System.out.println("Room Type    : " + type.name());
        System.out.println("Description  : " + description);
        System.out.println("Price/Night  : $" + String.format("%.2f", price));
    }

    public String toCsv() {
        String dates = String.join(";", this.reservedDates);
        return this.id + "," + this.floor + "," + this.capacity + ","
             + this.type.name() + "," + this.description + ","
             + this.price + "," + dates;
    }
}
