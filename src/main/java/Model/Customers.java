package Model;
import helper.Conversions;
import helper.CustomersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class Customers {

    public static ObservableList allCustomers = FXCollections.observableArrayList();
    private int customerId,divisionId;
    private String customerName, address, postalCode,
            phoneNumber, division,country;

    public Customers(int customerId,
                     String customerName,
                     String address,
                     String postalCode,
                     String phoneNumber,
                     //String division,
//                    String country,
                     int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }
    public static boolean deleteCustomer(Customers selectedCustomer) throws SQLException {
        if(selectedCustomer == null){
            Conversions.toAlert("No Customer was selected!");
            return false;
        }
        //Customers deletedCustomer = getAllCustomers().get(getAllCustomers().indexOf(selectedCustomer));
        CustomersQuery.deleteCustomer(selectedCustomer.customerId);
        return true;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public void setAllCustomer(Customers customer) throws SQLException {
        //if allcustomers does not already have the insertion object
        if(allCustomers.size()==0){
            allCustomers.add(customer);
        }
        if(!allCustomers.contains(customer)){
            allCustomers.add(customer);
        }
    }

    public static ObservableList<Customers> getAllCustomers(){
        return allCustomers;
    }
}
