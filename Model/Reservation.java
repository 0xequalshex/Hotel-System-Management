package enums;
import enums.ReservationStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter; 
import java.time.temporal.ChronoUnit; 

public class Reservation {
	private LocalDate         arrivalDate;
    private LocalDate         departureDate;
    private double            price;
    private ReservationStatus status;
    private Guest             guest;  
    private Room              room;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Reservation(LocalDate arrivalDate, LocalDate departureDate, double price,ReservationStatus status, Guest guest, Room room) {
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.price = price;
        this.status = status;
        this.guest = guest;
        this.room = room;
    }

    public Reservation() {}

   	public void setArrivalDate(LocalDate arrivalDate) {this.arrivalDate = arrivalDate;}
    public void setDepartureDate(LocalDate departureDate) {this.departureDate = departureDate;}
    public void setPrice(double price) { this.price = price;}
    public void setStatus(ReservationStatus status) {this.status = status;}
    public void setGuest(Guest guest) { this.guest = guest;}
    public void setRoom(Room room) { this.room = room;}

    public LocalDate getArrivalDate() {return this.arrivalDate;}
    public LocalDate getDepartureDate() {return this.departureDate;}
    public double getPrice() {return this.price;}
    public ReservationStatus getStatus() {return this.status;}
    public Guest getGuest() {return this.guest;}
    public Room getRoom() {return this.room;}

    public String getArrivalDateStr() {return this.arrivalDate.format(FORMATTER);}
    public String getDepartureDateStr() {return this.departureDate.format(FORMATTER);}


    public long getNights() {
        return ChronoUnit.DAYS.between(this.arrivalDate, this.departureDate);
    }

    public void print() {
        System.out.println("  Arrival   : " + this.getArrivalDateStr());
        System.out.println("  Departure : " + this.getDepartureDateStr());
        System.out.println("  Nights    : " + this.getNights());
        System.out.println("  Guest     : " + this.guest.getName() + " (ID: " + this.guest.getId() + ")");
        System.out.println("  Room ID   : " + this.room.getId() + " [" + this.room.getType().getLabel() + "]");
        System.out.println("  Price     : $" + String.format("%.2f", this.price));
        System.out.println("  Status    : " + this.status.getLabel());
    }

	public void print(boolean detailed) {
        this.print();
        if (detailed) {
            System.out.println("  --- Guest Details ---");
            this.guest.print();
            System.out.println("  --- Room Details ---");
            this.room.print();
        }
    }
	
    public String toCsv() {
        return this.getArrivalDateStr() + "," + this.getDepartureDateStr() + ","
             + this.price + "," + this.status.name() + ","
             + this.guest.getId() + "," + this.room.getId();
    }
}

