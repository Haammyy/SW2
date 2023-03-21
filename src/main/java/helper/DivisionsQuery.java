package helper;
import Model.Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionsQuery {
    /**
     * getDivision will pull the Division table, and turn it into a Java Object.
     * @return
     * @throws SQLException
     */
    public ObservableList<Divisions> getDivisions() throws SQLException {
        ObservableList<Divisions> divisions = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            /**
             * While the result set has remaining iterables, continue creating new Division Objects.
             */
            while (rs.next()) {
                Divisions newDivision = new Divisions(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getInt("COUNTRY_ID")
                );

                System.out.println(newDivision.getDivisionId()+" "+newDivision.getDivision()+" "+ newDivision.getCountryId());

                divisions.add(newDivision);
                Divisions.setAllDivisions(newDivision);
            }
            return divisions;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public static String getDivisionNameByDivisionID(int divisionID) throws SQLException {
        String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        try {
            rs.next();
            return rs.getString("Division");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
