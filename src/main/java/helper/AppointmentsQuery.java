package helper;

import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static helper.CustomersQuery.getCustomerTable;

public class AppointmentsQuery {
    public static int deleteAppointment(int selectedAppointment) throws  SQLException{
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, selectedAppointment);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static boolean createAppointment(String contactName, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customerId, Integer userID) throws SQLException{
        Contacts contact = ContactsQuery.getContactId(contactName);

        String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7, customerId);
        ps.setInt(8, Contacts.getContactId());
        ps.setInt(9, userID);
        try{

            ps.execute();
            //while (rs.next()){
            if (ps.getUpdateCount() > 0) {
                Customers.getAllCustomers().clear();
                getCustomerTable();
                System.out.println("Rows affected: " + ps.getUpdateCount());
            }
            else {System.out.println("No change");}

            //}

            return true;

        }
        catch (Exception e ){
            Conversions.toAlert("could not add this customer, double check format");
            throw new RuntimeException(e);
        }

    }

    public static void getAppointmentsTable() throws SQLException {
        int Appointment_ID, customerId, userId, contactId;
        String title,description, location, type,contactName;
        LocalDate startDate;
        LocalDateTime startTime;
        LocalDate endDate;
        LocalDateTime endTime;
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            customerId = rs.getInt("Customer_ID");
            Appointment_ID = rs.getInt("Appointment_ID");
            userId = rs.getInt("User_ID");
            contactId = rs.getInt("Contact_ID");
            title = rs.getString("Title");
            description = rs.getString("Description");
            location = rs.getString("Location");
            type = rs.getString("Type");
            startDate = rs.getDate("Start").toLocalDate();
            endDate = rs.getDate("End").toLocalDate();
            startTime = rs.getTimestamp("Start").toLocalDateTime();
            endTime = rs.getTimestamp("End").toLocalDateTime();
            Appointments appointments = new Appointments(Appointment_ID, title, description,
                    location, type, startDate, startTime, endDate, endTime, customerId, userId, contactId);
            appointments.setAllAppointments(appointments);
            if(!Appointments.allAppointments.contains(appointments)){
                appointments.setAllAppointments(appointments);
            }

        }

    }



}