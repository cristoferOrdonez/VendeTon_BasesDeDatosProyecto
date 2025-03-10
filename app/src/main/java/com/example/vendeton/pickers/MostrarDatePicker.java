package com.example.vendeton.pickers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.EditText;

import java.util.Calendar;

public class MostrarDatePicker {
    private Integer[] fecha;
    public  MostrarDatePicker(Context context, EditText fechaEvento, Integer[] fecha) {

        this.fecha = fecha;

        if (fechaEvento.getText().toString().isEmpty()) {
            Calendar calendario = Calendar.getInstance();
            fecha[2] = calendario.get(Calendar.DAY_OF_MONTH);
            fecha[1] = calendario.get(Calendar.MONTH);
            fecha[0] = calendario.get(Calendar.YEAR);
        }

        DatePickerDialog picker = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            this.fecha[2] = dayOfMonth;
            this.fecha[1] = month;
            this.fecha[0] = year;

            fechaEvento.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
        }, fecha[0], fecha[1], fecha[2]);

        picker.show();
    }
}

