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

    public Reservation(LocalDate arrivalDate, LocalDate departureDate, double price,
                       ReservationStatus status, Guest guest, Room room) {
        this.arrivalDate   = arrivalDate;
        this.departureDate = departureDate;
        this.price         = price;
        this.status        = status;
        this.guest         = guest;
        this.room          = room;
    }

    public Reservation() {}

    public LocalDate         getArrivalDate()   { 
    	return this.arrivalDate;   
    }
    public LocalDate         getDepartureDate() { 
    	return this.departureDate; 
    }
    public double            getPrice()         { 
    	return this.price;         
    }
    public ReservationStatus getStatus()        { 
    	return this.status;       
    }
    public Guest             getGuest()         { 
    	return this.guest;         
    }
    public Room   getRoom()          { return this.room;          }
    public String getArrivalDateStr()   { return this.arrivalDate.format(FORMATTER);   }
    public String getDepartureDateStr() { return this.departureDate.format(FORMATTER); }

    public void setArrivalDate(LocalDate d)    { 
    	this.arrivalDate   = d; 
    }
    public void setDepartureDate(LocalDate d)  { 
    	this.departureDate = d; 
    }
    public void setPrice(double price)         { 
    	this.price         = price;  
    }
    public void setStatus(ReservationStatus s) { 
    	this.status        = s;     
    }
    public void setGuest(Guest g)              {
this.guest         = g;     
    }
    public void setRoom(Room r)                {
    	this.room          = r;    
}

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


    public String toCsv() {
        return this.getArrivalDateStr() + "," + this.getDepartureDateStr() + ","
             + this.price + "," + this.status.name() + ","
             + this.guest.getId() + "," + this.room.getId();
    }
}

