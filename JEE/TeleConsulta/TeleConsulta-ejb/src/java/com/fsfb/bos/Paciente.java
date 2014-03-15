/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.bos;

/**
 * Clase Medico
 * @author davidmesa
 */
public class Paciente {
    
    /**
     * Nombre de usuario del paciente
     */
    private String usuario;
    
    /**
     * Contrasena del paciente
     */
    private String contrasena;

    /**
     * Constructor de la clase Paciente
     * @param usuario Nombre de usuario del paciente.
     * @param contrasena Contrasena del paciente.
     */
    public Paciente(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
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
