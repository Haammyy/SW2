package controllers;

import helper.NavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class reportController {
    @FXML
    Button cancelButton;
    public void onCancelButtonClick(ActionEvent event) {
        NavigationUtil.navigateToHomePage(event);
    }
}
