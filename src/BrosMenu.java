import java.util.ArrayList;
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
            printMenu();
            choice = askChoice();
            evalChoice(choice);
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
            System.out.println("Student already exists.");
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

    }

    void generateDummyData() {
        studentDao.add(new Student("raini", "Rainie Yang", 20));
        studentDao.add(new Student("hyun", "Hyun Bin", 30));
        studentDao.add(new Student("aaron", "Aaron Yang", 40));
        studentDao.add(new Student("simi", "Shiela Sim", 50));

        facilityDao.add(new Facility("F001", "Room 2-1", 4, 2));
        facilityDao.add(new Facility("F002", "Room 2-2", 6, 2));
        facilityDao.add(new Facility("F003", "Room 2-3", 8, 2));
        facilityDao.add(new Facility("F004", "Room 2-4", 10, 2));
        facilityDao.add(new Facility("F005", "Room 2-5", 12, 2));
        facilityDao.add(new Facility("F006", "Room 2-6", 14, 2));
        facilityDao.add(new Facility("F007", "Room 2-7", 16, 2));

        try {
            BookingResult br = bookingDao.add(studentDao.retrieve("raini"), facilityDao.retrieve("F005"), "28/09/2016 16:05", "14/11/2016 15:00", 2);
            System.out.println(br);   
            bookingDao.add(studentDao.retrieve("hyun"), facilityDao.retrieve("F006"), "28/09/2016 16:05", "14/11/2016 15:00", 2);
            bookingDao.add(studentDao.retrieve("aaron"), facilityDao.retrieve("F003"), "29/09/2016 16:06", "15/11/2016 13:00", 1);
            bookingDao.add(studentDao.retrieve("aaron"), facilityDao.retrieve("F003"), "29/09/2016 16:06", "18/11/2016 18:00", 2);
            bookingDao.add(studentDao.retrieve("simi"), facilityDao.retrieve("F001"), "30/09/2016 17:00", "19/11/2016 10:00", 3);
        } catch (StudentNotFoundException e) {
            System.out.println("Student not found while creating dummy data");
        } catch (FacilityNotFoundException e) {
            System.out.println("Facility not found while creating dummy data");
        }
    }
}
