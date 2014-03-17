/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fsfb.servicios;

import com.fsfb.bos.Medico;
import com.fsfb.bos.Paciente;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Cristian
 */
@Stateful
public class ServicioDashBoard implements ServicioDashBoardLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    /**
     * Medico actualmente usando el DashBoard
     */
    private Medico actual;
    
    /**
     * Conexión con la central de datos
     */
    private ServicioFSFB fsfb;
    
    public ServicioDashBoard() {
        fsfb = ServicioFSFB.darInstancia();
    }
    
    @Override
    public ArrayList<Paciente> darPacientes() {
        return actual.darpacientes();
    }
    
    @Override
    public ArrayList<Paciente> darPacientesConEmergencia() {
        return fsfb.darPacientesConEmergencia();
    }

    @Override
    public Integer[] darRegistrosSemanales() {
        return fsfb.darRegistrosSemanales();
    }
    
    @Override
    public void setActual(Medico actual) {
        this.actual = actual;
    }

    @Override
    public ArrayList<Paciente> darPacientesConConsulta() {
        return fsfb.darPacientesConConsulta();
    }
}
