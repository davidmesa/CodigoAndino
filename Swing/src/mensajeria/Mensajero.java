package mensajeria;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import sistema.Seguridad;

public class Mensajero {

	private static final String BASE_URL = "http://localhost:8080/TeleConsulta-war/cliente/movil";

	private static final String IMC_METHOD_NAME = "/imc";

	private static final String TENSION_METHOD_NAME = "/tension";

	private static final String AUTH_METHOD_NAME = "/auth";

	private static Mensajero instancia;
	
	private String token;
	
	public static Mensajero getInstance() {
		if(instancia == null) {
			instancia = new Mensajero();
		}
		return instancia;
	}
	
	private Mensajero() {}

	public String enviarTension(int diastole, int sistole, int pulso)
			throws Exception {
		if (token == null) {
			throw new Exception();
		} else {
			HashMap<String, String> valores = new HashMap<String, String>();
			valores.put("token", token);
			valores.put("diastole", diastole + "");
			valores.put("sistole", sistole + "");
			valores.put("pulso", pulso + "");
			valores.put("fecha", Seguridad.hashMD5(token+"+"+diastole+"-"+sistole+"*"+pulso));

			HttpURLConnection conexion = getReadyConnection(TENSION_METHOD_NAME);
			enviarMensaje(conexion, valores);
			JSONObject response = (JSONObject) JSONValue.parse(darJSON(conexion
					.getInputStream()));
			conexion.disconnect();

			if (((String) response.get("status")).equals("error")) {
				throw new Exception();
			} else if (((String) response.get("status")).equals("alert")) {
				return ((String) response.get("mensaje"));
			}
			return "ok";
		}
	}

	@SuppressWarnings("unchecked")
	public String enviarIMC(double peso, int altura) throws Exception {
		if (token == null) {
			throw new Exception();
		} else {
			HashMap<String, String> valores = new HashMap<String, String>();
			valores.put("token", token);
			valores.put("peso", peso + "");
			valores.put("altura", altura + "");
			valores.put("fecha", Seguridad.hashMD5(token+"+"+peso+"-"+altura));
			
			HttpURLConnection conexion = getReadyConnection(IMC_METHOD_NAME);
			enviarMensaje(conexion, valores);
			JSONObject response = (JSONObject) JSONValue.parse(darJSON(conexion
					.getInputStream()));
			conexion.disconnect();

			if (((String) response.get("status")).equals("error")) {
				throw new Exception();
			} else if (((String) response.get("status")).equals("alert")) {
				return (String) response.get("mensaje");
			}
			return "ok";
		}
	}

	public boolean autenticar(String id, String password) throws Exception {
		HashMap<String, String> valores = new HashMap<String, String>();
		valores.put("id", id);
		valores.put("password", password);
		valores.put("fecha", id+"+"+password);
		
		HttpURLConnection conexion = getReadyConnection(AUTH_METHOD_NAME);
		enviarMensaje(conexion, valores);
		JSONObject response = (JSONObject) JSONValue.parse(darJSON(conexion
				.getInputStream()));
		conexion.disconnect();

		if (response.containsKey("status")) {
			if(response.get("status").equals("error")) {
				throw new Exception((String) response.get("mensaje"));				
			}
		} else {
			token = (String) response.get("token");
			return true;
		}
		return false;
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
