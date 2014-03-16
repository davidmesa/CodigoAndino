/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.bos;

import java.util.ArrayList;
import java.util.HashMap;

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
    
    private HashMap<String, Paciente> pacientes;
    
    /**
     * Metodo constructor de la clase medico
     * @param nUsuario Nombre de usuario del medico.
     * @param nContrasena Contrasena del medico.
     */
    public Medico( String nUsuario, String nContrasena ) {
        usuario = nUsuario;
        contrasena = nContrasena;
        pacientes = new HashMap<String, Paciente>();
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
    
    /**
     * Método que agrega un paciente al hashmap
     */
    public void agregarPaciente(Paciente paciente)
    {
        pacientes.put(paciente.getUsuario(), paciente);
    }
    
    /**
     * Método que remueve un paciente de la lista del médico
     * @param paciente el paciente que se quiere eliminar
     */
    public void eliminarPaciente(Paciente paciente)
    {
        pacientes.remove(paciente.getUsuario());
    }
    
    /**
     * Método que retorna le arreglo de pacientes
     * @return los pacientes en un arreglo
     */
    public ArrayList<Paciente> darpacientes()
    {
        return (ArrayList)pacientes.values();
    }
}
