package com.medibook.medibook.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kevin on 6/19/2016.
 * Updated by Jason on 7/15/2016.
 */
public class Prescription {

    private Integer id, user_id;
    private String dosage, name;

    public Prescription(Integer id, Integer user_id,String name, String dosage){
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.dosage = dosage;
    }

    public String getName(){return this.name; }

    public String getDosage(){ return this.dosage;}

    public String toJson(){
        JSONObject prescription = new JSONObject();
        try{
            prescription.put("id", this.id);
            prescription.put("user_id", this.user_id);
            prescription.put("name", this.name);
            prescription.put("dosage", this.dosage);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return prescription.toString();
    }

    public static Prescription fromJson(String prescription_data){
        try{
            JSONObject jsonPrescription = new JSONObject(prescription_data);
            Prescription prescription = new Prescription(
                    jsonPrescription.getInt("id"),
                    jsonPrescription.getInt("user_id"),
                    jsonPrescription.getString("name"),
                    jsonPrescription.getString("dosage")
            );
            return prescription;
        } catch (JSONException je){
            je.printStackTrace();
        }

        return null;
    }

}
