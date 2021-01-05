import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Exceptions.FacilityNotFoundException;
import Exceptions.StudentNotFoundException;

public class BrosMenu {

    BookingDAO bookingDao;
    FacilityDAO facilityDao;
    StudentDAO studentDao;
    Scanner sc;

    public BrosMenu(boolean generateDummyData) {
        studentDao = new StudentDAO();
        facilityDao = new FacilityDAO();
        bookingDao = new BookingDAO();
        sc = new Scanner(System.in);

        if (generateDummyData) {
            generateDummyData();
        }
    }

    public BrosMenu() {
        this(false);
    }

    public void run() {
        int choice = 0;

        while (choice != 7) {
            try {
                printMenu();
                choice = askChoice();
                evalChoice(choice);
            } catch (InputMismatchException e) {
                System.out.println("Unrecognised input, please try again!");
                sc.nextLine();
            }
        }

        sc.close();
    }

    void printMenu() {
        System.out.println();
        System.out.println("== BROS :: Main Menu ==");
        System.out.println();
        System.out.println("1. List all students");
        System.out.println("2. List all facilities");
        System.out.println("3. List all bookings");
        System.out.println("4. List all bookings by a student");
        System.out.println("5. Add a student");
        System.out.println("6. Book a facility");
        System.out.println("7. Quit Application");
    }

    int askChoice() {
        int input = 0;
        System.out.print("Enter your choice > ");
        input = sc.nextInt();
        sc.nextLine();
        return input;
    }

    void evalChoice(int choice) {
        switch(choice) {
            case 1:
                printAllStudents();
                return;
            case 2:
                printAllFacilities();
                return;
            case 3:
                printAllBookings();
                return;
            case 4:
                printAllBookingsByStudent();
                return;
            case 5:
                addStudent();
                return;
            case 6:
                bookFacility();
                return;
            default:
                return;
        }
    }


    // Menu choice 1
    void printAllStudents() {

        ArrayList<Student> allStudents = studentDao.retrieveAll();
        if (allStudents.isEmpty()) {
            System.out.println("There is no student");
            return;
        }

        int serial = 1;
        String fmt = "%-3s %-12s %-15s %-10s\n";

        System.out.printf("\n== BROS :: List all Students ==\n\n");
        System.out.printf(fmt, "S/N", "Username", "Name", "Balance");
        for(Student s : allStudents) {
            System.out.printf(
                fmt, 
                serial, 
                s.getUsername(), 
                s.getName(), 
                s.getBalance()
            );
            serial++;
        }
    }

    // Menu choice 2
    void printAllFacilities() {

        ArrayList<Facility> allFacilities = facilityDao.retrieveAll();
        if (allFacilities.isEmpty()) {
            System.out.println("There is no facility");
            return;
        }

        int serial = 1;
        String fmt = "%-3s %-7s %-15s %-10s\n";

        System.out.printf("\n== BROS :: List all Facilities ==\n\n");
        System.out.printf(fmt, "S/N", "ID", "Description", "Capacity");
        for (Facility f : allFacilities) {
            System.out.printf(
                fmt, 
                serial, 
                f.getId(), 
                f.getDescription(), 
                f.getCapacity()
            );
            serial++;
        }
    }

    // Menu choice 3
    void printAllBookings() {

        ArrayList<Booking> allBookings = bookingDao.retrieveAll();
        if (allBookings.isEmpty()) {
            System.out.println("There is no booking.");
            return;
        }

        String fmt = "%-15s %-18s %-18s %-10s %-15s\n";
        System.out.printf("\n== BROS :: List all Bookings ==\n\n");
        System.out.printf(fmt, "Facility", "Booking DateTime", "Start DateTime", "Duration", "Student");
        for (Booking b : allBookings) {
            System.out.printf(
                fmt,
                b.getFacility().getDescription(), 
                b.getBookingDate(), 
                b.getStartDate(), 
                b.getDuration(), 
                b.getStudent().getName()
            );
        }
    }

    // Menu choice 4
    void printAllBookingsByStudent() {
        String studentName = "";
        System.out.print("Enter username > ");
        studentName = sc.next();

        ArrayList<Booking> allBookings = bookingDao.retrieve(studentName);
        if (allBookings.isEmpty()) {
            System.out.println("The student has not made any booking");
            return;
        }

        String fmt = "%-15s %-18s %-18s %-10s %-15s\n";
        System.out.printf("\n== BROS :: List all Bookings By %s ==\n\n", studentName);
        System.out.printf(fmt, "Facility", "Booking DateTime", "Start DateTime", "Duration", "Student");
        for (Booking b : allBookings) {
            System.out.printf(
                fmt,
                b.getFacility().getDescription(), 
                b.getBookingDate(), 
                b.getStartDate(), 
                b.getDuration(), 
                b.getStudent().getName()
            );
        }
    }

