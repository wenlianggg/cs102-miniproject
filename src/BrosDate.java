import java.util.GregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 
// Decompiled by Procyon v0.5.36
// 

public class BrosDate
{
    private Date dateTime;
    
    public BrosDate() {
        this.dateTime = new Date();
    }
    
    public BrosDate(final String source) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            this.dateTime = simpleDateFormat.parse(source);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    
    private Date getDateTime() {
        return this.dateTime;
    }
    
    private void setDateTime(final Date dateTime) {
        this.dateTime = dateTime;
    }
    
    public boolean equals(final BrosDate brosDate) {
        return this.dateTime.equals(brosDate.getDateTime());
    }
    
    public boolean after(final BrosDate brosDate) {
        return this.dateTime.after(brosDate.getDateTime());
    }
    
    public boolean before(final BrosDate brosDate) {
        return this.dateTime.before(brosDate.getDateTime());
    }
    
    public BrosDate computeDate(final int amount) {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(this.dateTime);
        try {
            gregorianCalendar.add(11, amount);
        }
        catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        final BrosDate brosDate = new BrosDate();
        brosDate.setDateTime(gregorianCalendar.getTime());
        return brosDate;
    }
    
    @Override
    public String toString() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(this.dateTime);
    }
}