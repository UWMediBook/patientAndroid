package com.medibook.medibook.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kevin on 6/19/2016.
 * Updated by Jason on 7/15/2016.
 */
public class Visit {

    private Integer visit_id, user_id;
    private String visit;

    public Visit(Integer visit_id, Integer user_id, String visit){
        this.visit_id = visit_id;
        this.user_id = user_id;
        this.visit = visit;
    }

    public String getVisit(){return this.visit;}

    public String toJson(){
        JSONObject visit = new JSONObject();
        try{
            visit.put("VISIT_ID", this.visit_id);
            visit.put("USER_ID", this.user_id);
            visit.put("VISIT", this.visit);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return visit.toString();
    }

}