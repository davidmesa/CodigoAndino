/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.teleconsulta.beans;

import com.fsfb.bos.Medico;
import com.fsfb.servicios.ServicioPacienteLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author davidmesa
 */
@ManagedBean
@RequestScoped
public class PacientesBean {
    
    @EJB
    private ServicioPacienteLocal servicioPaciente;

    //-----------------------------------------------------------
    // Parametros
    //-----------------------------------------------------------
    
    private String usuario;
    
    private String contrasena;
    
    private double estatura;
    
    private String fecha;
      
    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------
    
    /**
     * Creates a new instance of Login
     */
    public PacientesBean() {

    }
    
    //-----------------------------------------------------------
    // Geters y Seters
    //-----------------------------------------------------------
    
    /**
     * Get the value of usuario
     *
     * @return the value of usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Set the value of usuario
     *
     * @param usuario new value of usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Get the value of contrasena
     *
     * @return the value of contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Set the value of contrasena
     *
     * @param contrasena new value of contrasena
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    /**
     * Get the value of estatura
     *
     * @return the value of estatura
     */
    public double getEstatura() {
        return estatura;
    }

    /**
     * Set the value of estatura
     *
     * @param estatura new value of estatura
     */
    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }
    
    /**
     * Get the value of fecha
     *
     * @return the value of fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Set the value of fecha
     *
     * @param fecha new value of fecha
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String ingresarDatos()
    {
        HttpServletRequest request = (HttpServletRequest) FacesContext  
                .getCurrentInstance().getExternalContext().getRequest();
        servicioPaciente.agregarOModificar(usuario, contrasena, estatura, fecha, request.getUserPrincipal().getName());
        return null;
    }
    
       
}
