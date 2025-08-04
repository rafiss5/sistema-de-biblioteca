package dao;

import model.Artigo;
import model.Livro;
import model.Obra;
import com.google.gson.*;
import model.Revista;

import java.lang.reflect.Type;
import java.util.Map;

public class DeserializadorObra implements JsonDeserializer<Obra> {

    private static final Map<String, Class<? extends Obra>> MAPA_TIPOS = Map.of(
            "Livro", Livro.class,
            "Revista", Revista.class,
            "Artigo", Artigo.class
    );

    @Override
    public Obra deserialize(JsonElement json, Type tipo, JsonDeserializationContext ctx) throws JsonParseException {
        JsonObject objeto = json.getAsJsonObject();
        String tipoVeiculo = objeto.get("tipo").getAsString();
        JsonElement propriedades = objeto.get("propriedades");

        Class<? extends Obra> classe = MAPA_TIPOS.get(tipoVeiculo);
        if (classe == null) {
            throw new JsonParseException("Tipo de obra desconhecido: " + tipoVeiculo);
        }

        return ctx.deserialize(propriedades, classe);
    }

}