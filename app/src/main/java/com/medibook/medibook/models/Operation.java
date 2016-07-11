package com.medibook.medibook.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kevin on 6/19/2016.
 */
public class Operation {

    private Integer operation_id, user_id;
    private String operation;

    public Operation(Integer operation_id, Integer user_id, String operation){
        this.operation_id = operation_id;
        this.user_id = user_id;
        this.operation = operation;
    }

    public String toJson(){
        JSONObject operation = new JSONObject();
        try{
            operation.put("SURGERY_ID", this.operation_id);
            operation.put("USER_ID", this.user_id);
            operation.put("OPERATION", this.operation);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return operation.toString();
    }
}
