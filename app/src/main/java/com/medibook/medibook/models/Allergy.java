package com.medibook.medibook.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kevin on 6/19/2016.
 * Updated by Jason on 7/15/2016.
 */
public class Allergy {
    private Integer user_id, allergy_id;
    private String name, severity;

    public Allergy(int user_id, String name, String severity, int allergy_id){
        this.user_id = user_id;
        this.name = name;
        this.severity = severity;
        this.allergy_id = allergy_id;
    }

    public Integer getUserId(){
        return this.user_id;
    }

    public String getName(){
        return this.name;
    }

    public String getSeverity(){
        switch(this.severity){
            case "M":
                return "Mild";
            case "S":
                return "Severe";
            default:
                return "N/A";
        }
    }

    public String toJson(){
        JSONObject allergy = new JSONObject();
        try{
            allergy.put("user_id", this.user_id);
            allergy.put("id", this.allergy_id);
            allergy.put("name", this.name);
            allergy.put("severity", this.severity);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return allergy.toString();
    }

    public static Allergy fromJson(String data){
        try{
            JSONObject json_object = new JSONObject(data);
            Allergy allergy = new Allergy(
                    json_object.getInt("user_id"),
                    json_object.getString("name"),
                    json_object.getString("severity"),
                    json_object.getInt("id")
            );
            return allergy;
        } catch (JSONException je){
            je.printStackTrace();
        }

        return null;
    }
}
