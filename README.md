# Hotel Management System

A console-based Java application for managing hotel rooms, guests, employees, and reservations. Built as a final project for the Object-Oriented Programming course.

---

## Project Structure

```
HotelManagementSystem/
├── Main.java
├── Hotel.java
├── models/
│   ├── Person.java
│   ├── Guest.java
│   ├── Employee.java
│   ├── Room.java
│   └── Reservation.java
└── enums/
    ├── RoomType.java
    ├── JobRole.java
    └── ReservationStatus.java
```

| File | Role |
|---|---|
| `Main.java` | Entry point — creates a Hotel object and calls run() |
| `Hotel.java` | All business logic: login, menu, CRUD operations, file I/O |
| `models/Person.java` | Abstract base class — Inheritance root for Guest and Employee |
| `models/Guest.java` | Extends Person — adds discount field |
| `models/Employee.java` | Extends Person — adds salary and job role |
| `models/Room.java` | Room data and reserved-dates tracking |
| `models/Reservation.java` | Links Guest and Room with dates, price, and status |
| `enums/RoomType.java` | SINGLE, DOUBLE, SUITE — with label and default capacity |
| `enums/JobRole.java` | MANAGER, RECEPTIONIST, CLEANER, SECURITY — with duty description |
| `enums/ReservationStatus.java` | PENDING, PAID, CANCELLED — with label |

---

## How to Compile and Run

**Requirements:** Java 11 or higher.

**Compile:**
```bash
javac enums/*.java models/*.java Hotel.java Main.java
```

**Run:**
```bash
java Main
```

---

## Login

The system requires a username and password before any operation. There are 3 attempts before the system locks.

Default credentials:
```
Username: admin
Password: 11037
```

To change the credentials, edit the following constants at the top of `Hotel.java`:
```java
private static final String USERNAME = "admin";
private static final String PASSWORD = "11037";
```

---

## Menu Options

The main menu has 18 options organized into four groups:

**Rooms**
1. Add new room
2. Show all rooms
3. Show rooms by type
4. Edit room

**Guests**
5. Show all guests
6. Search guest by name
7. Search guest by ID
8. Edit guest

**Reservations**
9. Create new reservation
10. Show all reservations (summary)
11. Show all reservations (detailed)
12. Find reservation by guest name
13. Find reservation by guest ID
14. Edit reservation
15. Cancel reservation

**Employees**
16. Add new employee
17. Show all employees
18. Edit employee

Enter `0` to save all data and exit.

---

## Data Persistence

All data is saved automatically to text files in the `data/` folder when you exit the program (option 0), and loaded back when you start it again.

| File | Contents |
|---|---|
| `data/rooms.txt` | All rooms and their reserved dates |
| `data/guests.txt` | All registered guests |
| `data/employees.txt` | All employees |
| `data/reservations.txt` | All reservations |

The `data/` folder is created automatically on first run

---

## Reservation Pricing

Price is calculated as:

```
nights = departure date - arrival date
total  = nights x room price per night
total  = total - (total x discount / 100)
```

---

## OOP Concepts Used

| Concept | Where |
|---|---|
| Classes and Methods | 9 classes across the project |
| The Keyword this | Every constructor in every class |
| Methods with Primitive Parameters | `readInt()`, `searchGuest(int)`, `showAllReservations(boolean)` |
| Using Objects as Parameters | `Reservation(Guest, Room, ...)`, `showAllRooms(RoomType)` |
| Public and Private Modifiers | Hotel exposes only `run()` as public; all other methods are private |
| Methods Calling Methods | `showRoomsByType()` calls `showAllRooms(type)`; `editReservation()` calls `getRoomById()` then `printPriceSummary()` |
| Method Overloading | `showAllRooms()` / `showAllRooms(RoomType)`, `searchGuest(String)` / `searchGuest(int)`, `print()` / `print(boolean)` / `print(String)` |
| Enumeration as a class | RoomType, JobRole, ReservationStatus each have private fields, a constructor, and methods |
| Arrays in classes and methods | `ArrayList<String> reservedDates` inside Room |
| Exception Handling | `NumberFormatException`, `DateTimeParseException`, `IOException`, `IllegalArgumentException`, `FileNotFoundException` |
| Inheritance | `abstract Person` extended by `Guest` and `Employee` |
| Text-file Input and Output | `loadAllData()` on startup, `saveAllData()` on exit |
| Constructors | Every class has a full constructor and a default constructor |

---

## Note

- Guest accounts are created as part of the reservation flow. Use options 5-8 to view or edit them afterwards.
