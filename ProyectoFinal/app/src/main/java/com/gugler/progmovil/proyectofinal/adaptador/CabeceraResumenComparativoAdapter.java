package com.gugler.progmovil.proyectofinal.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gugler.progmovil.proyectofinal.modelo.dto.ResumenComparativoDTO;

import java.util.ArrayList;
import java.util.List;

import progmovil.gugler.com.pf.R;

/**
 * Created by Ariel on 31/3/2018.
 */

public class CabeceraResumenComparativoAdapter extends BaseAdapter {
    private ArrayList<Object> mData = new ArrayList<Object>();

    private LayoutInflater mInflater;

    public CabeceraResumenComparativoAdapter(Context context, List<Object> items) {
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
        ResumenComparativoDTO item = (ResumenComparativoDTO) getItem(position);
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.consulta_comparador_item,parent,false);
        }

        TextView txvColumnaConcepto = (TextView) convertView.findViewById(R.id.txvColumnaConcepto);
        TextView txvColumnaPeriodo1 = (TextView) convertView.findViewById(R.id.txvColumnaPeriodo1);
        TextView txvColumnaPeriodo2 = (TextView) convertView.findViewById(R.id.txvColumnaPeriodo2);
        TextView txvColumnaDiferencia = (TextView) convertView.findViewById(R.id.txvColumnaDiferencia);

        if (item.getConcepto().equals("Concepto")) {
            txvColumnaConcepto.setBackgroundResource(R.color.tableHeader);
            txvColumnaPeriodo1.setBackgroundResource(R.color.tableHeader);
            txvColumnaPeriodo2.setBackgroundResource(R.color.tableHeader);
            txvColumnaDiferencia.setBackgroundResource(R.color.tableHeader);
        }

        txvColumnaConcepto.setText(item.getConcepto());
        txvColumnaPeriodo1.setText(item.getPeriodo1());
        txvColumnaPeriodo2.setText(item.getPeriodo2());
        txvColumnaDiferencia.setText(item.getDiferencia());

        return convertView;
    }
}

