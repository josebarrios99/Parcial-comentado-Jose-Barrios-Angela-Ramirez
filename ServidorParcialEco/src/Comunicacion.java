import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
/**
 * Esta clase es la encargada de establecer una comuniacion
 * entre el cliente y el servidor
 * @author Angela
 *
 */
public class Comunicacion extends Observable implements Runnable {
	
	/*
	 * Se estan creando las variables (los sockets y las entradas y salidas de informacion)
	 */
	
	private ServerSocket ss;
	private Socket s;
	private DataInputStream entrada;
	private DataOutputStream salida;
	
	/**
	 * Esta clase se encarga de inicializar las variables e indicarnos
	 * cuando se establecio una conexion
	 */
	
	public Comunicacion() {		
		try {
			/*
			 * Se inicializa la variable en un puerto
			 */
			ss = new ServerSocket(5000);
			/*
			 * Se inicializa la variable con el metodo accept
			 * para que el servidor espere hasta que reciba
			 * algun dato del cliente
			 */
			s = ss.accept();
			/*
			 * Estas variables se inicializan como entras
			 * y salidas de informacion
			 */
			entrada = new DataInputStream(s.getInputStream());
			salida = new DataOutputStream(s.getOutputStream());
			System.out.println("CONECTADO");
		} catch (IOException e) {			
			e.printStackTrace();
		}			
	}
	

	@Override
	/**
	 * Este metodo es el encargado de dormir el hilo
	 * cuando el servidor reciba un dato del cliente
	 */
	public void run() {
		while(true) {			
			try {
				/*
				 * Se utiliza el metodo recibir para que no se pueda continuar
				 * hasta no recibir un mensaje
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
	 * @throws IOException
	 */
	private void recibir() throws IOException {
		/*
		 * Se inicializa una variable que es el mensaje con la entrada
		 * que creamos mas el metodo que le permite al servidor leerlo
		 */
		String mensaje = entrada.readUTF();
		setChanged();
		/*
		 * Se le notifica al observer que se recibio un mensaje
		 */
		notifyObservers(mensaje);
		clearChanged();		
	}
	/**
	 * Es el metodo encargado de enviar un mensaje al cliente
	 * @param mensaje -Un string que se manda al cliente
	 */
	public void enviar(String mensaje) {
		try {
			/*
			 * A la salida creada se le agrega el metodo que nos permite escribir un mensaje para el servidor
			 */
			salida.writeUTF(mensaje);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

}