package facility;

import java.util.Date;

public class FacilityReservation {
    private Facility facility;
    private Date date;
    private String reservedBy;

    public FacilityReservation(Facility facility, Date date, String reservedBy) {
        this.facility = facility;
        this.date = date;
        this.reservedBy = reservedBy;
    }

    public void confirmReservation() {
        System.out.println("Reservation confirmed for " + facility.getId() + " on " + date);
    }
}
