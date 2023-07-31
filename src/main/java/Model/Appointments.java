package Model;

import helper.Conversions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class        Appointments {
    public static ObservableList listOfAppointmentsFifteenMinutes = FXCollections.observableArrayList();
    public static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointments> allByTypeAppointments = FXCollections.observableArrayList();
    private int Appointment_ID,customerId,userId,contactId;
    private String title,description,location,type,contactName;
    private LocalDate startDate,endDate;
    private LocalDateTime startTime,endTime;
    public Appointments(int Appointment_ID,
                        String title,
                        String description,
                        String location,
                        String type,
                        LocalDate startDate,
                        LocalDateTime startTime,
                        LocalDate endDate,
                        LocalDateTime endTime,
                        int customerId,
                        int userId,
                        int contactId
    ){
        this.Appointment_ID = Appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public static boolean checkFifteen() {
        //check to see if any appointment end dates are within 15 minutes of current time, compared to the current time of the local machine
        LocalDateTime now = Conversions.toUTC(LocalDateTime.now());
        System.out.println(allAppointments.size() + " appointments in the list");
        LocalDateTime fifteen = now.plusMinutes(15);
        System.out.println("A55: The time: "+ now +" compared to: "+fifteen);
        for (int a = 0; a < allAppointments.size(); a++) {
            if (Conversions.toUTC(allAppointments.get(a).getStartTime()).isAfter(Conversions.toUTC(now)) && (allAppointments.get(a).getStartTime()).isBefore(Conversions.toUTC(fifteen))) {
                System.out.println(allAppointments.get(a).getAppointment_ID());
                listOfAppointmentsFifteenMinutes.add(allAppointments.get(a).getType() + " with " + allAppointments.get(a).getContactId());
                return true;
            }
        }
        Conversions.toAlert("No appointments within 15 minutes");
        return false;
    }

    public static ObservableList<String> getFifteenAppointments() {
        //
        if (!listOfAppointmentsFifteenMinutes.isEmpty()) {
            for (int z=0; listOfAppointmentsFifteenMinutes.size()>z; z++){
            }
        }

        return listOfAppointmentsFifteenMinutes;
    }

    public static int getAllAppointmentsMonth(String month) {
        //return the number of appointments in a given month
        int count = 0;
        for (int a = 0; a < allAppointments.size(); a++) {
            if (allAppointments.get(a).getStartDate().getMonth().toString().equals(month)) {
                count++;
            }
        }
        return count;
    }

    public List<Appointments> getAppointmentsByType(String type) {
        List<Appointments> filteredAppointments = allAppointments.stream()
                .filter(appointment -> appointment.getType().equals(type))
                .collect(Collectors.toList());

        return filteredAppointments;
    }
    public static ObservableList<Appointments> groupByType() throws SQLException {
        return allByTypeAppointments;
    }
    static ObservableList<Appointments> tempAp = allAppointments;
    public void setTempAp(){
        tempAp = allAppointments;
    }



    public void setAllAppointments(Appointments appointments){
        allAppointments.add(appointments);
    }
    public void setAllAppointmentsType(Appointments appointments){
        allByTypeAppointments.add(appointments);
    }


    public static ObservableList<Appointments> getAllAppointments(){
        return allAppointments;
    }
    public static ObservableList<Appointments> getAllAppointmentsType(){
        return allByTypeAppointments;
    }


    public int getAppointment_ID() {
        return Appointment_ID;
    }

    public void setAppointment_ID(int appointment_ID) {
        Appointment_ID = appointment_ID;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


}
