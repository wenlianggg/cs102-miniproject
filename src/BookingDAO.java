import java.text.ParseException;
import java.util.ArrayList;

import Exceptions.FacilityNotFoundException;
import Exceptions.StudentNotFoundException;

public class BookingDAO {
    
    ArrayList<Booking> bookingList;
    FacilityDAO fd;
    StudentDAO sd;

    BookingDAO() {
        bookingList = new ArrayList<Booking>();
    }

    BookingDAO(FacilityDAO fd, StudentDAO sd) {
        bookingList = new ArrayList<Booking>();
        this.fd = fd;
        this.sd = sd;
        this.createDummyData();
    }

    ArrayList<Booking> retrieveAll() {
        return bookingList;
    }

    ArrayList<Booking> retrieve(String username) {
        ArrayList<Booking> filtered = new ArrayList<Booking>();
        
        for (Booking b : bookingList) {
            if (b.getStudent().getUsername().equals(username)) {
                filtered.add(b);
            }
        }

        return filtered;
    }

    /**
     * This function will perform the following:
     * - Ensure that the date is parseable
     * - Ensure the the duration is non-negative
     * - Check to make sure that the booking does not overlap with another
     * - Deduct the amount from the student account
     */
    boolean add(Student stud, Facility facility, String bkDate, String stDate, int duration) {
        try {
           
            Booking bk = new Booking(stud, facility, bkDate, stDate, duration);
            int bookingPrice = duration * facility.getPrice();
            
            for ( Booking otherBooking : bookingList ) {
                if (bk.overlaps(otherBooking)) {
                    System.out.println("Booking could not be performed because it overlaps.");
                    return false;
                }
            }

            if (!stud.sufficientBalanceFor(bookingPrice)) {
                System.out.println("Student does not have sufficient balance.");
                return false;
            }

            // All checks OK, deduct and save.
            stud.deductFromBalance(bookingPrice);
            bookingList.add(bk);
            return true;
            
        } catch (ParseException e) {
            System.out.println("Something went wrong while parsing the date");
            return false;

        } catch (IllegalArgumentException e) {
            System.out.println("The duration cannot be negative or zero!");
            return false;

        }

    }

    void createDummyData() {
        try {
            this.add(sd.retrieve("raini"), fd.retrieve("F005"), "28/09/2016 16:05", "14/11/2016 15:00", 2);            
            this.add(sd.retrieve("hyun"), fd.retrieve("F006"), "28/09/2016 16:05", "14/11/2016 15:00", 2);
            this.add(sd.retrieve("aaron"), fd.retrieve("F003"), "29/09/2016 16:06", "15/11/2016 13:00", 1);
            this.add(sd.retrieve("aaron"), fd.retrieve("F003"), "29/09/2016 16:06", "18/11/2016 18:00", 2);
            this.add(sd.retrieve("simi"), fd.retrieve("F001"), "30/09/2016 17:00", "19/11/2016 10:00", 3);
        } catch (StudentNotFoundException e) {
            System.out.println("Student not found while creating dummy data");
        } catch (FacilityNotFoundException e) {
            System.out.println("Facility not found while creating dummy data");
        }
    }
}
