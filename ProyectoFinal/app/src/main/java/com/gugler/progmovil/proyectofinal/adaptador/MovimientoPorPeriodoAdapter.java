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

import com.gugler.progmovil.proyectofinal.modelo.dto.MovimientosPorPeriodoDTO;

import java.util.ArrayList;
import java.util.List;

import progmovil.gugler.com.pf.R;

/**
 * Created by Ariel on 20/4/2018.
 */

public class MovimientoPorPeriodoAdapter extends BaseAdapter{
    private ArrayList<Object> mData = new ArrayList<Object>();

    private LayoutInflater mInflater;

    public MovimientoPorPeriodoAdapter(Context context, List<Object> items) {
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
        MovimientosPorPeriodoDTO item = (MovimientosPorPeriodoDTO) getItem(position);
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.consulta_movimiento_periodo_item,parent,false);
        }
        TextView txvColumnaNroPeriodoRango = (TextView) convertView.findViewById(R.id.txvColumnaNroPeriodoRango);
        TextView txvColumnaFechaRango = (TextView) convertView.findViewById(R.id.txvColumnaFechaRango);
        TextView txvColumnaTransaccionRango = (TextView) convertView.findViewById(R.id.txvColumnaTransaccionRango);
        TextView txvColumnaMontoRango = (TextView) convertView.findViewById(R.id.txvColumnaMontoRango);
        TextView txvColumnaSaldoRango = (TextView) convertView.findViewById(R.id.txvColumnaSaldoRango);

        txvColumnaNroPeriodoRango.setText((item.getPeriodo()).toString());
        txvColumnaFechaRango.setText(item.getFecha());
        txvColumnaTransaccionRango.setText(item.getTransaccion());
        txvColumnaMontoRango.setText(item.getMonto());
        txvColumnaSaldoRango.setText(item.getSaldo());

        ImageView imgArrow = (ImageView) convertView.findViewById(R.id.imgTipoMovimiento);
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
