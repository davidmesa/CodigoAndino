package sistema;

import interfaz.Login;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Seguridad {

	public SecretKey desKey; 
	private final static String ALGORITMO="AES"; 
	private final static String PADDING="AES/ECB/PKCS5Padding";
	
	public static String hashMD5(String aResumir)
	{
            MessageDigest md;
			try {
				md = MessageDigest.getInstance("MD5");
	            md.update(aResumir.getBytes());
	            byte[] bytes = md.digest();
	            StringBuilder sb = new StringBuilder();
	            for(int i=0; i< bytes.length ;i++)
	            {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            System.out.println(sb.toString());
	            return sb.toString();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	}
	
	public byte[] cifrar() { 
		 byte [] cipheredText; 
		 
		 try { 
		 KeyGenerator keygen = KeyGenerator.getInstance(ALGORITMO); 
		 desKey = keygen.generateKey(); 
		 Cipher cipher = Cipher.getInstance(PADDING);
		 
		 BufferedReader stdIn = new BufferedReader( 
		 new InputStreamReader(System.in)); 
		 String pwd = stdIn.readLine(); 
		 byte [] clearText = pwd.getBytes(); 
		 String s1 = new String (clearText); 
		 System.out.println("clave original: " + s1); 
		 
		 cipher.init(Cipher.ENCRYPT_MODE, desKey); 
		 long startTime = System.nanoTime(); 
		 cipheredText = cipher.doFinal(clearText); 
		 long endTime = System.nanoTime(); 
		 String s2 = new String (cipheredText); 
		 System.out.println("clave cifrada: " + s2); 
		 System.out.println("Tiempo: " + (endTime - startTime)); 
		 return cipheredText; 
		 } 
		 catch (Exception e) { 
		 System.out.println("Excepcion: " + e.getMessage()); 
		 return null; 
		 } 
	}
	
	public void descifrar(byte [] cipheredText) {
		 
		 try { 
		 Cipher cipher = Cipher.getInstance(PADDING); 
		 cipher.init(Cipher.DECRYPT_MODE, desKey); 
		 byte [] clearText = cipher.doFinal(cipheredText); 
		 String s3 = new String(clearText); 
		 System.out.println("clave original: " + s3); 
		 } 
		 catch (Exception e) { 
		 System.out.println("Excepcion: " + e.getMessage()); 
		 } 
	}
	
	public static void main(String[] args) {
		Seguridad seguridad=new Seguridad();
		seguridad.descifrar(seguridad.cifrar());
	}

}
