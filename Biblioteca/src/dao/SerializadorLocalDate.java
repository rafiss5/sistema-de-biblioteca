package dao;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SerializadorLocalDate implements JsonSerializer<LocalDate> {

    @Override
    public JsonElement serialize(LocalDate data, Type tipo, JsonSerializationContext serializador) {
        return new JsonPrimitive(data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
}
