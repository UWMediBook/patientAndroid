package com.medibook.medibook.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by Kevin on 6/19/2016.
 * Updated by Jason on 7/15/2016
 */
public class Operation {

    private Integer operation_id, user_id;
    private String operation;
    private Long created_at, updated_at;

    private SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy @ h:mm a");

    public Operation(Integer operation_id, Integer user_id, String operation, Long created_at, Long updated_at){
        this.operation_id = operation_id;
        this.user_id = user_id;
        this.operation = operation;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getOperation(){
        return this.operation;
    }

    public String toJson(){
        JSONObject operation = new JSONObject();
        try{
            operation.put("id", this.operation_id);
            operation.put("user_id", this.user_id);
            operation.put("operation", this.operation);
            operation.put("created_at", this.created_at);
            operation.put("updated_at", this.updated_at);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return operation.toString();
    }

    public static Operation fromJson(String data){
        try{
            JSONObject json_object = new JSONObject(data);
            Operation operation = new Operation(
                    json_object.getInt("id"),
                    json_object.getInt("user_id"),
                    json_object.getString("operation"),
                    json_object.getLong("created_at"),
                    json_object.getLong("updated_at")
            );
            return operation;
        } catch (JSONException je){
            je.printStackTrace();
        }

        return null;
    }

    public String getCreated(){
        return sdf.format(this.created_at * 1000);
    }

}
