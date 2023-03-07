package controllers;
import Model.Appointments;
import Model.Customers;
import Model.Divisions;
import helper.AppointmentsQuery;
import helper.Conversions;
import helper.CustomersQuery;
import helper.DivisionsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static java.lang.Integer.parseInt;
public class scheduleViewController {
    //BUTTONS
    @FXML private Button addAppointmentButton,addCustomerButton,updateCustomerButton,deleteCustomerButton,updateAppointmentButton,deleteAppointmentButton;

    //CUSTOMER TABLE
    @FXML public TableView<Customers> customersTableView;
    @FXML public TableColumn<Customers, Integer> idColumn,divisionColumn;
    @FXML public TableColumn<Customers, String> nameColumn, addressColumn, postalCodeColumn, phoneNumberColumn;

    //APPOINTMENT TABLE
    @FXML public TableView<Appointments> appointmentsTableView;
    @FXML public TableColumn<Appointments, Integer> appointmentIDColumn,customerIDColumn, contactIDColumn;
    @FXML public TableColumn<Appointments, String> titleColumn, descriptionColumn, locationColumn, typeColumn;
    @FXML public TableColumn<Appointments, LocalDateTime> startColumn, endColumn;

    //LISTS TO BE FILLED WITH TABLE DATA
    ObservableList customerList = FXCollections.observableArrayList();
    ObservableList appointmentList = FXCollections.observableArrayList();

    //CURRENT SELECTED OBJECT FROM TABLE (CUSTOMER OR APPOINTMENT TABLE)
    public static Customers selectedCustomer = null;
    public static Appointments selectedAppointment = null;

    /**
     * Changes Scene, click this to create a new appointment
     * @param event
     * @throws IOException
     */
    public void addAppointment(ActionEvent event) throws IOException {
        System.out.println("Create appointment clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/createAppointmentView.fxml"));
        Parent viewParent = loader.load();
        Scene viewScene = new Scene(viewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Create Appointment");
        window.setScene(viewScene);
        window.show();
    }

    /**
     * Changes Scene, click this to create a new Customer
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void addCustomer(ActionEvent event) throws IOException, SQLException {
        System.out.println("create customer clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/createCustomerView.fxml"));
        Parent viewParent = loader.load();
        Scene viewScene = new Scene(viewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Create Customer");
        window.setScene(viewScene);
        window.show();
    }

    /**
     * Loads data from the selected customer, then switches scenes.
     * @param event
     * @throws IOException
     */
    public void updateCustomer(ActionEvent event) throws IOException {

        if (customersTableView.getSelectionModel().getSelectedItem() == null) {
            Conversions.toAlert("Please select an item to modify");
            throw new IOException();
        }
        selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        System.out.println("update customer clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/modifyCustomerView.fxml"));
        Parent viewParent = loader.load();
        Scene viewScene = new Scene(viewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Modify Customer");
        window.setScene(viewScene);
        window.show();
    }

    /**
     * sends a confirmation, then deletes selected Customer upon confirmation
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML public void deleteCustomer(ActionEvent event) throws IOException, SQLException {
        if (customersTableView.getSelectionModel().getSelectedItem() == null) {
            Conversions.toAlert("Please select an item to modify");
            throw new IOException();
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + customersTableView.getSelectionModel().getSelectedItem().getCustomerName() + " from table?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            System.out.println("Delete button Pressed");
            Customers.deleteCustomer(customersTableView.getSelectionModel().getSelectedItem());
            customersTableView.getItems().clear();
            CustomersQuery.getCustomerTable();
            customerList = Customers.getAllCustomers();
            customersTableView.setItems(customerList);

            //Customers.getAllCustomers();
        }
    }
    public void updateAppointment(ActionEvent event) throws IOException {
        System.out.println("update appointment clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/modifyAppointmentView.fxml"));
        Parent viewParent = loader.load();
        Scene viewScene = new Scene(viewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Update Appointment");
        window.setScene(viewScene);
        window.show();
    }
    public void deleteAppointment(ActionEvent event) throws IOException, SQLException {
        if (appointmentsTableView.getSelectionModel().getSelectedItem() == null) {
            Conversions.toAlert("Please select an item to modify");
            throw new IOException();
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + appointmentsTableView.getSelectionModel().getSelectedItem().getAppointment_ID() + " from table?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            System.out.println("Delete button Pressed");
            AppointmentsQuery.deleteAppointment(appointmentsTableView.getSelectionModel().getSelectedItem().getAppointment_ID());
            appointmentsTableView.getItems().clear();
            AppointmentsQuery.getAppointmentsTable();
            appointmentList = Appointments.getAllAppointments();
            appointmentsTableView.setItems(appointmentList);

            //appointments.getAllappointments();
        }
    }
    public void reportsClicked(ActionEvent event) throws IOException{
        System.out.println("reports clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/reportView.fxml"));
        Parent viewParent = loader.load();
        Scene viewScene = new Scene(viewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Reports");
        window.setScene(viewScene);
        window.show();
    }
    public void initialize() throws SQLException {
        DivisionsQuery divQue = new DivisionsQuery();
        divQue.getDivisions();

        customersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        customerList = Customers.getAllCustomers();
        System.out.println("List of all "+customerList.size()+" customer objects: " + customerList);
        //customersTableView.getItems().clear();
        customersTableView.setItems(customerList);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        appointmentsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        appointmentList = Appointments.getAllAppointments();
        System.out.println("List of all "+appointmentList.size()+" appointment objects: " + appointmentList);
        //appointmentsTableView.getItems().clear();
        appointmentsTableView.setItems(appointmentList);

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        contactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));

    }
}
