package helper;

import javafx.scene.control.Alert;

public abstract class Conversions {

    public static Alert toAlert (String alertText){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(alertText);
        alert.showAndWait();
        return alert;
    }

    //localDateTime to UTC
    public static String toUTC (String localDateTime){
        String[] split = localDateTime.split("T");
        String date = split[0];
        String time = split[1];
        String[] splitTime = time.split(":");
        int hour = Integer.parseInt(splitTime[0]);
        int minute = Integer.parseInt(splitTime[1]);
        int second = Integer.parseInt(splitTime[2]);
        hour = hour - 7;
        if (hour < 0){
            hour = hour + 24;
        }
        String newTime = hour + ":" + minute + ":" + second;
        String newDateTime = date + " " + newTime;
        return newDateTime;
    }

}
