package com.gugler.progmovil.proyectofinal.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ariel on 21/2/2018.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private int componentID;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date fecha = null;
        Bundle r = getArguments();
        SimpleDateFormat formmater = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        final Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        try
        {
            componentID = r.getInt("campoFecha");
            Button btnFecha = (Button) getActivity().findViewById(componentID);
            fecha = formmater.parse(btnFecha.getText().toString());
            c.setTime(fecha);
        }
        catch (Exception e)
        {
            anio = c.get(Calendar.YEAR);
            mes = c.get(Calendar.MONTH);
            dia = c.get(Calendar.DAY_OF_MONTH);

        }
        // Devolver un nuevo objeto de tipo DatePickerDialog
        return new DatePickerDialog(getActivity(), this, anio, mes, dia);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Date selectedDate = null;
        SimpleDateFormat formmater = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Button btnFecha = (Button) getActivity().findViewById(componentID);
        Calendar calendario = Calendar.getInstance();
        calendario.set(year, month, dayOfMonth);
        selectedDate = calendario.getTime();
        btnFecha.setText(formmater.format(selectedDate));
    }
}
