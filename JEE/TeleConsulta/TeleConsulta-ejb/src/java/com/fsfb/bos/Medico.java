/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.bos;

/**
 * Clase Medico
 * @author davidmesa
 */
public class Medico {

    /**
     * Nombre de usuario del medico
     */
    private String usuario;
    
    /**
     * Contrasena del medico
     */
    private String contrasena;
    
    /**
     * Metodo constructor de la clase medico
     * @param nUsuario Nombre de usuario del medico.
     * @param nContrasena Contrasena del medico.
     */
    public Medico( String nUsuario, String nContrasena ) {
        usuario = nUsuario;
        contrasena = nContrasena;
    }

    /**
     * Get the value of usuario
     * @return the value of usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Set the value of usuario
     * @param usuario new value of usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Get the value of contrasena
     * @return the value of contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Set the value of contrasena
     * @param contrasena new value of contrasena
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
