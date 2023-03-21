package controllers;

import Model.Countries;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class createCustomerController {
    @FXML public Button saveCustomer, cancelCustomer;
    @FXML public TextField nameField, phoneField, addressField, postalField;
    @FXML public ComboBox<String> countryMenu;
    @FXML public ComboBox<String> stateMenu;


    /**
     * Save button clicked, save a new object, go home
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void saveCustomerClicked(ActionEvent event) throws IOException, SQLException {
        if (    phoneField   == null ||
                nameField    == null ||
                addressField == null ||
                postalField  == null ||
                countryMenu  == null ||
                stateMenu    == null  ){
            Conversions.toAlert("No fields may be empty to create a new customer");
            throw new IOException();
        }
        CustomersQuery.createCustomer(nameField.getText(),
                             addressField.getText(),
                             postalField.getText(),
                             phoneField.getText(),
                             Divisions.getDivisionByName(stateMenu.getSelectionModel().getSelectedItem()));
        System.out.println("save customer clicked");
        NavigationUtil.navigateToHomePage(event);
        }


    /**
     * Do not save anything, go back home
     * @param event
     * @throws IOException
     */
    public void cancelCustomerClicked(ActionEvent event) throws IOException{
        System.out.println("cancel customer clicked");
        NavigationUtil.navigateToHomePage(event);
    }

    private void setDivCombo() throws SQLException {
        countryMenu.setItems(CountriesQuery.getCountryName());
    }

    public void initialize() throws SQLException {
        setDivCombo();
    }


    public void onCountryClicked(ActionEvent event) throws IOException{
            System.out.println(countryMenu.getSelectionModel().getSelectedItem());
            ObservableList<String> divList = (Divisions.getAllDivisions(countryMenu.getSelectionModel().getSelectedItem()));
            System.out.println("state menu clicked");

            for(int i = 0 ; divList.size()>i ; i++){
                System.out.println(divList.get(i));
            }

            // If a new country is selected...
            if (countryMenu.getSelectionModel().getSelectedItem() != null) {
                // Clear the existing items in the division combobox
                stateMenu.getItems().clear();
                // Add the divisions to the division combobox
                stateMenu.setItems(divList);

        }
    }
}





