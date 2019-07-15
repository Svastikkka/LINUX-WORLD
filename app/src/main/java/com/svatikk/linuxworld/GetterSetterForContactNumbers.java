package com.svatikk.linuxworld;

/**
 * Created by manshusharma on 30/06/17.
 */
public class GetterSetterForContactNumbers {

private  String institutes , designation;
    private  int image_Id;




    public GetterSetterForContactNumbers(String institutes, int image_Id, String designation) {
        this.setImage_Id(image_Id);
        this.setInstitutes(institutes);
        this.setDesignation(designation);
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getImage_Id() {
        return image_Id;
    }

    public void setImage_Id(int image_Id) {
        this.image_Id = image_Id;
    }

    public String getInstitutes() {
        return institutes;
    }

    public void setInstitutes(String institutes) {
        this.institutes = institutes;
    }


}
