package pl.hycom.ip2018.searchengine.googlesearch.util;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ISOStringZonedDateTimeTypeAdapter extends TypeAdapter<ZonedDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @Override
    public void write(JsonWriter out, ZonedDateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        String dateFormatAsString = value.format(formatter);
        out.value(dateFormatAsString);
    }

    @Override
    public ZonedDateTime read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        String json = in.nextString();
        try {
            return ZonedDateTime.parse(json);
        } catch (Exception e) {
            throw new JsonSyntaxException(json, e);
        }
    }
}
