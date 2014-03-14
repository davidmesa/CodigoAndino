/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.teleconsulta.rest;

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

    /**
     * Creates a new instance of TeleconsultaResource
     */
    public TeleconsultaResource() {
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
        return "{\"mensaje\":\"hola mundo\", \"token\":\""+token+"\"}";
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

}
