package com.gugler.progmovil.proyectofinal.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.adaptador.ListAdapter;
import com.gugler.progmovil.proyectofinal.modelo.dto.ListaItem;
import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;

import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

public class NormalActivity extends BaseActivity {
    private ArrayList<String> listaCtas;
    private ArrayList<Object> listaOperaciones;
    private ListAdapter adapterOperaciones;
    private ArrayList<Object> listaFavoritos;
    private ListAdapter adapterFavoritos;

    private final String OPERACIONES = "Operaciones";
    private final String FAVORITOS = "AHI VA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        prepararStringSql();
        configurarInterface("");
        inicializarListViewOperaciones();
        inicializarListViewFavoritos();


        ListView lst = (ListView) findViewById(R.id.lstOperaciones);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txvId = (TextView) view.findViewById(R.id.txvId);
                TextView txvDescripcion = (TextView) view.findViewById(R.id.txvDescripcion);

                Toast.makeText(getApplicationContext(), txvId.getText().toString() +" "+txvDescripcion.getText(), Toast.LENGTH_SHORT).show();

                switch (txvDescripcion.getText().toString()){
                    case "Débito":
                        Intent intento = new Intent(getApplicationContext(),ElegirDebitoActivity.class);
                        startActivity(intento);
                        break;
                    case "Crédito":
                        break;
                    case "Consultas":
                        break;
                    case "Administrar":
                        Intent intentoAdmin = new Intent(getApplicationContext(), AdministracionActivity.class);
                        startActivity(intentoAdmin);
                        break;
                    default:
                        break;
                }
            }
        });
        llenarFavoritos(); //redimensión
    }



    private void configurarInterface(String modo) {
        switch (modo) {
            default:
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Seguimiento de efectivo");
                actionBar.setSubtitle("Elegir acción");
        }
    }

    /*
    Lo usamos para redimensionar por ahora, pero se puede utilizar para acceder
    a la base de datos a buscar los elementos.
     */
    public void llenarFavoritos() {
        final ListView lstFavoritos = (ListView)findViewById(R.id.lstFavoritos);
        lstFavoritos.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private Integer alto;
            private ListView lstFavoritos;
            private Integer items;

            /**
             * Esta lógica ayuda a disparar rutinas luego de que se haya cargado efectivamente el activity en pantalla
             * De no hacerlo, no es posible manipular los objetos visuales directamente desde OnCreate
             */
            @Override
            public void onGlobalLayout() {
                lstFavoritos = (ListView)findViewById(R.id.lstFavoritos);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    lstFavoritos.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                alto = lstFavoritos.getHeight();
                modificarAlto();
            }

            /**
             * Modifica el alto del listview de favoritos, teniendo como máximo tres elementos para mostrar (permite scroll)
             */
            public void modificarAlto() {
                try{
                    lstFavoritos = (ListView)findViewById(R.id.lstFavoritos);
                    ViewGroup.LayoutParams lstParams = lstFavoritos.getLayoutParams();
                    items = lstFavoritos.getCount();
                    lstParams.height = (alto / items)*3;
                }catch (Exception ex){
                    return;
                }

            }
        });
    }

    /**
     * Método utilizado para llamar a la carga de elementos de listview de favoritos
     */
    private void inicializarListViewFavoritos(){
        listaFavoritos = new ArrayList<Object>();
        ListView lstFavoritos = (ListView)findViewById(R.id.lstFavoritos);
        llenarListView(this.FAVORITOS);
        try{
            adapterFavoritos= new ListAdapter(this,listaFavoritos);
            lstFavoritos.setAdapter(adapterFavoritos);
        }catch(Exception ex){
            throw  ex;
        }
        adapterFavoritos.notifyDataSetChanged();
    }

    /**
     * Carga el listview de operaciones con valores preestablecidos
     */
    private void inicializarListViewOperaciones(){
        listaOperaciones = new ArrayList<Object>();
        ListView lstOperaciones = (ListView)findViewById(R.id.lstOperaciones);
        llenarListView(this.OPERACIONES);
        try{
            adapterOperaciones = new ListAdapter(this,listaOperaciones);
            lstOperaciones.setAdapter(adapterOperaciones);
        }catch(Exception ex){
            throw  ex;
        }
        adapterOperaciones.notifyDataSetChanged();
    }

    /**
     * Método encargado de llenar los listviews
     * @param nombreListview
     */
    private void llenarListView(String nombreListview) {
        switch (nombreListview) {
            case OPERACIONES:
                listaOperaciones.add(new ListaItem(1,"Débito"));
                listaOperaciones.add(new ListaItem(2,"Crédito"));
                listaOperaciones.add(new ListaItem(3,"Consultas"));
                listaOperaciones.add(new ListaItem(4,"Administrar"));
                break;
            case FAVORITOS:
                listaFavoritos.add(new ListaItem(1,"Pasaje diario"));
                listaFavoritos.add(new ListaItem(2,"Compra comida"));
                listaFavoritos.add(new ListaItem(3,"Carga de crédito"));
                listaFavoritos.add(new ListaItem(4,"SUBE obrero"));
                listaFavoritos.add(new ListaItem(5,"Pasaje diario"));
                listaFavoritos.add(new ListaItem(6,"Compra comida"));
                listaFavoritos.add(new ListaItem(7,"Carga de crédito"));
                listaFavoritos.add(new ListaItem(8,"SUBE obrero"));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);
    }

    private Boolean existenCuentas() {
        ServicioCuentas sCuentas = new ServicioCuentas();
        sCuentas.crearBase(this,CADENA_SQL);
        if (sCuentas.listarTodo().isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        prepararStringSql();
        if (existenCuentas() == false) {
            Intent intento = new Intent(this, InicioActivity.class);
            startActivity(intento);
        }
    }
}
