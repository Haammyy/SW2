package Model;

import helper.DivisionsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;

public class Divisions {
    private int divisionId, countryId;
    private String division;

    /**
     * Constructor for Division object
     * @param divisionId
     * @param division
     * @param countryId
     */
    public Divisions(int divisionId,
                     String division,
                     int countryId
                     ) {
        this.divisionId = divisionId;
        this.countryId = countryId;
        this.division = division;
    }

    /**
     * allDivisions Observable list will contain all objects of type "Division"
     */
    static ObservableList<Divisions> allDivisions = FXCollections.observableArrayList();

    /**
     * Adds division objects into the observable list "allDivisions"
     */
    public static void setAllDivisions(Divisions divisions){
        allDivisions.add(divisions);
    }

    /**
     * This method takes a String input, from the dropdown list. populates an observable list with the division names corresponding to the country.
     * @param input
     * @return
     */
    public static ObservableList<String> getAllDivisions(String input) {
        ObservableList<String> newDivs = FXCollections.observableArrayList();
        try{
            if(input!=null){
                newDivs.clear();
                for (int i = 0 ; allDivisions.size()>i;i++){
                    if(input.equals("U.S")){
                        if(allDivisions.get(i).getCountryId()==1 && !newDivs.contains(allDivisions.get(i).getDivision())){
                            newDivs.add(allDivisions.get(i).getDivision());
                        }
                    }
                    if(input.equals("UK")){
                        if(allDivisions.get(i).getCountryId()==2 && !newDivs.contains(allDivisions.get(i).getDivision())){
                            newDivs.add(allDivisions.get(i).getDivision());
                        }
                    }
                    if(input.equals("Canada")){
                        if(allDivisions.get(i).getCountryId()==3 && !newDivs.contains(allDivisions.get(i).getDivision())){
                            newDivs.add(allDivisions.get(i).getDivision());
                        }
                    }

                }
                return newDivs;
            }
        }
        catch (Exception e){
            System.out.println("something went wrong");
        }

        return null;
    }

    /**
     * this method will return a divisionID
     * @param selectedItem
     * @return
     */
    public static int getDivisionByName(String selectedItem) {
        for(int i = 0; i<allDivisions.size();i++){
            if(allDivisions.get(i).getDivision().equals(selectedItem)){
                return allDivisions.get(i).getDivisionId();
            }
        }
        return -1;
    }


    public int getDivisionId() {
        return divisionId;
    }

    /** Division ID
     * @param divisionId Integer value of Division ID*/
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** Division
     * @return division String value of Division */
    public String getDivision() {
        return division;
    }

    /** Division
     * @param division String value of Division*/
    public void setDivision(String division) {
        this.division = division;
    }

    /** Country ID
     * @return countryId Integer value of Country ID*/
    public int getCountryId() {
        return countryId;
    }

    /** Country ID
     * @param countryId Integer value of Country ID*/
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
