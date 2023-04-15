package controllers;

import Model.Divisions;
import helper.Conversions;
import helper.CountriesQuery;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static controllers.scheduleViewController.selectedCustomer;

public class modifyCustomerController {
    @FXML public Button saveCustomer, cancelCustomer;
    @FXML public TextField nameField, phoneField, addressField, postalField;
    @FXML public ComboBox<String> countryMenu;
    @FXML public ComboBox<String> stateMenu;

    String currentState = DivisionsQuery.getDivisionNameByDivisionID(selectedCustomer.getDivisionId());

    public modifyCustomerController() throws SQLException {
    }

    public void goHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/scheduleView.fxml"));
        Parent viewParent = loader.load();
        Scene viewScene = new Scene(viewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("");
        window.setScene(viewScene);
        window.show();
    }
    /**
     * Save button clicked, save a new object (if all fields are full), then go home
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void saveCustomerClicked(ActionEvent event) throws IOException, SQLException {
        System.out.println("***\nDATA SUPPOSED TO BE UPDATED: \n"+phoneField.getText()+"\n"+nameField.getText()+"\n"+addressField.getText()+"\n"+postalField.getText()+"\n"+currentState+selectedCustomer.getCustomerId()+"\n***");


        if (    phoneField   == null ||
                nameField    == null ||
                addressField == null ||
                postalField  == null ||
                countryMenu  == null ||
                stateMenu    == null  ){
            Conversions.toAlert("No fields may be empty to create a new customer");
            throw new IOException();
        }
        //check if all fields are identical to the selected customer, if so, return to home instead of updating the database

        //this was not needed after fixing the bug where no value was assigned to the stateMenu

//        if (    phoneField.getText().equals(selectedCustomer.getPhoneNumber()) &&
//                nameField.getText().equals(selectedCustomer.getCustomerName()) &&
//                addressField.getText().equals(selectedCustomer.getAddress()) &&
//                postalField.getText().equals(selectedCustomer.getPostalCode()) &&
//                countryMenu.getPromptText().equals(CountriesQuery.getCountryNameByCountryID(selectedCustomer.getDivisionId())) &&
//                stateMenu.getPromptText().equals(DivisionsQuery.getDivisionNameByDivisionID(selectedCustomer.getDivisionId()))){
//            goHome(event);
//        }

        CustomersQuery.updateCustomer(
                selectedCustomer.getCustomerId(),
                nameField.getText(),
                addressField.getText(),
                postalField.getText(),
                phoneField.getText(),
                stateMenu.getValue());
        System.out.println("save customer clicked");
        goHome(event);
    }


    /**
     * Do not save anything, go back home
     * @param event
     * @throws IOException
     */
    public void cancelCustomerClicked(ActionEvent event) throws IOException{
        System.out.println("cancel customer clicked");
        goHome(event);
    }

    /**
     * Query the database, set the Countries fields with Strings of all countries.
     * @throws SQLException
     */
    private void setDivCombo() throws SQLException {
        countryMenu.setItems(CountriesQuery.getCountryName());
    }

    /**
     * When a country button is clicked, fill the divisions menu with corresponding data from the database.
     * @param event
     * @throws IOException
     */
    public void onCountryClicked(ActionEvent event) throws IOException{
        countryMenu.setValue(countryMenu.getValue());

        stateMenu.setPromptText("");
        System.out.println(countryMenu.getSelectionModel().getSelectedItem());
        ObservableList<String> divList = (Divisions.getAllDivisions(countryMenu.getSelectionModel().getSelectedItem()));
        System.out.println("state menu clicked");

        for(int i = 0 ; divList.size()>i ; i++){
            System.out.println(divList.get(i));
        }

        // If a new country is selected...
        if (countryMenu.getSelectionModel().getSelectedItem() != null) {
            // Clear the existing items in the division combobox
            stateMenu.setItems(null);
            // Add the divisions to the division combobox
            stateMenu.setItems(divList);

        }
    }

    public void initialize() throws SQLException {
        setDivCombo();

        //set the Country value
        countryMenu.setValue(selectedCustomer.getCountry());
        stateMenu.setValue(DivisionsQuery.getDivisionNameByDivisionID(selectedCustomer.getDivisionId()));

        //set the prompt text of both menus
        countryMenu.setPromptText(CountriesQuery.getCountryNameByCountryID(selectedCustomer.getDivisionId()));
        stateMenu.setPromptText(DivisionsQuery.getDivisionNameByDivisionID(selectedCustomer.getDivisionId()));

        stateMenu.setItems(Divisions.getAllDivisions(String.valueOf(CountriesQuery.getCountryNameByCountryID(selectedCustomer.getDivisionId()))));

        //set the text fields for the rest of the fields
        nameField.setText(selectedCustomer.getCustomerName());
        phoneField.setText(selectedCustomer.getPhoneNumber());
        addressField.setText(selectedCustomer.getAddress());
        postalField.setText(selectedCustomer.getPostalCode());

    }
}