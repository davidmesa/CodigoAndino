
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
import javax.ejb.Local;

/**
 *
 * @author Cristian
 */
@Local
public interface ServicioDashBoardLocal {
    
    /**
     * Retorna los Pacientes del médico en cuestión
     * @return Arreglo de Pacientes
     */
    Collection<Paciente> darPacientes();
    

    /**
     * Retorna el arreglo de pacientes que han tenido emergencia
     * @return Arreglo de Pacientes
     */
    ArrayList<Paciente> darPacientesConEmergencia();
    
    /**
     * Retorna el arreglo de pacientes que han tenido una consulta
     * @return Arreglo de pacientes
     */
    ArrayList<Paciente> darPacientesConConsulta();

    /**
     * Retorna arreglo de Enteros que indican para cada posicion el numero de Registros
     * Sea posición 0=Domingo, 1=Lunes, 2=Martes y asi hasta el día 6=Sábado
     * @return Arreglo de Enteros
     */
    Integer[] darRegistrosSemanales();

    public void setActual(Medico actual);

}
