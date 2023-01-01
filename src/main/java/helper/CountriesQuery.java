package helper;

import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesQuery {
    public static int getCountryByDivision(int divisionID) throws SQLException{
        String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        try {
            rs.next();
            return rs.getInt("Country_ID");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }
    public static String getCountryNameByCountryID(int divisionID) throws SQLException {
        int CountryID = getCountryByDivision(divisionID);
        String sql = "SELECT Country FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, CountryID);
        ResultSet rs = ps.executeQuery();
        try {
            rs.next();
            return rs.getString("Country");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static ObservableList<Countries> getCountryList() throws SQLException {
        ObservableList<Countries> countries = FXCollections.observableArrayList();
        int Country_ID;
        String Country;
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Country_ID = rs.getInt("Country_ID");
            Country = rs.getString("Country");
            Countries country = new Countries(Country_ID, Country);
//            if (!Countries.allCustomers.contains(customer)) {
//                customer.setAllCustomer(customer);
//            }
            countries.add(country);
        }
        return countries;
    }

    public static ObservableList<String> getCountryName() throws SQLException {
        ObservableList<String> names = FXCollections.observableArrayList();
        String Country;
        String sql = "SELECT Country FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Country = rs.getString("Country");
            names.add(Country);
        }
        return names;
    }
    /**
     * This method will get the Country ID FROM THE countries TABLE
     * @param country
     * @return
     * @throws SQLException
     */
    public static int getCountryId(String country) throws SQLException{
        String sql = "SELECT Country_ID from countries where Country = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, country);
        ResultSet rs = ps.executeQuery();

        try {
            rs.next();
            return rs.getInt("Country_ID");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }




    }
