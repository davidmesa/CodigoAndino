package mensajeria;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Mensajero {

	private static final String BASE_URL = "http://localhost:8080/TeleConsulta-war/cliente/movil";

	private static final String IMC_METHOD_NAME = "/imc";

	private static final String TENSION_METHOD_NAME = "/tension";

	private static final String AUTH_METHOD_NAME = "/auth";

	public String enviarTension() {
		return null;
	}
	
	public String enviarIMC() {
		return null;
	}

	public String autenticar(String id, String password) {
		try {
			URL url = new URL(BASE_URL + AUTH_METHOD_NAME);
			HttpURLConnection conexion = (HttpURLConnection) url
					.openConnection();
			conexion.setDoOutput(true);
			conexion.setRequestMethod("POST");

			OutputStreamWriter writer = new OutputStreamWriter(
					conexion.getOutputStream());
			writer.write("id=" + id + "&password=" + password);
			writer.close();

			InputStream in = conexion.getInputStream();
			Scanner scanner = new Scanner(in,"UTF-8").useDelimiter("\\A");
	        String res = scanner.hasNext() ? scanner.next() : "";
	        System.out.println(res);
	        in.close();
	        scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
