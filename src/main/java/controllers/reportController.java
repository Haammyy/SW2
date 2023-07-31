package controllers;

import Model.Appointments;
import Model.Customers;
import helper.AppointmentsQuery;
import helper.NavigationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class reportController {
    //APPOINTMENTS REPORTS TABLE
    @FXML public TableView<Appointments> typeTable;

    @FXML
    TextArea monthText;

    public VBox vboxBind;


    ObservableList appointmentList = FXCollections.observableArrayList();

    @FXML public TableColumn<Appointments, String> titleColumn, descriptionColumn, locationColumn, typeColumn;
    @FXML public TableColumn<Appointments, LocalDateTime> startColumn, endColumn;
    @FXML
    TableColumn<Appointments, Integer>
            T1C1, T1C2,T1C3,T1C4,T1C5,T1C6,T1C7,T1C8,
            T2C1, T2C2,T2C3,T2C4,T2C5,T2C6,T2C7,T2C8,
            T3C1, T3C2,T3C3,T3C4,T3C5,T3C6,T3C7,T3C8,
            T4C1, T4C2,T4C3,T4C4,T4C5,T4C6,T4C7,T4C8;


    @FXML
    public RadioButton radioJan, radioFeb, radioMar, radioApr, radioMay, radioJun, radioJul, radioAug, radioSep, radioOct, radioNov, radioDec;
    @FXML
    Button cancelButton;

    public void onCancelButtonClick(ActionEvent event) {
        NavigationUtil.navigateToHomePage(event);
    }

    public void setTables() throws SQLException {
        typeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        typeTable.prefWidthProperty().bind(vboxBind.widthProperty());
        typeTable.prefHeightProperty().bind(vboxBind.heightProperty());
        setTypeTable();
        setContactTable();
        setEndingTable();

    }


    public void setTypeTable() throws SQLException {

        typeTable.getItems().clear();
        appointmentList = Appointments.getAllAppointmentsType();
        System.out.println(appointmentList);

        T1C1.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        T1C2.setCellValueFactory(new PropertyValueFactory<>("title"));
        T1C3.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        T1C5.setCellValueFactory(new PropertyValueFactory<>("description"));
        T1C6.setCellValueFactory(new PropertyValueFactory<>("type"));
        T1C7.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        T1C8.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        //contactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        T1C4.setCellValueFactory(new PropertyValueFactory<>("userId"));
        typeTable.setItems(appointmentList);

    }
    public static void setContactTable(){


    }
    public static void setEndingTable(){

    }
    String month;
    public void initialize() throws SQLException {
        month = "";
        radioJan.setSelected(false);
        radioFeb.setSelected(false);
        radioMar.setSelected(false);
        radioApr.setSelected(false);
        radioMay.setSelected(false);
        radioJun.setSelected(false);
        radioJul.setSelected(false);
        radioAug.setSelected(false);
        radioSep.setSelected(false);
        radioOct.setSelected(false);
        radioNov.setSelected(false);
        radioDec.setSelected(false);
        monthText.setText("");
        setTables();
    }
    public void checkMonthSelectedClicked(ActionEvent actionEvent) throws SQLException {
        //set month to the currently selected radio button in the group
        monthText.setText("");
        month = "";
        if (radioJan.isSelected()) {
            month = "January";
        } else if (radioFeb.isSelected()) {
            month = "February";
        } else if (radioMar.isSelected()) {
            month = "March";
        } else if (radioApr.isSelected()) {
            month = "April";
        } else if (radioMay.isSelected()) {
            month = "May";
        } else if (radioJun.isSelected()) {
            month = "June";
        } else if (radioJul.isSelected()) {
            month = "July";
        } else if (radioAug.isSelected()) {
            month = "August";
        } else if (radioSep.isSelected()) {
            month = "September";
        } else if (radioOct.isSelected()) {
            month = "October";
        } else if (radioNov.isSelected()) {
            month = "November";
        } else if (radioDec.isSelected()) {
            month = "December";
        }
        if (month.equals("")) {
            return;
        } else {
            int count = 0;
            //if a month is selected, display the number of appointments in that month
            monthText.setText("There are " + AppointmentsQuery.getMonthlyAppointments(month) + " appointments in " + month.toLowerCase() + "."
                    + "\nDe-Briefing: " + AppointmentsQuery.returnNumOfAppointmentsByTypeAndMonth(month,"De-Briefing")
                    + "\nPlanning Session: "+ AppointmentsQuery.returnNumOfAppointmentsByTypeAndMonth(month,"Planning Session")
                    + "\nFollow-Up: "+ AppointmentsQuery.returnNumOfAppointmentsByTypeAndMonth(month,"Follow-Up")
                    + "\nPre-Briefing: "+ AppointmentsQuery.returnNumOfAppointmentsByTypeAndMonth(month,"Pre-Briefing")
                    + "\nOpen Session: "+ AppointmentsQuery.returnNumOfAppointmentsByTypeAndMonth(month,"Open Session"));
        }
    }
}
