package controllers;

import Model.Appointments;
import Model.Customers;
import helper.NavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class reportController {
    //APPOINTMENTS REPORTS TABLE
    @FXML public TableView<Appointments> typeTable, monthTable, contactTable, endingTable;

    public VBox vboxBind;
    @FXML public TableColumn<Appointments, String> titleColumn, descriptionColumn, locationColumn, typeColumn;
    @FXML public TableColumn<Appointments, LocalDateTime> startColumn, endColumn;
    @FXML
    TableColumn<Appointments, Integer>
            T1C1, T1C2,T1C3,T1C4,T1C5,T1C6,T1C7,T1C8,
            T2C1, T2C2,T2C3,T2C4,T2C5,T2C6,T2C7,T2C8,
            T3C1, T3C2,T3C3,T3C4,T3C5,T3C6,T3C7,T3C8,
            T4C1, T4C2,T4C3,T4C4,T4C5,T4C6,T4C7,T4C8;


    @FXML
    static RadioButton radioJan, radioFeb, radioMar, radioApr, radioMay, radioJun, radioJul, radioAug, radioSep, radioOct, radioNov, radioDec;
   //String radioJanS = ""
    @FXML
    Button cancelButton;
    public void onCancelButtonClick(ActionEvent event) {
        NavigationUtil.navigateToHomePage(event);
    }

    public void setTables(){
        typeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        typeTable.prefWidthProperty().bind(vboxBind.widthProperty());
        typeTable.prefHeightProperty().bind(vboxBind.heightProperty());
        monthTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        contactTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        endingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        setTypeTable();
        setMonthTable();
        setContactTable();
        setEndingTable();

    }

    public static void setTypeTable(){

    }
    public static void setMonthTable(){
        List<RadioButton> radioButtons = new ArrayList<>();  // Your list of radio buttons

        for (RadioButton radioButton : radioButtons) {
            if (radioButton.isSelected()) {
                //methodToExecute();  // Call your method here
                break;  // Exit the loop if a radio button is selected
            }
        }


    }
    public static void setContactTable(){


    }
    public static void setEndingTable(){

    }

    public void initialize(){
        setTables();
    }
}
