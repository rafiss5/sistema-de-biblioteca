package biblioteca.util;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Converters.registerAll(gsonBuilder);
        return gsonBuilder.setPrettyPrinting().create();
    }
}
