package com.example.auladigital;

import android.app.DatePickerDialog;
import android.content.Context;
import java.util.Calendar;

public class CalendarDialogUtils {

    public interface OnFechaSeleccionada {
        void onFecha(String fecha);
    }

    public static void mostrarCalendario(Context context, OnFechaSeleccionada listener) {
        final Calendar c = Calendar.getInstance();
        int año = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String fecha = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                    listener.onFecha(fecha);
                }, año, mes, dia);
        datePickerDialog.show();
    }
}

