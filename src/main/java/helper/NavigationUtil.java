package helper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This is the setup for cancel buttons, to prevent copy/paste this will return to teh home screen
 */
public class NavigationUtil {
    public static void navigateToHomePage(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource("/application/scheduleView.fxml"));
            Parent viewParent = loader.load();
            Scene viewScene = new Scene(viewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("");
            window.setScene(viewScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
