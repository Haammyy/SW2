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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ContactsQuery {

    /** This method gets a list of Contact and Appointment Objects joined by the Contact ID
     * @return ObservableList Returns list of Contacts
     * @throws SQLException Catches SQLException, prints stacktrace, and error message.
     */
    public static ObservableList<Contacts> getContacts() throws SQLException {
        ObservableList<Contacts> contacts = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM contacts AS c INNER JOIN appointments AS a ON c.Contact_ID = a.Contact_ID;";
        PreparedStatement ps = JDBC.connection.prepareStatement(queryStatement);


        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();;

            // Forward scroll resultSet
            while (rs.next()) {
                Contacts newContact = new Contacts(
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Email")
                );

                contacts.add(newContact);
            }
            return contacts;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    //get contact name by id
    public static String getContactName(int contactId) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactId);

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();;

            //resultSet
            while (rs.next()) {
                Contacts newContact = new Contacts(
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Email")
                );

                return newContact.getContactName();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    //get contact id by name of contact
    public static int getContactIdByName(String name) throws SQLException {
        int id = 0;
        String sql = "SELECT * FROM contacts WHERE Contact_Name=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();;

            //resultSet
            while (rs.next()) {
                Contacts newContact = new Contacts(
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Email")
                );

                id = newContact.getContactId();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return id;
    }


    public static Contacts getContactId(String contactName) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_Name=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactName);

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();;

            //resultSet
            while (rs.next()) {
                Contacts newContact = new Contacts(
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Email")
                );

                return newContact;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    //return all contacts
    public static ObservableList<Contacts> getAllContacts() throws SQLException {
        ObservableList<Contacts> contacts = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM contacts;";
        PreparedStatement ps = JDBC.connection.prepareStatement(queryStatement);

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();;

            // Forward scroll resultSet
            while (rs.next()) {
                Contacts newContact = new Contacts(
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Email")
                );

                contacts.add(newContact);
            }
            return contacts;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

    }
}