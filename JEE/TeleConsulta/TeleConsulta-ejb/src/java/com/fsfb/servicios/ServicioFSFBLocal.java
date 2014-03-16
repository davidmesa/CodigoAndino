/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fsfb.servicios;

import com.fsfb.bos.Medico;
import com.fsfb.bos.Paciente;
import javax.ejb.Local;
import javax.security.auth.message.AuthException;

/**
 *
 * @author Cristian
 */
@Local
public interface ServicioFSFBLocal {
    /**
     * Método que retorna null si la contraseña no es correcta.
     * @param usuario, El nombre del usuario
     * @param contrasena, Contraseña del Medico
     * @return Medico, dado el caso, que sea correcto el Usuario y Contraseña
     * @throws AuthException si no existe el Usuario
     */
    public Medico darMedicoPorDatos(String usuario, String contrasena) throws AuthException;
    
    /**
     * Registra el ingreso de un paciente al sistema.
     * @param token Token del paciente que quiere ingresar al sistema.
     * @return paciente Retorna el objeto que contiene la información del paciente que ingreso al sistema.
     * @exception AuthException en caso de que el paciente no exista.
     */
    public Paciente darPacientePorToken(String token) throws AuthException;
    
    /**
     * Crea un token para ser utilizado por el paciente registrado en la aplicacion.
     * @param usuario Nombre de usuario del paciente.
     * @param contrasena Contrasena del paciente. 
     * @return Token del usuario en caso de estar registrado.
     * @exception AuthException en caso de que el paciente no exista.
     */
    public String darTokenDelPaciente(String usuario, String contrasena) throws AuthException;
}
