/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.bos;

import java.util.Date;

/**
 * Reporte de Presión Arterial
 * @author Cristian
 */
public class ReportePresionArterial {

    //-------------------------------------------------------------------------
    //  Atributos
    //-------------------------------------------------------------------------
    /**
     * Diástole de la Presión Arterial
     */
    private int diastole;
    
    /**
     * Siástole de la Presión Arterial
     */
    private int siastole;
    
    /**
     * Pulsaciones de la Presión Arterial
     */
    private int pulsaciones;
    
    /**
     * Fecha de realización del reporte
     */
    private Date fechaReporte;
    
    /**
     * Paciente asociado al Registro
     */
    private Paciente paciente;
    
    //-------------------------------------------------------------------------
    //  Constructor
    //-------------------------------------------------------------------------
    public ReportePresionArterial(Paciente paramPaciente, int paramDiastole, int paramSiastole, int paramPulsaciones, Date fecha) {
        paciente=paramPaciente;
        diastole=paramDiastole;
        siastole=paramSiastole;
        pulsaciones=paramPulsaciones;
        fechaReporte=fecha;
    }
    
    //-------------------------------------------------------------------------
    //  Getters and Setters
    //-------------------------------------------------------------------------
    /**
     * Retorna el valor de la Diastole
     * @return diastole de la presión arterial
     */
    public int getDiastole() {
        return diastole;
    }

    /**
     * Retorna el valor de la Siastole
     * @return siastole de la presión arterial
     */
    public int getSiastole() {
        return siastole;
    }

    /**
     * Retorna las pulsaciones
     * @return pulsaciones de la presión arterial
     */
    public int getPulsaciones() {
        return pulsaciones;
    }

    /**
     * Retorna la fecha del reporte
     * @return Date, que indica la hora "exacta" de realización del examen
     */
    public Date getFechaReporte() {
        return fechaReporte;
    }
    
    public String getFechaString()
    {
        return fechaReporte.toString();
    }
}
