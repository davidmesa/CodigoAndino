/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.bos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;

/**
 * Clase Medico
 * @author davidmesa
 */
@Entity
@Table(name = "medico")
public class Medico implements Serializable {
    
    /**
     * Nombre de usuario del medico
     */
    @Id
    @Column(name = "nombre")
    private String usuario;
    
    /**
     * Contrasena del medico
     */
    private String contrasena;
    
    /**
     * Lista de Pacientes asignados
     */
    
    @OneToMany(mappedBy = "medicoAsignado")
    private List<Paciente> pacientes;
    
    //-------------------------------------------------------------------------
    //  Métodos
    //-------------------------------------------------------------------------
    
    /**
     * Constructor sin argumentos (necesario para JPA)
     */
    public Medico() {}
    
    /**
     * Metodo constructor de la clase medico
     * @param nUsuario Nombre de usuario del medico.
     * @param nContrasena Contrasena del medico.
     */
    public Medico( String nUsuario, String nContrasena ) {
        usuario = nUsuario;
        contrasena = nContrasena;
        pacientes = new ArrayList<Paciente>();
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
        pacientes.add(paciente);
    }
    
    /**
     * Método que remueve un paciente de la lista del médico
     * @param paciente el paciente que se quiere eliminar
     */
    public void eliminarPaciente(Paciente paciente)
    {
        pacientes.remove(paciente);
    }
    
    /**
     * Método que retorna le arreglo de pacientes
     * @return los pacientes en un arreglo
     */
    public Collection<Paciente> darpacientes()
    {
        return pacientes;
    }
}