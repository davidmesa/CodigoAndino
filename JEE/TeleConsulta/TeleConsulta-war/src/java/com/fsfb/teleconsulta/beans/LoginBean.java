/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.teleconsulta.beans;

import com.fsfb.servicios.ServicioLogin;
import com.fsfb.servicios.ServicioLoginLocal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author davidmesa
 */
@ManagedBean
@RequestScoped
public class LoginBean {

    //-----------------------------------------------------------
    // Parametros
    //-----------------------------------------------------------
    
    private String usuario;
    
    private String contrasena;

    private ServicioLoginLocal servicio;
    
    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------
    
    /**
     * Creates a new instance of Login
     */
    public LoginBean() {
        servicio = new ServicioLogin();
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
    
    //-----------------------------------------------------------
    // Metodos
    //-----------------------------------------------------------
    
    /**
     * Verifica si el medico puede ingresar
     * @return Retorna mensaje de redireccion
     */
    public String loginMedico()
    {
        return "dashboard";
    }
}
