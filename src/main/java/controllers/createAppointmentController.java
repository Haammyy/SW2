package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class createAppointmentController {

    //save appointment & cancel appointment FXML Button objects:
    @FXML public Button saveAppointment, cancelAppointment;


    /**
     * saveAppointment method handles when the save button is clicked within the "create Appointment" window
     * @param event
     * @throws IOException
     */
    public void saveAppointmentClicked(ActionEvent event) throws IOException {

        //set scene to go home
        System.out.println("save appointment clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/scheduleView.fxml"));
        Parent viewParent = loader.load();
        Scene viewScene = new Scene(viewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("");
        window.setScene(viewScene);
        window.show();
    }

    /**
     * cancelAppointment method handles when the cancel button is clicked within the "createAppointment" window
     * @param event
     * @throws IOException
     */
    public void cancelAppointmentClicked(ActionEvent event) throws IOException{
        System.out.println("cancel appointment clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/scheduleView.fxml"));
        Parent viewParent = loader.load();
        Scene viewScene = new Scene(viewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("");
        window.setScene(viewScene);
        window.show();
    }

    /**
     * initialize runs every time the window is created
     */
    public void initialize(){
        
    }
}
