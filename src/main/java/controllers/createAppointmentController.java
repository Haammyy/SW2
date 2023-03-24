package controllers;

import helper.NavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.events.UIEvent;

import java.io.IOException;

public class createAppointmentController {

    //save appointment & cancel appointment FXML Button objects:
    @FXML
    public Button saveAppointment, cancelAppointment;
    public DatePicker startDatePicker, endDatePicker;

    public TextField customerIdField, userIdField, titleField, descriptionField;

    public SplitMenuButton contactMenu, typeMenu,
            startHourPicker, startMinutePicker, startSecondPicker,
            endHourPicker, endMinutePicker, endSecondPicker;




    /**
     * saveAppointment method handles when the save button is clicked within the "create Appointment" window
     * @param event
     * @throws IOException
     */
    public void saveAppointmentClicked(ActionEvent event) throws IOException {


        NavigationUtil.navigateToHomePage(event);
    }

    /**
     * cancelAppointment method handles when the cancel button is clicked within the "createAppointment" window
     * @param event
     * @throws IOException
     */
    public void cancelAppointmentClicked(ActionEvent event) throws IOException{
        NavigationUtil.navigateToHomePage(event);
    }

    /**
     * initialize runs every time the window is created
     */
    public void initialize(){
        
    }
}
