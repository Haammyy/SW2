package Model;

import java.time.ZoneId;

public class Location {
    public String getUserLocation() {
        ZoneId zone = ZoneId.systemDefault();
        return zone.toString();
    }
}
