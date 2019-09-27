import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

/**
 * Esta clase es la encargada de especificar el tamano del lienzo y
 * de ejecutar los procesos principales
 * @author Angela
 */

public class MainApp extends PApplet implements Observer {
	
	

	public static void main(String[] args) {
		PApplet.main("MainApp");
	}
	
	/**
	 * En este metodo se especifican las medidas que tendra el lienzo
	 * donde se van a pintar las figuras
	 */
	
	@Override
	public void settings() {
		/*
		 * Metodo usado para crear el lienzo y darle un tamano
		 */
		size(500,500);
	}
	/*
	 * Aqui se esta creando un arraylist de objetos de tipo Cuadro
	 * y una variable de la clase comuniacion
	 */
	Comunicacion com;
	ArrayList<Cuadro> cuadros;
	
	/**
	 * Este metodo es el que se ejecuta una vez y es donde se inicializan
	 * las variables
	 */
	
	@Override
	public void setup() {
		/*
		 * Se estan inicializando las variables
		 */
		
		cuadros = new ArrayList<Cuadro>();
		
		com = new Comunicacion();
		/*
		 * A la variable comunicacion se le aplica el metodo
		 * AddObserver con una instancia de la clase
		 */
		com.addObserver(this);
		/*
		 * Se esta creando un hilo e iniciandolo
		 */
		new Thread(com).start();		
	}
	/**
	 * Este metodo se ejecuta continuamente y es el encargado
	 * de pintar las cosas sobre el lienzo y recorrer los arreglos
	 */
	
	@Override
	public void draw() {
		/*
		 * Se creaun color para el fondo
		 */
		background(255);
		/*
		 * Se esta recorriendo el arraylist de cuadros
 		 * para pintarlos
		 */
		for (int i = 0; i < cuadros.size(); i++) {
			cuadros.get(i).pintar(this);
		}
	}
	/**
	 * Este metodo sirve para verificar cuando entre un mensaje
	 * desde el cliente
	 */
	@Override
	public void update(Observable arg0, Object o) {	
		if(o instanceof String) {
			/*
			 * Se crea la variable mensaje y se la da el valor de o
			 * con un cast de String
			 */
			String mensaje = (String)o;
			/*
			 * Se crea un arreglo de strings en donde estaran
			 * las partes del mensaje para que el servidor los pueda
			 * leer por separado
			 */
			String[] partes = mensaje.split(":");
			/*
			 * Se crean varias variables a las cuales se les da valor
			 * con cada una de las partes del mensaje
			 */
			int x = Integer.parseInt(partes[0]);
			int y = Integer.parseInt(partes[1]);
			String color = partes[2];
			/*
			 * Se agregan los objetos al arreglo con las variables
			 * creadas anteriormente
			 */
			cuadros.add(new Cuadro(x, y, color));
			/*
			 * Se envia un mensaje de cuantos cuadros se agregaron al arreglo
			 */
			com.enviar("num:"+cuadros.size());
		}
	}

}
