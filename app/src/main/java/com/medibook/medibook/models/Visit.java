package com.medibook.medibook.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by Kevin on 6/19/2016.
 * Updated by Jason on 7/15/2016.
 */
public class Visit {

    private SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy @ h:mm a");


    private Integer id, user_id;
    private String visit;
    private Long created, updated;

    public Visit(Integer id,
                 Integer user_id,
                 String visit,
                 Long created,
                 Long updated)
    {
        this.id = id;
        this.user_id = user_id;
        this.visit = visit;
        this.created = created;
        this.updated = updated;
    }

    public String getVisit(){return this.visit;}

    public String getCreated(){
        return sdf.format(this.created * 1000);
    }

    public String getLastUpdated(){
        return sdf.format(this.updated * 1000);
    }

    public String toJson(){
        JSONObject visit = new JSONObject();
        try{
            visit.put("id", this.id);
            visit.put("user_id", this.user_id);
            visit.put("visit", this.visit);
            visit.put("created_at", this.created);
            visit.put("updated_at", this.updated);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return visit.toString();
    }

    public static Visit fromJson(String visit_data){
        try{
            JSONObject jsonVisit = new JSONObject(visit_data);
            Visit visit = new Visit(
                    jsonVisit.getInt("id"),
                    jsonVisit.getInt("user_id"),
                    jsonVisit.getString("visit"),
                    jsonVisit.getLong("created_at"),
                    jsonVisit.getLong("updated_at")
            );
            return visit;
        } catch (JSONException je){
            je.printStackTrace();
        }

        return null;
    }

}