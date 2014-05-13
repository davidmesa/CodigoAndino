/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.teleconsulta.rest;

import com.fsfb.bos.Paciente;
import com.fsfb.bos.ReporteIMC;
import com.fsfb.bos.ReportePresionArterial;
import com.fsfb.servicios.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author davidmesa
 */
@Path("/movil")
public class TeleconsultaResource {

    @Context
    private UriInfo context;
    
    private ServicioLoginLocal servicio;
    
    private ServicioFSFBLocal serFSFB;

    /**
     * Creates a new instance of TeleconsultaResource
     */
    public TeleconsultaResource() {
        servicio = new ServicioLogin();
        serFSFB = ServicioFSFB.darInstancia();
    }
    
    /**
     * 
     * @param token
     * @param id
     * @param diastole
     * @param sistole
     * @param pulso
     * @return 
     */
    @POST
    @Path("tension")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public String registrarTension(@FormParam("token") String token, @FormParam("id") String id, 
    @FormParam("diastole") int diastole, @FormParam("sistole") int sistole, @FormParam("pulso") int pulso, @FormParam("fecha") String fecha) {
        try {
            if(fecha.equals(hashMD5(token+"+"+diastole+"-"+sistole+"*"+pulso)))
            {
                Paciente paciente = servicio.loginPaciente(token);
                return serFSFB.registrarPresionArterial(paciente, diastole, sistole, pulso);
            }
            else
            {
                return "{\"status\":\"error\", \"mensaje\":\""+"Los datos fueron adulterados"+"\"}";
            }
        } catch (Exception ex) {
            return "{\"status\":\"error\", \"mensaje\":\""+ex.getMessage()+"\"}";
        }
    }
    
    @POST
    @Path("imc")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public  String registrarIMC(@FormParam("token") String token,
    @FormParam("peso") double peso, @FormParam("altura") int altura, @FormParam("fecha") String fecha)
    {
        try {
            if(fecha.equals(hashMD5(token+"+"+peso+"-"+altura)))
            {
                Paciente paciente = servicio.loginPaciente(token);
                return serFSFB.registarIMC(paciente, peso, altura);
            }
            else
            {
                return "{\"status\":\"error\", \"mensaje\":\""+"Los datos fueron adulterados"+"\"}";
            }
        } catch (Exception e) {
            return "{\"status\":\"error\", \"mensaje\":\""+e.getMessage()+"\"}";
        }
    }

    @POST
    @Path("auth")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public String auth(@FormParam("id") String id, @FormParam("password") String password, @FormParam("fecha") String fecha) {
        try {
            if(fecha.equals(hashMD5(id+"+"+password)))
            {
                String token = serFSFB.darTokenDelPaciente(id, password);
                return "{\"token\":\""+token+"\"}";
            }
            else
            {
                return "{\"status\":\"error\", \"mensaje\":\""+"Los datos fueron adulterados"+"\"}";
            }
        } catch(Exception e) {
            return "{\"status\":\"error\", \"mensaje\":\""+e.getMessage()+"\"}";
        }
    }
    
    @POST
    @Path("reportesimc")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public  String darReportesIMC(@FormParam("token") String token)
    {
        try {
            List<ReporteIMC> reportes;
            reportes = serFSFB.darPacientePorToken(token).darReportesIMC();
            String mensaje="*";
            for(ReporteIMC r: reportes)
            {
                mensaje=mensaje+r.getFechaString()+"-"+r.getFechaReporte()+"-"+r.getPeso()+"-"+r.getAltura()+"-"+r.getIMC()+"*";
            }
            return "{\"mensaje\":\""+mensaje+"\"}";
            
        } catch (Exception e) {
            return "{\"status\":\"error\", \"mensaje\":\""+e.getMessage()+"\"}";
        }
    }
    
    @POST
    @Path("reportespresionarterial")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public  String darReportesPresion(@FormParam("token") String token)
    {
        try {
            List<ReportePresionArterial> reportes;
            reportes = serFSFB.darPacientePorToken(token).darReportesPresionArterial();
            String mensaje="*";
            for(ReportePresionArterial r: reportes)
            {
                mensaje=mensaje+r.getFechaString()+"-"+r.getFechaReporte()+"-"+r.getSistole()+"-"+r.getDiastole()+"-"+r.getPulsaciones()+"*";
            }
            return "{\"mensaje\":\""+mensaje+"\"}";
            
        } catch (Exception e) {
            return "{\"status\":\"error\", \"mensaje\":\""+e.getMessage()+"\"}";
        }
    }
    
    
    public String hashMD5(String aResumir)
	{
            MessageDigest md;
		try {
                    md = MessageDigest.getInstance("MD5");
	            md.update(aResumir.getBytes());
	            byte[] bytes = md.digest();
	            StringBuilder sb = new StringBuilder();
	            for(int i=0; i< bytes.length ;i++)
	            {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            System.out.println(sb.toString());
	            return sb.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	}
}
