import java.text.ParseException;
import java.util.ArrayList;

public class BookingDAO {
    
    ArrayList<Booking> bookingList;

    BookingDAO() {
        bookingList = new ArrayList<Booking>();
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
    BookingResult add(Student stud, Facility facility, String bkDate, String stDate, int duration) {
        Booking bk;
        
        try {
            bk = new Booking(stud, facility, bkDate, stDate, duration);
        } catch (IllegalArgumentException e) {
            return BookingResult.INVALID_DURATION_ERROR;
        } catch (ParseException e) {
            return BookingResult.DATE_PARSER_ERROR;
        }

        int bookingPrice = duration * facility.getPrice();
        if (!stud.sufficientBalanceFor(bookingPrice)) {
            return BookingResult.INSUFFICIENT_BALANCE;
        }
        
        for (Booking otherBooking : bookingList) {
            if (bk.overlaps(otherBooking)) {
                return BookingResult.INVALID_DURATION_ERROR;
            }
        }

        // All checks OK, deduct and save.
        stud.deductFromBalance(bookingPrice);
        bookingList.add(bk);
        return BookingResult.OK;
    }
}
