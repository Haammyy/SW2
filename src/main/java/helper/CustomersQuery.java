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
public class CustomersQuery {
    static ObservableList<Customers> custList = FXCollections.observableArrayList();

    /**
     * CREATES A NEW CUSTOMER
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param division
     * @return
     * @throws SQLException
     */
    public static boolean createCustomer(String name, String address, String postalCode, String phone, int division) throws SQLException{

        String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //ResultSet rs = ps.executeQuery();

        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, division);
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

    /**
     * READS ALL ITEMS FROM THE TABLE, CREATES A MODEL CUSTOMER FROM ALL THE TABLE DATA
     * @throws SQLException
     */
    public static void getCustomerTable() throws SQLException {
        int customerId;
        String customerName;
        String address;
        String postalCode;
        String phoneNumber;
        String country;
        int divisionId;
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            customerId = rs.getInt("Customer_ID");
            customerName = rs.getString("Customer_Name");
            address = rs.getString("Address");
            postalCode = rs.getString("Postal_Code");
            phoneNumber = rs.getString("Phone");
            divisionId = rs.getInt("Division_ID");
            Customers customer = new Customers(customerId, customerName, address, postalCode, phoneNumber, divisionId);
            if (!Customers.allCustomers.contains(customer)) {
                customer.setAllCustomer(customer);
            }
        }

    }

    /**
     * TAKES IN THE UPDATED INFO, AND SAVES IT TO THE SERVER
     * @param customerId
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param division
     * @return
     * @throws SQLException
     */
    public static boolean updateCustomer(int customerId, String name, String address, String postalCode, String phone, String division) throws SQLException {
        //Division newDivision = DivisionQuery.getDivisionId(division);
        System.out.println(customerId);
        String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //ResultSet rs = ps.executeQuery();

        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, Integer.parseInt(division));
        ps.setInt(6, customerId);
        ps.execute();
        Customers.getAllCustomers().clear();
        getCustomerTable();
        return true;
    }

    /**
     * REMOVES THE DELETED CUSTOMER FROM THE SERVER
     * @param selectedCustomerID
     * @return
     * @throws SQLException
     */
    public static int deleteCustomer(int selectedCustomerID) throws  SQLException{
        String sql = "DELETE FROM customers WHERE customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, selectedCustomerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
