package controllers;

import Model.Appointments;
import Model.Users;
import helper.ContactsQuery;
import helper.Conversions;
import helper.NavigationUtil;
import helper.UsersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import static controllers.scheduleViewController.selectedAppointment;
import static controllers.scheduleViewController.selectedCustomer;
import static java.lang.Integer.parseInt;

public class modifyAppointmentsController {

    //save appointment & cancel appointment FXML Button objects:
    //When adding and updating an appointment, record the following data: Appointment_ID, title, description, location, contact, type, start date and time, end date and time, Customer_ID, and User_ID.

    //save appointment & cancel appointment FXML Button objects:
    //When adding and updating an appointment, record the following data: Appointment_ID, title, description, location, contact, type, start date and time, end date and time, Customer_ID, and User_ID.
    @FXML
    public Button saveAppointment, cancelAppointment;
    public DatePicker startDatePicker, endDatePicker;

    public TextField customerIdField, userIdField, titleField, descriptionField, locationField;

    public ComboBox<String> contactMenu, typeMenu,
            startHourPicker, startMinutePicker, startSecondPicker,
            endHourPicker, endMinutePicker, endSecondPicker;

    public void saveAppointmentClicked(ActionEvent event) throws IOException, SQLException {
        //get the values from the fields
        String customerId = customerIdField.getText();
        String userId = userIdField.getText();
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        int contactID = ContactsQuery.getContactIdByName(contactMenu.getValue());
        String type = typeMenu.getValue();
        LocalDateTime start = LocalDateTime.of(startDatePicker.getValue(), LocalTime.of(parseInt(startHourPicker.getValue()), parseInt(startMinutePicker.getValue()), parseInt(startSecondPicker.getValue())));
        LocalDateTime end = LocalDateTime.of(endDatePicker.getValue(), LocalTime.of(parseInt(endHourPicker.getValue()), parseInt(endMinutePicker.getValue()), parseInt(endSecondPicker.getValue())));
        int appointmentId = selectedAppointment.getAppointment_ID();

        //if the fields are empty, display an error message and do not save the appointment
        if (customerId.isEmpty() || userId.isEmpty() || title.isEmpty() || description.isEmpty() || location.isEmpty() || contactMenu.getValue().isEmpty() || type.isEmpty() || String.valueOf(start).isEmpty() || String.valueOf(end).isEmpty()){
            System.out.println("ERROR: One or more fields are empty");
            Conversions.toAlert("One or more fields are empty");
            throw new RuntimeException();
        }
        else {
            //if the fields are not empty, save the appointment to the database using jdbc
            helper.AppointmentsQuery.updateAppointment(contactID,
                    title,
                    description,
                    location,
                    type,
                    start,
                    end,
                    parseInt(customerId),
                    parseInt(userId),
                    appointmentId);
            System.out.println("Appointment saved");
            NavigationUtil.navigateToHomePage(event);
        }

    }

    /**
     * cancelAppointment method handles when the cancel button is clicked within the "createAppointment" window
     * @param event
     * @throws IOException
     */
    public void cancelAppointmentClicked(ActionEvent event) throws IOException{
        System.out.println("Cancel button clicked");
        NavigationUtil.navigateToHomePage(event);
    }

    public void onContactMenuClicked(ActionEvent event) throws IOException{

    }

    public void setContacts() throws SQLException {

        contactMenu.getItems().clear();
        for (int i = 0; i < ContactsQuery.getAllContacts().size(); i++) {
            MenuItem menuItem = new MenuItem(ContactsQuery.getAllContacts().get(i).getContactName());
            menuItem.setOnAction(event -> {
                contactMenu.setValue(menuItem.getText());
            });

            contactMenu.getItems().add(ContactsQuery.getAllContacts().get(i).getContactName());
        }
    }
    public void initialize() throws SQLException {
        setContacts();
        //unable to edit:
        customerIdField.setText(String.valueOf(selectedAppointment.getCustomerId()));

        System.out.println(ContactsQuery.getContactName(selectedAppointment.getContactId()));
        contactMenu.setValue(ContactsQuery.getContactName(selectedAppointment.getContactId()));


        //set the values for every field to the values of the selected appointment
        userIdField.setText(String.valueOf(selectedAppointment.getUserId()));
        titleField.setText(selectedAppointment.getTitle());
        descriptionField.setText(selectedAppointment.getDescription());
        locationField.setText(selectedAppointment.getLocation());
        typeMenu.setValue(selectedAppointment.getType());
        startDatePicker.setValue(selectedAppointment.getStartDate());

        //set the start time pickers
        startHourPicker.setValue(String.valueOf(selectedAppointment.getStartTime().getHour()));
        startMinutePicker.setValue(String.valueOf(selectedAppointment.getStartTime().getMinute()));
        startSecondPicker.setValue(String.valueOf(selectedAppointment.getStartTime().getSecond()));

        //set the end time pickers
        endHourPicker.setValue(String.valueOf(selectedAppointment.getEndTime().getHour()));
        endMinutePicker.setValue(String.valueOf(selectedAppointment.getEndTime().getMinute()));
        endSecondPicker.setValue(String.valueOf(selectedAppointment.getEndTime().getSecond()));

        //set the end date picker
        endDatePicker.setValue(selectedAppointment.getEndDate());

        //set the contact menu and type menu
        typeMenu.setValue(selectedAppointment.getType());
        ObservableList<String> typeList = FXCollections.observableArrayList();
        typeList.addAll("Planning Session", "De-Briefing", "Follow-up", "Pre-Briefing", "Open Session");
        typeMenu.setItems(typeList);
//        UsersQuery.getUsers();
//        userIdField.setText(String.valueOf(Users.getUserId()));
        ObservableList<String> hourList = FXCollections.observableArrayList();
        ObservableList<String> minuteList = FXCollections.observableArrayList();
        ObservableList<String> secondList = FXCollections.observableArrayList();

        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hourList.add("0" + i);
            } else {
                hourList.add(String.valueOf(i));
            }
        }
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                minuteList.add("0" + i);
                secondList.add("0" + i);
            } else {
                minuteList.add(String.valueOf(i));
                secondList.add(String.valueOf(i));
            }
        }
        startHourPicker.setItems(hourList);
        startMinutePicker.setItems(minuteList);
        startSecondPicker.setItems(secondList);
        endHourPicker.setItems(hourList);
        endMinutePicker.setItems(minuteList);
        endSecondPicker.setItems(secondList);
    }


}
