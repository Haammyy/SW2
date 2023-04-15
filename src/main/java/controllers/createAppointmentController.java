package controllers;

import Model.Appointments;
import helper.ContactsQuery;
import helper.Conversions;
import helper.NavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.events.UIEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static controllers.scheduleViewController.selectedCustomer;
import static java.lang.Integer.parseInt;

public class createAppointmentController {

    //save appointment & cancel appointment FXML Button objects:
    //When adding and updating an appointment, record the following data: Appointment_ID, title, description, location, contact, type, start date and time, end date and time, Customer_ID, and User_ID.
    @FXML
    public Button saveAppointment, cancelAppointment;
    public DatePicker startDatePicker, endDatePicker;

    public TextField customerIdField, userIdField, titleField, descriptionField, locationField;

    public SplitMenuButton contactMenu, typeMenu,
            startHourPicker, startMinutePicker, startSecondPicker,
            endHourPicker, endMinutePicker, endSecondPicker;




    /**
     * saveAppointment method handles when the save button is clicked within the "create Appointment" window
     * @param event
     * @throws IOException
     */
    public void saveAppointmentClicked(ActionEvent event) throws IOException, SQLException {
        //get the values from the fields
        String customerId = customerIdField.getText();
        String userId = userIdField.getText();
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String contact = contactMenu.getText();
        String type = typeMenu.getText();
        LocalDateTime start = LocalDateTime.of(startDatePicker.getValue(), LocalTime.of(parseInt(startHourPicker.getText()), parseInt(startMinutePicker.getText()), parseInt(startSecondPicker.getText())));
        LocalDateTime end = LocalDateTime.of(endDatePicker.getValue(), LocalTime.of(parseInt(endHourPicker.getText()), parseInt(endMinutePicker.getText()), parseInt(endSecondPicker.getText())));
        //create a new appointment object

        //if the fields are empty, display an error message and do not save the appointment
        if (customerId.isEmpty() || userId.isEmpty() || title.isEmpty() || description.isEmpty() || location.isEmpty() || contact.isEmpty() || type.isEmpty() || String.valueOf(start).isEmpty() || String.valueOf(end).isEmpty()){
            System.out.println("ERROR: One or more fields are empty");
            Conversions.toAlert("One or more fields are empty");
            throw new RuntimeException();
        }
        else {
            //if the fields are not empty, save the appointment to the database using jdbc
            helper.AppointmentsQuery.createAppointment(contact,
                    title,
                    description,
                    location,
                    type,
                    start,
                    end,
                    parseInt(customerId),
                    parseInt(userId));
            NavigationUtil.navigateToHomePage(event);
        }

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

    public void setContacts() throws SQLException {
        for(int i = 0; i <ContactsQuery.getContacts().size(); i++){
            MenuItem newItem = new MenuItem(String.valueOf(ContactsQuery.getContacts().get(i)));
            contactMenu.getItems().add(newItem);
        }
    }
    /**
     * initialize runs every time the window is created
     */
    public void initialize() throws SQLException {
        customerIdField.setText(String.valueOf(selectedCustomer.getCustomerId()));
        setContacts();
        //set the default values for the start and end time pickers
        startHourPicker.setText("00");
        startMinutePicker.setText("00");
        startSecondPicker.setText("00");
        endHourPicker.setText("00");
        endMinutePicker.setText("00");
        endSecondPicker.setText("00");

        //set the default value for the contact menu
        contactMenu.setText("Select Contact");

        //set the default value for the type menu
        typeMenu.setText("Select Type");

        //set the default value for the start date picker
        startDatePicker.setValue(LocalDate.now());

        //set the default value for the end date picker
        endDatePicker.setValue(LocalDate.now());
    }
}
