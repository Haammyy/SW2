package controllers;
import Model.Appointments;
import Model.Customers;
import Model.Divisions;
import helper.*;
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
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static helper.AppointmentsQuery.getAppointmentsByType;
import static java.lang.Integer.parseInt;
public class scheduleViewController {
    //BUTTONS
    @FXML private Button reportsButton,addAppointmentButton,addCustomerButton,updateCustomerButton,deleteCustomerButton,updateAppointmentButton,deleteAppointmentButton;

    //CUSTOMER TABLE
    @FXML public TableView<Customers> customersTableView;
    @FXML public TableColumn<Customers, Integer> idColumn,divisionColumn;
    @FXML public TableColumn<Customers, String> nameColumn, addressColumn, postalCodeColumn, phoneNumberColumn;

    //APPOINTMENT TABLE
    @FXML public TableView<Appointments> appointmentsTableView;
    @FXML public TableColumn<Appointments, Integer> appointmentIDColumn,customerIDColumn, userIDColumn;
    @FXML public TableColumn<Appointments, String> titleColumn, descriptionColumn, locationColumn, typeColumn;
    @FXML public TableColumn<Appointments, LocalDateTime> startColumn, endColumn;

    //add radio buttons
    @FXML private RadioButton monthlyAppointmentsRadioButton,weeklyAppointmentsRadioButton;


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
        if (customersTableView.getSelectionModel().getSelectedItem() == null) {
            Conversions.toAlert("Please select a customer");
            throw new IOException();
        }
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
     * Go to the reports stack pane
     */
    public void reportsButtonClicked(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/reportView.fxml"));
        Parent viewParent = loader.load();
        Scene viewScene = new Scene(viewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Reports");
        window.setScene(viewScene);
        window.show();
    }

    //change the appointment table to show monthly appointments
    public void monthlyRadioClicked(ActionEvent event) throws IOException, SQLException {
        System.out.println("Monthly appointments radio button clicked");
        appointmentsTableView.getItems().clear();
        AppointmentsQuery.getMonthlyAppointments();
        appointmentList = Appointments.getAllAppointments();
        appointmentsTableView.setItems(appointmentList);
    }

    //change the appointment table to show weekly appointments
    public void weeklyRadioClicked(ActionEvent event) throws IOException, SQLException {

        System.out.println("Weekly appointments radio button clicked");
        appointmentsTableView.getItems().clear();
        AppointmentsQuery.getWeeklyAppointments();
        appointmentList = Appointments.getAllAppointments();
        appointmentsTableView.setItems(appointmentList);
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
        selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        if (customersTableView.getSelectionModel().getSelectedItem() == null) {
            Conversions.toAlert("Please select an item to modify");
            throw new IOException();
        }
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete Customer: " + customersTableView.getSelectionModel().getSelectedItem().getCustomerName() + " from table?\nALL APPOINTMENTS ASSOCIATED WILL ALSO BE REMOVED", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            System.out.println("Delete button Pressed");
            Customers.deleteCustomer(customersTableView.getSelectionModel().getSelectedItem());
            customersTableView.getItems().clear();
            CustomersQuery.getCustomerTable();
            customerList = Customers.getAllCustomers();
            customersTableView.setItems(customerList);
            deleteAppointmentsForCustomer(selectedCustomer.getCustomerId());
            //update the appointment table
            appointmentsTableView.getItems().clear();
            AllRadioClicked(event);
        }
    }
    public static void deleteAppointmentsForCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE customer_id = ?";
        try (PreparedStatement statement = JDBC.connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
        }
    }

    public void updateAppointment(ActionEvent event) throws IOException {
        selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete Appointment #" +
                appointmentsTableView.getSelectionModel().getSelectedItem().getAppointment_ID() +
                " from table? \nThis is a "+ appointmentsTableView.getSelectionModel().getSelectedItem().getType() + " Meeting", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            System.out.println("Delete button Pressed");
            AppointmentsQuery.deleteAppointment(appointmentsTableView.getSelectionModel().getSelectedItem().getAppointment_ID());
            appointmentsTableView.getItems().clear();
            AppointmentsQuery.getAppointmentsTable();
            appointmentList = Appointments.getAllAppointments();
            appointmentsTableView.setItems(appointmentList);
        }
    }
    public void reportsClicked(ActionEvent event) throws IOException, SQLException {
        AppointmentsQuery.getAppointmentsByType();
        System.out.println("reports clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/reportView.fxml"));
        Parent viewParent = loader.load();
        Scene viewScene = new Scene(viewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Reports");
        window.setScene(viewScene);
        window.show();
    }
    //make it so that double-clicking a customer takes you to the modify customer screen
    public void customerClicked(javafx.scene.input.MouseEvent event) throws IOException {
        //set the selected customer to the customer that was clicked
        selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

        if (event.getClickCount() == 2) {
            System.out.println("double clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/modifyCustomerView.fxml"));
            Parent viewParent = loader.load();
            Scene viewScene = new Scene(viewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Modify Customer");
            window.setScene(viewScene);
            window.show();
        }
    }

    public void appointmentClicked(javafx.scene.input.MouseEvent event) throws IOException {

        if (event.getClickCount() == 2) {
            selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
            System.out.println("double clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/modifyAppointmentView.fxml"));
            Parent viewParent = loader.load();
            Scene viewScene = new Scene(viewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Modify Appointment");
            window.setScene(viewScene);
            window.show();
        }
    }

    public void initialize() throws SQLException {
        appointmentsTableView.getItems().clear();

        DivisionsQuery divQue = new DivisionsQuery();
        divQue.getDivisions();

        //customersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        customerList = Customers.getAllCustomers();
        customersTableView.setItems(customerList);


        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        appointmentsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        appointmentList = Appointments.getAllAppointments();
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
        //contactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        AllRadioClicked(null);


    }

    public void AllRadioClicked(ActionEvent actionEvent) throws SQLException {
        appointmentsTableView.getItems().clear();
        AppointmentsQuery.getAppointmentsTable();
        appointmentList = Appointments.getAllAppointments();
        for(int i = 0 ; i< appointmentList.size() ; i++){
            //if the appointment at index i is not in the list, add it
            if(!appointmentList.contains(appointmentList.get(i))){
                appointmentList.add(appointmentList.get(i));
            }
        }}

}
