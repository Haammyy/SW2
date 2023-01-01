package helper;

import javafx.scene.control.Alert;

public abstract class Conversions {

    public static Alert toAlert (String alertText){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(alertText);
        alert.showAndWait();
        return alert;
    }
}
