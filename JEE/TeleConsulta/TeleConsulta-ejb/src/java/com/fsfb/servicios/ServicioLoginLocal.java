/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.servicios;

import com.fsfb.bos.Medico;
import com.fsfb.bos.Paciente;
import javax.ejb.Local;
import javax.security.auth.message.AuthException;

/**
 * Interfaz local para servicio login
 * @author davidmesa
 */
@Local
public interface ServicioLoginLocal {

    /**
     * Registra el ingreso de un medico al sistema.
     * @param usuario Login del medico que quiere ingresar al sistema.
     * @param contrasena Contraseña del medico que quiere ingresar al sistema.
     * @return medico Retorna el objeto que contiene la información del medico que ingreso al sistema.
     * @exception AuthException En caso de que el medico no este registrado.
     */
    Medico loginMedico(String usuario, String contrasena) throws AuthException;

    /**
     * Registra el ingreso de un paciente al sistema.
     * @param token Token del paciente que quiere ingresar al sistema.
     * @return paciente Retorna el objeto que contiene la información del paciente que ingreso al sistema.
     * @exception AuthException en caso de que el paciente no exista.
     */
    Paciente loginPaciente(String token) throws AuthException;

    /**
     * Crea un token para ser utilizado por el paciente registrado en la aplicacion.
     * @param usuario Nombre de usuario del paciente.
     * @param contrasena Contrasena del paciente. 
     * @return Token del usuario en caso de estar registrado.
     * @exception AuthException en caso de que el paciente no exista.
     */
    String getTokenPaciente(String usuario, String contrasena) throws AuthException;
}
