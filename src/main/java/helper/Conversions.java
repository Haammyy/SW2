package helper;

import javafx.scene.control.Alert;

import java.time.*;
import java.time.format.DateTimeFormatter;

public abstract class Conversions {
        public static LocalDateTime toUTC(LocalDateTime localDateTime) {

        ZoneId localZone = ZoneId.systemDefault();
        ZoneId utcZone = ZoneId.of("UTC");

        ZonedDateTime localZonedDateTime = localDateTime.atZone(localZone);
        ZonedDateTime utcZonedDateTime = localZonedDateTime.withZoneSameInstant(utcZone);

        return utcZonedDateTime.toLocalDateTime();
    }
    //take a time input, convert it to local time regardless of what timezone the user is in
    public static LocalDateTime toLocal(LocalDateTime time, ZoneId zoneId) {
        ZonedDateTime utcDateTime = time.atZone(ZoneId.of("UTC"));
        ZonedDateTime localDateTime = utcDateTime.withZoneSameInstant(zoneId);
        return localDateTime.toLocalDateTime();
    }
    //take a time input, convert it to local time regardless of what timezone the user is in
    public static LocalDateTime toLocal(LocalDateTime time) {
        System.out.println("time before conversion: " + time);
        ZoneId localZone = ZoneId.systemDefault();
        ZoneId utcZone = ZoneId.of("UTC");

        ZonedDateTime utcZonedDateTime = time.atZone(utcZone);
        ZonedDateTime localZonedDateTime = utcZonedDateTime.withZoneSameInstant(localZone);
        System.out.println("time after conversion: " + localZonedDateTime.toLocalDateTime());
        return localZonedDateTime.toLocalDateTime();
    }

    public static Alert toAlert (String alertText){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(alertText);
        alert.showAndWait();
        return alert;
    }
    public static Alert toAlert (String alertText, int alertType) {
        //three different alert types, all converting a string to an alert
        // 0 = warning
        // 1 = information
        // 2 = error
        if (alertType == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(alertText);
            alert.showAndWait();
            return alert;
        } else if (alertType == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(alertText);
            alert.showAndWait();
            return alert;
        } else if (alertType == 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(alertText);
            alert.showAndWait();
            return alert;
        }
        return null;
    }




}
