package dao;

import model.Obra;
import com.google.gson.*;

import java.lang.reflect.Type;

public class SerializadorObra implements JsonSerializer<Obra> {

    @Override
    public JsonElement serialize(Obra obra, Type tipo, JsonSerializationContext serializador) {

        JsonObject objeto = new JsonObject();

        objeto.add("tipo", new JsonPrimitive(obra.getClass().getSimpleName()));
        objeto.add("propriedades", serializador.serialize(obra, obra.getClass()));

        return objeto;
    }
}