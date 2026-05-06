import models.*;
import enums.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Hotel {

    private ArrayList<Room>        rooms;
    private ArrayList<Guest>       guests;
    private ArrayList<Employee>    employees;
    private ArrayList<Reservation> reservations;

    private Scanner scanner;

    private static final String USERNAME          = "admin";
    private static final String PASSWORD          = "11037";
    private static final int    MAX_LOGIN_ATTEMPTS = 3;

    private static final String ROOMS_FILE        = "data/rooms.txt";
    private static final String GUESTS_FILE       = "data/guests.txt";
    private static final String EMPLOYEES_FILE    = "data/employees.txt";
    private static final String RESERVATIONS_FILE = "data/reservations.txt";


    public Hotel() {
        this.rooms        = new ArrayList<>();
        this.guests       = new ArrayList<>();
        this.employees    = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.scanner      = new Scanner(System.in);

        new File("data").mkdirs();

        this.loadAllData();
    }
    
    public void run() {
        if (!this.login()) {
            System.out.println("System locked. Goodbye.");
            return;
        }
        this.showMenu();
    }

    private boolean login() {
        System.out.println("========================================");
        System.out.println("    Hotel Management System - Login     ");
        System.out.println("========================================");

        int attempts = MAX_LOGIN_ATTEMPTS;

        while (attempts > 0) {
            try {
                System.out.print("Username: ");
                String username = this.scanner.next().trim();

                System.out.print("Password: ");
                String password = this.scanner.next().trim();

                if (username.equals(USERNAME) && password.equals(PASSWORD)) {
                    System.out.println("\nLogin successful! Welcome, " + username + ".\n");
                    return true;
                }

                attempts--;
                if (attempts > 0) {
                    System.out.println("Incorrect credentials. " + attempts + " attempt(s) remaining.\n");
                }

            } catch (Exception e) {
                System.out.println("Input error: " + e.getMessage());
                attempts--;
            }
        }

        System.out.println("Too many failed attempts. System locked.");
        return false;
    }

    private void showMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n========================================");
            System.out.println("             MAIN MENU");
            System.out.println("========================================");
            System.out.println("  --- Rooms ---");
            System.out.println("   1. Add new room");
            System.out.println("   2. Show all rooms");
            System.out.println("   3. Show rooms by type");
            System.out.println("   4. Edit room");
            System.out.println("  --- Guests ---");
            System.out.println("   5. Show all guests");
            System.out.println("   6. Search guest by name");
            System.out.println("   7. Search guest by ID");
            System.out.println("   8. Edit guest");
            System.out.println("  --- Reservations ---");
            System.out.println("   9. Create new reservation");
            System.out.println("  10. Show all reservations");
            System.out.println("  11. Show reservations (detailed)");
            System.out.println("  12. Find reservation by guest name");
            System.out.println("  13. Find reservation by guest ID");
            System.out.println("  14. Edit reservation");
            System.out.println("  15. Cancel reservation");
            System.out.println("  --- Employees ---");
            System.out.println("  16. Add new employee");
            System.out.println("  17. Show all employees");
            System.out.println("  18. Edit employee");
            System.out.println("========================================");
            System.out.println("   0. Save & Quit");
            System.out.println("========================================");
            System.out.print("Enter choice: ");

            int choice = this.readInt();

            switch (choice) {

                case 1:  this.addRoom();                    break;
                case 2:  this.showAllRooms();               break;
                case 3:  this.showRoomsByType();            break; 
                case 4:  this.editRoom();                   break;

                case 5:  this.showAllGuests();              break;
                case 6:  this.searchGuestByName();          break;
                case 7:  this.searchGuestById();            break;
                case 8:  this.editGuest();                  break;

                case 9:  this.createReservation();          break;
                case 10: this.showAllReservations();        break;
                case 11: this.showAllReservations(true);    break;
                case 12: this.getReservationByGuestName();  break;
                case 13: this.getReservationByGuestId();    break;
                case 14: this.editReservation();            break;
                case 15: this.cancelReservation();          break;

                case 16: this.addEmployee();                break;
                case 17: this.showAllEmployees();           break;
                case 18: this.editEmployee();               break;

                case 0:
                    this.saveAllData();
                    this.scanner.close();
                    System.out.println("Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice! Enter a number from the menu.");
            }
        }
    }

    private void addRoom() {
        System.out.println("\n--- Add New Room ---");

        System.out.print("Floor number: ");
        int floor = this.readInt();

        System.out.print("Capacity (number of persons): ");
        int capacity = this.readInt();

        System.out.print("Type (SINGLE / DOUBLE / SUITE): ");
        RoomType type = this.readRoomType();

        System.out.print("Description (no spaces, e.g. SeaView): ");
        String description = this.scanner.next();

        System.out.print("Price per night ($): ");
        double price = this.readDouble();

        int id = 1000 + this.rooms.size();
        Room room = new Room(id, floor, capacity, type, description, price);
        this.rooms.add(room);

        System.out.println("Room added successfully! (ID: " + id + ")");
    }

    private void showAllRooms() {
        if (this.rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }
        System.out.println("\n--- All Rooms (" + this.rooms.size() + ") ---");
        for (Room room : this.rooms) {
            System.out.println("----------------------------------------");
            room.print();
        }
        System.out.println("----------------------------------------");
    }

    private void showAllRooms(RoomType filter) {
        System.out.println("\n--- Rooms of type: " + filter.getLabel() + " ---");
        boolean found = false;
        for (Room room : this.rooms) {
            if (room.getType() == filter) {
                System.out.println("----------------------------------------");
                room.print();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms of type '" + filter.getLabel() + "' found.");
        }
        System.out.println("----------------------------------------");
    }

    private void showRoomsByType() {
        System.out.print("Enter type (SINGLE / DOUBLE / SUITE): ");
        RoomType type = this.readRoomType();
        this.showAllRooms(type);
    }


    private void editRoom() {
        if (this.rooms.isEmpty()) {
            System.out.println("No rooms to edit.");
            return;
        }

        System.out.print("Enter room ID (-1 to show all rooms): ");
        int id = this.readInt();

        if (id == -1) {
            this.showAllRooms();
            System.out.print("Enter room ID: ");
            id = this.readInt();
        }

        int index = this.getRoomIndex(id);
        if (index == -1) {
            System.out.println("Room not found!");
            return;
        }

        Room room = this.rooms.get(index);
        System.out.println("Editing room " + id + " (enter -1 to keep current value):");

        System.out.print("Floor [" + room.getFloor() + "]: ");
        int floor = this.readInt();
        if (floor != -1) room.setFloor(floor);

        System.out.print("Capacity [" + room.getCapacity() + "]: ");
        int capacity = this.readInt();
        if (capacity != -1) room.setCapacity(capacity);

        System.out.print("Type (SINGLE/DOUBLE/SUITE) [" + room.getType().getLabel() + "] or -1 to keep: ");
        String typeInput = this.scanner.next().trim();
        if (!typeInput.equals("-1")) {
            try {
                room.setType(RoomType.valueOf(typeInput.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid type! Keeping original.");
            }
        }

        System.out.print("Description [" + room.getDescription() + "] or -1 to keep: ");
        String desc = this.scanner.next().trim();
        if (!desc.equals("-1")) room.setDescription(desc);

        System.out.print("Price [" + room.getPrice() + "] or -1 to keep: ");
        double price = this.readDouble();
        if (price != -1) room.setPrice(price);

        System.out.println("Room updated successfully!");
    }

    private Room getRoomById(int id) {
        for (Room room : this.rooms) {
            if (room.getId() == id) return room;
        }
        return null;
    }


    private int getRoomIndex(int id) {
        for (int i = 0; i < this.rooms.size(); i++) {
            if (this.rooms.get(i).getId() == id) return i;
        }
        return -1;
    }

    private void showAllGuests() {
        if (this.guests.isEmpty()) {
            System.out.println("No guests registered.");
            return;
        }
        System.out.println("\n--- All Guests (" + this.guests.size() + ") ---");
        for (Guest guest : this.guests) {
            System.out.println("----------------------------------------");
            guest.print();
        }
        System.out.println("----------------------------------------");
    }

    private void searchGuestByName() {
        System.out.print("Enter guest name: ");
        String name = this.scanner.next().trim();
        this.searchGuest(name);
    }

    private void searchGuestById() {
        System.out.print("Enter guest ID: ");
        int id = this.readInt();
        this.searchGuest(id);
    }

    private void searchGuest(String name) {
        boolean found = false;
        for (Guest guest : this.guests) {
            if (guest.getName().equalsIgnoreCase(name)) {
                System.out.println("----------------------------------------");
                guest.print();
                found = true;
            }
        }
        if (!found) System.out.println("No guest found with name: " + name);
    }

    private void searchGuest(int id) {
        for (Guest guest : this.guests) {
            if (guest.getId() == id) {
                System.out.println("----------------------------------------");
                guest.print();
                return;
            }
        }
        System.out.println("No guest found with ID: " + id);
    }

    private void editGuest() {
        if (this.guests.isEmpty()) {
            System.out.println("No guests to edit.");
            return;
        }

        System.out.print("Enter guest ID (-1 to search by name): ");
        int id = this.readInt();

        if (id == -1) {
            this.searchGuestByName();
            System.out.print("Enter guest ID: ");
            id = this.readInt();
        }

        Guest guest = this.getGuestById(id);
        if (guest == null) {
            System.out.println("Guest not found!");
            return;
        }

        System.out.println("Editing guest " + id + " (enter -1 to keep current value):");

        System.out.print("Name [" + guest.getName() + "]: ");
        String name = this.scanner.next().trim();
        if (!name.equals("-1")) guest.setName(name);

        System.out.print("Email [" + guest.getEmail() + "]: ");
        String email = this.scanner.next().trim();
        if (!email.equals("-1")) guest.setEmail(email);

        System.out.print("Discount % [" + guest.getDiscount() + "]: ");
        int discount = this.readInt();
        if (discount != -1) guest.setDiscount(discount);

        System.out.println("Guest updated successfully!");
    }

    private Guest getGuestById(int id) {
        for (Guest guest : this.guests) {
            if (guest.getId() == id) return guest;
        }
        return null;
    }

    private void createReservation() {
        System.out.println("\n--- Create New Reservation ---");

        System.out.print("Arrival date (yyyy-MM-dd): ");
        LocalDate arrival = this.readDate();

        System.out.print("Departure date (yyyy-MM-dd): ");
        LocalDate departure = this.readDate();

        if (!departure.isAfter(arrival)) {
            System.out.println("Error: Departure must be after arrival!");
            return;
        }

        if (arrival.isBefore(LocalDate.now())) {
            System.out.println("Warning: Arrival date is in the past.");
        }

        System.out.print("Guest name: ");
        String guestName = this.scanner.next().trim();

        System.out.print("Guest email: ");
        String guestEmail = this.scanner.next().trim();

        System.out.print("Discount % (0 for no discount): ");
        int discount = this.readInt();

        if (discount < 0 || discount > 100) {
            System.out.println("Invalid discount! Setting to 0.");
            discount = 0;
        }

        Guest guest = new Guest(this.guests.size(), guestName, guestEmail, discount);
        this.guests.add(guest);

        System.out.print("Room ID (-1 to show all rooms): ");
        int roomId = this.readInt();

        if (roomId == -1) {
            this.showAllRooms();
            System.out.print("Room ID: ");
            roomId = this.readInt();
        }

        Room room = this.getRoomById(roomId);
        if (room == null) {
            System.out.println("Room not found!");
            this.guests.remove(guest);
            return;
        }

        if (room.isReserved(arrival, departure)) {
            System.out.println("Room is not available for these dates!");
            this.guests.remove(guest);
            return;
        }

        long   nights        = ChronoUnit.DAYS.between(arrival, departure);
        double beforeDiscount = nights * room.getPrice();
        double total          = beforeDiscount - (beforeDiscount * discount / 100.0);

        this.printPriceSummary(beforeDiscount, total, discount); 

        Reservation reservation = new Reservation(
            arrival, departure, total, ReservationStatus.PAID, guest, room
        );
        this.reservations.add(reservation);
        room.reserve(arrival, departure);

        System.out.println("\nReservation created successfully!");
        reservation.print();
    }

    private void showAllReservations() {
        if (this.reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        System.out.println("\n--- All Reservations (" + this.reservations.size() + ") ---");
        for (int i = 0; i < this.reservations.size(); i++) {
            System.out.println("--- Reservation #" + i + " ---");
            this.reservations.get(i).print();
        }
    }


    private void showAllReservations(boolean detailed) {
        if (this.reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        System.out.println("\n--- All Reservations (Detailed) ---");
        for (int i = 0; i < this.reservations.size(); i++) {
            System.out.println("========================================");
            System.out.println("Reservation #" + i);
            this.reservations.get(i).print(detailed);
        }
    }

    private void getReservationByGuestName() {
        System.out.print("Enter guest name: ");
        String name = this.scanner.next().trim();
        boolean found = false;

        for (int i = 0; i < this.reservations.size(); i++) {
            Reservation r = this.reservations.get(i);
            if (r.getGuest().getName().equalsIgnoreCase(name)) {
                System.out.println("--- Reservation #" + i + " ---");
                r.print();
                found = true;
            }
        }
        if (!found) System.out.println("No reservations found for: " + name);
    }

    private void getReservationByGuestId() {
        System.out.print("Enter guest ID: ");
        int id = this.readInt();
        boolean found = false;

        for (int i = 0; i < this.reservations.size(); i++) {
            Reservation r = this.reservations.get(i);
            if (r.getGuest().getId() == id) {
                System.out.println("--- Reservation #" + i + " ---");
                r.print();
                found = true;
            }
        }
        if (!found) System.out.println("No reservations found for guest ID: " + id);
    }


    private void editReservation() {
        if (this.reservations.isEmpty()) {
            System.out.println("No reservations to edit.");
            return;
        }

        System.out.print("Reservation ID (-1 to show all): ");
        int id = this.readInt();

        if (id == -1) {
            this.showAllReservations();
            System.out.print("Reservation ID: ");
            id = this.readInt();
        }

        if (id < 0 || id >= this.reservations.size()) {
            System.out.println("Reservation not found!");
            return;
        }

        Reservation reservation = this.reservations.get(id);

        LocalDate oldArrival   = reservation.getArrivalDate();
        LocalDate oldDeparture = reservation.getDepartureDate();
        Room      oldRoom      = reservation.getRoom();

        System.out.println("Editing reservation #" + id + " (enter -1 to keep current value):");

        System.out.print("Arrival date [" + reservation.getArrivalDateStr() + "]: ");
        String arrInput = this.scanner.next().trim();
        LocalDate arrival = oldArrival;
        if (!arrInput.equals("-1")) {
            try {
                arrival = LocalDate.parse(arrInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Keeping original.");
            }
        }

        System.out.print("Departure date [" + reservation.getDepartureDateStr() + "]: ");
        String depInput = this.scanner.next().trim();
        LocalDate departure = oldDeparture;
        if (!depInput.equals("-1")) {
            try {
                departure = LocalDate.parse(depInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Keeping original.");
            }
        }

        if (!departure.isAfter(arrival)) {
            System.out.println("Departure must be after arrival!");
            return;
        }

        System.out.print("Room ID [" + reservation.getRoom().getId() + "] (-1 to keep, -2 to show all): ");
        int roomId = this.readInt();
        Room room = oldRoom;

        if (roomId == -2) {
            this.showAllRooms();
            System.out.print("Room ID: ");
            roomId = this.readInt();
        }

        if (roomId != -1) {
            Room newRoom = this.getRoomById(roomId);
            if (newRoom == null) {
                System.out.println("Room not found! Keeping original.");
            } else {
                room = newRoom;
            }
        }

        oldRoom.unreserve(oldArrival, oldDeparture);

        if (room.isReserved(arrival, departure)) {
            oldRoom.reserve(oldArrival, oldDeparture);
            System.out.println("Room not available for these dates! Edit cancelled.");
            return;
        }

        Guest guest        = reservation.getGuest();
        long  nights       = ChronoUnit.DAYS.between(arrival, departure);
        double before      = nights * room.getPrice();
        double total       = before - (before * guest.getDiscount() / 100.0);

        this.printPriceSummary(before, total, guest.getDiscount()); 

        reservation.setArrivalDate(arrival);
        reservation.setDepartureDate(departure);
        reservation.setRoom(room);
        reservation.setPrice(total);
        reservation.setStatus(ReservationStatus.PAID);

        room.reserve(arrival, departure);

        System.out.println("Reservation updated successfully!");
        reservation.print();
    }

    private void cancelReservation() {
        if (this.reservations.isEmpty()) {
            System.out.println("No reservations to cancel.");
            return;
        }

        System.out.print("Reservation ID (-1 to show all): ");
        int id = this.readInt();

        if (id == -1) {
            this.showAllReservations();
            System.out.print("Reservation ID: ");
            id = this.readInt();
        }

        if (id < 0 || id >= this.reservations.size()) {
            System.out.println("Reservation not found!");
            return;
        }

        Reservation reservation = this.reservations.get(id);

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            System.out.println("This reservation is already cancelled.");
            return;
        }

        reservation.getRoom().unreserve(
            reservation.getArrivalDate(),
            reservation.getDepartureDate()
        );

        reservation.setStatus(ReservationStatus.CANCELLED);

        System.out.println("Reservation #" + id + " cancelled. Room is now available.");
    }

    private void addEmployee() {
        System.out.println("\n--- Add New Employee ---");

        System.out.print("Name: ");
        String name = this.scanner.next().trim();

        System.out.print("Email: ");
        String email = this.scanner.next().trim();

        System.out.print("Monthly salary ($): ");
        double salary = this.readDouble();

        System.out.print("Job role (MANAGER / RECEPTIONIST / CLEANER / SECURITY): ");
        JobRole job = this.readJobRole();

        int id = this.employees.size();
        Employee employee = new Employee(id, name, email, salary, job);
        this.employees.add(employee);

        System.out.println("Employee added successfully! (ID: " + id + ")");
    }


    private void showAllEmployees() {
        if (this.employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        System.out.println("\n--- All Employees (" + this.employees.size() + ") ---");
        for (Employee emp : this.employees) {
            System.out.println("----------------------------------------");
            emp.print(true);
        }
        System.out.println("----------------------------------------");
    }

    private void editEmployee() {
        if (this.employees.isEmpty()) {
            System.out.println("No employees to edit.");
            return;
        }

        System.out.print("Enter employee ID (-1 to show all): ");
        int id = this.readInt();

        if (id == -1) {
            this.showAllEmployees();
            System.out.print("Enter employee ID: ");
            id = this.readInt();
        }

        if (id < 0 || id >= this.employees.size()) {
            System.out.println("Employee not found!");
            return;
        }

        Employee emp = this.employees.get(id);
        System.out.println("Editing employee " + id + " (enter -1 to keep current value):");

        System.out.print("Name [" + emp.getName() + "]: ");
        String name = this.scanner.next().trim();
        if (!name.equals("-1")) emp.setName(name);

        System.out.print("Email [" + emp.getEmail() + "]: ");
        String email = this.scanner.next().trim();
        if (!email.equals("-1")) emp.setEmail(email);

        System.out.print("Salary [" + emp.getSalary() + "]: ");
        double salary = this.readDouble();
        if (salary != -1) emp.setSalary(salary);

        System.out.print("Job (MANAGER/RECEPTIONIST/CLEANER/SECURITY) [" + emp.getJob().getLabel() + "] or -1: ");
        String jobInput = this.scanner.next().trim();
        if (!jobInput.equals("-1")) {
            try {
                emp.setJob(JobRole.valueOf(jobInput.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid job role! Keeping original.");
            }
        }

        System.out.println("Employee updated successfully!");
    }

    private void loadAllData() {
        this.loadRooms();        
        this.loadGuests();       
        this.loadEmployees();
        this.loadReservations();
        System.out.println("Data loaded. ("
            + this.rooms.size()        + " rooms, "
            + this.guests.size()       + " guests, "
            + this.employees.size()    + " employees, "
            + this.reservations.size() + " reservations)");
    }

    private void saveAllData() {
        this.saveRooms();
        this.saveGuests();
        this.saveEmployees();
        this.saveReservations();
        System.out.println("All data saved successfully.");
    }

    private void loadRooms() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ROOMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",", 7);
                if (parts.length < 6) continue;

                int      id       = Integer.parseInt(parts[0].trim());
                int      floor    = Integer.parseInt(parts[1].trim());
                int      capacity = Integer.parseInt(parts[2].trim());
                RoomType type     = RoomType.valueOf(parts[3].trim());
                String   desc     = parts[4].trim();
                double   price    = Double.parseDouble(parts[5].trim());

                Room room = new Room(id, floor, capacity, type, desc, price);

               
                if (parts.length == 7 && !parts[6].trim().isEmpty()) {
                    String[] dates = parts[6].trim().split(";");
                    for (String date : dates) {
                        if (!date.trim().isEmpty()) {
                            room.getReservedDates().add(date.trim());
                        }
                    }
                }
                this.rooms.add(room);
            }
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            System.out.println("Warning: Could not fully load rooms: " + e.getMessage());
        }
    }

    private void loadGuests() {
        try (BufferedReader reader = new BufferedReader(new FileReader(GUESTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 4) continue;

                int    id       = Integer.parseInt(parts[0].trim());
                String name     = parts[1].trim();
                String email    = parts[2].trim();
                int    discount = Integer.parseInt(parts[3].trim());

                this.guests.add(new Guest(id, name, email, discount));
            }
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            System.out.println("Warning: Could not fully load guests: " + e.getMessage());
        }
    }

    private void loadEmployees() {
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                int     id     = Integer.parseInt(parts[0].trim());
                String  name   = parts[1].trim();
                String  email  = parts[2].trim();
                double  salary = Double.parseDouble(parts[3].trim());
                JobRole job    = JobRole.valueOf(parts[4].trim());

                this.employees.add(new Employee(id, name, email, salary, job));
            }
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            System.out.println("Warning: Could not fully load employees: " + e.getMessage());
        }
    }

    private void loadReservations() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                LocalDate         arrival   = LocalDate.parse(parts[0].trim());
                LocalDate         departure = LocalDate.parse(parts[1].trim());
                double            price     = Double.parseDouble(parts[2].trim());
                ReservationStatus status    = ReservationStatus.valueOf(parts[3].trim());
                int               guestId   = Integer.parseInt(parts[4].trim());
                int               roomId    = Integer.parseInt(parts[5].trim());

                Guest guest = this.getGuestById(guestId);
                Room  room  = this.getRoomById(roomId);

                if (guest != null && room != null) {
                    this.reservations.add(new Reservation(arrival, departure, price, status, guest, room));
                } else {
                    System.out.println("Warning: Skipped reservation with missing guest/room.");
                }
            }
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            System.out.println("Warning: Could not fully load reservations: " + e.getMessage());
        }
    }

    private void saveRooms() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ROOMS_FILE))) {
            for (Room room : this.rooms) {
                writer.println(room.toCsv());
            }
        } catch (IOException e) {
            System.out.println("Error saving rooms: " + e.getMessage());
        }
    }

    private void saveGuests() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(GUESTS_FILE))) {
            for (Guest guest : this.guests) {
                writer.println(guest.toCsv());
            }
        } catch (IOException e) {
            System.out.println("Error saving guests: " + e.getMessage());
        }
    }

    private void saveEmployees() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EMPLOYEES_FILE))) {
            for (Employee emp : this.employees) {
                writer.println(emp.toCsv());
            }
        } catch (IOException e) {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }

    private void saveReservations() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RESERVATIONS_FILE))) {
            for (Reservation r : this.reservations) {
                writer.println(r.toCsv());
            }
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }
    }

    private void printPriceSummary(double before, double after, int discount) {
        System.out.println("\n--- Price Summary ---");
        if (discount == 0) {
            System.out.println("Total: $" + String.format("%.2f", before));
        } else {
            System.out.println("Before discount : $" + String.format("%.2f", before));
            System.out.println("Discount        :  " + discount + "%");
            System.out.println("After discount  : $" + String.format("%.2f", after));
        }
        System.out.println("--------------------");
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(this.scanner.next().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a whole number: ");
            }
        }
    }

    private double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(this.scanner.next().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number: ");
            }
        }
    }

    private LocalDate readDate() {
        while (true) {
            try {
                return LocalDate.parse(this.scanner.next().trim());
            } catch (DateTimeParseException e) {
                System.out.print("Invalid date. Use format yyyy-MM-dd: ");
            }
        }
    }

    private RoomType readRoomType() {
        while (true) {
            try {
                return RoomType.valueOf(this.scanner.next().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.print("Invalid type. Enter SINGLE, DOUBLE, or SUITE: ");
            }
        }
    }

    private JobRole readJobRole() {
        while (true) {
            try {
                return JobRole.valueOf(this.scanner.next().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.print("Invalid role. Enter MANAGER, RECEPTIONIST, CLEANER, or SECURITY: ");
            }
        }
    }
}