package com.gugler.progmovil.proyectofinal.adaptador;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gugler.progmovil.proyectofinal.modelo.Cuenta;

import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

/**
 * Created by Ariel on 22/7/2017.
 */

public class CuentaAdapter extends ArrayAdapter<Cuenta> {
    public CuentaAdapter(Context context, ArrayList<Cuenta> items) {
        super(context, R.layout.cuenta_item,items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Obtener el elemento de la posición
        Cuenta cuenta = getItem(position);
        //Verificar si la vista existe para reutilizar
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cuenta_item,null);
        }
        // Obtenemos los componentes y le establecemos los valores
        TextView txvDenominacion = (TextView) convertView.findViewById(R.id.txvDenominacionCuenta);
        TextView txvDescripcion = (TextView) convertView.findViewById(R.id.txvDescripcionCuenta);
        TextView txvSaldo = (TextView) convertView.findViewById(R.id.txvSaldo);

        txvDenominacion.setText(cuenta.getDenominacion());
        txvDescripcion.setText(cuenta.getDescripcion());
        txvSaldo.setText("$ " + cuenta.getSaldo().toString());
        // Devolvemos la vista con los valores
        return convertView;

    }
}
