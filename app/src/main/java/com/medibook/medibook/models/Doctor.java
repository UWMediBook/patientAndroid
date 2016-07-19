package com.medibook.medibook.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kevin on 6/19/2016.
 */
public class Doctor {

    private Integer doctor_id;
    private String first_name, last_name, address, phonenumber;

    public Doctor(Integer doctor_id, String first_name, String last_name, String address, String phonenumber){
        this.doctor_id = doctor_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phonenumber = phonenumber;
    }

    public String getFirst(){ return this.first_name;}

    public String getLast(){ return this.last_name;}

    public String getAddress() { return this.address;}

    public String getPhoneNumber() {return this.phonenumber;}

    public String toJson(){
        JSONObject doctor = new JSONObject();
        try{
            doctor.put("PRIMARY_ID", this.doctor_id);
            doctor.put("F_NAME", this.first_name);
            doctor.put("L_NAME", this.last_name);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return doctor.toString();
    }
}
