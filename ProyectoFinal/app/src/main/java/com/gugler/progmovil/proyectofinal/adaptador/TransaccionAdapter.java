package com.gugler.progmovil.proyectofinal.adaptador;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gugler.progmovil.proyectofinal.modelo.Transaccion;

import java.util.ArrayList;
import java.util.List;

import progmovil.gugler.com.pf.R;

/**
 * Created by Ariel on 22/7/2017.
 */

public class TransaccionAdapter extends BaseAdapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private ArrayList<Object> mData = new ArrayList<Object>();

    private LayoutInflater mInflater;
    //
    public TransaccionAdapter(Context context, List<Object> items) {
        this.mData = (ArrayList<Object>) items;
        this.mInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Transaccion) {
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
                    convertView = mInflater.inflate(R.layout.transaccion_item,parent,false);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.header_item,parent,false);
                    break;
            }
        }

        switch (type){
            case TYPE_ITEM:
                Transaccion transaccion = (Transaccion) getItem(position);

                // Obtenemos los componentes y le establecemos los valores
                TextView txvTransaccion = (TextView) convertView.findViewById(R.id.txvNombreTransaccion);
                txvTransaccion.setText(transaccion.getNombre());
                TextView txvIdtr = (TextView) convertView.findViewById(R.id.txvIdtr);
                txvIdtr.setText(transaccion.getId().toString());
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
        return 2;
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) == TYPE_ITEM);
    }
}
