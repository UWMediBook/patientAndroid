package com.medibook.medibook.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jesper on 7/19/2016.
 */
public class Physician {

    private Integer physician_id;
    private String first_name, last_name, address, phonenumber;

    public Physician(Integer physician_id, String first_name, String last_name, String address, String phonenumber){
        this.physician_id = physician_id;
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
        JSONObject physician = new JSONObject();
        try{
            physician.put("PRIMARY_ID", this.physician_id);
            physician.put("F_NAME", this.first_name);
            physician.put("L_NAME", this.last_name);
            physician.put("ADDRESS", this.address);
            physician.put("PHONE_NUMBER", this.phonenumber);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return physician.toString();
    }
}
