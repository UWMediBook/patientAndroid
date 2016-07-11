package com.medibook.medibook.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kevin on 6/19/2016.
 */
public class Allergy {
    private Integer user_id, allergy_id;
    private String allergy;

    public Allergy(Integer user_id, String allergy, Integer allergy_id){
        this.user_id = user_id;
        this.allergy = allergy;
        this.allergy_id = allergy_id;
    }

    public Integer getUserId(){
        return this.user_id;
    }

    public String getAllergy(){
        return this.allergy;
    }

    public String toJson(){
        JSONObject allergy = new JSONObject();
        try{
            allergy.put("USER_ID", this.user_id);
            allergy.put("ALLERGY_ID", this.allergy_id);
            allergy.put("ALLERGY", this.allergy);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return allergy.toString();
    }
}
