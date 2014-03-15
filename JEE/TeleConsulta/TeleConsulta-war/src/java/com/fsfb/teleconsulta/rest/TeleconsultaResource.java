/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.teleconsulta.rest;

import com.fsfb.bos.Paciente;
import com.fsfb.servicios.ServicioLogin;
import com.fsfb.servicios.ServicioLoginLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.message.AuthException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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

    /**
     * Creates a new instance of TeleconsultaResource
     */
    public TeleconsultaResource() {
        servicio = new ServicioLogin();
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
    @FormParam("diastole") double diastole, @FormParam("sistole") double sistole, @FormParam("pulso") double pulso) {
        try {
            Paciente paciente = servicio.loginPaciente(token);
            return "{\"mensaje\":\"hola mundo\", \"token\":\""+token+"\"}";
        } catch (AuthException ex) {
            return "{\"mensaje\":\"error\", \"mensaje\":\""+ex.getMessage()+"\"}";
        }
    }
    
    @POST
    @Path("imc")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public  String registrarIMC(@FormParam("token") String token, @FormParam("id") String id,
    @FormParam("peso") double peso)
    {
        return "";
    }

    @POST
    @Path("auth")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public String auth(@FormParam("id") String id, @FormParam("password") String password) {
        String token = "";
        if(id.equals("rolon") && password.equals("1234")) {
            token = "welcome";
        }
        return "{\"token\":\""+token+"\"}";
    }
}
