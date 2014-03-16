/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fsfb.servicios;

import com.fsfb.bos.Paciente;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author Cristian
 */
@Local
public interface ServicioDashBoardLocal {
    
    ArrayList<Paciente> darPacientes();

}
