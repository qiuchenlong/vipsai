package com.vs.vipsai.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.vs.vipsai.util.TLog;

import java.lang.reflect.Type;

/**
 * Author: cynid
 * Created on 3/13/18 3:09 PM
 * Description:
 */

public class FloatJsonDeserializer implements JsonDeserializer<Float> {

    @Override
    public Float deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return json.getAsFloat();
        } catch (Exception e) {
            TLog.log("FloatJsonDeserializer-deserialize-error:" + (json != null ? json.toString() : ""));
            return 0F;
        }
    }

}
