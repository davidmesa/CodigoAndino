/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.bos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;

/**
 * Clase Medico
 * @author davidmesa
 */
@Entity
public class Paciente implements Serializable {
    
    //--------------------------------------------------------------------------
    //  Atributos
    //--------------------------------------------------------------------------

    /**
     * Nombre de usuario del paciente
     */
    @Id
    private String usuario;
    
    /**
     * Contrasena del paciente
     */
    private String contrasena;
    

    /**
     * Estatura del paciente, en centimetros.
     */
    private double estatura;
    
    /**
     * Fecha en la que nacio el paciente
     */
    @Temporal(TemporalType.DATE)
    private Date fecha_nacimiento;

    /**
     * Registro de IMC's
     */
    private ArrayList<ReporteIMC> reportesIMC;
    
    /**
     * Registros de Presión Arterial
     */
    private ArrayList<ReportePresionArterial> reportesPresionArterial;
    
    //--------------------------------------------------------------------------
    //  Constructor
    //--------------------------------------------------------------------------

    /**
     * Constructor sin argumentos (para JPA)
     */
    public Paciente() {
        
    }
    
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
        
        reportesIMC = new ArrayList<ReporteIMC>();
        reportesPresionArterial = new ArrayList<ReportePresionArterial>();
    }
    
    //--------------------------------------------------------------------------
    //  Métodos
    //--------------------------------------------------------------------------

    /**
     * Método que hace un registor de IMC al Paciente
     * @param peso, del Paciente al momento de hacer la medida
     * @param altura, puede ser -1, que indica que no se tomo, o !=-1 que indica una actualización
     * @return Reporte de IMC generado
     */
    public ReporteIMC registarIMC(double peso, double altura)
    {
        Date tiempo=new Date();
        ReporteIMC reporte=new ReporteIMC(this, peso, altura, tiempo);
        reportesIMC.add(reporte);
        return reporte;
    }
    
    /**
     * Método que hace un registro de Presión Arterial al Paciente
     * @param diastole, dato de presión arterial
     * @param siastole, dato de presión arterial
     * @param pulsaciones, dato de presión arterial
     * @return Reporte generado para el paciente
     */
    public ReportePresionArterial registarPresionArterial(int diastole, int siastole, int pulsaciones)
    {
        Date tiempo=new Date();
        ReportePresionArterial reporte=new ReportePresionArterial(this, diastole, siastole, pulsaciones, tiempo);
        reportesPresionArterial.add(reporte);
        return reporte;
    }
    
    /**
     * Retorna la Lista de Reportes de Presión Arterial
     * @return ArrayList, de objeto tipo ReportePresionArterial
     */
    public ArrayList<ReportePresionArterial> darReportesPresionArterial()
    {
        return reportesPresionArterial;
    }
    
    /**
     * Retorna la lista de Reportes IMC del Paciente
     * @return ArrayList, de objeto tipo ReporteIMC
     */
    public ArrayList<ReporteIMC> darReportesIMC()
    {
        return reportesIMC;
    }

    //--------------------------------------------------------------------------
    //  Getters and Setters
    //--------------------------------------------------------------------------
    
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
    public double getEstatura()
    {
        return estatura;
    }
    
    /**
     * Cambia la estatura por una nueva
     * @param estatura nuevo valor para estatura
     */
    public void setEstatura(double estatura)
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
    
    public String darUltimaFechaIMC()
    {
        if(!reportesIMC.isEmpty())
        {
            Date fecha = reportesIMC.get(reportesIMC.size()-1).getFechaReporte();
            return fecha.toString();
        }
        return "Now";
    }
    
    public String darUltimaFechaTension()
    {
        if(!reportesPresionArterial.isEmpty())
        {
            Date fecha;
            fecha = reportesPresionArterial.get(reportesPresionArterial.size()-1).getFechaReporte();
            return fecha.toString();
        }
        return "Now";
    }
}
