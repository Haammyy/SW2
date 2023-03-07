package helper;

import Model.Appointments;
import Model.Customers;
import Model.Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
public class AppointmentsQuery {
    public static int deleteAppointment(int selectedAppointment) throws  SQLException{
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, selectedAppointment);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
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
