package progmovil.gugler.com.proyectofinal;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import progmovil.gugler.com.proyectofinal.dao.CuentaDAO;
import progmovil.gugler.com.proyectofinal.exception.ValidacionException;
import progmovil.gugler.com.proyectofinal.modelo.Cuenta;

public class MainActivity extends AppCompatActivity {
    private CuentaDAO cuentaDao;
    private StringBuffer cadena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cadena = new StringBuffer();
        leerScript();
        cuentaDao = new CuentaDAO(this,cadena.toString());

        //Mirar aca http://elbauldeandroid.blogspot.com.ar/2013/10/actionbar-android-en-construccion.html

        ActionBar actionBar = getSupportActionBar(); // Permite personalizar el action bar
        actionBar.setTitle("Main activity");
        actionBar.setSubtitle("Bienvenido");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_back); //ícono de la izquierda

    }
    /*PROBANDO UN MENU*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); //En menu.xml se definen
        return true;
    }

    private void leerScript(){
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.script);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            for (String linea; (linea=reader.readLine())!=null;){
                cadena.append(linea);
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Problemas al intentar leer el archivo.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

/*    public void onButtonTestClick(View view) {

        TextView tv = (TextView) findViewById(R.id.textViewRes);
        try {
            Toast toast = Toast.makeText(this, "Entro en el try", Toast.LENGTH_SHORT);
            toast.show();

            StringBuffer cadena = new StringBuffer();
            InputStream inputStream = getResources().openRawResource(R.raw.script);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            for (String linea; (linea=reader.readLine())!=null;){
                cadena.append(linea);
            }

            tv.setText(cadena);
        } catch (Exception e) {
            //tv.setText(e.getMessage() );
            Toast toast = Toast.makeText(this, "Problemas al intentar leer el archivo.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }*/

    public void onBtnGuardarClick(View view){
        Cuenta cuenta = new Cuenta();
        cuenta.setDenominacion(((TextView)findViewById(R.id.txtDenominacion)).getText().toString());
        cuenta.setDescripcion(((TextView)findViewById(R.id.txtDescripcion)).getText().toString());
        Float saldo =  Float.parseFloat (((TextView)findViewById(R.id.txtSaldo)).getText().toString());
        cuenta.setSaldo(saldo);
        Boolean resultado = false;
        try {
            resultado = cuentaDao.agregar(cuenta);
        }catch (ValidacionException ex){
            Toast toast = Toast.makeText(this, ex.getMensaje(), Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Falló al intentar agregar la cuenta.", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (resultado == true) {
            Toast toast = Toast.makeText(this, "Se agregó la cuenta correctamente.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
