/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fsfb.servicios;

import com.fsfb.bos.Medico;
import com.fsfb.bos.Paciente;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.security.auth.message.AuthException;

/**
 *
 * @author Cristian
 */
@Stateful(mappedName="ServicioDashBoard", name="ServicioDashBoard")
public class ServicioDashBoard implements ServicioDashBoardLocal, ServicioDashBoardRemote{

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
    public Collection<Paciente> darPacientes() {
        return actual.darpacientes();
    }
    
    @Override
    public Collection<Paciente> darPacientes(String medico) {
        try {
            actual = fsfb.darMedicoPorDatos(medico, "algo");
            return actual.darpacientes();
        } catch (AuthException ex) {
            Logger.getLogger(ServicioDashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
    
    @Override
    public Medico darMedico()
    {
        return actual;
    }
}
