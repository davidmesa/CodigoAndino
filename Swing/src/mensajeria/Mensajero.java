package mensajeria;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Mensajero {

	private static final String BASE_URL = "http://localhost:8080/TeleConsulta-war/cliente/movil";

	private static final String IMC_METHOD_NAME = "/imc";

	private static final String TENSION_METHOD_NAME = "/tension";

	private static final String AUTH_METHOD_NAME = "/auth";

	private String token;

	public String enviarTension() {
		return null;
	}

	public String enviarIMC() {
		return null;
	}

	public void autenticar(String id, String password) throws Exception {
		HashMap<String, String> valores = new HashMap<String, String>();
		valores.put(id, password);

		HttpURLConnection conexion = getReadyConnection(AUTH_METHOD_NAME);
		enviarMensaje(conexion, valores);
		System.out.println(darJSON(conexion.getInputStream()));
		conexion.disconnect();
	}

	private void enviarMensaje(HttpURLConnection conexion,
			HashMap<String, String> valores) throws Exception {
		String res = valores.toString().replaceAll("\\{", "")
				.replaceAll(", ", "&");
		res = res.substring(0, res.length() - 1);

		OutputStreamWriter writer = new OutputStreamWriter(
				conexion.getOutputStream());
		writer.write(res);
		writer.close();
	}

	private HttpURLConnection getReadyConnection(String methodName)
			throws Exception {
		URL url = new URL(BASE_URL + methodName);
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
		conexion.setDoOutput(true);
		conexion.setRequestMethod("POST");
		return conexion;
	}

	@SuppressWarnings("resource")
	private String darJSON(InputStream in) throws Exception {
		Scanner scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
		String res = scanner.hasNext() ? scanner.next() : "";
		in.close();
		scanner.close();
		return res;
	}
}
