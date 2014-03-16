/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.servicios;

import com.fsfb.bos.Medico;
import com.fsfb.bos.Paciente;
import java.util.Date;
import java.util.HashMap;
import javax.ejb.Stateless;
import javax.security.auth.message.AuthException;

/**
 * Servicio de logIn local
 * @author davidmesa
 */
@Stateless
public class ServicioLogin implements ServicioLoginLocal {

    /**
     * HashMap que almacena los medicos.
     */
    private HashMap<String, Medico> medicos;
    
    /**
     * HashMap que almacena los pacientes.
     */
    private HashMap<String, Paciente> pacientes;
    
    /**
     * Constructor del Servicio login
     */
    public ServicioLogin() {
        medicos = new HashMap<String, Medico>();
        medicos.put("davidmesa", new Medico("davidmesa", "algo"));
        
        pacientes = new HashMap<String, Paciente>();
        pacientes.put("cristiansierra", new Paciente("cristiansierra", "algo", 160, new Date("01/01/1995")));
    }

    /**
     * Registra el ingreso de un medico al sistema.
     * @param usuario Login del medico que quiere ingresar al sistema.
     * @param contrasena Contraseña del medico que quiere ingresar al sistema.
     * @return medico Retorna el objeto que contiene la información del medico que ingreso al sistema.
     */
    @Override
    public Medico loginMedico(String usuario, String contrasena) throws AuthException{
        return null;
    }

    /**
     * Registra el ingreso de un paciente al sistema.
     * @param token Token del paciente que quiere ingresar al sistema.
     * @return paciente Retorna el objeto que contiene la información del paciente que ingreso al sistema.
     * @exception AuthException en caso de que el paciente no exista.
     */
    @Override
    public Paciente loginPaciente(String token) throws AuthException{
        return null;
    } 

    /**
     * Crea un token para ser utilizado por el paciente registrado en la aplicacion.
     * @param usuario Nombre de usuario del paciente.
     * @param contrasena Contrasena del paciente. 
     * @return Token del usuario en caso de estar registrado.
     * @exception AuthException en caso de que el paciente no exista.
     */
    @Override
    public String getTokenPaciente(String usuario, String contrasena) throws AuthException{
        return null;
    }
}
