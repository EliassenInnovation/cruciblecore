package com.eliassen.crucible.core.sharedobjects;

import org.json.JSONObject;

import java.util.Hashtable;

public class ApiResponse {
    public int code;
    public Hashtable<String, String> headers;
    public String payload;
    public double executionTime;

    public ApiResponse(int code, Hashtable<String, String> headers, String payload){
        this.code = code;
        this.headers = headers;
        this.payload = payload;
    }

    public JSONObject getJSONPayload()
    {
        return new JSONObject(payload);
    }
}
