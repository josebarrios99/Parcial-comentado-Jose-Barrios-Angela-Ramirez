import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Esta clase es la encargada de establecer una comuniacion entre el cliente y
 * el servidor
 * 
 * @author Angela
 *
 */
public class Comunicacion extends Observable implements Runnable, Observer {

	/*
	 * Se estan creando las variables (los sockets y las entradas y salidas de
	 * informacion)
	 */

	private ServerSocket ss;
	private DataInputStream entrada;
	private DataOutputStream salida;
	private ArrayList<Cliente> sockets;
	

	/**
	 * Esta clase se encarga de inicializar las variables e indicarnos cuando se
	 * establecio una conexion
	 */

	public Comunicacion() {

		
		try {
			/*
			 * Se inicializa la variable en un puerto
			 * 
			 */
			ss = new ServerSocket(5000);
			sockets = new ArrayList<>();

			System.out.println("CONECTADO");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * Este metodo es el encargado de dormir el hilo cuando el servidor reciba un
	 * dato del cliente
	 */
	public void run() {
		while (true) {
			try {
				/*
				 * Se utiliza el metodo recibir para que no se pueda continuar hasta no recibir
				 * un mensaje
				 */
				recibir();
				/*
				 * Se pone al hilo a dormir
				 */
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Es el metodo encargado de recibir los mensajes del cliente
	 * 
	 * @throws IOException
	 */
	private void recibir() throws IOException {

		// Se acepta conexion
		Socket s = ss.accept();
		entrada = new DataInputStream(s.getInputStream());
		salida = new DataOutputStream(s.getOutputStream());
		//la conexion se lo pasamos al cliente
		Cliente c = new Cliente(s);
		// agregamos cliente a la lista de clientes del servidor
		sockets.add(c);

		Thread t = new Thread(c);
		t.start();
		c.addObserver(this);
		
		
	}

	/**
	 * Es el metodo encargado de enviar un mensaje al cliente
	 * 
	 * @param mensaje -Un string que se manda al cliente
	 */
	public void enviar(String mensaje) {
		try {
			/*
			 * A la salida creada se le agrega el metodo que nos permite escribir un mensaje
			 * para el servidor
			 */
			salida.writeUTF(mensaje);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		String mensaje= (String) arg1;
		// TODO Auto-generated method stub
		setChanged();
		/*
		 * Se le notifica al observer que se recibio un mensaje
		 */
		notifyObservers(mensaje);
		clearChanged();
	}

}