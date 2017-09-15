package com.gugler.progmovil.proyectofinal.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gugler.progmovil.proyectofinal.modelo.dto.ListaItem;

import java.util.ArrayList;
import java.util.List;

import progmovil.gugler.com.pf.R;

/**
 * Created by Ariel on 10/9/2017.
 */

public class ListAdapter extends BaseAdapter {
    private ArrayList<Object> mData = new ArrayList<Object>();

    private LayoutInflater mInflater;

    public ListAdapter(Context context, List<Object> items) {
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
        ListaItem item = (ListaItem) getItem(position);
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.simple_list_item,parent,false);
        }
        TextView txvId = (TextView) convertView.findViewById(R.id.txvId);
        TextView txvDescripcion = (TextView) convertView.findViewById(R.id.txvDescripcion);
        txvId.setText(item.getId().toString());
        txvDescripcion.setText(item.getDescripcion());
        return convertView;
    }
}
