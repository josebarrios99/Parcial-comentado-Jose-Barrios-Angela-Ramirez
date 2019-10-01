import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;

public class Cliente extends Observable implements Runnable {
	
	private Socket s;
	private DataOutputStream salida;
	private DataInputStream entrada;
	
	public  Cliente(Socket cliente) {
		// TODO Auto-generated constructor stub
		
		
		try {
			this.s = cliente;
			salida = new DataOutputStream(s.getOutputStream());
			entrada = new DataInputStream(s.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {			
			try {
				recibir();
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
				break;
			}
		}
	}



	private void recibir() throws IOException {
		// TODO Auto-generated method stub
		if(s!=null && s.isConnected() && !s.isClosed()){		
			String mensaje = entrada.readUTF();		
			System.out.println("CC: " + mensaje);
			setChanged();
			notifyObservers(mensaje);
			clearChanged();
			}
	}

}
