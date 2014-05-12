/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fsfb.servicios;

import com.fsfb.bos.Medico;
import com.fsfb.bos.Paciente;
import java.util.ArrayList;
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

    /**
     * Crea un registro de IMC para el paciente indicado.
     * @param paciente, del que vamos a registar IMC
     * @param peso, peso del paciente que servirá para calcular IMC
     * @param altura, altura del paciente por si ha cambiado desde la última vez.
     * @return String, NULL si todo esta bien, NO NULL, con el mensaje de alerta
     */
    String registarIMC(Paciente paciente, double peso, int altura);

    /**
     * Crea un registro de Presión Arterial para el paciente indicado por parámestro
     * @param diastole, dato de la Presión Arterial
     * @param siastole, dato de la Presión Arterial
     * @param pulsaciones, dato de la Presión Arterial
     * @param paciente de FSFB
     * @return String, NULL si todo esta bien, NO NULL, con la alerta de emergencia.
     */
    String registrarPresionArterial(Paciente paciente, int diastole, int siastole, int pulsaciones);

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
    
}
