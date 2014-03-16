/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fsfb.servicios;

import com.fsfb.bos.Medico;
import com.fsfb.bos.Paciente;
import java.util.ArrayList;
import javax.ejb.Stateful;
import javax.security.auth.message.AuthException;

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
        fsfb=new ServicioFSFB();
        try {
            actual=fsfb.darMedicoPorDatos("davidmesa", "algo");
        } catch (AuthException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public ArrayList<Paciente> darPacientes() {
        return actual.darpacientes();
    }
}
