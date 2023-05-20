package controllers;

import helper.NavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class reportController {
    @FXML
    TableView typeTable, monthTable, contactTable;

    @FXML
    TableColumn T1C1, T1C2,T1C3,T1C4,T1C5,T1C6,T1C7,T1C8,
            T2C1, T2C2,T2C3,T2C4,T2C5,T2C6,T2C7,T2C8,
            T3C1, T3C2,T3C3,T3C4,T3C5,T3C6,T3C7,T3C8;

    @FXML
    RadioButton radioJan, radioFeb, radioMar, radioApr, radioMay, radioJun, radioJul, radioAug, radioSep, radioOct, radioNov, radioDec;
    @FXML
    Button cancelButton;
    public void onCancelButtonClick(ActionEvent event) {
        NavigationUtil.navigateToHomePage(event);
    }

    public void setTables(){


    }

    public void initialize(){
        setTables();
    }
}
