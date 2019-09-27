package edu.co.icesi.ecosistemas.singletontcpexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

/**
 * Esta clase es la segunda actividad donde se la da
 * la posicion a los cuadros
 */
public class SecondActivity extends AppCompatActivity implements Observer {

/*
Se crean variables
 */
    private String color;
    private TextView num;

    @Override
    /**
     * Es el metodo principal de la clase donde se inicializan las variables
     * de los botones y textos que usan en la pantalla
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        /*
        Se inicializan las variables
         */

        Intent i = getIntent();
        color = i.getStringExtra("color");

        num = findViewById(R.id.tv_second_num);

        SingletonComunicacion com = SingletonComunicacion.getInstance();
        com.addObserver(this);

        Button btnSend = findViewById(R.id.btn_second_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Es el metodo que se ejecuta al darle click a un boton
             */
            public void onClick(View view) {
                /*
                Se le esta dando valores a las variables
                 */
                EditText edtX = findViewById(R.id.edt_second_x);
                EditText edtY = findViewById(R.id.edt_second_y);
                String valX = edtX.getText().toString();
                String valY = edtY.getText().toString();
                SingletonComunicacion.getInstance().enviar(valX+":"+valY+":"+color);
                finish();
            }
        });
    }

    @Override
    /**
     * Es el metodo que verifica la entrada de datos
     */
    public void update(Observable observable, Object o) {
        if(o instanceof String){
            String m = (String)o;
            /*
            Se crea un arreglo de strings que seran
            las partes del mensaje
             */
            String[] partes = m.split(":");
            if(m.contains("num") && partes.length == 2){
                final int value = Integer.parseInt(partes[1]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        num.setText(""+value);
                    }
                });
            }
        }
    }
}
