package com.medibook.medibook.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kevin on 6/18/2016.
 */

public class User {
    private String first_name, last_name, gender, address, birthday, email, password, healthcard;
    private Integer id;

    public User(String first_name, String last_name, Integer id, String gender, String address, String birthday, String email, String password, String healthcard){
        this.first_name = first_name;
        this.last_name = last_name;
        this.id = id;
        this.gender = gender;
        this.address = address;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.healthcard = healthcard;
    }

    public Integer getId(){return this.id;}

    public String getName(){
        return this.first_name + " " + this.last_name;
    }

    public String getGender(){
        switch(this.gender){
            case "M":
                return "Male";
            case "m":
                return "Male";
            case "F":
                return "Female";
            case "f":
                return "Female";
            default:
                return "Unknown";
        }
    }

    public String getAddress(){
        return this.address;
    }

    public String getBirthday(){
        return this.birthday;
    }

    public String getEmail(){
        return this.email;
    }

    public String getHealthcard(){
        return this.healthcard;
    }

    public String toJson(){
        JSONObject user = new JSONObject();
        try{
            user.put("first_name", this.first_name);
            user.put("last_name", this.last_name);
            user.put("address", this.address);
            user.put("birthday", this.birthday);
            user.put("email", this.email);
            user.put("password", this.password);
            user.put("gender", this.gender);
            user.put("healthcard", this.healthcard);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return user.toString();

    }

}
