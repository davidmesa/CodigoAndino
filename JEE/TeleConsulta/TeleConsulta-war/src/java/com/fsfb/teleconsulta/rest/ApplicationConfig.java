/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fsfb.teleconsulta.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Aplication config para Rest
 * @author davidmesa
 */

@ApplicationPath("/cliente")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(TeleconsultaResource.class);
        return classes;
    }
}