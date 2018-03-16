/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vs.mvp.net.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.vs.mvp.net.BaseResults;
import com.vs.mvp.net.exception.TokenInvalidException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.adapter = adapter;
        this.gson = gson;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        BaseResults httpStatus = gson.fromJson(response, BaseResults.class);
        if (httpStatus.getResponse().getResponse_code() == 401) {
            value.close();
            throw new TokenInvalidException();
        }

        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);

        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
//        JsonReader jsonReader = gson.newJsonReader(value.charStream());
//        try {
////            return adapter.read(jsonReader);
//            BaseResults apiModel = (BaseResults) adapter.fromJson(value.charStream());
//            if (apiModel.getCode() == 401) {
//                throw new TokenInvalidException();
//            } else {
//                return adapter.read(jsonReader);
//            }
//        } finally {
//            value.close();
//        }
    }
}
