package edu.co.icesi.ecosistemas.singletontcpexample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

/**
 * Esta clase es la encargada de la conexion entre el servidor y cliente
 */
public class SingletonComunicacion extends Observable implements Runnable{

    /*
    Se estan creando las variables de entrada y salida y los sockets
     */
    private static SingletonComunicacion ref;

    private Socket s;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private boolean conectado;

    /**
     * Este metodo sirve para inicializar la conexion cuando este conectado
     */
    private SingletonComunicacion(){
        conectado = false;
    }

    /**
     * En este metodo se instancia la clase singleton y se crea un hilo
     * y se pone a correr
     *
     * @return instance instance
     */
    public static SingletonComunicacion getInstance() {
        if( ref == null ){
            ref = new SingletonComunicacion();
            new Thread(ref).start();
            System.out.println("INICIADO");
        }
        return ref;
    }

    @Override
    /**
     * Es el metodo que va a estar pendiente
     * si se establecio una conexion con el servidor
     */
    public void run() {
        /*
        Se crea un if para conectarse
         */
        if(!conectado){
            try {
                /*
                Aqui se crea una conexion con la ip del servidor
                 */
                InetAddress ip = InetAddress.getByName("10.0.2.2");
                /*
                Se esta inicializando la variable de socket en un puerto
                 */
                s = new Socket(ip, 5000);
                /*
                Se inicializan las variables de entrada y de salida
                 */
                entrada = new DataInputStream(s.getInputStream());
                salida = new DataOutputStream(s.getOutputStream());
                /*
                Esta es la variable que nos indica si hay coneccion o no
                 */
                conectado = true;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        while(true){
            try {
                /*
                Cuando se reciba un mensaje el hilo duerme
                 */
                recibir();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Este es el metodo encargado de recibir la informacion del servidor
     * @throws IOException
     */
    private void recibir() throws IOException {
        if(conectado){
            String mensajeEntrada = entrada.readUTF();
            setChanged();
            notifyObservers(mensajeEntrada);
            clearChanged();
        }
    }

    /**
     * Es el metodo encargado de mandar informacion al servidor
     *
     * @param mensaje the mensaje
     */
    public void enviar(final String mensaje){
        if(conectado) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        salida.writeUTF(mensaje);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
