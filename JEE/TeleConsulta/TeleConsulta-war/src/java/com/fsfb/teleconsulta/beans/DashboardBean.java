/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.teleconsulta.beans;

import com.fsfb.bos.Medico;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author davidmesa
 */
@ManagedBean(name="dashboardBean")
@SessionScoped
public class DashboardBean {

    
    private Medico medico;

    /**
     * Creates a new instance of DashboardBean
     */
    public DashboardBean() {
        
    }
    
    /**
     * Get the value of nombreMedico
     *
     * @return the value of nombreMedico
     */
    public String getNombreMedico() {
        return medico.getUsuario();
    }
    
    /**
     * Set the value of Medico
     * 
     * @param nMedico  The value of Medico
     */
    public void setMedico(Medico nMedico)
    {
        medico = nMedico;
    }
}
