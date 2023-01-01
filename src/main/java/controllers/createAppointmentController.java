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
    @FXML public Button saveAppointment, cancelAppointment;

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
    public void initialize(){
        
    }
}
