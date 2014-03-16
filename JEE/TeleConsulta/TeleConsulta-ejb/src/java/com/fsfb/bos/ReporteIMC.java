/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.bos;

import java.util.Date;

/**
 *
 * @author davidmesa
 */
public class ReporteIMC {
    
    /**
<<<<<<< HEAD
     * Atributo que modela el paciente al que pertence el reporte
     */
    private Paciente paciente;
    
    /**
     * Peso de la pèrson en kilogtramos
     */
    private double peso;
    
    /**
     * Altura del paciente
     */
    private double altura;
    
    /**
     * IMC del reporte del paciente
     */
    private double IMC;
    
    /**
     * Fecha del Reporte de IMC
     */
    private Date fechaReporte;
    
    public ReporteIMC(Paciente paramPaciente, double paramPeso, double paramAltura, Date fecha)
    {
        paciente=paramPaciente;
        peso=paramPeso;
        altura=paramAltura;
        IMC=peso/(altura*altura);
        fechaReporte=fecha;
    }

    /**
     * Retorna el IMC reportado
     * @return IMC reportado
     */
    public double getIMC() {
        return IMC;
    }

    /**
     * Retorna el Peso reportado
     * @return Peso reportado
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Retorna la Altura reportada
     * @return Altura reportada
     */
    public double getAltura() {
        return altura;
    }

    /**
     * Retorna la Fecha en la que se realizo el Reporte
     * @return Fecha Reporte
     */
    public Date getFechaReporte() {
        return fechaReporte;
    }

}
