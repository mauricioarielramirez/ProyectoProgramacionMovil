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

import com.gugler.progmovil.proyectofinal.modelo.dto.FavoritoItem;

import java.util.ArrayList;
import java.util.List;

import progmovil.gugler.com.pf.R;

/**
 * Created by Ariel on 18/11/2017.
 */

public class FavoritosAdapter extends BaseAdapter {
    private ArrayList<Object> mData = new ArrayList<Object>();

    private LayoutInflater mInflater;

    public FavoritosAdapter(Context context, List<Object> items) {
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
        FavoritoItem item = (FavoritoItem) getItem(position);
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.favorito_item,parent,false);
        }
        TextView txvIdTransaccion = (TextView) convertView.findViewById(R.id.txvIdTransaccion);
        TextView txvNombre = (TextView) convertView.findViewById(R.id.txtNombreTransaccionFavorito);
        TextView txvCuentas = (TextView) convertView.findViewById(R.id.txvCuentaAsociadasFavoritos);
        TextView txvTipoTransaccion = (TextView) convertView.findViewById(R.id.txvTipoTransaccion);

        txvIdTransaccion.setText(item.getIdTransaccion());
        txvNombre.setText(item.getNombreTransaccion());
        txvCuentas.setText(item.getCantidadCuentasAsociadas());
        txvTipoTransaccion.setText(item.getTipo());
        ImageView imgArrow = (ImageView) convertView.findViewById(R.id.imgArrow);
        Resources resource = convertView.getResources();
        Drawable drawable;
        if (item.getTipo().equals("C")) {
            drawable = resource.getDrawable(R.drawable.ic_arrow_credit);
        } else {
            drawable = resource.getDrawable(R.drawable.ic_arrow_debit);
        }
        imgArrow.setImageDrawable(drawable);
        return convertView;
    }
}
