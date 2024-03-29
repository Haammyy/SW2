package controllers;
import Model.Appointments;
import Model.Location;
import helper.AppointmentsQuery;
import helper.Conversions;
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

/**
 * This is the login form that will grant access to the application
 */
public class HelloController {

    String fileName = "src\\main\\resources\\loginAttempts.txt";

    //boolean variables to determine what language to display
    public boolean isEnglish = false;
    public boolean isFrench = false;

    String zone = "America/Los_Angeles";
    ZoneId zoneId = ZoneId.of(zone);
    private ResourceBundle rs;

    @FXML private Label localeLabel;
    @FXML private Label welcomeText;
    @FXML private TextField userName, passWord,Username,Password;
    @FXML private Button logInButton;


    /**
     * print the login attempt information to loginAttempts.txt
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    protected void onLoginClick(ActionEvent event) throws IOException, SQLException {
        BufferedWriter out = null;
        String toAppend = "\nUsername: " + userName.getText() + " Password: " + passWord.getText() + " Time attempted: " + LocalDateTime.now(zoneId)+ " Status: "+ JDBC.openConnection(userName.getText(),passWord.getText());
        try {
            out = new BufferedWriter(new FileWriter(fileName,true));
            out.write(toAppend);
            out.close();
        } catch (IOException e) {
            System.out.println("exception occurred"+ e);
        }

        //check to see if the username and password match the MySQL credentials. If true, change the scene. If false, alert.
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
            if (Appointments.checkFifteen()) {
                Conversions.toAlert("You have an appointment in 15 minutes or less.",1);
                Appointments.getFifteenAppointments();
            }
        }
        else{
            toAlert("credentials are incorrect").show();
        }

    }

    /**
     * this will set the language based upon the locale of the user's windows settings.
     */
    public void initialize(){
        Location location = new Location();


        //testing only:::::::::::::::::::::::::::::
        userName.setText("sqlUser");
        passWord.setText("Passw0rd!");
        //:::::::::::::::::::::::::::::::::::::::::

        //set the language based upon the locale of the user's windows settings.
        Locale locale = Locale.getDefault();
        System.out.println(locale.getLanguage());
        if(locale.getLanguage().equals("fr")){
            isFrench = true;
            isEnglish = false;
        }
        else{
            isEnglish = true;
            isFrench = false;
        }

        //set the resource bundle based upon the language
        rs = ResourceBundle.getBundle("lang", Locale.ENGLISH);

        if(isFrench){
            rs = ResourceBundle.getBundle("lang", Locale.FRENCH);
        }

        //set locale label to the location of the user from Location method
        localeLabel.setText(String.valueOf(ZoneId.systemDefault()));

        //set the text of the labels and buttons based upon the language
        logInButton.setText(rs.getString("login"));
        userName.setPromptText(rs.getString("username"));
        passWord.setPromptText(rs.getString("password"));



    }
}