/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.teleconsulta.rest;

import com.fsfb.bos.Paciente;
import com.fsfb.servicios.*;
import javax.crypto.Cipher;
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
    @POST
    @Path("tension")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public String registrarTension(@FormParam("token") String token, @FormParam("id") String id, 
    @FormParam("diastole") int diastole, @FormParam("sistole") int sistole, @FormParam("pulso") int pulso) throws Exception {
        try {
            Paciente paciente = servicio.loginPaciente(token);
            return serFSFB.darCifrado(serFSFB.registrarPresionArterial(paciente, diastole, sistole, pulso));
        } catch (Exception ex) {
            return serFSFB.darCifrado("{\"status\":\"error\", \"mensaje\":\""+ex.getMessage()+"\"}");
        }
    }
    
    @POST
    @Path("imc")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public String registrarIMC(@FormParam("token") String token,
    @FormParam("peso") double peso, @FormParam("altura") int altura) throws Exception
    {
        try {
            Paciente paciente = servicio.loginPaciente(token);
            return serFSFB.darCifrado(serFSFB.registarIMC(paciente, peso, altura));
        } catch (Exception e) {
            return serFSFB.darCifrado("{\"status\":\"error\", \"mensaje\":\""+e.getMessage()+"\"}");
        }
    }

    @POST
    @Path("auth")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public String auth(@FormParam("id") String id, @FormParam("password") String password) throws Exception {
        try {
            String token = serFSFB.darTokenDelPaciente(id, password);
            return serFSFB.darCifrado("{\"token\":\""+token+"\"}");
        } catch(Exception e) {
            return serFSFB.darCifrado("{\"status\":\"error\", \"mensaje\":\""+e.getMessage()+"\"}");
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
