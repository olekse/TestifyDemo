package com.olek.testify.Utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSONParser {
    public static List<String> parseJSON(String jsonData) throws JSONException {

        JSONParser parser = new JSONParser();

        JSONObject emp = new JSONObject(jsonData);
        JSONArray arr = emp.getJSONArray("ParsedResults");
        JSONObject textOverlay =  arr.getJSONObject(0);
        String text = textOverlay.get("ParsedText").toString();

        return new ArrayList<String>(Arrays.asList(text.split("\r\n")));
    }
}
