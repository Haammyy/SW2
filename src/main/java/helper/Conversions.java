package helper;

import javafx.scene.control.Alert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Conversions {

    public static Alert toAlert (String alertText){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(alertText);
        alert.showAndWait();
        return alert;
    }

    //localDateTime to UTC
    public static String toUTC(String localDateTime) {
        LocalDateTime dateTime = LocalDateTime.parse(localDateTime.replace(' ', 'T'));
        ZonedDateTime localZoned = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
        ZonedDateTime utcZoned = localZoned.withZoneSameInstant(ZoneOffset.UTC);
        //print the UTC time
        System.out.println("UTC time: " + utcZoned.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //print the local time
        System.out.println("local time: " + localZoned.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return utcZoned.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public static String fromUTC(String utcDateTime) {
        LocalDateTime dateTime = LocalDateTime.parse(utcDateTime.replace(' ', 'T'));
        ZonedDateTime utcZoned = ZonedDateTime.of(dateTime, ZoneOffset.UTC);
        ZonedDateTime localZoned = utcZoned.withZoneSameInstant(ZoneId.systemDefault());
        return localZoned.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


}
