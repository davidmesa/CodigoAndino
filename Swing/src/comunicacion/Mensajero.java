package comunicacion;

import java.net.Socket;

public class Mensajero {
	private static final String HOST = "localhost";
	
	private static final int PORT = 8080;
	
	private Socket socket;
	
	public Mensajero() {
		try {
			socket = new Socket(HOST, PORT);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public void enviarMensaje() {
		
	}
}
