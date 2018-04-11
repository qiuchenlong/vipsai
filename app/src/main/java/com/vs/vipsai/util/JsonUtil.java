package com.vs.vipsai.util;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class JsonUtil {

    public static <T> String toJsonArrayString(Gson gson, List<T> data) {
        String result = "";
        try {
            result = gson.toJson(data, new TypeToken<List<T>>() {
            }.getType());
        }catch (JsonParseException e){}

        return result;
    }

    public static <T> List<T> fromJsonArrayString(Gson gson, String json) {
        List<T> result = null;
        try {
            result = gson.fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        }catch (JsonParseException e){}

        return result;
    }
}
