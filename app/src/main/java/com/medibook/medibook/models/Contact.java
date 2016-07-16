package com.medibook.medibook.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kevin on 6/19/2016.
 * Updated by Jason on 7/15/2016.
 */
public class Contact {

    private Integer ec_id, user_id;
    private String first_name, last_name, phone_number, relationship;

    public Contact(Integer ec_id, Integer user_id, String first_name, String last_name, String phone_number, String relationship){
        this.ec_id = ec_id;
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.relationship = relationship;
    }

    public String getName(){
        return this.first_name + " " +this.last_name;
    }

    public String getPhone_number(){
        return this.phone_number;
    }

    public String getRelationship(){
        return  this.relationship;
    }

    public String toJson(){
        JSONObject contact = new JSONObject();
        try{
            contact.put("EC_ID", this.ec_id);
            contact.put("USER_ID", this.user_id);
            contact.put("F_NAME", this.first_name);
            contact.put("L_NAME", this.last_name);
            contact.put("PHONE_NUM", this.phone_number);
            contact.put("RELATIONSHIP", this.relationship);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return contact.toString();
    }
}
