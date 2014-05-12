/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.servicios;

import javax.ejb.Local;

/**
 *
 * @author davidmesa
 */
@Local
public interface ServicioPacienteLocal {

    void agregarOModificar(String user, String contrasena, double estatura, String fecha, String medico);
    
}
