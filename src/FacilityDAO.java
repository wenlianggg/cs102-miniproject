import java.util.ArrayList;

import Exceptions.FacilityNotFoundException;

public class FacilityDAO {
    
    private ArrayList<Facility> facilityList;

    FacilityDAO() {
        facilityList = new ArrayList<Facility>();
    }

    void add(Facility f) {
        facilityList.add(f);
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
