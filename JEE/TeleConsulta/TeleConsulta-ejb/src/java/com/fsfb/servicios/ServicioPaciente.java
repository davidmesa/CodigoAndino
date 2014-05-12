/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.servicios;

import com.fsfb.bos.Paciente;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author davidmesa
 */
@Stateless(mappedName="ServicioPaciente", name="ServicioPaciente")
@LocalBean
public class ServicioPaciente implements ServicioPacienteLocal {
    
    @EJB
    private ServicioDashBoardLocal servicioDashBoard;
    @PersistenceContext(unitName = "TeleConsulta")
    private EntityManager em;
    
    
    public ServicioPaciente()
    {
    }

    @Override
    public void agregarOModificar(String user, String contrasena, double estatura, String fecha, String medico) {
        Collection<Paciente> pacientes;
        if(servicioDashBoard == null) System.out.print("Errorklfhalkfj");
        pacientes = servicioDashBoard.darPacientes(medico);
        for (Paciente paciente : pacientes) {
            if(paciente.getUsuario().equals(user))
            {
                if(contrasena != null && !contrasena.equals("")) paciente.setContrasena(contrasena);
                if(estatura != 0.0) paciente.setEstatura(estatura);
                if(fecha != null && !fecha.equals("")){
                    String [] partesFecha = fecha.split("/");
                    //AÑO, MES, DIA
                    Calendar cal = new GregorianCalendar(Integer.parseInt(partesFecha[0]), Integer.parseInt(partesFecha[1]), Integer.parseInt(partesFecha[2]));
                    Date date = cal.getTime();
                    paciente.setFechaNacimiento(date);
                }
                persist(paciente);
                return;
            }
        }
        Date date = null;
        if(fecha != null && !fecha.equals("")){
                    String [] partesFecha = fecha.split("/");
                    //AÑO, MES, DIA
                    Calendar cal = new GregorianCalendar(Integer.parseInt(partesFecha[0]), Integer.parseInt(partesFecha[1]), Integer.parseInt(partesFecha[2]));
                    date = cal.getTime();
        }
        Paciente nuevo = new Paciente(user, contrasena, (int)estatura, date);
        nuevo.setMedico(servicioDashBoard.darMedico());
        persist(nuevo);
        
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
