package cripto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.security.MessageDigest;
import java.util.Properties;

public class Autenticador {
	
	private Properties usuarios;
	
	private static Autenticador instancia;
	
	private String usuarioActual;
	
	public static Autenticador getInstance() {
		if(instancia == null) {
			instancia = new Autenticador();
		}
		return instancia;
	}
	
	private Autenticador() {
		try {
			usuarios = new Properties();
			
			File file = new File("usuarios.p");
			file.createNewFile();
			FileReader f = new FileReader(file);
			usuarios.load(f);
			f.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public boolean autenticar(String nomUsuario, String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(password.getBytes());
			password = new String(messageDigest.digest());
			
			return password.equals(usuarios.getProperty(nomUsuario));
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return false;
	}
	
	public void registrar(String nomUsuario, String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(password.getBytes());
			password = new String(messageDigest.digest());
			
			usuarios.put(nomUsuario, password);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public String darUsuarioActual() {
		return usuarioActual;
	}
	
	public void guardar() {
		try {
			FileOutputStream f = new FileOutputStream("usuarios.p");
			usuarios.store(f, null);
			f.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
