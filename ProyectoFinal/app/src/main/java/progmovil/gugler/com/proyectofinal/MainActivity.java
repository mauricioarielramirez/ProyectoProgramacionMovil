package progmovil.gugler.com.proyectofinal;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonTestClick(View view) {
        Toast.makeText(this, "Puto el que lee", Toast.LENGTH_LONG);
        try {
            FileOutputStream file = new FileOutputStream("Script.sql");
            TextView tv = (TextView) findViewById(R.id.textViewRes);
            //tv.setText(file.toString());
            Toast.makeText(this, "Contenido: " + file.toString(), Toast.LENGTH_LONG);
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            Toast.makeText(this, "Archivo no encontrado: " + e.getMessage(), Toast.LENGTH_SHORT);
        }
    }
}
