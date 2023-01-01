package Model;

import helper.DivisionsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;

public class Divisions {
    private int divisionId, countryId;
    private String division;

    public Divisions(int divisionId,
                     String division,
                     int countryId
                     ) {
        this.divisionId = divisionId;
        this.countryId = countryId;
        this.division = division;
    }

    static ObservableList<Divisions> allDivisions = FXCollections.observableArrayList();

    public static void setAllDivisions(Divisions divisions){
        allDivisions.add(divisions);
    }

    public static ObservableList<String> getAllDivisions(String input) {
        System.out.println(input);
        try{
            ObservableList<String> newDivs = FXCollections.observableArrayList();
            if(input!=null){
                for (int i = 0 ; allDivisions.size()>i;i++){
                    if(input.equals("U.S")){
                        if(allDivisions.get(i).getCountryId()==1){
                            newDivs.add(allDivisions.get(i).getDivision());
                        }
                    }
                    if(input.equals("UK")){
                        if(allDivisions.get(i).getCountryId()==2){
                            newDivs.add(allDivisions.get(i).getDivision());
                        }
                    }
                    if(input.equals("Canada")){
                        if(allDivisions.get(i).getCountryId()==3){
                            newDivs.add(allDivisions.get(i).getDivision());
                        }
                    }

                }
                System.out.println(newDivs);
                return newDivs;

            }
        }
        catch (Exception e){
            System.out.println("something went wrong");
        }

        return null;
    }

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
