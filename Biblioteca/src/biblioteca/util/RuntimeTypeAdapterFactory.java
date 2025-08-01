package biblioteca.util;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe utilitária para lidar com herança ao serializar e desserializar objetos com Gson.
 */
public class RuntimeTypeAdapterFactory<T> implements TypeAdapterFactory {

    private final Class<?> classeBase;
    private final String campoTipo;
    private final Map<String, Class<?>> tiposRegistrados = new LinkedHashMap<>();
    private final Map<Class<?>, String> nomesTipos = new LinkedHashMap<>();

    private RuntimeTypeAdapterFactory(Class<?> classeBase, String campoTipo) {
        if (classeBase == null || campoTipo == null) {
            throw new NullPointerException("Classe base e campo do tipo não podem ser nulos");
        }
        this.classeBase = classeBase;
        this.campoTipo = campoTipo;
    }

    public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> classeBase, String campoTipo) {
        return new RuntimeTypeAdapterFactory<>(classeBase, campoTipo);
    }

    public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> classeBase) {
        return new RuntimeTypeAdapterFactory<>(classeBase, "type");
    }

    public RuntimeTypeAdapterFactory<T> registerSubtype(Class<? extends T> tipo, String nome) {
        if (tipo == null || nome == null) {
            throw new NullPointerException("Tipo e nome não podem ser nulos");
        }
        if (tiposRegistrados.containsKey(nome) || nomesTipos.containsKey(tipo)) {
            throw new IllegalArgumentException("Tipo ou nome já registrado: " + nome);
        }

        tiposRegistrados.put(nome, tipo);
        nomesTipos.put(tipo, nome);
        return this;
    }

    @Override
    public <R> TypeAdapter<R> create(Gson gson, TypeToken<R> tipo) {
        if (!classeBase.isAssignableFrom(tipo.getRawType())) {
            return null;
        }

        final Map<String, TypeAdapter<?>> adaptadoresPorNome = new LinkedHashMap<>();
        final Map<Class<?>, TypeAdapter<?>> adaptadoresPorClasse = new LinkedHashMap<>();

        for (Map.Entry<String, Class<?>> entrada : tiposRegistrados.entrySet()) {
            TypeToken<?> tipoToken = TypeToken.get(entrada.getValue());
            TypeAdapter<?> adaptador = gson.getDelegateAdapter(this, tipoToken);
            adaptadoresPorNome.put(entrada.getKey(), adaptador);
            adaptadoresPorClasse.put(entrada.getValue(), adaptador);
        }

        return new TypeAdapter<R>() {
            @Override
            public void write(JsonWriter out, R valor) throws IOException {
                Class<?> classe = valor.getClass();
                String tipoNome = nomesTipos.get(classe);

                if (tipoNome == null) {
                    throw new JsonParseException("Tipo não registrado: " + classe.getName());
                }

                @SuppressWarnings("unchecked")
                TypeAdapter<R> adaptador = (TypeAdapter<R>) adaptadoresPorClasse.get(classe);

                JsonObject json = adaptador.toJsonTree(valor).getAsJsonObject();
                json.addProperty(campoTipo, tipoNome);
                Streams.write(json, out);
            }

            @Override
            public R read(JsonReader in) throws IOException {
                JsonElement elemento = Streams.parse(in);
                JsonObject objeto = elemento.getAsJsonObject();

                JsonElement tipoElemento = objeto.get(campoTipo);
                if (tipoElemento == null) {
                    throw new JsonParseException("Campo do tipo '" + campoTipo + "' não encontrado no JSON");
                }

                String tipoNome = tipoElemento.getAsString();
                @SuppressWarnings("unchecked")
                TypeAdapter<R> adaptador = (TypeAdapter<R>) adaptadoresPorNome.get(tipoNome);

                if (adaptador == null) {
                    throw new JsonParseException("Tipo não registrado: " + tipoNome);
                }

                return adaptador.fromJsonTree(objeto);
            }
  };
}
}