    // Menu choice 5
    void addStudent() {
        String userName = "";
        String studentName = "";
        int eDollars = 0;

        System.out.print("Enter username > ");
        userName = sc.next();

        if (studentDao.contains(userName)) {
            System.out.println("Username not available.");
            return;
        }

        System.out.print("Enter name > ");
        sc.nextLine(); // clr existing buffer
        studentName = sc.nextLine();

        System.out.print("Enter the E$ > ");
        eDollars = sc.nextInt();

        studentDao.addStudent(userName, studentName, eDollars);
        if (studentDao.contains(userName)) {
            System.out.printf("%s added!\n", studentName);
            return;
        }
        
    }

    // Menu choice 6
    void bookFacility() {
        System.out.printf("\n== BROS :: Book a Facility ==\n\n");

        String username = "";
        String facilityId = "";
        String startDateTime = "";
        int hours = 0;
    
        Student stud = null;
        Facility faci = null;
        BookingResult rslt = null;
        
        while (stud == null) {
            System.out.printf("Enter username > ");
            username = sc.nextLine();
            try {
                stud = studentDao.retrieve(username);
            } catch (StudentNotFoundException e) {
                System.out.printf("Student \"%s\" was not found.\n", username);
            }
        }

        while (faci == null) {
            System.out.printf("Enter facility ID > ");
            facilityId = sc.nextLine();
            try {
                faci = facilityDao.retrieve(facilityId);
            } catch (FacilityNotFoundException e) {
                System.out.printf("Facility \"%s\" was not found.\n", facilityId);
            }
        }

        System.out.printf("Enter start date > ");
        startDateTime += sc.nextLine();

        System.out.printf("Enter start time (HH:00) > ");
        startDateTime += " " + sc.nextLine();

        System.out.printf("Enter number of hours > ");
        hours = sc.nextInt();

        String bookingDate = new BrosDate().toString();
        rslt = bookingDao.add(stud, faci, bookingDate, startDateTime, hours);

        switch(rslt) {
            case OK:
                System.out.println("You have successfully booked this facility");
                System.out.printf("You have %d E$ left\n", stud.getBalance());
                return;
            case ALREADY_BOOKED:
                System.out.println("You cannot make this booking as someone else booked it");
                return;
            case INSUFFICIENT_BALANCE:
                System.out.println("You have insufficient eDollars for this transaction");
                System.out.printf("Balance: %d E$, Required to book: %d E$\n", stud.getBalance(), hours * faci.getPrice());
                return;
            case DATE_PARSER_ERROR:
                System.out.println("Your date format was incorrect, please use dd/MM/yyyy and HH:00");
                return;
            case INVALID_DURATION_ERROR:
                System.out.println("Your duration has to be a positive integer in hours.");
                return;
            default:
                System.out.println("The system has reached an unknown state.");
                return;
        }
    }

    void generateDummyData() {
        studentDao.add(new Student("raini", "Rainie Yang", 24));
        studentDao.add(new Student("hyun", "Hyun Bin", 34));
        studentDao.add(new Student("aaron", "Aaron Yang", 46));
        studentDao.add(new Student("simi", "Shiela Sim", 56));

        facilityDao.add(new Facility("F001", "Room 2-1", 4, 2));
        facilityDao.add(new Facility("F002", "Room 2-2", 6, 2));
        facilityDao.add(new Facility("F003", "Room 2-3", 8, 2));
        facilityDao.add(new Facility("F004", "Room 2-4", 10, 2));
        facilityDao.add(new Facility("F005", "Room 2-5", 12, 2));
        facilityDao.add(new Facility("F006", "Room 2-6", 14, 2));
        facilityDao.add(new Facility("F007", "Room 2-7", 16, 2));

        try {
            bookingDao.add(studentDao.retrieve("raini"), facilityDao.retrieve("F005"), "28/09/2016 16:05", "14/11/2016 15:00", 2);
            bookingDao.add(studentDao.retrieve("hyun"),  facilityDao.retrieve("F006"), "28/09/2016 16:05", "14/11/2016 15:00", 2);
            bookingDao.add(studentDao.retrieve("aaron"), facilityDao.retrieve("F003"), "29/09/2016 16:06", "15/11/2016 13:00", 1);
            bookingDao.add(studentDao.retrieve("aaron"), facilityDao.retrieve("F003"), "29/09/2016 16:06", "18/11/2016 18:00", 2);
            bookingDao.add(studentDao.retrieve("simi"),  facilityDao.retrieve("F001"), "30/09/2016 17:00", "19/11/2016 10:00", 3);
        } catch (StudentNotFoundException e) {
            System.out.println("Student not found while creating dummy data");
        } catch (FacilityNotFoundException e) {
            System.out.println("Facility not found while creating dummy data");
        }
    }
}
