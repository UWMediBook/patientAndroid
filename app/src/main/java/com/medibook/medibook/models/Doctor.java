package com.medibook.medibook.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kevin on 6/19/2016.
 */
public class Doctor {

    private Integer id;
    private String first_name, last_name, email, password;

    public Doctor(Integer id,
                  String first_name,
                  String last_name,
                  String email,
                  String password){
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public String getName(){
        return this.first_name + " " + this.last_name;
    }

    public String toJson(){
        JSONObject doctor = new JSONObject();
        try{
            doctor.put("id", this.id);
            doctor.put("first_name", this.first_name);
            doctor.put("last_name", this.last_name);
            doctor.put("email", this.email);
            doctor.put("password", this.password);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return doctor.toString();
    }

    public static Doctor fromJson(String jsonString){
        try{
            JSONObject jsonDoctor = new JSONObject(jsonString);
            Doctor doctor = new Doctor(
                    jsonDoctor.getInt("id"),
                    jsonDoctor.getString("first_name"),
                    jsonDoctor.getString("last_name"),
                    jsonDoctor.getString("email"),
                    jsonDoctor.getString("password")
                    );
            return doctor;
        } catch (JSONException je){
            je.printStackTrace();
        }

        return null;
    }
}
