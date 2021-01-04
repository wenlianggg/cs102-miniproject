import java.util.ArrayList;

import Exceptions.FacilityNotFoundException;

public class FacilityDAO {
    
    private ArrayList<Facility> facilityList;

    FacilityDAO() {
        facilityList = new ArrayList<Facility>();
        facilityList.add(new Facility("F001", "Room 2-1", 4, 2));
        facilityList.add(new Facility("F002", "Room 2-2", 6, 2));
        facilityList.add(new Facility("F003", "Room 2-3", 8, 2));
        facilityList.add(new Facility("F004", "Room 2-4", 10, 2));
        facilityList.add(new Facility("F005", "Room 2-5", 12, 2));
        facilityList.add(new Facility("F006", "Room 2-6", 14, 2));
        facilityList.add(new Facility("F007", "Room 2-7", 16, 2));
    }

    ArrayList<Facility> retrieveAll() {
        return facilityList;
    }

    Facility retrieve(String id) throws FacilityNotFoundException {
        for (Facility f : facilityList) {
            if (f.getId().equals(id)) {
                return f;
            } 
        }

        throw new FacilityNotFoundException();
    }

}
