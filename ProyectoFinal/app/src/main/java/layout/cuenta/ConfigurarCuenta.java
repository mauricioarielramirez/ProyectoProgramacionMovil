package layout.cuenta;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import progmovil.gugler.com.proyectofinal.R;

public class ConfigurarCuenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_cuenta);

        ActionBar actionBar = getSupportActionBar(); // Permite personalizar el action bar
        actionBar.setTitle("Configurar Cuenta");
        actionBar.setIcon(R.mipmap.ic_launcher);
    }
}
