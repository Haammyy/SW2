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
        String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //print what should be created as sql statement
        System.out.println(sql);

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        System.out.println("AQ43 - TIME TO BE SUBMITTED TO DB: " + Conversions.toUTC(start));
        ps.setTimestamp(5, Timestamp.valueOf(Conversions.toUTC(start)));
        ps.setTimestamp(6, Timestamp.valueOf(Conversions.toUTC(end)));
        ps.setInt(7, customerId);
        ps.setInt(8, Contacts.getContactId());
        ps.setInt(9, userID);
        try{

            ps.execute();
            //while (rs.next()){
            if (ps.getUpdateCount() > 0) {
                Appointments.getAllAppointments().clear();
                getAppointmentsTable();
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


    public static void getMonthlyAppointments() throws SQLException {
        int Appointment_ID, customerId, userId, contactId;
        String title,description, location, type,contactName;
        LocalDate startDate;
        LocalDateTime startTime;
        LocalDate endDate;
        LocalDateTime endTime;
        String sql = "SELECT * FROM appointments WHERE MONTH(START) = MONTH(CURRENT_DATE())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        boolean hasResults = false;

        while (rs.next()){
            hasResults = true;
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
            if(!hasResults){
                Conversions.toAlert("There are no appointments this month");
            }
        }

    }
    public static int getMonthlyAppointments(String month) throws SQLException {
        int Appointment_ID, customerId, userId, contactId;
        String title,description, location, type,contactName;
        LocalDate startDate;
        LocalDateTime startTime;
        LocalDate endDate;
        LocalDateTime endTime;
        //sql string that will be used to query the database and find the appointments for the month
        String sql = "SELECT * FROM appointments WHERE MONTHNAME(Start) = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, month);
        ResultSet rs = ps.executeQuery();
        boolean hasResults = false;
    int count = 0;

        while (rs.next()){
            count++;
            hasResults = true;
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
            if(!hasResults){
                Conversions.toAlert("There are no appointments this month");
                return count;
            }
        }
        return count;

    }

    public static void getWeeklyAppointments() throws SQLException {
        //set all appointments to the ones that are in the current week
        Appointments.getAllAppointments().clear();
        int Appointment_ID, customerId, userId, contactId;
        String title,description, location, type,contactName;
        LocalDate startDate;
        LocalDateTime startTime;
        LocalDate endDate;
        LocalDateTime endTime;

        String sql = "SELECT * FROM appointments WHERE WEEK(START) = WEEK(CURRENT_DATE())";
        //print the data retrieved to the console
        System.out.println(sql);

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        //print result set to console
        System.out.println(rs);

//        if (!rs.next()){
//            Conversions.toAlert("There are no appointments this week");
//        }
        boolean hasResults = false;
        while (rs.next()){
            hasResults = true;
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
        if (!hasResults){
            Conversions.toAlert("There are no appointments this week");
        }
        }

    }

    public static boolean updateAppointment(int contactID, String title, String description,
                                            String location, String type, LocalDateTime start,
                                            LocalDateTime end, int customerId, int userId, int appointmentId) throws SQLException {
       try{
           System.out.println("start time: " + start);
           System.out.println("start time in UTC: " + Conversions.toUTC(start));
           String sql =
                   "UPDATE appointments SET Title = ?, " +
                   "Description = ?, " +
                   "Location = ?, " +
                   "Type = ?, " +
                   "Start = ?, " +
                   "End = ?, " +
                   "Customer_ID = ?," +
                   " Contact_ID = ?," +
                   " User_ID = ? " +
                   "WHERE Appointment_ID = ?";
           System.out.println(sql);
           PreparedStatement ps = JDBC.connection.prepareStatement(sql);
           ps.setString(1, title);
           ps.setString(2, description);
           ps.setString(3, location);
           ps.setString(4, type);
           //set start and end times
           ps.setTimestamp(5, Timestamp.valueOf(Conversions.toUTC(start)));
           ps.setTimestamp(6, Timestamp.valueOf(Conversions.toUTC(end)));
           //set the remaining values
           ps.setInt(7, customerId);
           ps.setInt(8, contactID);
           ps.setInt(9, userId);
           ps.setInt(10, appointmentId);
           ps.executeUpdate();

           Appointments.getAllAppointments().clear();
           getAppointmentsTable();
           return true;    }
       catch (Exception e){
            Conversions.toAlert("could not update this appointment, double check format");
            throw new RuntimeException(e);
        }


    }

    public static int returnNumOfAppointmentsByTypeAndMonth(String month, String type) throws SQLException{
        int count = 0;
        String sql = "SELECT * FROM appointments WHERE MONTHNAME(Start) = ? AND Type = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, month);
        ps.setString(2, type);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            count++;
        }
        return count;
    }

    public static void getAppointmentsByType() throws SQLException {
    int Appointment_ID, customerId, userId, contactId;
    String title,description, location, type,contactName;
    LocalDate startDate;
    LocalDateTime startTime;
    LocalDate endDate;
    LocalDateTime endTime;
    String sql = "SELECT * FROM appointments ORDER BY Type";
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
        Appointments appointmentsByType = new Appointments(Appointment_ID, title, description,
                location, type, startDate, startTime, endDate, endTime, customerId, userId, contactId);
        appointmentsByType.setAllAppointmentsType(appointmentsByType);
        if(!Appointments.allByTypeAppointments.contains(appointmentsByType)){
            appointmentsByType.setAllAppointments(appointmentsByType);
        }

    }
    }


}
