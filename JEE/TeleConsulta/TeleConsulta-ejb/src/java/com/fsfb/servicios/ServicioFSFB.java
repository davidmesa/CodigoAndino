/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fsfb.servicios;

import com.fsfb.bos.Medico;
import com.fsfb.bos.Paciente;
import java.util.HashMap;
import javax.ejb.Singleton;
import javax.security.auth.message.AuthException;

/**
 * Maneja los medicos y pacientes del hospital.
 * @author Cristian
 */
@Singleton
public class ServicioFSFB implements ServicioFSFBLocal {

    /**
     * Constante para la creación simulada de pacientes
     */
    public final static int NUMERO_PACIENTES=10;
    
    /**
     * Constante para la creación simulada de medicos
     */
    public final static int NUMERO_MEDICOS=5;
    
    /**
     * HashMap que almacena los medicos.
     */
    private HashMap<String, Medico> medicos;
    
    /**
     * HashMap que almacena los pacientes.
     */
    private HashMap<String, Paciente> pacientes;
    
    /**
     * Constructor
     */
    public ServicioFSFB()
    {
        medicos = new HashMap<String, Medico>();
        for(int i=0; i<NUMERO_MEDICOS; i++)
        {
            medicos.put("Medico"+(i+1), new Medico("Medico"+(i+1),"Medico"+(i+1)));
        }
        medicos.put("davidmesa", new Medico("davidmesa", "algo"));
        
        pacientes = new HashMap<String, Paciente>();
        for(int i=0; i<NUMERO_PACIENTES; i++)
        {
            pacientes.put("Paciente"+(i+1), new Paciente("Paciente"+(i+1),"Paciente"+(i+1)));
        }
        pacientes.put("cristiansierra", new Paciente("cristiansierra", "algo"));
    }
    
    /**
     * Método que retorna null si la contraseña no es correcta.
     * @param usuario, El nombre del usuario
     * @param contrasena, Contraseña del Medico
     * @return Medico, dado el caso, que sea correcto el Usuario y Contraseña
     * @throws AuthException si no existe el Usuario
     */
    @Override
    public Medico darMedicoPorDatos(String usuario, String contrasena) throws AuthException
    {
        Medico buscado=medicos.get(usuario);
        if(buscado!=null)
        {
            if(!buscado.getContrasena().equals(contrasena))
            {
                buscado=null;
            }
        }
        else
        {
            throw new AuthException();
        }
        return buscado;
    }
    
    /**
     * Registra el ingreso de un paciente al sistema.
     * @param token Token del paciente que quiere ingresar al sistema.
     * @return paciente Retorna el objeto que contiene la información del paciente que ingreso al sistema.
     * @exception AuthException en caso de que el paciente no exista.
     */
    @Override
    public Paciente darPacientePorToken(String token) throws AuthException
    {
        String[] fracciones=token.split("-");
        String paramUsuario=fracciones[1];
        String paramContrasena=fracciones[4];
        
        Paciente buscado=pacientes.get(paramUsuario);
        if(buscado!=null)
        {
            if(!buscado.getContrasena().equals(paramContrasena))
            {
                buscado=null;
            }
        }
        else
        {
            throw new AuthException();
        }
        return buscado;
    }
    
    /**
     * Crea un token para ser utilizado por el paciente registrado en la aplicacion.
     * @param usuario Nombre de usuario del paciente.
     * @param contrasena Contrasena del paciente. 
     * @return Token del usuario en caso de estar registrado.
     * @exception AuthException en caso de que el paciente no exista.
     */
    @Override
    public String darTokenDelPaciente(String usuario, String contrasena) throws AuthException
    {
        Paciente buscado=pacientes.get(usuario);
        if(buscado!=null)
        {
            if(!buscado.getContrasena().equals(contrasena))
            {
                throw new AuthException();
            }
            else
            {
                return "-"+usuario+"--"+contrasena+"-";
            }
        }
        else
        {
            throw new AuthException();
        }
    }
}