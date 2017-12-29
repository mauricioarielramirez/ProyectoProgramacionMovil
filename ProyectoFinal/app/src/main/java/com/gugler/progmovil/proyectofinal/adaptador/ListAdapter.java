package com.gugler.progmovil.proyectofinal.adaptador;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

        ImageView imgIconoLeft = (ImageView) convertView.findViewById(R.id.imgIconoL);
        ImageView imgIconoRight = (ImageView) convertView.findViewById(R.id.imgIconoR);
        Resources resource = convertView.getResources();
        Drawable drawableLeft = null;
        Drawable drawableRight = null;

        switch (item.getTipoItem()){
            case ListaItem.OPERACIONES_DEBITO:
                drawableLeft = resource.getDrawable(R.drawable.ic_debito_menu);
                drawableRight = resource.getDrawable(R.drawable.ic_blank_icon);
                break;
            case ListaItem.OPERACIONES_CREDITO:
                drawableLeft = resource.getDrawable(R.drawable.ic_credito_menu);
                drawableRight = resource.getDrawable(R.drawable.ic_blank_icon);
                break;
            case ListaItem.OPERACIONES_CONSULTAS:
                drawableLeft = resource.getDrawable(R.drawable.ic_consultar_menu);
                drawableRight = resource.getDrawable(R.drawable.ic_blank_icon);
                break;
            case ListaItem.OPERACIONES_ADMINISTRAR:
                drawableLeft = resource.getDrawable(R.drawable.ic_administrar_menu);
                drawableRight = resource.getDrawable(R.drawable.ic_blank_icon);
                break;
            case ListaItem.OPERACIONES_ADMINISTRAR_NUEVA_CUENTA:
                drawableLeft = resource.getDrawable(R.drawable.ic_item_new);
                drawableRight = resource.getDrawable(R.drawable.ic_account_menu);
                break;
            case ListaItem.OPERACIONES_ADMINISTRAR_NUEVA_TRANSACCION:
                drawableLeft = resource.getDrawable(R.drawable.ic_item_new);
                drawableRight = resource.getDrawable(R.drawable.ic_transaccion_menu);
                break;
            case ListaItem.OPERACIONES_ADMINISTRAR_MODIFICAR_CUENTA:
                drawableLeft = resource.getDrawable(R.drawable.ic_item_edit);
                drawableRight = resource.getDrawable(R.drawable.ic_account_menu);
                break;
            case ListaItem.OPERACIONES_ADMINISTRAR_MODIFICAR_TRANSACCION:
                drawableLeft = resource.getDrawable(R.drawable.ic_item_edit);
                drawableRight = resource.getDrawable(R.drawable.ic_transaccion_menu);
                break;
            case ListaItem.OPERACIONES_ADMINISTRAR_MODIFICAR_MOVIMIENTO:
                drawableLeft = resource.getDrawable(R.drawable.ic_item_edit);
                drawableRight = resource.getDrawable(R.drawable.ic_transaccion_menu);
                break;

            default:
                drawableLeft = resource.getDrawable(R.drawable.ic_blank_icon);
                drawableRight = resource.getDrawable(R.drawable.ic_blank_icon);
        }
        imgIconoLeft.setImageDrawable(drawableLeft);
        imgIconoRight.setImageDrawable(drawableRight);

//        if (item.getTipo().equals("C")) {
//            drawable = resource.getDrawable(R.drawable.ic_arrow_credit);
//        } else {
//            drawable = resource.getDrawable(R.drawable.ic_arrow_debit);
//        }
//        imgArrow.setImageDrawable(drawable);

        return convertView;
    }
}
