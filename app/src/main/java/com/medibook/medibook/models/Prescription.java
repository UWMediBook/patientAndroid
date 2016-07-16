package com.medibook.medibook.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kevin on 6/19/2016.
 * Updated by Jason on 7/15/2016.
 */
public class Prescription {

    private Integer prescription_id, user_id;
    private String prescription, name_of_prescription;

    public Prescription(Integer prescription_id, Integer user_id,String name_of_prescription, String prescription){
        this.prescription_id = prescription_id;
        this.user_id = user_id;
        this.name_of_prescription = name_of_prescription;
        this.prescription = prescription;
    }

    public String getName(){return this.name_of_prescription; }

    public String getPrescription(){ return this.prescription;}

    public String toJson(){
        JSONObject prescription = new JSONObject();
        try{
            prescription.put("PRESCRIPTION_ID", this.prescription_id);
            prescription.put("USER_ID", this.user_id);
            prescription.put("PRESCRIPTION", this.prescription);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return prescription.toString();
    }

}
