/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.bos;

import java.util.ArrayList;
import java.util.Date;

/**
 * Clase Medico
 * @author davidmesa
 */
public class Paciente {
    
    /**
     * Nombre de usuario del paciente
     */
    private String usuario;
    
    /**
     * Contrasena del paciente
     */
    private String contrasena;
    

    /**
     * Estatura del paciente, en centimetros.
     */
    private int estatura;
    
    /**
     * Fecha en la que nacio el paciente
     */
    private Date fecha_nacimiento;

    /**
     * Registro de IMC's
     */
    private ArrayList<ReporteIMC> reportesIMC;
    
    /**
     * Registros de Presión Arterial
     */
    private ArrayList<ReportePresionArterial> reportesPresionArterial;
    
    /**
     * Constructor de la clase Paciente
     * @param usuario Nombre de usuario del paciente.
     * @param contrasena Contrasena del paciente.
     */
    public Paciente(String usuario, String contrasena, int estatura, Date fecha_nacimiento) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.estatura = estatura;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    /**
     * Get the value of usuario
     * @return the value of usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Set the value of usuario
     * @param usuario new value of usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Get the value of contrasena
     * @return the value of contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Set the value of contrasena
     * @param contrasena new value of contrasena
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     *Método que devuelve la estatura del médico
     */
    public int getEstatura()
    {
        return estatura;
    }
    
    /**
     * Cambia la estatura por una nueva
     * @param estatura nuevo valor para estatura
     */
    public void setEstatura(int estatura)
    {
        this.estatura = estatura;
    }
    
    /**
     * Devuelve la fecha de nacimiento del paciente
     * @return fecha de nacimiento del paciente
     */
    public Date getFechaNacimiento()
    {
        return fecha_nacimiento;
    }
    
    /**
     * Cambia la fecha de nacimiento por una nueva
     */
    public void setFechaNacimiento(Date fecha_nacimiento)
    {
        this.fecha_nacimiento = fecha_nacimiento;
    }
    
    /**
     * Método que calcula la edad del paciente y la retorna
     * @return la edad actual del paciente
     */
    public int calcularEdad()
    {
        Date hoy = new Date();
        if(fecha_nacimiento.getMonth()>hoy.getMonth())
        {
            return hoy.getYear()-fecha_nacimiento.getYear()-1;
        }
        else if(fecha_nacimiento.getMonth()<hoy.getMonth())
        {
            return hoy.getYear()-fecha_nacimiento.getYear();
        }
        else
        {
            if(fecha_nacimiento.getDate()>hoy.getDate())
            {
                return hoy.getYear()-fecha_nacimiento.getYear()-1;
            }
            else
            {
                return hoy.getYear()-fecha_nacimiento.getYear();
            }
        }
    }
}
