import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FeriadoUtils {

    // Clase interna para modelar un feriado
    public static class Feriado {
        private String dia;    // fecha en formato "YYYY-MM-DD"
        private String nombre;

        public String getDia() { return dia; }
        public String getNombre() { return nombre; }
    }

    public interface Callback {
        void onResult(Set<String> feriadosISO);
        void onError(String mensaje);
    }

    public static void obtenerFeriados(Context context, int anio, Callback callback) {
        new Thread(() -> {
            try {
                URL url = new URL("https://argentina-api.vercel.app/api/feriados");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder json = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    json.append(line);
                }

                in.close();

                Type listType = new TypeToken<List<Feriado>>() {}.getType();
                List<Feriado> feriados = new Gson().fromJson(json.toString(), listType);

                Set<String> dias = new HashSet<>();
                for (Feriado f : feriados) {
                    if (f.getDia().startsWith(anio + "-")) {
                        dias.add(f.getDia()); // formato "2025-05-25"
                    }
                }

                callback.onResult(dias);
            } catch (Exception e) {
                e.printStackTrace();
                callback.onError("Error al obtener feriados: " + e.getMessage());
            }
        }).start();
    }
}

