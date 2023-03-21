package application;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Entry point to the application.
 */
public class HelloApplication extends Application {
    /**
     * start will open up the "hello-view" a.k.a. the "login" screen.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method executes "launch" which will begin the Application program loop
     * @param args
     * @throws SQLException
     */

    public static void main(String[] args) throws SQLException {
        launch();
        System.out.println("connection closing");
        JDBC.closeConnection();
    }
}