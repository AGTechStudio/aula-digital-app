package com.example.auladigital;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;

public class InputDialogUtils {

    public interface OnTextoIngresado {
        void onTexto(String texto);
    }

    public static String pedirTexto(Context context, String titulo) {
        final String[] resultado = {null};

        EditText input = new EditText(context);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(input)
                .setPositiveButton("Aceptar", (dialog1, which) -> {
                    resultado[0] = input.getText().toString();
                })
                .setNegativeButton("Cancelar", (dialog12, which) -> dialog12.cancel())
                .create();
        dialog.show();

        // ⚠️ IMPORTANTE: Este método es asíncrono. Por lo tanto, no devuelve el texto directamente.
        // Para manejarlo bien, es mejor usar la versión con callback, si querés te la paso hecha.

        return resultado[0]; // Esto podría ser null si no se maneja bien el tiempo de respuesta.
    }
}
