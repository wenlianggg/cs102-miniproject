import java.text.ParseException;

public class Booking {

    private Student student;
    private Facility facility;
    private BrosDate startDate;
    private BrosDate bookingDate;
    private int duration;

    Booking(Student std, Facility facility, String bkDate, String stDate, int duration) throws ParseException, IllegalArgumentException {
        this.student = std;
        this.facility = facility;
        this.startDate = new BrosDate(stDate);
        this.bookingDate = new BrosDate(bkDate);

        if (duration <= 0) {
            throw new IllegalArgumentException("Duration cannot be less than or equal to zero...");
        }

        this.duration = duration;
    }

    Student getStudent() {
        return student;
    }

    Facility getFacility() {
        return facility;
    }

    BrosDate getStartDate() {
        return startDate;
    }

    BrosDate getBookingDate() {
        return this.bookingDate;
    }

    Integer getDuration() {
        return this.duration;
    }
    
    boolean overlaps(Booking other) {
        return overlaps(this.bookingDate, this.duration, other.bookingDate, other.duration);
    }

    public static boolean overlaps(BrosDate b1Start, int duration1, BrosDate b2Start, int duration2) {

        // Check for (b1Start) ==== (b2Start) ==== (b1End)
        BrosDate b1End = b1Start.computeDate(duration1);
        if (b1End.after(b2Start) && b1Start.before(b2Start)) {
            return true;
        }

        // Check for (b2Start) ==== (b1Start) ==== (b2End)
        BrosDate b2End = b2Start.computeDate(duration2);
        if (b2Start.before(b1Start) && b2End.after(b1Start)) {
            return true;
        }

        return false;
    }

}
