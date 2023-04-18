package helper;

import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersQuery {

    public static boolean checkCredentials(String user, String pass) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name=? AND Password=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setString(1, user);
        ps.setString(2, pass);

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();
            return (rs.next());
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public static ObservableList<Users> getUsers() throws SQLException {
        ObservableList<Users> users = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        try {
            ps.execute();
            ResultSet resultSet = ps.getResultSet();

            while (resultSet.next()) {
                Users newUser = new Users(
                        resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("Password")
                );

                users.add(newUser);
            }
            return users;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new SQLException();
        }
    }
}
