/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.teleconsulta.beans;

import com.fsfb.bos.Medico;
import com.fsfb.servicios.ServicioLoginLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;

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

    @EJB
    private ServicioLoginLocal servicioLogin;
    
    private String mensaje;

    private boolean mostrarMensaje;

        
    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------
    
    /**
     * Creates a new instance of Login
     */
    public LoginBean() {
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
     * Get the value of mostrarMensaje
     *
     * @return the value of mostrarMensaje
     */
    public boolean isMostrarMensaje() {
        return mostrarMensaje;
    }

    /**
     * Get the value of mensaje
     *
     * @return the value of mensaje
     */
    public String getMensaje() {
        return mensaje;
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
        HttpServletRequest request = (HttpServletRequest) FacesContext  
                .getCurrentInstance().getExternalContext().getRequest();
        if(request.isUserInRole("medico"))
        {
            if(autenticar(request.getUserPrincipal().getName()))
            {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                String outcome = "principal_menu";
                facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
            }
        }
        else if (request.isUserInRole("unidad"))
        {
           if(autenticar(request.getUserPrincipal().getName()))
            {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                String outcome = "principal_menu_unidad";
                facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
            } 
        }
        return "principal_menu";
    }
    
    private boolean autenticar(String usuario)
    {
        try {
            mostrarMensaje = false;
            mensaje = null;
            //Busco el medico y verifico el login
            Medico medico = servicioLogin.loginMedico(usuario, "algo");
            FacesContext context = FacesContext.getCurrentInstance();
            DashboardBean dashboardBean = (DashboardBean) context.getApplication().evaluateExpressionGet(context, "#{dashboardBean}", DashboardBean.class);
            dashboardBean.setMedico(medico);
            return true;
        } catch (AuthException ex) {
            mostrarMensaje = true;
            mensaje = ex.getMessage();
            return false;
        }
        
    }
}
