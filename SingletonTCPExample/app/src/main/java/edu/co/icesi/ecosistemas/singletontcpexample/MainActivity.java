package edu.co.icesi.ecosistemas.singletontcpexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

/**
 * Esta es la actividad principal, en donde se implementa la clase observer y sirve para ejecutar
 * los procesos de los botones
 */
public class MainActivity extends AppCompatActivity implements Observer {

    /*
    Se crean variables
     */

    private TextView numberOfRects;
    private String color;

    @Override
    /**
     * Este es el metodo en donde se crean las variables de los botones
     * y textos que tenga la actividad y se les asigna una tarea
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberOfRects = findViewById(R.id.tv_num_main);
        /*
        Se inician los metodos del singleton
         */
        SingletonComunicacion.getInstance();
        SingletonComunicacion.getInstance().addObserver(this);
        System.out.println("INSTANCIA:" + SingletonComunicacion.getInstance());
        /*
        Se instancian las variables de cada boton
         */
        Button btnRed = findViewById(R.id.btn_main_red);
        Button btnGreen = findViewById(R.id.btn_main_green);
        Button btnBlue = findViewById(R.id.btn_main_blue);

        /**
         * Este metodo se ejecuta cada vez que le demos click al boton
         * asignado
         */
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "rojo";
            }
        });
        /**
         * Este metodo se ejecuta cada vez que le demos click al boton
         * asignado
         */
        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "verde";
            }
        });
        /**
         * Este metodo se ejecuta cada vez que le demos click al boton
         * asignado
         */
        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "azul";
            }
        });

        Button btnSend = findViewById(R.id.btn_main_go);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Este metodo se ejecuta cada vez que le demos click al boton
             * asignado
             */
            public void onClick(View view) {
                /*
                Se esta cambiando de actividad
                 */
                Intent i = new Intent(getApplicationContext(), SecondActivity.class);
                i.putExtra("color",color);
                startActivity(i);
               /* i.putExtra("apellido",color);
                i.putExtra("nombre",color);
                i.putExtra("telefono",color);
                i.putExtra("nota",color);*/
            }
        });

    }

    @Override
    /**
     * Trata la informacion que le brinda la conexion entre
     * el servidor y cliente
     */
    public void update(Observable observable, Object o) {
        if(o instanceof String){
            String m = (String)o;
            String[] partes = m.split(":");
            if(m.contains("num") && partes.length == 2){
                final int value = Integer.parseInt(partes[1]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        numberOfRects.setText(""+value);
                    }
                });
            }
        }
    }
}
