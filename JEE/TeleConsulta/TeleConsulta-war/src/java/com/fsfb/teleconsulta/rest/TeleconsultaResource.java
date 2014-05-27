/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.teleconsulta.rest;

import com.fsfb.bos.Paciente;
import com.fsfb.bos.ReporteIMC;
import com.fsfb.bos.ReportePresionArterial;
import com.fsfb.servicios.*;
import java.util.ArrayList;
import java.util.Date;
import javax.crypto.Cipher;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    
    private ServicioFSFB serFSFB;

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
    @GET
    @Path("tension")
    @Produces("application/json")
    public String registrarTension(@QueryParam("token") String token, @QueryParam("id") String id, 
    @QueryParam("diastole") int diastole, @QueryParam("siastole") int sistole, @QueryParam("pulso") int pulso) throws Exception {
        try {
            Paciente paciente = servicio.loginPaciente(token);
            return serFSFB.registrarPresionArterial(paciente, diastole, sistole, pulso);
        } catch (Exception ex) {
            return "{\"status\":\"error\", \"mensaje\":\""+ex.getMessage()+"\"}";
        }
    }
    
    @GET
    @Path("imc")
    @Produces("application/json")
    public String registrarIMC(@QueryParam("token") String token,
    @QueryParam("peso") String peso, @QueryParam("altura") int altura) throws Exception
    {
        try {
            Paciente paciente = servicio.loginPaciente(token);
            return serFSFB.registarIMC(paciente, Double.parseDouble(peso), altura);
        } catch (Exception e) {
            return "{\"status\":\"error\", \"mensaje\":\""+e.getMessage()+"\"}";
        }
    }
    
    @GET
    @Path("reportesimc")
    @Produces("application/json")
    public String reportesIMC(@QueryParam("token") String token) throws Exception
    {
        try {
            Paciente paciente = servicio.loginPaciente(token);
            ArrayList<ReporteIMC> arreglo=paciente.darReportesIMC();
            String aRetornar="&";
            for(int i=0; i<arreglo.size(); i++)
            {
                ReporteIMC imc=arreglo.get(i);
                Date fecha=imc.getFechaReporte();
                aRetornar=aRetornar+fecha.getDate()+"/"+fecha.getMonth()+"/"+(fecha.getYear()-100)+"-"+imc.getPeso()+"-"+imc.getAltura()+"&";
            }
            return "{\"status\":\"ok\", \"mensaje\":\""+aRetornar+"\"}";
        } catch (Exception e) {
            return "{\"status\":\"error\", \"mensaje\":\""+e.getMessage()+"\"}";
        }
    }

    @GET
    @Path("reportestension")
    @Produces("application/json")
    public String reportesTension(@QueryParam("token") String token) throws Exception
    {
        try {
            Paciente paciente = servicio.loginPaciente(token);
            ArrayList<ReportePresionArterial> arreglo=paciente.darReportesPresionArterial();
            String aRetornar="&";
            for(int i=0; i<arreglo.size(); i++)
            {
                ReportePresionArterial presion=arreglo.get(i);
                Date fecha=presion.getFechaReporte();
                aRetornar=aRetornar+fecha.getDate()+"/"+fecha.getMonth()+"/"+(fecha.getYear()-100)+"-"+presion.getSiastole()+"-"+presion.getDiastole()+"-"+presion.getPulsaciones()+"&";
            }
            return "{\"status\":\"ok\", \"mensaje\":\""+aRetornar+"\"}";
        } catch (Exception e) {
            return "{\"status\":\"error\", \"mensaje\":\""+e.getMessage()+"\"}";
        }
    }
    
    @GET
    @Path("auth")
    @Produces("application/json")
    public String auth(@QueryParam("id") String id, @QueryParam("password") String password) throws Exception {
        try {
            System.out.println("LLEGO a Pedir "+id+"-"+password);
            String token = serFSFB.darTokenDelPaciente(id, password);
            return "{\"status\":\"ok\", \"token\":\""+token+"\"}";
        } catch(Exception e) {
            return "{\"status\":\"error\", \"mensaje\":\""+e.getMessage()+"\"}";
        }
    }
    
    @POST
    @Path("getPublicKey")
    @Consumes("application/x-www-form-urlencoded")
    public String getPublicKey() {
        return serFSFB.getPublicKey();
    }
    
    @POST
    @Path("getSymmetricKey")
    @Consumes("application/x-www-form-urlencoded")
    public String getSymmetricKey() throws Exception {
        return serFSFB.getSymmetricKey();
    }
}
