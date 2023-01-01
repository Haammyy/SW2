package controllers;
import helper.AppointmentsQuery;
import helper.CustomersQuery;
import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.*;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static helper.Conversions.toAlert;


public class HelloController {
    String fileName = "src\\main\\resources\\loginAttempts.txt";

    public boolean isEnglish = false;
    public boolean isFrench = false;

    String zone = "America/Los_Angeles";
    ZoneId zoneId = ZoneId.of(zone);
    private ResourceBundle rs;

    @FXML private Label localeLabel;
    @FXML private Label welcomeText;
    @FXML private TextField userName, passWord,Username,Password;
    @FXML private Button logInButton;


    @FXML
    protected void onLoginClick(ActionEvent event) throws IOException, SQLException {

        BufferedWriter out = null;

        //welcomeText.setText(JDBC.openConnection(userName.getText(),passWord.getText()));
        String toAppend = "\nUsername: " + userName.getText() + " Password: " + passWord.getText() + " Time attempted: " + LocalDateTime.now(zoneId)+ " Status: "+ JDBC.openConnection(userName.getText(),passWord.getText());
        try {
            out = new BufferedWriter(new FileWriter(fileName,true));
            out.write(toAppend);
            out.close();
        } catch (IOException e) {
            System.out.println("exception occurred"+ e);
        }
        if(JDBC.openConnection(userName.getText(),passWord.getText()).equals("Connection successful!")){
            System.out.println("login clicked");
            CustomersQuery.getCustomerTable();
            AppointmentsQuery.getAppointmentsTable();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/scheduleView.fxml"));
            Parent viewParent = loader.load();
            Scene viewScene = new Scene(viewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Schedule");
            window.setScene(viewScene);
            window.show();
        }
        else{
            toAlert("credentials are incorrect").show();
        }

    }

    public void initialize(){
        //testing only:::::::::::::::::::::::::::::
        userName.setText("sqlUser");
        passWord.setText("Passw0rd!");
        //:::::::::::::::::::::::::::::::::::::::::
        rs = ResourceBundle.getBundle("lang", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr")||Locale.getDefault().getLanguage().equals("en")) {
            logInButton.setText(rs.getString("login"));
            userName.setPromptText(rs.getString("username"));
            passWord.setPromptText(rs.getString("password"));
            //localeLabel.setText(rs.getString("localeLabel"));
        }
}}