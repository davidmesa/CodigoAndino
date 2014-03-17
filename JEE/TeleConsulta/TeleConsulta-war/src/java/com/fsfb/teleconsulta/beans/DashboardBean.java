/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.teleconsulta.beans;

import com.fsfb.bos.Medico;
import com.fsfb.bos.Paciente;
import com.fsfb.servicios.ServicioDashBoard;
import com.fsfb.servicios.ServicioDashBoardLocal;
import java.util.Collection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author davidmesa
 */
@ManagedBean(name="dashboardBean")
@SessionScoped
public class DashboardBean {

    private ServicioDashBoardLocal servicio;
    
    private Medico medico;

    /**
     * Creates a new instance of DashboardBean
     */
    public DashboardBean() {
        servicio = new ServicioDashBoard();
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
        servicio.setActual(medico);
    }
    
    /**
     * Get the amount of Alertas
     * 
     * @return The amount of alertas.
     */
    public int getNumeroDeAlertas()
    {
        return servicio.darPacientesConEmergencia().size();
    }
    
    /**
     * Get the amount of Consejos
     * 
     * @return The amount of consejos.
     */
    public int getNumeroConsejos()
    {
        return servicio.darPacientesConConsulta().size();
    }
    
    /**
     * Get collection of Reportes Consejos
     * @return Collection of Reportes Consejos
     */
    public Collection<Paciente> getReportesConsejos()
    {
        return servicio.darPacientesConConsulta();
    }
    
    /**
     * Get collection of Reportes Alertas
     * @return Collection of reportes Alertas
     */
    public Collection<Paciente> getReportesAlerta()
    {
        return servicio.darPacientesConEmergencia();
    }
    
    /**
     * Get data for registros chart
     * 
     * @return Data for registros chart
     */
    public String getDataRegistrosSemana()
    {
        Integer[] datos = servicio.darRegistrosSemanales();
        String respuesta = ""+ datos[0];
        for (int i = 1; i < datos.length; i++) {
            respuesta += " , "+datos[i];
        }
        return respuesta;
    }
}
