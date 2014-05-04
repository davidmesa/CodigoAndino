/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.bos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Reporte de Presión Arterial
 * @author Cristian
 */
@Entity
@Table(name = "presion_arterial")
public class ReportePresionArterial implements Serializable {

    //-------------------------------------------------------------------------
    //  Atributos
    //-------------------------------------------------------------------------
    
    /**
     * Id único del reporte
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    /**
     * Diástole de la Presión Arterial
     */
    private int diastole;
    
    /**
     * Sistole de la Presión Arterial
     */
    private int sistole;
    
    /**
     * Pulsaciones de la Presión Arterial
     */
    private int pulsaciones;
    
    /**
     * Fecha de realización del reporte
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private Date fechaReporte;
    
    /**
     * Paciente asociado al Registro
     */
    @ManyToOne
    private Paciente paciente;
    
    //-------------------------------------------------------------------------
    //  Constructor
    //-------------------------------------------------------------------------
    
    /**
     * Constructor vacío (para JPA)
     */
    public ReportePresionArterial() {}
    
    public ReportePresionArterial(Paciente paramPaciente, int paramDiastole, int paramSiastole, int paramPulsaciones, Date fecha) {
        paciente=paramPaciente;
        diastole=paramDiastole;
        sistole=paramSiastole;
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
     * @return sistole de la presión arterial
     */
    public int getSistole() {
        return sistole;
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
