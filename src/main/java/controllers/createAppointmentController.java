package controllers;

import Model.Appointments;
import helper.Conversions;
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

import static java.lang.Integer.parseInt;

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
        //create a new appointment object using the field information from the window if the fields are not empty,make the hour, minute, and second fields constrained to the correct values of 0-23, 0-59, 0-59 make the date fields constrained to the correct values of 1-12, 1-31, 1-9999 make the customerId, userId, and contactId fields constrained to only accept integers make the title, description, and location fields constrained to only accept strings

        //if the fields are empty, display an error message and do not save the appointment


        //if the fields are not empty, save the appointment and navigate to the home page
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

    public void onContactMenuClicked(ActionEvent event) throws IOException{
        //create an arrayList of contacts, then populate the menu with the contacts
        //when a contact is clicked, set the contactMenu text to the contact name





    }

    /**
     * initialize runs every time the window is created
     */
    public void initialize(){
        
    }
}
