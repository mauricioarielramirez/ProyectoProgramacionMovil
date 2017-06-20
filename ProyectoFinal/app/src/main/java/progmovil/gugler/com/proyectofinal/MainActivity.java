package progmovil.gugler.com.proyectofinal;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static android.R.attr.path;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonTestClick(View view) {

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
    }
}
