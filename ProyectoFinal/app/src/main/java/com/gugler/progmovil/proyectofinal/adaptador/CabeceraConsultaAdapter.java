package com.gugler.progmovil.proyectofinal.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gugler.progmovil.proyectofinal.modelo.dto.CabeceraConsultaDTO;

import java.util.ArrayList;
import java.util.List;

import progmovil.gugler.com.pf.R;

/**
 * Created by Ariel on 31/3/2018.
 */

public class CabeceraConsultaAdapter extends BaseAdapter {
    private ArrayList<Object> mData = new ArrayList<Object>();

    private LayoutInflater mInflater;

    public CabeceraConsultaAdapter(Context context, List<Object> items) {
        this.mData = (ArrayList<Object>) items;
        this.mInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        CabeceraConsultaDTO item = (CabeceraConsultaDTO) getItem(position);
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.consulta_cabecera_item,parent,false);
        }
        TextView txvConcepto = (TextView) convertView.findViewById(R.id.txvConceptoCabecera);
        TextView txvValor = (TextView) convertView.findViewById(R.id.txvValorCabecera);

        txvConcepto.setText(item.getTextoConcepto());
        txvValor.setText(item.getTextoValor());

        return convertView;
    }
}
