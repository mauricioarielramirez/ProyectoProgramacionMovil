package com.gugler.progmovil.proyectofinal.adaptador;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gugler.progmovil.proyectofinal.modelo.Cuenta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import progmovil.gugler.com.pf.R;

/**
 * Created by Ariel on 22/7/2017.
 */

public class CuentaAdapter extends BaseAdapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private ArrayList<Object> mData = new ArrayList<Object>();

    private LayoutInflater mInflater;
//
    public CuentaAdapter(Context context, List<Object> items) {
        this.mData = (ArrayList<Object>) items;
        this.mInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Cuenta) {
            return TYPE_ITEM;
        }

        return TYPE_SEPARATOR;
    }

    @Override
    public int getCount() {
        return mData.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int type = getItemViewType(position);
        if (convertView == null){
            switch (type) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.cuenta_item,parent,false);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.header_item,parent,false);
                    break;
            }
        }

        switch (type){
            case TYPE_ITEM:
                Cuenta cuenta = (Cuenta) getItem(position);

                // Obtenemos los componentes y le establecemos los valores
                TextView txvDenominacion = (TextView) convertView.findViewById(R.id.txvDenominacionCuenta);
                TextView txvDescripcion = (TextView) convertView.findViewById(R.id.txvDescripcionCuenta);
                TextView txvSaldo = (TextView) convertView.findViewById(R.id.txvSaldo);

                txvDenominacion.setText(cuenta.getDenominacion());
                txvDescripcion.setText(cuenta.getDescripcion());
                txvSaldo.setText("$ " + new DecimalFormat("#.##").format(cuenta.getSaldo())+ " ");
                        break;
            case TYPE_SEPARATOR:
                TextView textView = (TextView)convertView.findViewById(R.id.txvHeaderListView);
                String cadenaString = (String)getItem(position);
                textView.setText(cadenaString);
                break;
        }

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        // TYPE_PERSON and TYPE_DIVIDER
        return 2;
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) == TYPE_ITEM);
    }
}
