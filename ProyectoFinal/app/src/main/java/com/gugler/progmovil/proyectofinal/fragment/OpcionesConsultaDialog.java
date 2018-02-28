package com.gugler.progmovil.proyectofinal.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Ariel on 24/2/2018.
 */

public class OpcionesConsultaDialog extends DialogFragment {
    private ArrayList<Integer> mSelectedItems; //La voy usar como interna

    public ArrayList<Integer> getmSelectedItemsDevolver() {
        return mSelectedItemsDevolver;
    }

    private ArrayList<Integer> mSelectedItemsDevolver; // La cargo inicialmente con los elementos que vienen en el bundle

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
//        mSelectedItems = new ArrayList();
//        //AlertDialog.Builder builder = new AlertDialog.Builder(getOwnerActivity());
//        DialogFragment.instantiate(getActivity(),"opcionesConsultaDialog")
//            .setTitle("Seleccione").setMultiChoiceItems(R.array.OpcionesConsulta,null,new DialogInterface.OnMultiChoiceClickListener(){
//                @Override
//                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                    if (isChecked){
//                        mSelectedItems.add(which);
//                    } else if(mSelectedItems.contains(which)){
//                        mSelectedItems.remove(Integer.valueOf(which));
//                    }
//                }
//            })
//            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    mSelectedItemsDevolver.clear();
//                    mSelectedItemsDevolver.addAll(mSelectedItems);
//                }
//            });
//    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mSelectedItems = new ArrayList();
//        AlertDialog.Builder builder = new AlertDialog.Builder(getOwnerActivity());
//        builder.setTitle("Seleccione").setMultiChoiceItems(R.array.OpcionesConsulta,null,new DialogInterface.OnMultiChoiceClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                if (isChecked){
//                    mSelectedItems.add(which);
//                } else if(mSelectedItems.contains(which)){
//                    mSelectedItems.remove(Integer.valueOf(which));
//                }
//            }
//        })
//                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        mSelectedItemsDevolver.clear();
//                        mSelectedItemsDevolver.addAll(mSelectedItems);
//                    }
//                });
//
//    }


}
