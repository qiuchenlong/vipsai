package com.vs.vipsai.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.vs.vipsai.util.TLog;

import java.lang.reflect.Type;

/**
 * Author: cynid
 * Created on 3/13/18 3:11 PM
 * Description:
 */

public class StringJsonDeserializer implements JsonDeserializer<String> {

    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return json.getAsString();
        } catch (Exception e) {
            TLog.log("StringJsonDeserializer-deserialize-error:" + (json != null ? json.toString() : ""));
            return null;
        }
    }

}
