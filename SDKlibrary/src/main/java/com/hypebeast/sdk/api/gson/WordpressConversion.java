package com.hypebeast.sdk.api.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by hesk on 30/9/15.
 */
public class WordpressConversion extends TypeAdapter<String> {
    public WordpressConversion() {

    }

    public String read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return "";
        }
        String beforeDecoding = reader.nextString();
        return Jsoup.parse(beforeDecoding).text();
    }

    public void write(JsonWriter writer, String value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.value(value);
    }

}
